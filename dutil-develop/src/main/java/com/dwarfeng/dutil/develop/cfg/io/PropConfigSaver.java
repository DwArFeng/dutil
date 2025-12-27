package com.dwarfeng.dutil.develop.cfg.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.dutil.basic.io.StreamSaver;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.CurrentValueContainer;

import java.io.OutputStream;
import java.util.*;

/**
 * Properties 配置保存器。
 *
 * <p>
 * 该配置保存器假设待保存的文件格式符合 java 的 properties 文件格式。比如 <blockquote> <code>
 * # 注释...<br>
 * Config_0 = TURE<br>
 * Config_1 = FALSE<br>
 * Config_2 = 12.450
 * </code> </blockquote> 其中 等号左边的是键，等号右边的是值。
 *
 * @author DwArFeng
 * @since 0.0.3-beta
 */
public class PropConfigSaver extends StreamSaver<CurrentValueContainer> {

    private boolean flag = true;

    /**
     * 生成一个新的 Properties 配置保存器。
     *
     * @param out 指定的输出流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public PropConfigSaver(OutputStream out) {
        super(out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(CurrentValueContainer container) throws SaveFailedException {
        if (flag) {
            flag = false;
        } else {
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.PROPERTIESCONFIGSAVER_1));
        }
        Objects.requireNonNull(container, DwarfUtil.getExceptionString(ExceptionStringKey.PROPERTIESCONFIGSAVER_0));

        Properties properties = new Properties();
        for (Map.Entry<ConfigKey, String> entry : container.getAllCurrentValue().entrySet()) {
            properties.setProperty(entry.getKey().getName(), entry.getValue());
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
    public Set<SaveFailedException> countinuousSave(CurrentValueContainer container) throws IllegalStateException {
        if (flag) {
            flag = false;
        } else {
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.PROPERTIESCONFIGSAVER_1));
        }
        Objects.requireNonNull(container, DwarfUtil.getExceptionString(ExceptionStringKey.PROPERTIESCONFIGSAVER_0));

        final Set<SaveFailedException> exceptions = new LinkedHashSet<>();

        Properties properties = new Properties();
        for (Map.Entry<ConfigKey, String> entry : container.getAllCurrentValue().entrySet()) {
            properties.setProperty(entry.getKey().getName(), entry.getValue());
        }
        try {
            properties.store(out, null);
        } catch (Exception e) {
            exceptions.add(new SaveFailedException(e.getMessage(), e.getCause()));
        }

        return exceptions;
    }
}
