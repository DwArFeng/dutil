package com.dwarfeng.dutil.task.sdk.composition;

import com.dwarfeng.dutil.task.sdk.Tasks;
import com.dwarfeng.dutil.task.sdk.executor.TaskExecutors;
import com.dwarfeng.dutil.task.stack.Task;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link TaskCompositions} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class TaskCompositionsTest {

    @Test
    public void testSequence() throws Exception {
        List<Task<Integer>> tasks = List.of(
                Tasks.fromSupplier(() -> 1),
                Tasks.fromSupplier(() -> 2),
                Tasks.fromSupplier(() -> 3)
        );

        try (TaskExecutor executor = TaskExecutors.virtualThreads()) {
            assertEquals(List.of(1, 2, 3), executor.submit(TaskCompositions.sequence(tasks)).get());
        }
    }

    @Test
    public void testParallelKeepsDefinitionOrder() throws Exception {
        List<Task<Integer>> tasks = List.of(
                context -> {
                    Thread.sleep(50);
                    return 1;
                },
                Tasks.fromSupplier(() -> 2)
        );

        try (TaskExecutor childExecutor = TaskExecutors.virtualThreads();
             TaskExecutor parentExecutor = TaskExecutors.virtualThreads()) {
            assertEquals(
                    List.of(1, 2),
                    parentExecutor.submit(TaskCompositions.parallel(tasks, childExecutor)).get()
            );
        }
    }
}
