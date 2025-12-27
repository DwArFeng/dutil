package com.dwarfeng.dutil.basic;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 程序中的图片键。
 *
 * <p>
 * 此枚举是对内使用的，它的主要作用是返回程内部类所需要的图片对应的字段。<br>
 * 请不要在外部程序中调用此包的枚举，因为该枚举对内使用，其本身不保证兼容性。
 *
 * <p>
 * <b>注意：</b> 该枚举在设计的时候不考虑兼容性，当发生不向上兼容的改动时，作者没有义务在变更日志中说明。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public enum ImageKey implements Name {

    /**
     * 图片读取失败对应的图片键。
     */
    IMG_LOAD_FAILED("img_load_failed_blue.png"),
    /**
     * 清除屏幕对应的图片键。
     */
    CLEAN_SCREEN("clean_screen.png"),
    /**
     * 换行对应的图片键。
     */
    LINE_WRAP("line_warp.png"),
    /**
     * 全部选择对应的图片键。
     */
    SELECT_ALL("select_all.png"),

    ;

    private final String name;

    ImageKey(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }
}
