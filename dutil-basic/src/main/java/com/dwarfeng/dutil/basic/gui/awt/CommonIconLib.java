package com.dwarfeng.dutil.basic.gui.awt;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.str.Name;

/**
 * 公共图标库。
 *
 * <p>
 * 该图片库整合了大量的图标，所有的图标都具有黑、蓝、紫、绿、红、黄六色。<br>
 * 调用图标的代码如下：
 *
 * <pre>
 * <code>
 * public final static Image getInternalIconLib(InternalIconLib name, Size2D size){
 * 	<b>return ImageUtil.getInternalImage(name,size);</b>
 * }
 * </code>
 * </pre>
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public enum CommonIconLib implements Name {

    /**
     * 未知图标，圆圈问号图案
     */
    UNKNOWN_BLACK("unknown_black.png"),
    /**
     * 未知图标，圆圈问号图案
     */
    UNKNOWN_BLUE("unknown_blue.png"),
    /**
     * 未知图标，圆圈问号图案
     */
    UNKNOWN_PURPLE("unknown_purple.png"),
    /**
     * 未知图标，圆圈问号图案
     */
    UNKNOWN_GREEN("unknown_green.png"),
    /**
     * 未知图标，圆圈问号图案
     */
    UNKNOWN_RED("unknown_red.png"),
    /**
     * 未知图标，圆圈问号图案
     */
    UNKNOWN_YELLOW("unknown_yellow.png"),
    /**
     * 保存图标，磁盘图案
     */
    SAVE_BLACK("save_black.png"),
    /**
     * 保存图标，磁盘图案
     */
    SAVE_BLUE("save_blue.png"),
    /**
     * 保存图标，磁盘图案
     */
    SAVE_PURPLE("save_purple.png"),
    /**
     * 保存图标，磁盘图案
     */
    SAVE_GREEN("save_green.png"),
    /**
     * 保存图标，磁盘图案
     */
    SAVE_RED("save_red.png"),
    /**
     * 保存图标，磁盘图案
     */
    SAVE_YELLOW("save_yellow.png"),
    /**
     * 另存为图标，磁盘箭头图案
     */
    SAVEAS_BLACK("saveas_black.png"),
    /**
     * 另存为图标，磁盘箭头图案
     */
    SAVEAS_BLUE("saveas_blue.png"),
    /**
     * 另存为图标，磁盘箭头图案
     */
    SAVEAS_PURPLE("saveas_purple.png"),
    /**
     * 另存为图标，磁盘箭头图案
     */
    SAVEAS_GREEN("saveas_green.png"),
    /**
     * 另存为图标，磁盘箭头图案
     */
    SAVEAS_RED("saveas_red.png"),
    /**
     * 另存为图标，磁盘箭头图案
     */
    SAVEAS_YELLOW("saveas_yellow.png"),
    /**
     * 图片读取失败图标，图裂图案
     */
    IMG_LOAD_FAILED_BLACK("img_load_failed_black.png"),
    /**
     * 图片读取失败图标，图裂图案
     */
    IMG_LOAD_FAILED_BLUE("img_load_failed_blue.png"),
    /**
     * 图片读取失败图标，图裂图案
     */
    IMG_LOAD_FAILED_PURPLE("img_load_failed_purple.png"),
    /**
     * 图片读取失败图标，图裂图案
     */
    IMG_LOAD_FAILED_GREEN("img_load_failed_green.png"),
    /**
     * 图片读取失败图标，图裂图案
     */
    IMG_LOAD_FAILED_RED("img_load_failed_red.png"),
    /**
     * 图片读取失败图标，图裂图案
     */
    IMG_LOAD_FAILED_YELLOW("img_load_failed_yellow.png"),
    /**
     * 设置图片，齿轮图案
     */
    SETTING_BLACK("setting_black.png"),
    /**
     * 设置图片，齿轮图案
     */
    SETTING_BLUE("setting_blue.png"),
    /**
     * 设置图片，齿轮图案
     */
    SETTING_PURPLE("setting_purple.png"),
    /**
     * 设置图片，齿轮图案
     */
    SETTING_GREEN("setting_green.png"),
    /**
     * 设置图片，齿轮图案
     */
    SETTING_RED("setting_red.png"),
    /**
     * 设置图片，齿轮图案
     */
    SETTING_YELLOW("setting_yellow.png"),
    /**
     * 复制图片，双文件图案
     */
    COPY_BLACK("copy_black.png"),
    /**
     * 复制图片，双文件图案
     */
    COPY_BLUE("copy_blue.png"),
    /**
     * 复制图片，双文件图案
     */
    COPY_PURPLE("copy_purple.png"),
    /**
     * 复制图片，双文件图案
     */
    COPY_GREEN("copy_green.png"),
    /**
     * 复制图片，双文件图案
     */
    COPY_RED("copy_red.png"),
    /**
     * 复制图片，双文件图案
     */
    COPY_YELLOW("copy_yellow.png"),
    /**
     * 粘贴图片，剪贴板文件图案
     */
    PASTE_BLACK("paste_black.png"),
    /**
     * 粘贴图片，剪贴板文件图案
     */
    PASTE_BLUE("paste_blue.png"),
    /**
     * 粘贴图片，剪贴板文件图案
     */
    PASTE_PURPLE("paste_purple.png"),
    /**
     * 粘贴图片，剪贴板文件图案
     */
    PASTE_GREEN("paste_green.png"),
    /**
     * 粘贴图片，剪贴板文件图案
     */
    PASTE_RED("paste_red.png"),
    /**
     * 粘贴图片，剪贴板文件图案
     */
    PASTE_YELLOW("paste_yellow.png"),
    /**
     * 撤销图片，左箭头图案
     */
    UNDO_BLACK("undo_black.png"),
    /**
     * 撤销图片，左箭头图案
     */
    UNDO_BLUE("undo_blue.png"),
    /**
     * 撤销图片，左箭头图案
     */
    UNDO_PURPLE("undo_purple.png"),
    /**
     * 撤销图片，左箭头图案
     */
    UNDO_GREEN("undo_green.png"),
    /**
     * 撤销图片，左箭头图案
     */
    UNDO_RED("undo_red.png"),
    /**
     * 撤销图片，左箭头图案
     */
    UNDO_YELLOW("undo_yellow.png"),
    /**
     * 重做图片，右箭头图案
     */
    REDO_BLACK("redo_black.png"),
    /**
     * 重做图片，右箭头图案
     */
    REDO_BLUE("redo_blue.png"),
    /**
     * 重做图片，右箭头图案
     */
    REDO_PURPLE("redo_purple.png"),
    /**
     * 重做图片，右箭头图案
     */
    REDO_GREEN("redo_green.png"),
    /**
     * 重做图片，右箭头图案
     */
    REDO_RED("redo_red.png"),
    /**
     * 重做图片，右箭头图案
     */
    REDO_YELLOW("redo_yellow.png"),

    ;

    private final String name;

    CommonIconLib(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return DwarfUtil.IMAGE_ROOT + name;
    }
}
