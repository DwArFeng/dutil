package com.dwarfeng.dutil.basic.prog;

/**
 * 默认的版本实现形式。
 * <p>
 * 这是我最常用的版本形式。 <br>
 * 版本的形式如下：<code>alpha_0.3.2_160701_build_A</code>
 * 
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class DefaultVersion implements Version {

	/** 表示编译版本的字段 */
	protected static final String BUILD_TEXT = "build";
	/** 版本数字段之间的分隔符 */
	protected static final char VERSION_SEP = '.';
	/** 版本域字段之间的分隔符 */
	protected static final char FIELD_SEP = '_';

	/** 版本的类型 */
	protected final VersionType type;
	/** 第一个版本号 */
	protected final byte firstVersion;
	/** 第二个版本号 */
	protected final byte secondVersion;
	/** 第三个版本号 */
	protected final byte thirdVersion;
	/** 创建日期 */
	protected final String buildDate;
	/** 创建版本 */
	protected final char buildVersion;

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

		/**
		 * 构造默认的版本构造者。
		 */
		public Builder() {
		}

		/**
		 * 设置构造者中的版本类型。
		 * 
		 * @param val
		 *            指定的版本类型。
		 * @return 构造器自身。
		 */
		public Builder type(VersionType val) {
			this.type = val;
			return this;
		}

		/**
		 * 设置构造器中第一个版本的值。
		 * 
		 * @param val
		 *            指定的值。
		 * @return 构造器自身。
		 */
		public Builder firstVersion(byte val) {
			this.firstVersion = val;
			return this;
		}

		/**
		 * 设置构造器中第二个版本的值。
		 * 
		 * @param val
		 *            指定的值。
		 * @return 构造器自身。
		 */
		public Builder secondVersion(byte val) {
			this.secondVersion = val;
			return this;
		}

		/**
		 * 设置构造器中第三个版本的值。
		 * 
		 * @param val
		 *            指定的值。
		 * @return 构造器自身。
		 */
		public Builder thirdVersion(byte val) {
			this.thirdVersion = val;
			return this;
		}

		/**
		 * 设置构造器中的编译时间。
		 * 
		 * @param val
		 *            指定的编译时间。
		 * @return 构造器自身。
		 */
		public Builder buildDate(String val) {
			this.buildDate = val;
			return this;
		}

		/**
		 * 设置构造器中的编译版本号。
		 * 
		 * @param val
		 *            构造器中的编译版本号。
		 * @return 构造器自身。
		 */
		public Builder buildVersion(char val) {
			this.buildVersion = val;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.dwarfeng.dutil.basic.infs.Buildable#build()
		 */
		@Override
		public DefaultVersion build() {
			return new DefaultVersion(type, firstVersion, secondVersion, thirdVersion, buildDate, buildVersion);
		}

	}

	private DefaultVersion(VersionType type, byte firstVersion, byte secondVersion, byte thirdVersion, String buildDate,
			char buildVersion) {
		this.type = type;
		this.firstVersion = firstVersion;
		this.secondVersion = secondVersion;
		this.thirdVersion = thirdVersion;
		this.buildDate = buildDate;
		this.buildVersion = Character.toUpperCase(buildVersion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dwarfeng.dwarffunction.program.PVersion#getVersionType()
	 */
	@Override
	public VersionType getVersionType() {
		return this.type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dwarfeng.dwarffunction.program.PVersion#getLongName()
	 */
	@Override
	public String getLongName() {
		return type.getName() + FIELD_SEP + firstVersion + VERSION_SEP + secondVersion + VERSION_SEP + thirdVersion
				+ FIELD_SEP + buildDate + FIELD_SEP + BUILD_TEXT + FIELD_SEP + buildVersion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dwarfeng.dwarffunction.program.PVersion#getShortName()
	 */
	@Override
	public String getShortName() {
		return type.getName() + FIELD_SEP + firstVersion + VERSION_SEP + secondVersion + VERSION_SEP + thirdVersion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getLongName();
	}

}