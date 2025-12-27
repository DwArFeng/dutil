package com.dwarfeng.dutil.basic.mea;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.num.NumberValue;
import com.dwarfeng.dutil.basic.num.unit.Time;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class TimeMeasurer {

    /**
     * 计时器的状态。
     *
     * @author DwArFeng
     * @since 0.0.2-beta
     */
    protected enum Status {
        /**
         * 没有启动
         */
        NOTSTART,
        /**
         * 正在计时
         */
        TIMING,
        /**
         * 计时结束
         */
        STOPED
    }

    /**
     * 计时器的状态
     */
    protected Status status = Status.NOTSTART;
    /**
     * 时间统计(纳秒)
     */
    private long l;
    /**
     * 同步锁
     */
    protected final Lock lock = new ReentrantLock();

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
    protected Status getStatus() {
        return this.status;
    }

    /**
     * 获取计时器是否还未启动。
     *
     * @return 计时器是否还未启动。
     */
    public boolean isNotStart() {
        return status == Status.NOTSTART;
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
    public boolean isStoped() {
        return status == Status.STOPED;
    }

    /**
     * 开始计时。
     *
     * @throws IllegalStateException 计时器已经开始计时或者已经计时结束。
     */
    public void start() {
        lock.lock();
        try {
            if (!isNotStart()) {
                throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_0));
            }
            l = -System.nanoTime();
            this.status = Status.TIMING;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 停止计时。
     *
     * @throws IllegalStateException 计时器还未开始计时或者已经计时结束。
     */
    public void stop() {
        lock.lock();
        try {
            if (!isTiming()) {
                throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_1));
            }
            l += System.nanoTime();
            this.status = Status.STOPED;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取该计时器的时间，以纳秒为单位。
     *
     * @return 该代码计时器的时间。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public long getTimeNs() {
        if (!isStoped()) {
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_2));
        }
        return l;
    }

    /**
     * 获取该计时器的时间，以毫秒为单位，并且元整为整数。
     *
     * @return 该代码计时器的时间。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public long getTimeMs() {
        if (!isStoped()) {
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_2));
        }
        return l / 1000000;
    }

    /**
     * 获取该计时器的时间，以秒为单位，并且元整为整数。
     *
     * @return 该代码计时器的时间。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public long getTimeSec() {
        if (!isStoped()) {
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_2));
        }
        return l / 1000000000;
    }

    /**
     * 获取该计时器的时间，以指定的等效权重为作为单位，返回双精度浮点值。
     *
     * <p>
     * 等效权重的取值方法为： <code> 86400000000000 / x，其中 x 为 1 指定的单位对应的毫秒数。 </code><br>
     * 有关于时间单位，请参阅 {@link Time} 其中包含了大部分常用的时间单位。
     *
     * @param valueable 指定的单位的等效权重。
     * @return 该代码计时器的时间。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public double getTime(NumberValue valueable) {
        if (!isStoped()) {
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_2));
        }
        return NumberUtil.unitTrans(l, Time.NS, valueable).doubleValue();
    }

    /**
     * 返回计时器预设的计时的格式化字符串，单位为纳秒。
     *
     * @return 预设的格式化字符串。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public String formatStringNs() {
        if (!isStoped()) {
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_2));
        }
        return String.format(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_3), getTimeNs());
    }

    /**
     * 返回计时器预设的计时的格式化字符串，单位为毫秒。
     *
     * @return 预设的格式化字符串。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public String formatStringMs() {
        if (!isStoped()) {
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_2));
        }
        return String.format(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_4), getTimeMs());
    }

    /**
     * 返回计时器预设的计时的格式化字符串，单位为秒。
     *
     * @return 预设的格式化字符串。
     * @throws IllegalStateException 计时器还未计时结束。
     */
    public String formatStringSec() {
        if (!isStoped()) {
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_2));
        }
        return String.format(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEMEASURER_5), getTimeSec());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TimeMeasure [status = " + status.toString() + ", l = " + l +
                "]";
    }
}
