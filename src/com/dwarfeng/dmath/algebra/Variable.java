package com.dwarfeng.dmath.algebra;

import java.util.Objects;

import com.dwarfeng.dfunc.DwarfFunction;
import com.dwarfeng.dfunc.StringFiledKey;
import com.dwarfeng.dmath.AbstractDMath;

/**
 * 变量。
 * <p> 该类不接受可变对象，所有传入其中的值对象都会进行取值，以<code>double</code>。
 * 的形式存储。
 * @author DwArFeng
 * @since 1.8
 */
public class Variable extends AbstractDMath implements VariableValue {
	
	/**变量的值*/
	protected double val;
	/**变量的名称*/
	protected final String name;
	
	/**
	 * 生成一个默认的，值为0的变量。
	 * @param name 变量的名称。
	 */
	public Variable(String name) {
		this(name, 0d);
	}
	
	/**
	 * 生成一个值为指定值接口的当前值的变量。
	 * @param name 变量的名称。
	 * @param valueable 指定的值接口。
	 * @throws IllegalArgumentException name参数为 <code>null</code>或非法。
	 * @throws NullPointerException 入口参数为 <code>null</code>时抛出异常。
	 */
	public Variable(String name, Valueable valueable) {
		if(Objects.isNull(name) || name.equals("")){
			throw new IllegalArgumentException(DwarfFunction.getStringField(StringFiledKey.Variable_1));
		}
		Objects.requireNonNull(valueable, DwarfFunction.getStringField(StringFiledKey.Variable_0));
		
		this.name = name;
		this.val = valueable.value();
	}
	
	/**
	 * 生成一个值为指定值的变量。
	 * @param name 变量的名称。
	 * @param d 指定的值。
	 * @throws IllegalArgumentException name参数为 <code>null</code> 或非法。
	 */
	public Variable(String name, double d) {
		if(Objects.isNull(name) || name.equals("")){
			throw new IllegalArgumentException(DwarfFunction.getStringField(StringFiledKey.Variable_1));
		}
		
		this.name = name;
		this.val = d;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.algebra.Valueable#value()
	 */
	@Override
	public double value() {
		return val;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.infs.Nameable#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.algebra.VariableValue#setValue(double)
	 */
	@Override
	public void setValue(double value) {
		this.val = value;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.AbstractDMath#getMathName()
	 */
	@Override
	public String getMathName() {
		return DwarfFunction.getStringField(StringFiledKey.Algebra_Variable);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.AbstractDMath#getExpression()
	 */
	@Override
	public String getExpression() {
		return getName();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.algebra.NumBase#getVariableSpace()
	 */
	@Override
	public VariableSpace getVariableSpace() {
		VariableSpace.Builder builder = new VariableSpace.Builder();
		try {
			builder.add(this);
		} catch (VariableConflictException e) {
			//由于只有一个变量，所以绝对不会抛出异常。
		}
		return builder.build();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.AbstractDMath#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder()
				.append(getMathName())
				.append(" : ")
				.append(getExpression())
				.append(" = ")
				.append(val)
				.toString();
	}

}
