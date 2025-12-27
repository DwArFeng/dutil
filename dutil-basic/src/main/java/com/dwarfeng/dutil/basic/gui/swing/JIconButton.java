package com.dwarfeng.dutil.basic.gui.swing;

import javax.swing.*;
import java.awt.*;

/**
 * 构造一个具有指定图像的无文字按钮，图像一经指定，不能缩放。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class JIconButton extends JButton {

    private static final long serialVersionUID = 1708306634638895381L;
    private Image image;

    /**
     * 生成一个具有默认图像 null 的图像按钮
     */
    public JIconButton() {
        this(null);
    }

    /**
     * 生成一个具有指定图像的按钮。
     *
     * @param image 指定的图像。
     */
    public JIconButton(Image image) {
        super("");
        this.setImage(image);
    }

    /**
     * 返回按钮的图像。
     *
     * @return 按钮的图像。
     */
    public Image getImage() {
        return image;
    }

    /**
     * 设置按钮的图像。
     *
     * @param image 指定的图像。
     */
    public void setImage(Image image) {
        this.image = image;
        if (this.image == null) this.setIcon(null);
        else this.setIcon(new ImageIcon(image));
    }

    @Override
    public void setText(String str) {
        super.setText("");
    }
}
