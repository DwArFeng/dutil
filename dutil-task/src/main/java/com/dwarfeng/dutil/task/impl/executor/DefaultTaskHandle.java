package com.dwarfeng.dutil.task.impl.executor;

import com.dwarfeng.dutil.task.internal.executor.DefaultTaskContext;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessageKey;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessages;
import com.dwarfeng.dutil.task.stack.Task;
import com.dwarfeng.dutil.task.stack.TaskProgress;
import com.dwarfeng.dutil.task.stack.TaskResult;
import com.dwarfeng.dutil.task.stack.TaskState;
import com.dwarfeng.dutil.task.stack.event.TaskEvent;
import com.dwarfeng.dutil.task.stack.event.TaskListener;
import com.dwarfeng.dutil.task.stack.event.TaskSubscription;
import com.dwarfeng.dutil.task.stack.executor.TaskHandle;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 默认单次任务执行句柄。
 *
 * @param <T> 任务返回值类型。
 * @author DwArFeng
 * @since 2.0.0
 */
public final class DefaultTaskHandle<T> implements TaskHandle<T> {

    private final AtomicReference<TaskState> state = new AtomicReference<>(TaskState.CREATED);
    private final AtomicReference<Instant> startedAt = new AtomicReference<>();
    private final AtomicReference<TaskProgress> progress = new AtomicReference<>(TaskProgress.initial());
    private final AtomicReference<TaskResult<T>> result = new AtomicReference<>();
    private final AtomicBoolean cancellationRequested = new AtomicBoolean();
    private final CopyOnWriteArrayList<TaskListener<T>> listeners = new CopyOnWriteArrayList<>();
    private final CompletableFuture<T> valueFuture = new CompletableFuture<>();
    private final CompletableFuture<TaskResult<T>> resultFuture = new CompletableFuture<>();
    private final String name;

    private volatile Future<?> executionFuture;

    DefaultTaskHandle(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    void bind(Future<?> future) {
        executionFuture = Objects.requireNonNull(future, "future");
        if (cancellationRequested.get()) {
            future.cancel(true);
        }
    }

    void execute(Task<T> task) {
        if (!state.compareAndSet(TaskState.CREATED, TaskState.RUNNING)) {
            return;
        }
        Instant executionStartedAt = Instant.now();
        startedAt.set(executionStartedAt);
        Thread currentThread = Thread.currentThread();
        String originalThreadName = currentThread.getName();
        currentThread.setName(name);
        fire(TaskEvent.Type.STARTED, null);
        try {
            T value = task.execute(new DefaultTaskContext(cancellationRequested::get, this::updateProgress));
            if (cancellationRequested.get()) {
                completeCancelled(executionStartedAt);
            } else {
                complete(TaskState.SUCCEEDED, value, null, executionStartedAt);
            }
        } catch (CancellationException exception) {
            cancellationRequested.set(true);
            completeCancelled(executionStartedAt);
        } catch (Throwable throwable) {
            if (cancellationRequested.get()) {
                completeCancelled(executionStartedAt);
            } else {
                complete(TaskState.FAILED, null, throwable, executionStartedAt);
            }
        } finally {
            currentThread.setName(originalThreadName);
        }
    }

    private void updateProgress(double fraction, String message) {
        if (state.get() != TaskState.RUNNING) {
            return;
        }
        TaskProgress current = new TaskProgress(fraction, message, Instant.now());
        progress.set(current);
        if (state.get() == TaskState.RUNNING) {
            fire(TaskEvent.Type.PROGRESS_CHANGED, null);
        }
    }

    private void completeCancelled(Instant startedAt) {
        complete(TaskState.CANCELLED, null, null, startedAt);
    }

    private boolean complete(TaskState terminalState, T value, Throwable failure, Instant executionStartedAt) {
        TaskResult<T> terminalResult = new TaskResult<>(
                terminalState, value, failure, executionStartedAt, Instant.now()
        );
        if (!result.compareAndSet(null, terminalResult)) {
            return false;
        }
        state.set(terminalState);
        switch (terminalState) {
            case SUCCEEDED -> valueFuture.complete(value);
            case FAILED, TIMED_OUT -> valueFuture.completeExceptionally(failure);
            case CANCELLED -> valueFuture.cancel(false);
            default -> throw new IllegalStateException(terminalState.name());
        }
        resultFuture.complete(terminalResult);
        fire(TaskEvent.Type.COMPLETED, terminalResult);
        return true;
    }

    private void fire(TaskEvent.Type type, TaskResult<T> terminalResult) {
        TaskEvent<T> event = new TaskEvent<>(type, state.get(), progress.get(), terminalResult, Instant.now());
        for (TaskListener<T> listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (RuntimeException ignored) {
            }
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public TaskState taskState() {
        return state.get();
    }

    @Override
    public TaskProgress progress() {
        return progress.get();
    }

    @Override
    public Optional<TaskResult<T>> result() {
        return Optional.ofNullable(result.get());
    }

    @Override
    public CompletionStage<TaskResult<T>> completion() {
        return resultFuture.minimalCompletionStage();
    }

    @Override
    public TaskSubscription subscribe(TaskListener<T> listener) {
        Objects.requireNonNull(listener, "listener");
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!cancellationRequested.compareAndSet(false, true)) {
            return false;
        }
        Instant executionStartedAt = Objects.requireNonNullElseGet(startedAt.get(), Instant::now);
        if (!complete(TaskState.CANCELLED, null, null, executionStartedAt)) {
            return false;
        }
        Future<?> currentFuture = executionFuture;
        if (currentFuture != null) {
            currentFuture.cancel(mayInterruptIfRunning);
        }
        return true;
    }

    void timeout(Duration timeout) {
        if (!cancellationRequested.compareAndSet(false, true)) {
            return;
        }
        TimeoutException failure = new TimeoutException(
                TaskMessages.message(TaskMessageKey.EXECUTION_TIMED_OUT, timeout)
        );
        Instant executionStartedAt = Objects.requireNonNullElseGet(startedAt.get(), Instant::now);
        if (!complete(TaskState.TIMED_OUT, null, failure, executionStartedAt)) {
            return;
        }
        Future<?> currentFuture = executionFuture;
        if (currentFuture != null) {
            currentFuture.cancel(true);
        }
    }

    @Override
    public boolean isCancelled() {
        return state.get() == TaskState.CANCELLED || valueFuture.isCancelled();
    }

    @Override
    public boolean isDone() {
        return valueFuture.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return valueFuture.get();
    }

    @Override
    public T get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException,
            TimeoutException {
        return valueFuture.get(timeout, unit);
    }
}
