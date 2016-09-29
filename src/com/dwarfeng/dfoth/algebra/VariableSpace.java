package com.dwarfeng.dfoth.algebra;

import java.util.Arrays;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dwarfeng.dfunc.DwarfFunction;
import com.dwarfeng.dfunc.StringFieldKey;
import com.dwarfeng.dfunc.cna.ArrayUtil;
import com.dwarfeng.dfunc.cna.CollectionUtil;
import com.dwarfeng.dfunc.infs.Buildable;
import com.dwarfeng.dfunc.str.NameableComparator;
import com.dwarfeng.dmath.AbstractDMath;
import com.dwarfeng.dmath.Region;

/**
 * �����ռ䡣
 * <p> �����ռ���ָ��һ�� {@link VariableValue} ��ɵĿռ䡣
 * <br> �ÿռ��еĶ����˳�����ֵ�˳�������໥��ͻ�ı������ܽ���ͬ�������ռ䡣
 * <p> ��ν�ı�����ͻ����ָ������ͬ���ƵĲ�ͬ���������ڱ����ؼ��б�����˳���������ֵ�˳������ģ�
 * �������ͬ�����������޷����ı��϶Ա����������𣬴Ӷ��ᵼ�½�һ���Ĵ���
 * <br> ���ڱ����ռ�����ܾ�������ͻ�����Ա����ռ���Ա�֤���еı������ǲ���ͬ�ģ�����һ�������ռ��е�����һ��
 * ��������һ������equals����ȣ�ͬʱ��ͬ������ֻ�����һ�Ρ�
 * <p> �����ռ�Ĺ������ǲ��ɼ��ģ���������ռ���Ҫ�ñ����ռ�Ĺ���������ɡ������ռ�Ľṹ�ǲ��ɸ��ĵģ�
 * ��Ȼ�����ռ��еı������������±���ֵ�����Ǳ����ռ��еı������������ӻ��Ƴ���
 * �ڱ����ռ�Ĺ����������ӻ��Ƴ�Ԫ���п��ܻ��׳� {@link VariableConflictException}���������༭��ɺ󣬼���
 * ���� {@link Builder#build()}��������������ռ䡣
 * <p>���������ռ���ȵ�ǰ�����������������ռ������Ԫ�ؾ���ȡ�
 * @author DwArFeng
 * @since 1.8
 */
public class VariableSpace extends AbstractDMath implements
Iterable<VariableValue>, Region<VariableValue>{

	/**�յı�������*/
	public static final VariableSpace EMPTY = new Builder().build();
	
	/**
	 * �����ռ�Ĺ�������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<VariableSpace>{

		private final Set<VariableValue> set;
		
		/**
		 * ����һ�������ռ乹������
		 */
		public Builder() {
			this.set = CollectionUtil.nonNullSet(new HashSet<VariableValue>());
		}
		
		/**
		 * ������ռ�Ĺ�����������ָ���Ŀɱ����
		 * @param var ָ���ı�����
		 * @return ������������
		 * @throws VariableConflictException �����ӵ�Ԫ���빹�����е�����һ��Ԫ�س�ͻ��
		 * @throws NullPointerException ��ڲ���Ϊ <code>null</code> ʱ�׳��쳣��
		 */
		public Builder add(VariableValue var){
			for(VariableValue vv : set){
				if(AlgebraUtil.checkConflict(vv, var)){
					throw new VariableConflictException(vv.getName());
				}
			}
			set.add(var);
			return this;
		}
		
		/**
		 * ������ռ�Ĺ�����������һ�������ռ��е����б�����
		 * @param variableSpace ָ���ı����ռ䡣
		 * @return ������������
		 * @throws VariableConflictException �����ӵ�Ԫ���빹�����е�����һ��Ԫ�س�ͻ��
		 * @throws NullPointerException �����Ԫ��Ϊ <code>null</code>ʱ�׳��쳣��
		 */
		public Builder add(VariableSpace variableSpace){
			for(VariableValue vv : variableSpace){
				add(vv);
			}
			return this;
		}
		
		/**
		 * ������ؼ��Ĺ�����������һ��ֵ�����е����б�����
		 * @param valueable ָ����ֵ����
		 * @return ������������
		 * @throws VariableConflictException ��ֵ�����еı����빹�����е�����һ��������ͻ��
		 * @throws NullPointerException ��ڲ���Ϊ <code>null</code> ����ֵ�����еı����ռ�Ϊ <code>null</code>��
		 */
		public Builder add(FValue valueable){
			add(valueable.getVariableSpace());
			return this;
		}
		
		/**
		 * �ڱ����ռ��Ƴ�ָ���Ķ���
		 * @param o ָ���Ķ���
		 * @return ������������
		 */
		public Builder remove(Object o){
			set.remove(o);
			return this;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dfunc.infs.Buildable#build()
		 */
		@Override
		public VariableSpace build() {
			VariableValue[] vars = set.toArray(new VariableValue[0]);
			Arrays.sort(vars, new NameableComparator());
			return new VariableSpace(vars);
		}
		
	}
	
	protected final VariableValue[] vars;
	
	private VariableSpace(VariableValue[] vars){
		this.vars = vars;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<VariableValue> iterator() {
		return new VsIterator();
	}
	
	private final class VsIterator implements Iterator<VariableValue>{

		private int i = 0;
		
		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return i < vars.length;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public VariableValue next() {
			return vars[i++];
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.AbstractDMath#getMathName()
	 */
	@Override
	public String getMathName() {
		return DwarfFunction.getStringField(StringFieldKey.Algebra_VariableSpace);
	}
	
	/**
	 * ���ر����ռ���Ԫ�ص�������
	 * @return �����ռ�Ԫ��������
	 */
	public int size(){
		return vars.length;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.AbstractDMath#getExpression()
	 */
	@Override
	public String getExpression() {
		if(size() == 0) return "��";
		
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		
		try{
			sb.append("[");
			for(VariableValue var : vars){
				formatter.format("%s = %.4f, ", var.getName(), var.value());
			}
			
			sb.delete(sb.length()-2, sb.length()).append("]");
		}finally{
			formatter.close();
		}
		
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dmath.Region#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(VariableValue t) {
		return ArrayUtil.contains(vars, t);
	}

}