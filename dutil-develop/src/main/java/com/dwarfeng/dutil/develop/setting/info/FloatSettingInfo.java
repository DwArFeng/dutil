package com.dwarfeng.dutil.develop.setting.info;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dwarfeng.dutil.develop.setting.AbstractSettingInfo;

/**
 * Float配置信息。
 * 
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class FloatSettingInfo extends AbstractSettingInfo {

	private String lastCheckedValue = null;
	private Float lastParsedValue = null;

	private final Lock lock = new ReentrantLock();

	/**
	 * 生成一个新的Float配置信息。
	 * 
	 * @param defaultValue
	 *            指定的默认值。
	 * @throws NullPointerException
	 *             指定的入口参数为 <code> null </code>。
	 * @throws IllegalArgumentException
	 *             指定的默认值不能通过自身检查。
	 */
	public FloatSettingInfo(String defaultValue) throws NullPointerException, IllegalArgumentException {
		super(defaultValue);
		checkDefaultValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return FloatSettingInfo.class.hashCode() * 61 + defaultValue.hashCode() * 17;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (Objects.isNull(obj))
			return false;
		if (!(obj.getClass() == FloatSettingInfo.class))
			return false;

		FloatSettingInfo that = (FloatSettingInfo) obj;
		return Objects.equals(this.defaultValue, that.defaultValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "FloatSettingInfo [defaultValue=" + defaultValue + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isNonNullValid(String value) {
		lock.lock();
		try {
			if (Objects.equals(value, lastCheckedValue))
				return Objects.nonNull(lastParsedValue);

			try {
				lastCheckedValue = value;
				lastParsedValue = Float.parseFloat(value);
			} catch (Exception e) {
				lastParsedValue = null;
				return false;
			}
			return true;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object parseValidValue(String value) {
		lock.lock();
		try {
			if (Objects.equals(value, lastCheckedValue))
				return lastParsedValue;

			try {
				lastCheckedValue = value;
				lastParsedValue = Float.parseFloat(value);
				return lastParsedValue;
			} catch (Exception e) {
				lastCheckedValue = null;
				lastParsedValue = null;
				throw new IllegalStateException();
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String parseNonNullObject(Object object) {
		if (!(object instanceof Float))
			return null;

		return Float.toString((float) object);
	}

}
