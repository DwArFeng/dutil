package com.dwarfeng.dutil.basic.stack.version;

/**
 * 编程中常见的版本类型。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public enum VersionType {

    /**
     * 内测版本
     */
    ALPHA("Alpha"),
    /**
     * 公测版本
     */
    BETA("Beta"),
    /**
     * 发布版本
     */
    RELEASE("Release"),

    ;

    private final String displayName;

    VersionType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 返回版本类型的展示名称。
     *
     * @return 版本类型的展示名称。
     */
    public String displayName() {
        return displayName;
    }
}
