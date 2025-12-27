package com.dwarfeng.dutil.develop.cfg.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.Loader;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.CurrentValueContainer;

import java.util.Map;

/**
 * 配置读取器。
 *
 * <p>
 * 用于读取指定的配置。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 * @deprecated 该接口由 {@link Loader} 接口代替。
 */
public interface ConfigLoader {

    @Deprecated
    Map<ConfigKey, String> loadConfig() throws LoadFailedException;

    @Deprecated
    void loadConfig(CurrentValueContainer container) throws LoadFailedException;

    @Deprecated
    void load(CurrentValueContainer container) throws LoadFailedException;
}
