package com.dwarfeng.dutil.basic.prog;

public interface Version {

    /**
     * 返回这个版本的类型。
     *
     * @return 这个版本的类型。
     */
    VersionType getVersionType();

    /**
     * 返回这个版本的长名称。
     *
     * @return 这个版本的长名称
     */
    String getLongName();

    /**
     * 返回这个版本的短名称。
     *
     * @return 这个版本的短名称。
     */
    String getShortName();
}
