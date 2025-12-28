package com.dwarfeng.dutil.basic.gui.swing;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.ImageKey;
import com.dwarfeng.dutil.basic.LabelStringKey;
import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Swing 控制台。
 *
 * <p>
 * 该控制台是 {@link JConsole} 的升级版，无论是定义的良好性还是输入输出的效率都远远好于前者。<br>
 * 该控制台继承之前的特性：提供大致的行数保证，它允许文本达到一个最大的行数，当文本超过最大的行数时，控制台会按照一定的比例删掉
 * 最早输出的一部分行数，以控制行数不超过最大值。<br>
 * 控制台提供两个流：输入流和输出流，与 {@link JConsole} 同的是，输入流变为阻塞式的，当输入流没有数据时，不会立即返回
 * <code>-1</code>，
 * 而是显示输入栏，等待用于的输入——这个特性与系统输入流完全一致。该控制台的输入再也不需要依赖于事件，您可以完全按照同系统控制台通信的格式
 * 来编写该控制台的通信，同时不必担心无意识的调用流中的 <code>close()</code> 方法——因为该控制台的输入流和输出流的关闭不是通过
 * <code>close()</code> 方法， 而是通过 {@link #dispose()} 法。<br>
 * 以下的例子完整的体现了上述的特性。
 *
 * <pre>
 * JExconsole console = new JExconsole();
 *
 * console.out.println("hello world"); // 就像 System.out.prinln(...)一样
 *
 * Scanner scanner = new Scanner(console.in);
 * try {
 * 	console.out.println(scanner.nextLine());
 * } finally {
 * 	scanner.close(); // 不用担心 Scanner 的
 * 						// close()方法调用 console.in.close()方法，因为控制台的输入流不响应该方法。
 * }
 *
 * console.dispose(); // 该方法才会真正的关闭控制台的输入输出流。
 * </pre>
 *
 * <p>
 * 经优化后，该控制台的效率可以达到 cmd 控制台的 6900%，是一个货真价实的高效控制台。
 *
 * @author DwArFeng
 * @since 0.0.3-beta
 */
public class JExconsole extends JPanel {

    private static final long serialVersionUID = 5833629064130812694L;

    private final static ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("jexconsole_cleaner");

    /**
     * 控制台的输入流
     */
    public final InputStream in = new InnerInputStream();
    /**
     * 控制台的输出流
     */
    public final PrintStream out = new PrintStream(new InnerOutputStream(), true);

    private final Lock renderLock = new ReentrantLock();
    private final Condition renderCondition = renderLock.newCondition();
    private final Queue<String> string2Render = new ArrayDeque<>();
    private final Lock inputLock = new ReentrantLock();
    private final Condition inputCondition = inputLock.newCondition();
    private final Lock outputLock = new ReentrantLock();
    private final List<String> rollbackStrings = new ArrayList<>();

    /**
     * 控制台的渲染线程
     */
    private final Thread renderer = THREAD_FACTORY.newThread(new RenderProcessor());

    private final AtomicBoolean disposeFlag = new AtomicBoolean(false);
    private int rollBackPos = -1;

    private int maxLine;
    private double cleanRatio;
    private int maxRollback;

    /**
     * 控制台的输入框
     */
    protected final JTextField textField;
    /**
     * 控制台的显示框
     */
    protected final JTextArea textArea;
    /**
     * 控制台的右键菜单
     */
    protected JPopupMenu popup;

    /**
     * 生成一个默认的控制台。
     *
     * <p>
     * 控制台的最大行数为 <code>3000</code> 行，清除系数为 <code>0.1</code>， 最大回滚数量为 <code>10</code>。
     */
    public JExconsole() {
        this(3000, 0.1, 10);
    }

    /**
     * 生成一个具有指定最大行数，指定的清除系数，指定的最大回滚量的控制台。
     *
     * @param maxLine     指定的最大行数，需要 <code> 0 &lt; maxLine</code>。
     * @param cleanRatio  指定的清除系数，需要<code> 0.0 &lt; cleanRatio &lt;= 1.0</code>。
     * @param maxRollback 最大的输入回滚数量。
     * @throws IllegalArgumentException 入口参数不符合要求。
     */
    public JExconsole(int maxLine, double cleanRatio, Integer maxRollback) {
        this(maxLine, cleanRatio, maxRollback, true);
    }

    /**
     * 生成一个具有指定的最大行数，指定的清除系数，指定的最大回滚量， 指定是否创建默认右键菜单的控制台。
     *
     * @param maxLine           最大的行数。
     * @param cleanRatio        指定的清除系数。
     * @param maxRollback       指定的额最大回滚数量。
     * @param creatDefaultPopup 是否创建默认的右键菜单。
     * @param maxRollback       最大的输入回滚数量。
     * @throws IllegalArgumentException 入口参数不符合要求。
     */
    public JExconsole(int maxLine, double cleanRatio, Integer maxRollback, boolean creatDefaultPopup) {
        if (maxLine <= 0)
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_1));
        if (cleanRatio <= 0 || cleanRatio > 1)
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_2));
        if (maxRollback < 0)
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_3));

        this.maxLine = maxLine;
        this.cleanRatio = cleanRatio;
        this.setMaxRollback(maxRollback);

        setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        textArea = new JTextArea();
        // 该方法与控制台的全选冲突。
        // textArea.addMouseListener(new MouseAdapter() {
        // @Override
        // public void mousePressed(MouseEvent e) {
        // textField.requestFocus();
        // }
        // });
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);

        textField = new JTextField();
        textField.setBorder(new EmptyBorder(0, 0, 0, 0));
        textField.addKeyListener(new KeyAdapter() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void keyPressed(KeyEvent e) {
                // When press enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    inputLock.lock();
                    try {
                        if (disposeFlag.get()) {
                            throw new IllegalStateException(
                                    DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_0));
                        }

                        InnerInputStream in = (InnerInputStream) JExconsole.this.in;
                        String str = textField.getText();
                        in.bs = (str + "\n").getBytes();
                        in.pos = 0;
                        in.readFlag = true;
                        inputCondition.signalAll();
                        SwingUtilities.invokeLater(() -> {
                            textField.setVisible(false);
                            textField.setText(null);
                            revalidate();
                        });
                        rollbackStrings.add(0, str);
                        if (rollbackStrings.size() > JExconsole.this.maxRollback)
                            rollbackStrings.remove(rollbackStrings.size() - 1);
                        rollBackPos = -1;
                    } finally {
                        inputLock.unlock();
                    }
                }

                // When press up or down
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP) {
                    if (rollBackPos < rollbackStrings.size() - 1)
                        rollBackPos++;
                    textField.setText(rollbackStrings.get(rollBackPos));
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_KP_DOWN) {
                    if (rollBackPos > 0)
                        rollBackPos--;
                    textField.setText(rollbackStrings.get(rollBackPos));
                }
            }
        });
        textField.setVisible(false);
        textField.setOpaque(true);
        add(textField, BorderLayout.SOUTH);
        textField.setColumns(10);
        textArea.setForeground(null);
        textArea.setWrapStyleWord(true);

        popup = creatDefaultPopup ? new InnerPopupMenu() : null;

        textArea.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mayShowMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mayShowMenu(e);
                }
            }

            private void mayShowMenu(MouseEvent e) {
                if (Objects.isNull(popup))
                    return;
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        textArea.getActionMap().put("cls", new AbstractAction() {

            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.isNull(textArea))
                    return;
                SwingUtilities.invokeLater(() -> {
                    textArea.requestFocus();
                    textArea.setText("");
                });
            }
        });
        textArea.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK), "cls");

        renderer.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFont(Font font) {
        if (Objects.nonNull(textArea))
            textArea.setFont(font);
        if (Objects.nonNull(textField))
            textField.setFont(font);
        super.setFont(font);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setForeground(Color fg) {
        if (Objects.nonNull(textArea))
            textArea.setForeground(fg);
        if (Objects.nonNull(textField))
            textField.setForeground(fg);
        super.setForeground(fg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackground(Color bg) {
        if (Objects.nonNull(textArea))
            textArea.setBackground(bg);
        if (Objects.nonNull(textField))
            textField.setBackground(bg);
        super.setBackground(bg);
    }

    /**
     * 释放该控制台。
     *
     * <p>
     * 该方法会依次关闭输入流、输出流、控制台清理线程。
     */
    public void dispose() {
        textArea.setEnabled(false);
        textField.setEnabled(false);

        disposeFlag.set(true);

        inputLock.lock();
        try {
            inputCondition.signalAll();
        } finally {
            inputLock.unlock();
        }

        renderLock.lock();
        try {
            renderCondition.signalAll();
        } finally {
            renderLock.unlock();
        }

    }

    /**
     * 获取控制台的最大显示行数。
     *
     * @return 控制台的最大显示行数。
     */
    public int getMaxLine() {
        return maxLine;
    }

    /**
     * 设置控制台的最大显示行数。
     *
     * @param maxLine 控制台的最大显示行数，需要 <code> 0 &lt; maxLine</code>。
     * @throws IllegalArgumentException 入口参数不符合要求。
     */
    public void setMaxLine(int maxLine) {
        if (maxLine <= 0)
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_1));

        renderLock.lock();
        try {
            this.maxLine = maxLine;
            renderCondition.signalAll();
        } finally {
            renderLock.unlock();
        }
    }

    /**
     * 获取控制台的删除比率。
     *
     * @return 控制台的删除比率。
     */
    public double getCleanRatio() {
        return cleanRatio;
    }

    /**
     * 设置控制台的删除比率。
     *
     * @param cleanRatio 控制台的删除比率，需要<code> 0.0 &lt; cleanRatio &lt;= 1.0</code>。
     * @throws IllegalArgumentException 入口参数不符合要求。
     */
    public void setCleanRatio(double cleanRatio) {
        if (cleanRatio <= 0 || cleanRatio > 1)
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_2));

        renderLock.lock();
        try {
            this.cleanRatio = cleanRatio;
            renderCondition.signalAll();
        } finally {
            renderLock.unlock();
        }
    }

    /**
     * 获取控制台中的最大回滚数量。
     *
     * @return 控制台的最大回滚数量。
     */
    public int getMaxRollback() {
        return maxRollback;
    }

    /**
     * 设置控制台的最大回滚数量，需要 <code> 0 &lt;= maxRollback</code>
     *
     * @param maxRollback 指定的最大回滚数量。
     * @throws IllegalArgumentException 入口参数不符合要求。
     */
    public void setMaxRollback(int maxRollback) {
        if (maxLine <= 0)
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_1));
        this.maxRollback = maxRollback;
        if (rollbackStrings.size() > maxRollback) {
            for (int i = 0; i < maxRollback - rollbackStrings.size(); i++) {
                rollbackStrings.remove(rollbackStrings.size() - 1);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocale(Locale l) {
        popup.setLocale(l);
        super.setLocale(l);
    }

    public JPopupMenu getPopup() {
        return popup;
    }

    public void setPopup(JPopupMenu popup) {
        this.popup = popup;
    }

    /**
     * 立即向该控制台的输入流中输入指定的字符串。
     *
     * <p>
     * 如果在调用此方法时，输入栏处于打开状态，则会清空输入栏中的字符，并且将其隐藏。 使用该方法输入的字符串不会被记录到回滚队列中。
     *
     * @param string 指定的字符串。
     */
    public void input(String string) {
        inputLock.lock();
        try {
            if (disposeFlag.get()) {
                throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_0));
            }

            InnerInputStream in = (InnerInputStream) JExconsole.this.in;
            in.bs = string.getBytes();
            in.pos = 0;
            in.readFlag = true;
            inputCondition.signalAll();
            textField.setVisible(false);
            textField.setText(null);
            revalidate();
        } finally {
            inputLock.unlock();
        }
    }

    /**
     * 中断输入过程，并根据指定标志决定是否将已经输入的文本提交给输入流。
     *
     * <p>
     * 该过程只有在输入流读取数据时（此时输入流处于等待状态）有效，如果在调用该方法时，
     * 输入流没有读取数据（未处于等待状态），则该程序不做任何事情，直接返回。
     *
     * <p>
     * 如果需要提交已经输入的文本，则当前的文本会被加上 "\n" 提交到输入流中，就像正常输入那样。
     *
     * @param submitExistingText 是否提交已经输入的文本。
     */
    public void interruptInput(boolean submitExistingText) {
        inputLock.lock();
        try {
            if (disposeFlag.get()) {
                throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_0));
            }

            InnerInputStream in = (InnerInputStream) JExconsole.this.in;

            if (!in.isBlocking())
                return;

            if (submitExistingText) {
                in.bs = (textField.getText() + "\n").getBytes();
                in.pos = 0;
            } else {
                in.bs = new byte[0];
                in.pos = 0;
            }

            in.readFlag = true;
            inputCondition.signalAll();

            textField.setVisible(false);
            textField.setText(null);
            revalidate();
        } finally {
            inputLock.unlock();
        }
    }

    /**
     * 创建控制台的右键菜单。
     *
     * <p>
     * 该方法会在初始化的时候调用，用于控制台用的菜单。 此方法会创建一个默认的菜单，包括全选，清除屏幕以及是否自动换行。
     *
     * <p>
     * 如果不希望控制台显示默认的右键菜单，可以覆盖此方法，并且返回 <code>null</code>。
     *
     * @return 控制台的右键菜单。
     * @deprecated 该方法已经废弃，现在控制台类中没有任何方法调用此方法。
     */
    protected JPopupMenu createPopup() {
        return new InnerPopupMenu();
    }

    private final class InnerInputStream extends InputStream {

        private byte[] bs = new byte[1024];
        private int pos = 0;
        private boolean readFlag = false;
        private boolean blockFlag = false;

        /**
         * {@inheritDoc}
         */
        @Override
        public int read() {
            if (disposeFlag.get()) {
                throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_0));
            }

            inputLock.lock();
            try {
                while ((!disposeFlag.get()) && !readFlag) {
                    try {
                        SwingUtilities.invokeLater(() -> {
                            if (!textField.isVisible()) {
                                textField.setVisible(true);
                                textField.requestFocus();
                                revalidate();
                            }
                        });
                        blockFlag = true;
                        inputCondition.await();
                    } catch (InterruptedException ignore) {
                        // 重新检测
                    }
                }
                blockFlag = false;

                if (disposeFlag.get())
                    return -1;
                if (pos == bs.length) {
                    readFlag = false;
                    return -1;
                }
                return bs[pos++];
            } finally {
                inputLock.unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean markSupported() {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() {
            // Do nothing
        }

        public boolean isBlocking() {
            inputLock.lock();
            try {
                return blockFlag;
            } finally {
                inputLock.unlock();
            }
        }

    }

    private final class InnerOutputStream extends OutputStream {

        private byte[] bs = new byte[1024];
        private int pos = 0;

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(int b) {
            if (disposeFlag.get()) {
                throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_0));
            }

            outputLock.lock();
            try {
                if (pos == bs.length) {
                    byte[] dejavu = bs;
                    bs = new byte[bs.length << 1];
                    System.arraycopy(dejavu, 0, bs, 0, dejavu.length);
                }

                bs[pos++] = NumberUtil.cutInt2Byte(b);
            } finally {
                outputLock.unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void flush() {
            if (disposeFlag.get()) {
                throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.JEXCONSOLE_0));
            }

            String str = null;

            outputLock.lock();
            try {
                str = new String(Arrays.copyOfRange(bs, 0, pos));
                pos = 0;
                bs = new byte[1024];
            } finally {
                outputLock.unlock();
            }

            if (Objects.isNull(str))
                return;

            renderLock.lock();
            try {
                string2Render.offer(str);
                renderCondition.signalAll();
            } finally {
                renderLock.unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() {
            // Do nothing
        }
    }

    private class InnerPopupMenu extends JPopupMenu {

        private static final long serialVersionUID = 5256502032514168869L;

        private final JMenuItem selectAllMenuItem;
        private final JMenuItem cleanScreenMenuItem;
        private final JCheckBoxMenuItem lineWrapMenuItem;

        public InnerPopupMenu() {
            super();
            selectAllMenuItem = add(
                    new JMenuItemAction.Builder().setIcon(new ImageIcon(DwarfUtil.getImage(ImageKey.SELECT_ALL)))
                            .setName(DwarfUtil.getLabelString(LabelStringKey.JEXCONSOLE_0, getDefaultLocale()))
                            .setKeyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK))
                            .setMnemonic('A').setListener(e -> {
                                if (Objects.isNull(textArea))
                                    return;
                                SwingUtilities.invokeLater(() -> {
                                    textArea.requestFocus();
                                    textArea.select(0, textArea.getText().length());
                                });
                            }).build());

            cleanScreenMenuItem = add(
                    new JMenuItemAction.Builder().setIcon(new ImageIcon(DwarfUtil.getImage(ImageKey.CLEAN_SCREEN)))
                            .setName(DwarfUtil.getLabelString(LabelStringKey.JEXCONSOLE_1, getDefaultLocale()))
                            .setKeyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK))
                            .setMnemonic('E').setListener(e -> {
                                if (Objects.isNull(textArea))
                                    return;
                                SwingUtilities.invokeLater(() -> {
                                    textArea.requestFocus();
                                    textArea.setText("");
                                });
                            }).build());

            addSeparator();

            lineWrapMenuItem = new JCheckBoxMenuItem(
                    DwarfUtil.getLabelString(LabelStringKey.JEXCONSOLE_2, getDefaultLocale()));
            lineWrapMenuItem.setIcon(new ImageIcon(DwarfUtil.getImage(ImageKey.LINE_WRAP)));
            lineWrapMenuItem.setMnemonic('W');
            lineWrapMenuItem.addActionListener(e -> {
                if (Objects.isNull(textArea))
                    return;
                SwingUtilities.invokeLater(() -> textArea.setLineWrap(lineWrapMenuItem.getState()));
            });
            add(lineWrapMenuItem);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setLocale(Locale l) {
            selectAllMenuItem.setText(DwarfUtil.getLabelString(LabelStringKey.JEXCONSOLE_0, l));
            cleanScreenMenuItem.setText(DwarfUtil.getLabelString(LabelStringKey.JEXCONSOLE_1, l));
            lineWrapMenuItem.setText(DwarfUtil.getLabelString(LabelStringKey.JEXCONSOLE_2, l));
            super.setLocale(l);
        }
    }

    private class RenderProcessor implements Runnable {

        private StringBuilder sb = new StringBuilder();
        private boolean appendFlag;

        @Override
        public void run() {
            next:
            while (!disposeFlag.get()) {

                renderLock.lock();
                try {
                    while (string2Render.isEmpty() && !disposeFlag.get()) {
                        try {
                            renderCondition.await();
                        } catch (InterruptedException e) {
                            // 检查退出条件
                        }
                    }

                    if (disposeFlag.get())
                        return;

                    if (string2Render.isEmpty())
                        continue next;

                    while (!string2Render.isEmpty()) {
                        sb.append(string2Render.poll());
                    }

                } finally {
                    renderLock.unlock();
                }

                appendFlag = true;

                checkAppend:
                while (appendFlag) {
                    try {
                        SwingUtilities.invokeAndWait(() -> {
                            textArea.append(sb.toString());
                            appendFlag = false;
                            sb = new StringBuilder();
                        });
                    } catch (InterruptedException e) {
                        continue checkAppend;
                    } catch (InvocationTargetException ignore) {
                        // 该过程不可能发生异常。
                    }
                }

                if (textArea.getCaretPosition() < textArea.getText().length()) {
                    int pos = textArea.getText().length();
                    SwingUtilities.invokeLater(() -> textArea.setCaretPosition(pos));
                }

                if (textArea.getLineCount() > maxLine) {
                    int pos = getLinePos();
                    SwingUtilities.invokeLater(() -> textArea.replaceRange(null, 0, pos));
                }

            }
        }

        private int getLinePos() {
            try {
                return textArea.getLineEndOffset((int) (textArea.getLineCount() + maxLine * cleanRatio - maxLine));
            } catch (BadLocationException ignore) {
                // 不可能抛出此异常
                return 0;
            }
        }

    }
}
