package com.dwarfeng.dutil.basic.gui.swing;

import java.util.EventListener;

/**
 * ����̨�������������
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface ConsoleInputEventListener extends EventListener {
	
	/**
	 * ������̨���������¼�ʱ�����ĵ��ȡ�
	 * @param e ����̨�����¼���
	 */
	public void onConsoleInput(ConsoleInputEvent e);
	
}