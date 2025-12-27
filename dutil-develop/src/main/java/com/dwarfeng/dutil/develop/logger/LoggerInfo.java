package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.prog.WithKey;

/**
 * 记录器信息。
 *
 * <p>
 * 记录器信息包含一个不可变更的名字，也就是主键，还具有能够生成特性记录器的方法。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface LoggerInfo extends WithKey<String> {

    /**
     * 由该记录器信息对象中的信息生成一个新的记录器。
     *
     * @return 由该记录器信息对象中的信息生成的一个新的记录器。
     * @throws Exception 生成新的记录器时发生异常。
     */
    Logger newLogger() throws Exception;
}
