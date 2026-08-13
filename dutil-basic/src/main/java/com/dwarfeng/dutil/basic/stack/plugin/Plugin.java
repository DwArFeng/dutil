package com.dwarfeng.dutil.basic.stack.plugin;

/**
 * dutil 插件标记接口。
 *
 * <p>
 * 插件 JAR 应通过 {@code META-INF/services/com.dwarfeng.dutil.basic.stack.plugin.Plugin}
 * 注册实现类。业务代码可以定义继承本接口的细分插件协议，并在加载后按该协议筛选。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface Plugin {
}
