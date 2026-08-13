package com.dwarfeng.dutil.basic.sdk.model.event;

import com.dwarfeng.dutil.basic.stack.lifecycle.RuntimeState;
import com.dwarfeng.dutil.basic.stack.model.event.ProgramObserver;

/**
 * 程序观察器适配器。
 *
 * <p>
 * 此类中的方法为空。此类存在的目的是方便创建侦听器对象。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public abstract class ProgramAdapter implements ProgramObserver {

    @Override
    public void fireRuntimeStateChanged(RuntimeState oldState, RuntimeState newState) {
    }
}
