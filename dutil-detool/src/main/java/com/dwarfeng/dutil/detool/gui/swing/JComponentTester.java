package com.dwarfeng.dutil.detool.gui.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

/**
 * 组件调试器。
 *
 * <p>
 * 该调试器是一个窗口，可以传入指定的组件。这样，用户就可以在程序中显示出这个界面，从而对组件进行调试了。 <br>
 * 该界面的内容面板使用的是 {@link BorderLayout} 待调试的组件始终处于界面的中间。
 *
 * <p>
 * 该调试器窗口按下关闭按钮，程序即结束。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class JComponentTester extends JFrame {

    private static final long serialVersionUID = 2757822629350396907L;

    /**
     * 界面的标题。
     */
    public static final String TITLE = "dutil.detool.gui.swing.JComponentTester";

    /**
     * 待调试组件
     */
    protected final JComponent component;

    /**
     * 内容面板
     */
    private final JPanel contentPane = new JPanel();

    /**
     * 生成一个默认的组件调试器。
     */
    public JComponentTester() {
        this(null);
    }

    /**
     * 生成一个具有指定待调试组件的组件调试器。
     *
     * @param component 指定的组件，允许为 <code>null</code>。
     */
    public JComponentTester(JComponent component) {
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
    public JComponent getComponent() {
        return component;
    }
}
