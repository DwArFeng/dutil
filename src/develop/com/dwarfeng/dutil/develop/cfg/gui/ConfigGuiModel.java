package com.dwarfeng.dutil.develop.cfg.gui;

import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigValueChecker;

/**
 * 配置界面模型。
 * @author  DwArFeng
 * @since 1.8
 */
public interface ConfigGuiModel{

	/**
	 * 获取指定序号处的配置键。
	 * @param index 指定的序号。
	 * @return 指定序号处的配置键。
	 * @throws IndexOutOfBoundsException 序号越界。
	 */
	public ConfigKey getConfigKey(int index);
	
	/**
	 * 获取指定序号处的当前值。
	 * @param index 指定的序号。
	 * @return 指定序号处的当前值。
	 * @throws IndexOutOfBoundsException 序号越界。
	 */
	public String getCurrentValue(int index);
	
	/**
	 * 获取指定序号处的默认值。
	 * @param index 指定的序号。
	 * @return 指定序号处的默认值。
	 * @throws IndexOutOfBoundsException 序号越界。
	 */
	public String getDefaultValue(int index);
	
	/**
	 * 获取指定序号处的值检查器。
	 * @param index 指定的序号。
	 * @return 指定序号处的值检查器。
	 * @throws IndexOutOfBoundsException 序号越界。
	 */
	public ConfigValueChecker getConfigValueChecker(int index);
	
	/**
	 * 返回该模型中的配置数量。
	 * @return 配置数量。
	 */
	public int size();
	
	/**
	 * 检测该模型中指定序号处的入口的当前值是否有效。
	 * @param index 指定的序号。
	 * @return 指定序号处的元素是否有效。
	 * @throws IndexOutOfBoundsException 序号越界。
	 */
	public default boolean isValid(int index){
		return getConfigValueChecker(index).isValid(getCurrentValue(index));
	}
	
	/**
	 * 检测该模型中指定序号处的入口的当前值是否无效。
	 * @param index 指定的序号。
	 * @return 指定序号处的元素是否无效。
	 * @throws IndexOutOfBoundsException 序号越界。
	 */
	public default boolean nonValid(int index){
		return getConfigValueChecker(index).nonValid(getCurrentValue(index));
	}
	
	public void addValue();
	
	/**
	 * 设置该模型中指定序号处的当前值。
	 * @param index 指定的序号。
	 * @param value 新的当前值。
	 * @throws IndexOutOfBoundsException 序号越界。
	 */
	public void setValue(int index, String value);
	
	/**
	 * 重置指定序号处当前值为默认值。
	 * @param index 指定的序号。
	 * @throws IndexOutOfBoundsException 序号越界。
	 */
	public default void resetValue(int index){
		setValue(index, getDefaultValue(index));
	}
	
	/**
	 * 增加观察器。
	 * @param obverser 指定的配置界面观察器。
	 * @return 是否增加成功。
	 */
	public boolean addObverser(ConfigGuiModelObverser obverser);
	
	/**
	 * 移除观察器。
	 * @param obverser 指定的配置界面观察器。
	 * @return 是否移除成功。
	 */
	public boolean removeObverser(ConfigGuiModelObverser obverser);
	
	/**
	 * 清除所有观察器。
	 */
	public void clearObverser();
	
}
