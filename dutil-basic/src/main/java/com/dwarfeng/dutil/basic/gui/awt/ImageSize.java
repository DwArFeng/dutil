package com.dwarfeng.dutil.basic.gui.awt;

import com.dwarfeng.dutil.basic.num.IntNumberValue;
import com.dwarfeng.dutil.basic.num.NumberValue;

/**
 * 程序的图片类型。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public enum ImageSize implements Size2D {

    /**
     * 小图标
     */
    ICON_SMALL(16, 16),

    /**
     * 中等图标
     */
    ICON_MEDIUM(32, 32),

    /**
     * 大图标
     */
    ICON_LARGE(48, 48),

    /**
     * 程序用超大图标
     */
    ICON_SUPER_LARGE(128, 128),

    ;

    private final NumberValue height;
    private final NumberValue width;

    ImageSize(int height, int width) {
        this.height = new IntNumberValue(height);
        this.width = new IntNumberValue(width);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NumberValue getHeight() {
        return height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NumberValue getWidth() {
        return width;
    }
}
