package com.dwarfeng.dmath.algebra;

import java.util.Arrays;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dwarfeng.dfunc.DwarfFunction;
import com.dwarfeng.dfunc.StringFiledKey;
import com.dwarfeng.dfunc.cna.ArrayUtil;
import com.dwarfeng.dfunc.cna.CollectionUtil;
import com.dwarfeng.dfunc.infs.Buildable;
import com.dwarfeng.dfunc.str.NameableComparator;
import com.dwarfeng.dmath.AbstractDMath;
import com.dwarfeng.dmath.Region;

/**
 * 变量空间。
 * <p> 变量空间是指由一组 {@link VariableValue} 组成的空间。
 * <br> 该空间中的对象的顺序按照字典顺序排序，相互冲突的变量不能进入同组向量空间。
 * <p> 所谓的变量冲突，是指含有相同名称的不同变量。由于变量控件中变量的顺序是由其字典顺序决定的，
 * 如果存在同名变量，则无法从文本上对变量进行区别，从而会导致进一步的错误。
 * <br> 由于变量空间积极拒绝变量冲突，所以变量空间可以保证所有的变量都是不相同的，即在一个变量空间中的任意一个
 * 变量与另一个变量equals不相等，同时，同名变量只会出现一次。
 * <p> 变量空间的构造器是不可见的，构造变量空间需要用变量空间的构造器来完成。变量空间的结构是不可更改的，
 * 虽然变量空间中的变量都可以重新被赋值，但是变量空间中的变量不可以增加或移除。
 * 在变量空间的构造器中增加或移除元素有可能会抛出 {@link VariableConflictException}，待变量编辑完成后，即可
 * 调用 {@link Builder#build()}方法来构造变量空间。
 * <p>两个变量空间相等的前提条件是两个变量空间的所有元素均相等。
 * @author DwArFeng
 * @since 1.8
 */
public class VariableSpace extends AbstractDMath implements
Iterable<VariableValue>, Region<VariableValue>{

	/**
	 * 变量空间的构造器。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<VariableSpace>{

		private final Set<VariableValue> set;
		
		/**
		 * 生成一个变量空间构造器。
		 */
		public Builder() {
			this.set = CollectionUtil.nonNullSet(new HashSet<VariableValue>());
		}
		
		/**
		 * 向变量空间的构造器中添加指定的可变对象。
		 * @param var 指定的变量。
		 * @return 该操作是否引起了构造器的改变。
		 * @throws VariableConflictException 当添加的元素与构造器中的至少一个元素冲突。
		 * @throws NullPointerException 入口参数为 <code>null</code> 时抛出异常。
		 */
		public boolean add(VariableValue var) throws VariableConflictException{
			for(VariableValue vv : set){
				if(AlgebraUtil.checkConflict(vv, var)){
					throw new VariableConflictException(vv.getName());
				}
			}
			return set.add(var);
		}
		
		/**
		 * 向变量空间的构造器中添加一个变量空间中的所有变量。
		 * @param variableSpace 指定的变量空间。
		 * @return 该操作是否引起了构造器的改变。
		 * @throws VariableConflictException 当添加的元素与构造器中的至少一个元素冲突。
		 * @throws NullPointerException 当入口元素为 <code>null</code>时抛出异常。
		 */
		public boolean add(VariableSpace variableSpace) throws VariableConflictException{
			boolean flag = false;
			for(VariableValue vv : variableSpace){
				if(add(vv)) flag = true;
			}
			return flag;
		}
		
		/**
		 * 在变量空间移除指定的对象。
		 * @param o 指定的对象。
		 * @return 该操作是否引起了构造器的改变。
		 */
		public boolean remove(Object o){
			return set.remove(o);
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
		return DwarfFunction.getStringField(StringFiledKey.Algebra_VariableSpace);
	}
	
	/**
	 * 返回变量空间中元素的数量。
	 * @return 变量空间元素数量。
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
		if(size() == 0) return "Φ";
		
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
