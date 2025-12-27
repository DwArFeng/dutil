/**
 * 记录器开发工具。
 *
 * <p>
 * 可以通过包中的工具快速开发具有特定行为的记录模块。
 *
 * <p>
 * 该工具以 <code>LoggerHandler</code> 为基本的记录器处理器，负责处理记录器的一切有关与记录器信息逻辑关系，它是一个关于
 * <code>String</code> 和 <code>LoggerInfo</code> 的键值集合。<br>
 * <code>LoggerHandler</code> 除了实现最基本的键值集合的功能之外，还维护其中各个<code>LoggerInfo</code>
 * 的使用情况，用户可以指定具体哪一个 <code>LoggerInfo</code> 被使用(当然，有快捷方法全部使用)、停止使用，还可以查询当前的
 * <code>LoggerInfo</code>的使用情况。 <br>
 * 当<code>LoggerHandler</code> 使用一个<code>LoggerInfo</code> 将会调用
 * <code>LoggerInfo</code>的生成新记录器的方法， 并且将这个记录器通过某种方式进行保持，取消使用该
 * <code>LoggerInfo</code> 时，则会将持有的 <code>Logger</code> 移除。
 *
 * <p>
 * <code>LoggerInfo</code> 为记录器信息，它含有一个主键，以及实现一个生成新的<code>Logger</code>的方法。<br>
 * 就像其它的键值集合那样，同一个 <code>LoggerHandler</code> 中不允许含有两个相同键值的
 * <code>LoggerInfo</code>，即使这两个<code>LoggerInfo</code> 是不一样的。
 *
 * <p>
 * <code>Logger</code> 是记录器的执行部分，它负责将特定的文本或者异常以某种方式进行记录。 <br>
 * 在该工具中， <code>Logger</code> 由 <code>LoggerInfo</code> 生成。<br>
 * <code>LoggerHandler</code> 本身也是一个
 * <code>Logger</code>，当调用<code>LoggerHandler</code>中的记录操作时，它将会依次调用其中所有正在使用的
 * <code>LoggerInfo</code>对应持有的 <code>Logger</code> 中的相应方法。
 * 当您要执行记录操作时，正确的使用方法就是调用 <code>LoggerHandler</code> 的记录方法。
 *
 * @author DwArFeng
 * @since 1.8
 */
package com.dwarfeng.dutil.develop.logger;
