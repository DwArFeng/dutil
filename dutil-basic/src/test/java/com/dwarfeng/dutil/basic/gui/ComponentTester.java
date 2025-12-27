package com.dwarfeng.dutil.basic.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

/**
 * 组件测试类。
 *
 * <p>
 * 该类会生成一个含有被测试组件的窗口，并显示在屏幕上，供测试使用。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class ComponentTester extends JFrame {

    private static final long serialVersionUID = 947660418687263849L;

    /**
     * 界面的标题。
     */
    public static final String TITLE = "dutil.detool.gui.swing.JComponentTester";

    /**
     * 待调试组件
     */
    protected final Component component;

    /**
     * 内容面板
     */
    private final JPanel contentPane = new JPanel();

    /**
     * 生成一个默认的组件调试器。
     */
    public ComponentTester() {
        this(null);
    }

    /**
     * 生成一个具有指定待调试组件的组件调试器。
     *
     * @param component 指定的组件，允许为 <code>null</code>。
     */
    public ComponentTester(JComponent component) {
        if (Objects.nonNull(component)) {
            this.component = component;
        } else {
            JLabel label = new JLabel(TITLE);
            label.setHorizontalAlignment(JLabel.CENTER);
            this.component = label;
        }
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setTitle(TITLE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        contentPane.add(component, BorderLayout.CENTER);
    }

    /**
     * 获取该调试器中的待调试组件。
     *
     * @return 待调试组件。
     */
    public Component getComponent() {
        return component;
    }
}
