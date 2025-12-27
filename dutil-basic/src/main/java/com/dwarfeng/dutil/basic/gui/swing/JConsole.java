package com.dwarfeng.dutil.basic.gui.swing;

import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.gui.event.EventListenerWeakSet;
import com.dwarfeng.dutil.basic.num.NumberUtil;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * swing 控制台类。
 *
 * <p>
 * 该类的表现行为类似控制台，用于替换重置之前版本的不完善的控制台，而重置之前版本的控制台将被移除。<br>
 * 这次的控制台不会主动覆盖<code>System.in</code>，因此，不必再担心使用控制台之后就会失去原有的系统输出流。
 * 控制台仍然提供一个输出流，当用户想重新定向输出流时，可以将提供的输出流重定向到<code>System.in</code>上。<br>
 * 控制台提供大致的行数保证，它允许文本达到一个最大的行数，当文本超过最大的行数时，控制台会按照一定的比例删掉 最早输出的一部分行数，以控制行数不超过最大值。
 * 被删除。这个行数成为能被显示的最大行数，最大行数在构造器中被指定，一旦被指定就不能更改。<br>
 * 控制台提供两个流：输入流和输出流，其中输出流是线程安全的，可以多个线程同时向输入流中输出字节，但是输入流是线程
 * 不安全的，它只能用于单线程输入，不管是不是加入了外部同步机制。<br>
 * 需要注意的是，此控制台的效率远远低于系统控制台，在同等输出内容下，此控制台的花费时间大约为系统控制台的 10 倍。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 * @deprecated 该控制台不完善且效率低下，dutil 提供了更好的工具类： {@link JExconsole}。
 */
public class JConsole extends JPanel {

    private static final long serialVersionUID = 4394657761909015686L;

    /**
     * 最大行数
     */
    protected final static int DEFAULT_MAX_LINE = 3000;
    /**
     * 行删除比率
     */
    protected final static int LINE_RATIO = 10;

    /**
     * 文本框
     */
    protected final JTextArea textArea;
    /**
     * 输入框
     */
    protected final JTextField inputField;

    /**
     * 事件集合
     */
    protected final EventListenerWeakSet eSet = new EventListenerWeakSet();

    /**
     * 最大显示行数
     */
    protected int maxLine = DEFAULT_MAX_LINE;
    /**
     * 是否回显标记
     */
    private boolean echoFlag = true;

    /**
     * 生成一个默认的控制台。
     */
    public JConsole() {
        this(DEFAULT_MAX_LINE);
    }

    /**
     * 生成一个拥有最大行数的控制台.
     */
    /**
     * 生成一个拥有最大行数的控制台。
     *
     * <p>
     * 控制台的最大行数为<code>maxLine</code>与 1 的最大值。
     *
     * @param maxLine 最大的行数。
     */
    public JConsole(int maxLine) {
        // 初始化常量
        this.maxLine = Math.max(maxLine, 1);

        inputField = new JTextField();
        textArea = new JTextArea();

        // 初始化界面
        init();
    }

    /**
     * 获取该控制台的输出流。
     *
     * @return 该控制台的输出流。
     */
    public PrintStream getOut() {
        return printStream;
    }

    private final InnerOutputStream outputStream = new InnerOutputStream();
    private final PrintStream printStream = new PrintStream(outputStream, true);

    public InputStream getIn() {
        return this.inputStream;
    }

    private final InnerInputStream inputStream = new InnerInputStream();

    /**
     * 向控制台中添加控制台输入事件侦听。
     *
     * <p>
     * 当入口参数<code>e</code>为<code>null</code>的时候，什么也不做。
     *
     * @param e 控制台输入事件侦听。
     */
    public void addConsoleInputEventListener(ConsoleInputEventListener e) {
        if (e == null)
            return;
        eSet.add(e);
    }

    /**
     * 向控制台中移除输入事件侦听。
     *
     * @param e 指定的控制台输入事件侦听。
     * @return 该移除操作是否成功移除了指定的事件侦听。
     */
    public boolean removeConsoleInputEventListener(ConsoleInputEventListener e) {
        return eSet.remove(e);
    }

    /**
     * 移除所有的事件侦听。
     */
    public void removeAllListeners() {
        eSet.clear();
    }

    /**
     * 获得该控制台对象的字体。
     *
     * @return 获得的字体。
     */
    public Font getConsoleFont() {
        return this.textArea.getFont();
    }

    /**
     * 设置该控制台对象的字体。
     *
     * @param font 指定的字体。
     */
    public void setConsoleFont(Font font) {
        this.textArea.setFont(font);
        this.inputField.setFont(font);
        this.repaint();
    }

