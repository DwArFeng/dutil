package com.dwarfeng.dwarffunction.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

/**
 * 菜单项目动作类，用该类可以快速的建立一个具有指定属性的菜单项目。
 * @author DwArFeng
 * @since 1.7
 */
public final class JMenuItemAction extends AbstractAction{

	private static final long serialVersionUID = 67574488277609694L;
	
	private ActionListener listener;
	
	/**
	 * 菜单项目动作构造器类。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private Icon icon = null;
		private String name = null;
		private String description = null;
		private KeyStroke keyStroke = null;
		private int mnemonic = 0;
		private ActionListener listener = null;
		
		/**
		 * 设定动作的图标。
		 * @param val 动作的图标。
		 * @return 构造器自身。
		 */
		public Productor icon(Icon val){
			this.icon = val;
			return this;
		}
		
		/**
		 * 设定动作的名称。
		 * @param val 动作的名称。
		 * @return 构造器自身。
		 */
		public Productor name(String val){
			this.name = val;
			return this;
		}
		
		/**
		 * 设定动作的描述。
		 * @param val 动作的描述。
		 * @return 构造器自身。
		 */
		public Productor description(String val){
			this.description = val;
			return this;
		}
		
		/**
		 * 设定动作的组合键。
		 * @param val 设定动作的组合键。
		 * @return 构造器自身。
		 */
		public Productor keyStorke(KeyStroke val){
			this.keyStroke = val;
			return this;
		}
		
		/**
		 * 设定动作的助记符。
		 * @param val 动作的助记符。
		 * @return 构造器自身。
		 */
		public Productor mnemonic(int val){
			this.mnemonic = val;
			return this;
		}
		
		/**
		 * 设定动作的动作侦听。
		 * @param val 动作的动作侦听。
		 * @return 构造器自身。
		 */
		public Productor listener(ActionListener val){
			this.listener = val;
			return this;
		}
		
		/**
		 * 由构造器构造的菜单项目动作。
		 * @return 由构造器构造的菜单项目动作。
		 */
		public JMenuItemAction product(){
			return new JMenuItemAction(icon, name, description, keyStroke, mnemonic, listener);
		}
	}
	
	private JMenuItemAction(Icon icon,String name,String description,KeyStroke keyStroke,int mnemonic,ActionListener listener ){
		if(listener != null) this.listener = listener;
		if(icon != null)putValue(SMALL_ICON,icon);
		if(name != null) putValue(NAME, name);
		if(description != null) putValue(SHORT_DESCRIPTION, description);
		if(keyStroke != null) putValue(ACCELERATOR_KEY,keyStroke);
		putValue(MNEMONIC_KEY, mnemonic);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(listener == null) return;
		listener.actionPerformed(e);
	}
}
