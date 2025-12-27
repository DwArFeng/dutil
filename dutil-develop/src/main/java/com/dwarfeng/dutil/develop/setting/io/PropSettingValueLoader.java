package com.dwarfeng.dutil.develop.setting.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.dutil.basic.struct.OrderedProperties;
import com.dwarfeng.dutil.develop.setting.SettingHandler;

import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * Properties 配置值读取器。
 *
 * <p>
 * 通过 properties 文件读取配置键对应的当前值到指定的配置处理器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class PropSettingValueLoader extends StreamLoader<SettingHandler> {

    private final boolean ordered;

    private boolean readFlag = false;

    /**
     * 生成一个 Properties 配置值读取器。
     *
     * @param in 指定的输入流。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public PropSettingValueLoader(InputStream in) {
        this(in, false);
    }

    /**
     * 生成一个 Properties 配置值读取器
     *
     * @param in      指定的输入流。
     * @param ordered 是否在读取时保持顺序。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public PropSettingValueLoader(InputStream in, boolean ordered) {
        super(in);
        this.ordered = ordered;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(SettingHandler handler) throws LoadFailedException, IllegalStateException {
        if (readFlag)
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.PROPSETTINGVALUELOADER_0));

        Objects.requireNonNull(handler, DwarfUtil.getExceptionString(ExceptionStringKey.PROPSETTINGVALUELOADER_1));

        Properties properties = genProperties(ordered);
        try {
            readFlag = true;

            properties.load(in);
            for (String key : properties.stringPropertyNames()) {
                handler.setCurrentValue(key, properties.getProperty(key));
            }

        } catch (Exception e) {
            throw new LoadFailedException(e.getMessage(), e.getCause());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<LoadFailedException> countinuousLoad(SettingHandler handler) throws IllegalStateException {
        if (readFlag)
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.PROPSETTINGVALUELOADER_0));

        Objects.requireNonNull(handler, DwarfUtil.getExceptionString(ExceptionStringKey.PROPSETTINGVALUELOADER_1));

        final Set<LoadFailedException> exceptions = new LinkedHashSet<>();

        Properties properties = genProperties(ordered);
        try {
            readFlag = true;

            properties.load(in);
            for (String key : properties.stringPropertyNames()) {
                handler.setCurrentValue(key, properties.getProperty(key));
            }

        } catch (Exception e) {
            exceptions.add(new LoadFailedException(e.getMessage(), e.getCause()));
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
