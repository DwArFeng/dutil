package com.dwarfeng.dutil.basic.prog;

/**
 * Ĭ�ϵİ汾ʵ����ʽ��
 * <p> ��������õİ汾��ʽ��
 * <br> �汾����ʽ���£�<code>alpha_0.3.2_160701_build_A</code>
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class DefaultVersion implements Version {

	/**��ʾ����汾���ֶ�*/
	protected static final String BUILD_TEXT = "build";
	/**�汾���ֶ�֮��ķָ���*/
	protected static final char VERSION_SEP = '.';
	/**�汾���ֶ�֮��ķָ���*/
	protected static final char FIELD_SEP = '_';
	
	/**�汾������*/
	protected final VersionType type;
	/**��һ���汾��*/
	protected final byte firstVersion;
	/**�ڶ����汾��*/
	protected final byte secondVersion;
	/**�������汾��*/
	protected final byte thirdVersion;
	/**��������*/
	protected final String buildDate;
	/**�����汾*/
	protected final char buildVersion;
	
	/**
	 * Ĭ�ϰ汾�Ĺ����ߡ�
	 * @author DwArFeng
	 * @since 0.0.2-beta
	 */
	public static class Builder implements Buildable<DefaultVersion>{
		
		private VersionType type = VersionType.RELEASE;
		private byte firstVersion = 0;
		private byte secondVersion = 0;
		private byte thirdVersion = 0;
		private String buildDate = "19700101";
		private char buildVersion = 'A';
		
		/**
		 * ����Ĭ�ϵİ汾�����ߡ�
		 */
		public Builder(){}
		
		/**
		 * ���ù������еİ汾���͡�
		 * @param val ָ���İ汾���͡�
		 * @return ������������
		 */
		public Builder type(VersionType val){
			this.type = val;
			return this;
		}
		
		/**
		 * ���ù������е�һ���汾��ֵ��
		 * @param val ָ����ֵ��
		 * @return ������������
		 */
		public Builder firstVersion(byte val){
			this.firstVersion = val;
			return this;
		}
		
		/**
		 * ���ù������еڶ����汾��ֵ��
		 * @param val ָ����ֵ��
		 * @return ������������
		 */
		public Builder secondVersion(byte val){
			this.secondVersion = val;
			return this;
		}
		
		/**
		 * ���ù������е������汾��ֵ��
		 * @param val ָ����ֵ��
		 * @return ������������
		 */
		public Builder thirdVersion(byte val){
			this.thirdVersion = val;
			return this;
		}
		
		/**
		 * ���ù������еı���ʱ�䡣
		 * @param val ָ���ı���ʱ�䡣
		 * @return ������������
		 */
		public Builder buildDate(String val){
			this.buildDate = val;
			return this;
		}
		
		/**
		 * ���ù������еı���汾�š�
		 * @param val �������еı���汾�š�
		 * @return ������������
		 */
		public Builder buildVersion(char val){
			this.buildVersion = val;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dutil.basic.infs.Buildable#build()
		 */
		@Override
		public DefaultVersion build() {
			return new DefaultVersion(type, firstVersion, secondVersion, thirdVersion, buildDate, buildVersion);
		}
		
		
	}
	
	private DefaultVersion(
			VersionType type,
			byte firstVersion,
			byte secondVersion,
			byte thirdVersion,
			String buildDate,
			char buildVersion
	) {
		this.type = type;
		this.firstVersion = firstVersion;
		this.secondVersion = secondVersion;
		this.thirdVersion = thirdVersion;
		this.buildDate = buildDate;
		this.buildVersion = Character.toUpperCase(buildVersion);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.PVersion#getVersionType()
	 */
	@Override
	public VersionType getVersionType() {
		return this.type;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.PVersion#getLongName()
	 */
	@Override
	public String getLongName() {
		return 
				type.getName() + FIELD_SEP +
				firstVersion + VERSION_SEP + secondVersion + VERSION_SEP + thirdVersion + FIELD_SEP +
				buildDate + FIELD_SEP + BUILD_TEXT + FIELD_SEP + buildVersion;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.PVersion#getShortName()
	 */
	@Override
	public String getShortName() {
		return 
				type.getName() + FIELD_SEP +
				firstVersion + VERSION_SEP + secondVersion + VERSION_SEP + thirdVersion;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return getLongName();
	}

}