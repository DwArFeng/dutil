package com.dwarfeng.dwarffunction.program;

public interface Version {
	
	/**
	 * ��������汾�����͡�
	 * @return ����汾�����͡�
	 */
	public VersionType getVersionType();
	
	/**
	 * ��������汾�ĳ����ơ�
	 * @return ����汾�ĳ�����
	 */
	public String getLongName();
	
	/**
	 * ��������汾�Ķ����ơ�
	 * @return ����汾�Ķ����ơ�
	 */
	public String getShortName();

}