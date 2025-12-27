package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.develop.cfg.struct.ConfigChecker;

/**
 * 配置的固定属性。
 *
 * <p>
 * 该结构定义了一个配置应该拥有的所有固定属性，这些属性在配置生成开始，就不应该再发生改变。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface ConfigFirmProps {

    /**
     * 获取配置默认值。
     *
     * @return 配置默认值。
     */
    String getDefaultValue();

    /**
     * 获取配置值检查器。
     *
     * @return 配置值检查器。
     */
    ConfigChecker getConfigChecker();
}
