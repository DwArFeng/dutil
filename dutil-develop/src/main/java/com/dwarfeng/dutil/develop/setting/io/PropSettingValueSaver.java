package com.dwarfeng.dutil.develop.setting.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.dutil.basic.io.StreamSaver;
import com.dwarfeng.dutil.basic.struct.OrderedProperties;
import com.dwarfeng.dutil.develop.setting.SettingHandler;

import java.io.OutputStream;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * Properties 配置值保存器。
 *
 * <p>
 * 保存指定配置处理器中配置键对应的当前值到 properties 文件。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class PropSettingValueSaver extends StreamSaver<SettingHandler> {

    private final boolean ordered;

    private final boolean writeFlag = false;

    /**
     * 生成一个新的 Properties 配置值保存器。
     *
     * @param out 指定的输出流。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public PropSettingValueSaver(OutputStream out) {
        this(out, false);
    }

    /**
     * 生成一个新的 Properties 配置值保存器。
     *
     * @param out     指定的输出流。
     * @param ordered 是否在保存时保持顺序。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public PropSettingValueSaver(OutputStream out, boolean ordered) {
        super(out);
        this.ordered = ordered;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(SettingHandler handler) throws SaveFailedException, IllegalStateException {
        if (writeFlag)
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.PROPSETTINGVALUESAVER_0));

        Objects.requireNonNull(handler, DwarfUtil.getExceptionString(ExceptionStringKey.PROPSETTINGVALUESAVER_1));

        Properties properties = genProperties(ordered);
        for (SettingHandler.Entry entry : handler.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getCurrentValue());
        }
        try {
            properties.store(out, null);
        } catch (Exception e) {
            throw new SaveFailedException(e.getMessage(), e.getCause());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SaveFailedException> countinuousSave(SettingHandler handler) throws IllegalStateException {
        if (writeFlag)
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.PROPSETTINGVALUESAVER_0));

        Objects.requireNonNull(handler, DwarfUtil.getExceptionString(ExceptionStringKey.PROPSETTINGVALUESAVER_1));

        final Set<SaveFailedException> exceptions = new LinkedHashSet<>();

        Properties properties = genProperties(ordered);
        for (SettingHandler.Entry entry : handler.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getCurrentValue());
        }
        try {
            properties.store(out, null);
        } catch (Exception e) {
            exceptions.add(new SaveFailedException(e.getMessage(), e.getCause()));
        }

        return exceptions;
    }

    private Properties genProperties(boolean ordered) {
        if (ordered) {
            return new OrderedProperties();
        } else {
            return new Properties();
        }
    }
}
