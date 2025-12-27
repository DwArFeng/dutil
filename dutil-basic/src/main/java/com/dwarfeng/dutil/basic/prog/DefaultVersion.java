package com.dwarfeng.dutil.basic.prog;

/**
 * 默认的版本实现形式。
 *
 * <p>
 * 这是我最常用的版本形式。<br>
 * 版本的形式如下：<code>alpha_0.3.2_160701_build_A</code>
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class DefaultVersion implements Version {

    /**
     * 表示编译版本的字段。
     */
    protected static final String BUILD_TEXT = "build";
    /**
     * 表示预览版本的字段。
     */
    protected static final String SNAPSHOT_TEXT = "SNAPSHOT";
    /**
     * 版本数字段之间的分隔符。
     */
    protected static final char VERSION_SEP = '.';
    /**
     * 版本域字段之间的分隔符。
     */
    protected static final char FIELD_SEP = '_';

    /**
     * 版本的类型。
     */
    protected final VersionType type;
    /**
     * 第一个版本号。
     */
    protected final byte firstVersion;
    /**
     * 第二个版本号。
     */
    protected final byte secondVersion;
    /**
     * 第三个版本号。
     */
    protected final byte thirdVersion;
    /**
     * 创建日期。
     */
    protected final String buildDate;
    /**
     * 创建版本。
     */
    protected final char buildVersion;
    /**
     * 是否是预览版。
     */
    protected final boolean snapshot;

    /**
     * 默认版本的构造者。
     *
     * @author DwArFeng
     * @since 0.0.2-beta
     */
    public static class Builder implements Buildable<DefaultVersion> {

        private VersionType type = VersionType.RELEASE;
        private byte firstVersion = 0;
        private byte secondVersion = 0;
        private byte thirdVersion = 0;
        private String buildDate = "19700101";
        private char buildVersion = 'A';
        private boolean snapshot = false;

        /**
         * 构造默认的版本构造者。
         */
        public Builder() {
        }

        /**
         * 设置构造者中的版本类型。
         *
         * @param val 指定的版本类型。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setType(VersionType)} 替代。
         */
        public Builder type(VersionType val) {
            this.type = val;
            return this;
        }

        /**
         * 设置构造者中的版本类型。
         *
         * @param versionType 指定的版本类型。
         * @return 构造器自身。
         */
        public Builder setType(VersionType versionType) {
            this.type = versionType;
            return this;
        }

        /**
         * 设置构造器中第一个版本的值。
         *
         * @param val 指定的值。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setFirstVersion(byte)} 代替。
         */
        public Builder firstVersion(byte val) {
            this.firstVersion = val;
            return this;
        }

        /**
         * 设置构造器中第一个版本的值。
         *
         * @param firstVersion 指定的值。
         * @return 构造器自身。
         */
        public Builder setFirstVersion(byte firstVersion) {
            this.firstVersion = firstVersion;
            return this;
        }

        /**
         * 设置构造器中第二个版本的值。
         *
         * @param val 指定的值。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setSecondVersion(byte)} 代替。
         */
        public Builder secondVersion(byte val) {
            this.secondVersion = val;
            return this;
        }

        /**
         * 设置构造器中第二个版本的值。
         *
         * @param secondVersion 指定的值。
         * @return 构造器自身。
         */
        public Builder setSecondVersion(byte secondVersion) {
            this.secondVersion = secondVersion;
            return this;
        }

        /**
         * 设置构造器中第三个版本的值。
         *
         * @param val 指定的值。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setThirdVersion(byte)} 代替。
         */
        public Builder thirdVersion(byte val) {
            this.thirdVersion = val;
            return this;
        }

        /**
         * 设置构造器中第三个版本的值。
         *
         * @param thirdVersion 指定的值。
         * @return 构造器自身。
         */
        public Builder setThirdVersion(byte thirdVersion) {
            this.thirdVersion = thirdVersion;
            return this;
        }

        /**
         * 设置构造器中的编译时间。
         *
         * @param val 指定的编译时间。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setBuildDate(String)} 代替。
         */
        public Builder buildDate(String val) {
            this.buildDate = val;
            return this;
        }

        /**
         * 设置构造器中的编译时间。
         *
         * @param buildDate 指定的编译时间。
         * @return 构造器自身。
         */
        public Builder setBuildDate(String buildDate) {
            this.buildDate = buildDate;
            return this;
        }

        /**
         * 设置构造器中的编译版本号。
         *
         * @param val 构造器中的编译版本号。
         * @return 构造器自身。
         * @deprecated 该方法被 {@link #setBuildVersion(char)} 代替。
         */
        public Builder buildVersion(char val) {
            this.buildVersion = val;
            return this;
        }

        /**
         * 设置构造器中的编译版本号。
         *
         * @param buildVersion 构造器中的编译版本号。
         * @return 构造器自身。
         */
        public Builder setBuildVersion(char buildVersion) {
            this.buildVersion = buildVersion;
            return this;
        }

        /**
         * 设置构造器中的预览版标记。
         *
         * @param snapshot 指定的预览版标记。
         * @return 构造器自身。
         */
        public Builder setSnapshot(boolean snapshot) {
            this.snapshot = snapshot;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public DefaultVersion build() {
            return new DefaultVersion(type, firstVersion, secondVersion, thirdVersion, buildDate, buildVersion,
                    snapshot);
        }

    }

    private DefaultVersion(VersionType type, byte firstVersion, byte secondVersion, byte thirdVersion, String buildDate,
                           char buildVersion, boolean snapshot) {
        this.type = type;
        this.firstVersion = firstVersion;
        this.secondVersion = secondVersion;
        this.thirdVersion = thirdVersion;
        this.buildDate = buildDate;
        this.buildVersion = Character.toUpperCase(buildVersion);
        this.snapshot = snapshot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionType getVersionType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLongName() {
        return type.getName() + FIELD_SEP + firstVersion + VERSION_SEP + secondVersion + VERSION_SEP + thirdVersion
                + FIELD_SEP + buildDate + FIELD_SEP + BUILD_TEXT + FIELD_SEP + buildVersion
                + (snapshot ? FIELD_SEP + SNAPSHOT_TEXT : "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getShortName() {
        return type.getName() + FIELD_SEP + firstVersion + VERSION_SEP + secondVersion + VERSION_SEP + thirdVersion
                + (snapshot ? FIELD_SEP + SNAPSHOT_TEXT : "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getLongName();
    }
}
