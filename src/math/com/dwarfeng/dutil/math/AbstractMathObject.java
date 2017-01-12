package com.dwarfeng.dutil.math;

/**
 * ��ѧ��������ࡣ
 * <p> ������������ѧ����ĳ����࣬��������������ѧ����Ӧ�þ��еĳ��󷽷���
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public abstract class AbstractMathObject implements MathObject{

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.DMath#getMathName()
	 */
	@Override
	public abstract String getMathName();
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.DMath#getExpression()
	 */
	@Override
	public abstract String getExpression();
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder()
				.append(getMathName())
				.append(" : ")
				.append(getExpression())
				.toString();
	}
	
}