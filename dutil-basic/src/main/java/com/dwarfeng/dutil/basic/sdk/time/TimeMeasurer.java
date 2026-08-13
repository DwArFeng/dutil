package com.dwarfeng.dutil.basic.sdk.time;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;

import com.dwarfeng.dutil.basic.sdk.number.NumberUtil;
import com.dwarfeng.dutil.basic.stack.number.NumberValue;
import com.dwarfeng.dutil.basic.stack.number.unit.Time;

/**
 * 计时器。
 *
 * <p>
 * 该计时器类似于现实中的秒表，可以用来测量一段有限的时间。<br>
 * 该计时器拥有 {@link #start()} 和 {@link #stop()} 个方法，用来控制计时器开始计时和结束计时。
 * 在不同的时间调用这两个方法，就能记录调用这两个方法的时间之差，从而记录这段时间。<br>
 * 计时器的单位是纳秒，在不同平台上，精度可能会稍微有些差别，因此只能当做粗略的计时器而使用，
 * 并且该计时器会受到系统时间的改变造成的影响。<br>
 * 由于长整形变量的存储限制，该计时器只能提供大约 292 年的计时长度。
 *
 * <p>
 * 该计时器线程安全，可以通过任何一个线程启动，并且被任何一个线程终止。但是无论如何，
 * 整个计时器只能启动一次并且在其后只能停止一次 - 也就是说这个计时器是一次性的，一次计时之后，需要新的实例进行下一次计时。
 *
 * <p>
 * 示例代码：
 * <blockquote><pre>
 * public static void main(String[] args) {
 *     // 定义并开启计时器。
 *     TimeMeasurer tm = new TimeMeasurer();
 *     tm.start();
 *     // 执行某些任务。
 *     executeSomeTask();
 *     // 停止计时器。
 *     tm.stop();
 *     // 输出日志。
 *     System.out.printf("任务完成, 用时 %d 毫秒%n", tm.getTimeMs());
 * }
 * </pre></blockquote>
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class TimeMeasurer {

    /**
     * 计时器的状态。
     *
     * @author DwArFeng
     * @since 0.0.2-beta
     */
    protected enum Status {

        /**
         * 没有启动。
         */
        NOT_STARTED,

        /**
         * 正在计时。
         */
        TIMING,

        /**
         * 计时结束。
         */
        STOPPED,
    }

    /**
     * 计时器的状态.
     */
    private volatile Status status = Status.NOT_STARTED;

    /**
     * 时间统计(纳秒).
     */
    private volatile long timeSpentNanos;

    /**
     * 生成一个代码计时器。
     */
    public TimeMeasurer() {
    }

    /**
     * 获取该计时器的计时状态。
     *
     * @return 该计时器的计时状态。
     */
    private Status getStatus() {
        return this.status;
    }

    /**
     * 获取计时器是否还未启动。
     *
     * @return 计时器是否还未启动。
     */
    public boolean isNotStarted() {
        return status == Status.NOT_STARTED;
    }

    /**
     * 获取计时器是否正在计时。
     *
     * @return 计时器是否正在计时。
     */
    public boolean isTiming() {
        return status == Status.TIMING;
    }

    /**
     * 获取计时器是否已经停止计时。
     *
     * @return 计时器是否已经停止计时。
     */
    public boolean isStopped() {
        return status == Status.STOPPED;
    }

    /**
     * 开始计时。
     *
     * @throws IllegalStateException 计时器已经开始计时或者已经计时结束。
     */
    public synchronized void start() {
        if (!isNotStarted()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.TIME_MEASURER_START_STATE_INVALID));
        }
        timeSpentNanos = -System.nanoTime();
        status = Status.TIMING;
    }

    /**
     * 停止计时。
     *
     * @throws IllegalStateException 计时器还未开始计时或者已经计时结束。
     */
    public synchronized void stop() {
        if (!isTiming()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.TIME_MEASURER_NOT_RUNNING));
        }
        timeSpentNanos += System.nanoTime();
        status = Status.STOPPED;
    }

    /**
     * 获取该计时器的时间，以纳秒为单位。
     *
     * @return 该代码计时器的时间。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public long getTimeNs() {
        if (!isStopped()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.TIME_MEASURER_NOT_STOPPED));
        }
        return timeSpentNanos;
    }

    /**
     * 获取该计时器的时间，以毫秒为单位，并且元整为整数。
     *
     * @return 该代码计时器的时间。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public long getTimeMs() {
        if (!isStopped()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.TIME_MEASURER_NOT_STOPPED));
        }
        return timeSpentNanos / 1000000;
    }

    /**
     * 获取该计时器的时间，以秒为单位，并且元整为整数。
     *
     * @return 该代码计时器的时间。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public long getTimeSec() {
        if (!isStopped()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.TIME_MEASURER_NOT_STOPPED));
        }
        return timeSpentNanos / 1000000000;
    }

    /**
     * 获取该计时器的时间，以指定的等效权重为作为单位，返回双精度浮点值。
     *
     * <p>
     * 等效权重的取值方法为： <code> 86400000000000 / x，其中 x 为 1 指定的单位对应的毫秒数。 </code><br>
     * 有关于时间单位，请参阅 {@link Time} 其中包含了大部分常用的时间单位。
     *
     * @param valuable 指定的单位的等效权重。
     * @return 该代码计时器的时间。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public double getTime(NumberValue valuable) {
        if (!isStopped()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.TIME_MEASURER_NOT_STOPPED));
        }
        return NumberUtil.unitTrans(timeSpentNanos, Time.NS, valuable).doubleValue();
    }

    /**
     * 返回计时器预设的计时的格式化字符串，单位为纳秒。
     *
     * @return 预设的格式化字符串。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public String formatStringNs() {
        if (!isStopped()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.TIME_MEASURER_NOT_STOPPED));
        }
        return BasicMessages.message(BasicMessageKey.TIME_MEASURER_TOTAL_NANOSECONDS, getTimeNs());
    }

    /**
     * 返回计时器预设的计时的格式化字符串，单位为毫秒。
     *
     * @return 预设的格式化字符串。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public String formatStringMs() {
        if (!isStopped()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.TIME_MEASURER_NOT_STOPPED));
        }
        return BasicMessages.message(BasicMessageKey.TIME_MEASURER_TOTAL_MILLISECONDS, getTimeMs());
    }

    /**
     * 返回计时器预设的计时的格式化字符串，单位为秒。
     *
     * @return 预设的格式化字符串。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public String formatStringSec() {
        if (!isStopped()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.TIME_MEASURER_NOT_STOPPED));
        }
        return BasicMessages.message(BasicMessageKey.TIME_MEASURER_TOTAL_SECONDS, getTimeSec());
    }

    @Override
    public String toString() {
        return "TimeMeasurer{" +
                "status=" + status +
                ", timeSpentNanos=" + timeSpentNanos +
                '}';
    }
}
