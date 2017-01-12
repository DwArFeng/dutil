/**
 * ��ʽ��ѧ����
 * 
 * <p> ǰ�ð��� 
 * <br> {@linkplain com.dwarfeng.dutil.basic}
 * <br> {@linkplain com.dwarfeng.dutil.math}
 * 
 * <p> �������Դ��֮ǰ��д����math��������֮ǰ���ǳ����Ӵ�java�����math��д�ĺ��ǲ��ã���;ֻ�÷�����
 * ���ڣ��Ҵ��㽫��ǰ�Ĺ������������������������ҵĹ��߰��У�������dmath��������з�װ��
 * <br> �ð���������Դ��formula math�ļ�д��
 * <br> foth��Ŀǰ�漰���������Դ����ȷ�������ݣ�ÿ����������ݶ��������Ӧ����ѧѧ�ơ�
 * <br> foth����֧�ֹ�ʽ�ṹ����ѧ������ˣ��ð��еĶ����ںܴ�̶��϶������ƵĲ�֧�ֹ�ʽ�ṹ��math���еĶ�Ӧʵ�֡�
 * 
 * <p> <b>ͬ�����⣺</b>
 * 
 * <p> �����ϣ����е���ѧ���󶼲����̰߳�ȫ�ģ����Ҫ�������ڶ��߳��У���ע���ⲿͬ�������
 * ���е�ĳ����ѧ�����̰߳�ȫ�ģ������ĵ���ָ��������֮�⣬������඼���̲߳���ȫ�ġ�
 * 
 * <p><b>�������</b>
 * 
 * <p><b>�����Ƿ�ɱ��Լ�һ�������Ƿ���ܿɱ����</b>
 * 
 * <br>�ɱ������ָһ�������е�Ԫ�ص�״̬���Ա䣬������ָ
 * һ������Ľṹ���Ըı䡣������ʽ <code> 3 * x</code>������һ���ɱ������Ϊ<code>3 * x</code>��ֵ������<code>x</code>
 * �ı仯���仯�����ǣ��ɱ����������ڽṹ�ɱ䣬������Σ���ʽ�Ӳ��ܱ��<code>3 + x</code>Ҳ���ǽṹ���޷��仯�ġ�
 * ���ɱ����ɱ���ָһ�������ǽṹ����ֵ������������״̬�Թ��������Զ���ɱ䡣<b>foth���е�����ʵ���඼�ǿɱ�ġ�</b>
 * <br>���տɱ����Ķ���һ���ǿɱ���󣬷�֮��ֻ���ղ��ɱ����Ķ���һ���ǲ��ɱ����
 * <br>�������ɱ���󲢲������κ�ʱ�򶼿ɱ༭������һ���ɱ������� {@link com.dwarfeng.dutil.foth.algebra.FothValue}����
 * ������������ǿɱ�Ķ���Ȼ���ڴ������ʱ��ȴ���뼸����������������Ȼ��������ǿɱ����ȴû���κη����ܹ��޸�
 * ���������ˣ��ɱ����̳� {@link com.dwarfeng.dutil.foth.DFoth}�ӿڣ�����ͨ������
 * {@link com.dwarfeng.dutil.foth.DFoth#canModify()}��������ѯ����྿���Ƿ�ɱ䣬���ڸð��е�ʵ���඼�ǿɱ�ģ�����
 * �ð��е�����ʵ���඼��Ӽ̳иýӿڡ�
 * 
 * <p><b>���ڹ��죺</b>
 * <br> �˰�����ѧ�����ð��е���������math���ж��ж�Ӧ��ʵ�֣����������ֶ�Ӧ��ϵ�ģ����Ậ�����µĹ��췽������
 * ��ڲ���Ϊmath���ж�Ӧ���࣬��ͨ��math���ж�Ӧ����������һ������Ԫ�ض��ǳ����Ķ�Ӧ���޽ṹfoth����
 * ͬʱ�����ӵ�������Ķ�Ӧ��ϵ��foth���е���ͬ�����п���ת��Ϊmath��ķ������÷�����to��ͷ����math���ж�Ӧ��������β��
 * 
 * <p><b>�������㣨�����Ǵ�������</b>
 * <br> ��ѧ���е������Ϊ��ʽ�����ֵ���㡣��ʽ������ͨ����ʽ�ཫ��ͬ�Ķ������һ����ʽ����������е� TODO ��ʽ
 * ��ʽ�������ڱ���ԭ�еĶ���Ľṹ�Ͻ��е����㣬��ˣ����ԭ�ж����к��пɱ��������ʽ����Ҳ�ǿɱ�ġ�
 * ��һ��������ֵ���㣬�������Դ������е� {@link com.dwarfeng.dutil.foth.linalge.DefaultFormulaRowVector}��������������������㷽������Щ�����Ƿ�װ������ڲ���
 * �ڵ�����Щ����ʱ����Զ������ڵ� {@link com.dwarfeng.dutil.foth.algebra.FothValue} �������ȡֵ������
 * ���¶���ֻ��������֮���ֵ������ֵ����ʱ�ƻ��Եģ�ͨ��ֵ���㷵�صĽ��������������Ԫ�ص����ԣ���ʹ���������
 * ����ʱ�ɱ�ģ�ͨ�������õĽ������Ҳͬ���ǲ����޸ĵġ�
 * 
 * <p> �ð��еļ��㶼��ͨ�� {@link com.dwarfeng.dutil.foth.algebra.FothValue} ���еģ���ˣ��ڼ�������У�������е�double
 * ���в���ͷ�������Ч�ʵĵ��¡����˵������������������ٶ�Ϊǿ��ģ��ð����������ڶ���ѧ��ʽ����
 * ���нṹ�ϵı��֣����ҿ�����ȡ�����ռ䣬����Ľ��и�ֵ������
 * 
 * @author DwArFeng
 * @since 0.0.2-beta
 */
package com.dwarfeng.dutil.foth;