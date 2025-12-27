package com.dwarfeng.dutil.basic.threads;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.str.Tag;

import java.util.Objects;

/**
 * 标签运行器。
 *
 * <p>
 * 该工具类对 {@link Runnable} 和 {@link Tag} 进行封装，使得一个可运行对象拥有名称和描述。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class TagRunnable implements Runnable, Tag {

    /**
     * 内部 Runnable
     */
    protected final Runnable runnable;
    /**
     * 内部 Tag
     */
    protected final Tag tag;

    /**
     * 生成一个新的标签运行器。
     *
     * @param runnable 指定的可运行对象。
     * @param tag      指定的标签。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public TagRunnable(Runnable runnable, Tag tag) {
        Objects.requireNonNull(runnable, DwarfUtil.getExceptionString(ExceptionStringKey.TAGRUNNER_0));
        Objects.requireNonNull(tag, DwarfUtil.getExceptionString(ExceptionStringKey.TAGRUNNER_1));
        this.runnable = runnable;
        this.tag = tag;
    }

    /*
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        runnable.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return tag.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return tag.getName();
    }
}
