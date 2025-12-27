/**
 * 配置开发包。
 *
 * <p>
 * 提供便捷的配置模板。
 *
 * <p>
 * <b>有关此配置模板：</b>
 *
 * <br>
 * 该配置模板能够实现：对配置的快捷读写；在程序中动态的设置配置，并且向感兴趣的对象对发放更改配置的通知； 配置纠错以及对错误进行还原为默认配置的处理。
 *
 * <p>
 * <b>配置结构：</b> <br>
 * 配置包中的核心结构是 {@link com.dwarfeng.dutil.develop.cfg.ConfigModel} ——配置模型，
 * 它负责与以上说明有关的功能的实现。
 * 用户可以在配置模型中注册配置键与固定配置属性，并且侦听它的变化，以对其变化做出即时的响应。io 包中的对应的读写类则可以用来读取或保存
 * 指定的配置，以做到配置的持久化。 <br>
 *
 * <p>
 * 自 beta-0.1.0 版本以来，配置包中的核心结构增加了
 * {@link com.dwarfeng.dutil.develop.cfg.ExconfigModel}。 <br>
 * 该结构比 <code>ConfigModel</code> 更强大，同时结构也更简单，<code>ConfigModel</code>
 * 的主要结构是三重映射，而 <code>ExConfigModel</code>只有一个映射：配置键与
 * {@link com.dwarfeng.dutil.develop.cfg.DefaultExconfigModel.ExconfigBean} 射。
 * 这种结构更稳定，也更好维护，在实现时只需要使用一个 {@link java.util.Map} 为代理即可，同时避免了
 * <code>ConfigModel</code>的代理实现时不同映射表现不同的问题。
 *
 * <p>
 * 在一般情况下，一个键下，值检查器负责检测某个特定的值是否有效，当当前值有效时，当前值在发挥作用；当当前值无效时，
 * 默认值发挥作用；默认值还可以作为复位或者初始配置使用。<b>默认值必须是有效的值，而当前值可以有效也可以无效。</b>
 * 当当前值无效时，用户可以使用默认值替代，也可以使用其他的方法处理，比如不让程序继续运行或者抛出异常。
 *
 * <p>
 * <b> 有关线程的安全性：</b>
 *
 * <p>
 * 该包中的所有对象都是线程不安全的——一般而言，配置操作并不需要多线程。 如果需要对配置进行多线程操作，请编写外部同步方法。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
package com.dwarfeng.dutil.develop.cfg;
