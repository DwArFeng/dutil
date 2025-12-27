package com.dwarfeng.dutil.basic.gui.swing;

import com.dwarfeng.dutil.basic.prog.Buildable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 菜单项目动作类，用该类可以快速的建立一个具有指定属性的菜单项目。
 *
 * <p>
 * 由于该类已经完全完成了<code>Action</code>的所有属性，因此该类不能被继承。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class JMenuItemAction extends AbstractAction {

    private static final long serialVersionUID = -2443355091954158655L;

    private ActionListener listener;

    /**
     * 菜单项目动作构造器类。
     *
     * @author DwArFeng
     * @since 0.0.2-beta
     */
    public static class Builder implements Buildable<JMenuItemAction> {

        private Icon icon = null;
        private String name = null;
        private String description = null;
        private KeyStroke keyStroke = null;
        private int mnemonic = 0;
        private ActionListener listener = null;

        /**
         * 设定动作的图标。
         *
         * @param val 动作的图标。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setIcon(Icon)} 替。
         */
        public Builder icon(Icon val) {
            this.icon = val;
            return this;
        }

        /**
         * 设定动作的图标。
         *
         * @param icon 指定的图标。
         * @return 构造器自身。
         */
        public Builder setIcon(Icon icon) {
            this.icon = icon;
            return this;
        }

        /**
         * 设定动作的名称。
         *
         * @param val 动作的名称。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setName(String)} 代替。
         */
        public Builder name(String val) {
            this.name = val;
            return this;
        }

        /**
         * 设定动作的名称。
         *
         * @param name 动作的名称。
         * @return 构造器自身。
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * 设定动作的描述。
         *
         * @param val 动作的描述。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setDescription(String)} 代替。
         */
        public Builder description(String val) {
            this.description = val;
            return this;
        }

        /**
         * 设定动作的描述。
         *
         * @param description 动作的描述。
         * @return 构造器自身。
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * 设定动作的组合键。
         *
         * @param val 设定动作的组合键。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setKeyStorke(KeyStroke)} 代替。
         */
        public Builder keyStorke(KeyStroke val) {
            this.keyStroke = val;
            return this;
        }

        /**
         * 设定动作的组合键。
         *
         * @param keyStroke 设定动作的组合键。
         * @return 构造器自身。
         */
        public Builder setKeyStorke(KeyStroke keyStroke) {
            this.keyStroke = keyStroke;
            return this;
        }

        /**
         * 设定动作的助记符。
         *
         * @param val 动作的助记符。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setDescription(String)} 代替。
         */
        public Builder mnemonic(int val) {
            this.mnemonic = val;
            return this;
        }

        /**
         * 设定动作的助记符。
         *
         * @param mnemonic 动作的助记符。
         * @return 构造器自身。
         */
        public Builder setMnemonic(int mnemonic) {
            this.mnemonic = mnemonic;
            return this;
        }

        /**
         * 设定动作的动作侦听。
         *
         * @param val 动作的动作侦听。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setListener(ActionListener)} 代替。
         */
        public Builder listener(ActionListener val) {
            this.listener = val;
            return this;
        }

        /**
         * 设定动作的动作侦听。
         *
         * @param listener 动作的动作侦听。
         * @return 构造器自身。
         */
        public Builder setListener(ActionListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public JMenuItemAction build() {
            return new JMenuItemAction(icon, name, description, keyStroke, mnemonic, listener);
        }
    }

    private JMenuItemAction(Icon icon, String name, String description, KeyStroke keyStroke, int mnemonic,
                            ActionListener listener) {
        if (listener != null)
            this.listener = listener;
        if (icon != null)
            putValue(SMALL_ICON, icon);
        if (name != null)
            putValue(NAME, name);
        if (description != null)
            putValue(SHORT_DESCRIPTION, description);
        if (keyStroke != null)
            putValue(ACCELERATOR_KEY, keyStroke);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener == null)
            return;
        listener.actionPerformed(e);
    }
}
