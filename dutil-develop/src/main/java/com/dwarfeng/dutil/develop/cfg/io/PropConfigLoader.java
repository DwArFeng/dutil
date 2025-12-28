package com.dwarfeng.dutil.develop.cfg.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.CurrentValueContainer;

import java.io.InputStream;
import java.util.*;

/**
 * Properties 配置读取器。
 *
 * <p>
 * 该配置读取器假设待读取的文件格式符合 java 的 properties 文件格式。比如 <blockquote> <code>
 * # 注释...<br>
 * Config_0 = TURE<br>
 * Config_1 = FALSE<br>
 * Config_2 = 12.450
 * </code> </blockquote> 其中 等号左边的是键，等号右边的是值。
 *
 * @author DwArFeng
 * @since 0.0.3-beta
 */
public class PropConfigLoader extends StreamLoader<CurrentValueContainer> {

    private boolean readFlag = false;

    /**
     * 生成一个新的 Properties 配置读取器。
     *
     * @param in 指定的输入流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public PropConfigLoader(InputStream in) {
        super(in);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public void load(CurrentValueContainer container) throws LoadFailedException {
        if (readFlag)
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.PROPERTIESCONFIGLOADER_1));

        Objects.requireNonNull(container, DwarfUtil.getExceptionString(ExceptionStringKey.PROPERTIESCONFIGLOADER_0));

        Properties properties = new Properties();
        try {
            readFlag = true;
            properties.load(in);
            Map<ConfigKey, String> configMap = new HashMap<>();
            for (String str : properties.stringPropertyNames()) {
                configMap.put(new ConfigKey(str), properties.getProperty(str));
            }
            container.setAllCurrentValue(configMap);

        } catch (Exception e) {
            throw new LoadFailedException(e.getMessage(), e.getCause());
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public Set<LoadFailedException> countinuousLoad(CurrentValueContainer container) throws IllegalStateException {
        if (readFlag)
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.PROPERTIESCONFIGLOADER_1));

        Objects.requireNonNull(container, DwarfUtil.getExceptionString(ExceptionStringKey.PROPERTIESCONFIGLOADER_0));

        final Set<LoadFailedException> exceptions = new LinkedHashSet<>();

        Properties properties = new Properties();
        try {
            readFlag = true;
            properties.load(in);
            Map<ConfigKey, String> configMap = new HashMap<>();
            for (String str : properties.stringPropertyNames()) {
                configMap.put(new ConfigKey(str), properties.getProperty(str));
            }
            container.setAllCurrentValue(configMap);

        } catch (Exception e) {
            exceptions.add(new LoadFailedException(e.getMessage(), e.getCause()));
        }

        return exceptions;
    }
}