    /**
     * 返回控制台显示的最大行数。
     *
     * @return 控制台显示的最大行数。
     */
    public int getMaxLine() {
        return maxLine;
    }

    /**
     * 返回输入框是否启用。
     *
     * @return 输入框是否被启用。
     */
    public boolean isInputFieldEnabled() {
        return inputField.isEnabled();
    }

    /**
     * 设置输入框是否被启用。
     *
     * @param flag 指定的启用标识。
     */
    public void setInputFieldEnabled(boolean flag) {
        inputField.setEnabled(flag);
    }

    /**
     * 返回输入框是否被显示。
     *
     * @return 输入框是否被显示。
     */
    public boolean isInputFieldVisible() {
        return inputField.isVisible();
    }

    /**
     * 设置输入框是否被显示。
     *
     * @param flag 指定的显示标识。
     */
    public void setInputFieldVisible(boolean flag) {
        inputField.setVisible(flag);
    }

    /**
     * 控制台输入是否回显。
     *
     * @return 控制台输入是否回显。
     */
    public boolean isEcho() {
        return echoFlag;
    }

    /**
     * 设置控制台输入是否回显
     *
     * @param echoFlag 控制台输入是否回显。
     */
    public void setEcho(boolean echoFlag) {
        this.echoFlag = echoFlag;
    }

    /**
     * 提供界面的初始化。
     */
    private void init() {
        setLayout(new BorderLayout(0, 0));

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String inputedText = inputField.getText();
                    inputStream.setText(inputedText + "\n");
                    ConsoleInputEvent event = new ConsoleInputEvent(this, inputedText + "\n", getIn());
                    fireConsoleInputEvent(event);
                    if (echoFlag) {
                        printStream.println(inputedText);
                    }
                    inputField.setText("");
                }
            }
        });

        add(inputField, BorderLayout.SOUTH);
        inputField.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);
    }

    protected void fireConsoleInputEvent(ConsoleInputEvent event) {
        for (ConsoleInputEventListener listener : eSet.subSet(ConsoleInputEventListener.class)) {
            listener.onConsoleInput(event);
        }
    }

    private final class InnerInputStream extends InputStream {

        private int mark = 0;
        private int lastMark = 0;
        private byte[] bytesForString;
        private final Lock inLock = new ReentrantLock();

        public void setText(String text) {
            inLock.lock();
            try {
                bytesForString = (text).getBytes();
                mark = 0;
                lastMark = 0;
            } finally {
                inputStream.inLock.unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean markSupported() {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized void mark(int readlimit) {
            int max = bytesForString == null ? 0 : bytesForString.length;
            int min = 0;
            readlimit = Math.max(min, readlimit);
            readlimit = Math.min(max, readlimit);
            lastMark = readlimit;
            mark = readlimit;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized void reset() throws IOException {
            mark = lastMark;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int available() throws IOException {
            return bytesForString == null ? 0 : Math.max(0, bytesForString.length - mark);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int read() throws IOException {
            inLock.lock();
            try {
                if (available() == 0)
                    return -1;
                return bytesForString[mark++];
            } finally {
                inLock.unlock();
            }
        }

    }

    private final class InnerOutputStream extends OutputStream {

        private final Lock outLock = new ReentrantLock();
        private final ArrayList<Byte> byteList = new ArrayList<Byte>();

        /**
         * {@inheritDoc}
         */
        @Override
        public void flush() {
            outLock.lock();
            try {
                String str = new String(ArrayUtil.unpack(byteList.toArray(new Byte[0])));
                if (textArea != null) {
                    append(str);
                }
                byteList.clear();
                // 最后，让列表释放多余的空间。
                byteList.trimToSize();
            } finally {
                outLock.unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(int b) throws IOException {
            outLock.lock();
            try {
                byteList.add(NumberUtil.cutInt2Byte(b));
            } finally {
                outLock.unlock();
            }
        }

        /**
         * 向控制台的输出窗口追加文本。
         *
         * @param str 指定的追加文本。
         */
        private void append(String str) {
            textArea.append(str);
            textArea.setCaretPosition(textArea.getText().length());
            ensureMaxLine();
        }

        /**
         * 确保最大行。
         */
        private void ensureMaxLine() {
            int line = textArea.getLineCount();
            if (line >= maxLine) {
                try {
                    textArea.replaceRange(null, textArea.getLineStartOffset(0),
                            textArea.getLineEndOffset(line - maxLine + maxLine / LINE_RATIO));
                } catch (BadLocationException e) {
                    // DO NOTHING
                }
            }
        }

    }
}
