package com.dwarfeng.dutil.basic.gui.swing;

import javax.swing.*;
import java.awt.*;

/**
 * 图片界面类，生成的界面被指定的图片填充。
 *
 * @author 来源于网络
 * @since 0.0.2-beta
 */
public class JImagePanel extends JPanel {

    private static final long serialVersionUID = -7734848431940782655L;
    /**
     * 背景图片。
     */
    private Image image = null;
    /**
     * 是否自适应。
     */
    private boolean autoResize = false;

    /**
     * 生成默认的图片界面实例，没有指定的图片，不可自适应。
     */
    public JImagePanel() {
        this(null, false);
    }

    /**
     * 生成图片界面实例，指定是否自适应。
     *
     * @param autoResize 是否自适应。
     */
    public JImagePanel(boolean autoResize) {
        this(null, autoResize);
    }

    /**
     * 生成带有指定图片的图片界面。
     *
     * @param image 指定的图片。
     */
    public JImagePanel(Image image) {
        this(image, false);
    }

    /**
     * 生成具有指定图片，以及指定是否可以自适应的图片界面。
     *
     * @param image      指定的图片。
     * @param autoResize 是否自适应。
     */
    public JImagePanel(Image image, boolean autoResize) {
        super();
        this.autoResize = autoResize;
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            if (autoResize) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            } else {
                g.drawImage(image, 0, 0, null);
            }
        }
    }

    /**
     * 返回背景图片。
     *
     * @return 背景图片。
     */
    public Image getImage() {
        return image;
    }

    /**
     * 设置背景图片为指定的图片。
     *
     * @param image 指定的图片。
     */
    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    /**
     * 返回图片是否自适应。
     *
     * @return 是否自适应。
     */
    public boolean isAutoResize() {
        return autoResize;
    }

    /**
     * 设置图片是否自适应。
     *
     * @param autoResize 是否自适应。
     */
    public void setAutoResize(boolean autoResize) {
        this.autoResize = autoResize;
        repaint();
    }
}
