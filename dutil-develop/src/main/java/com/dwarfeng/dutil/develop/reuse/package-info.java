/**
 * 复用池。
 * 
 * <p>
 * 复用池维护着一个对象和条件的映射，对象对应的条件用于判断该对象是否应该继续存在下去。 <br>
 * 如在经典的时间复用池维护着对象和时间条件的映射。 如果某一个对象在一定的时间内没有被使用（对象对应的条件没有被重置最后使用时间），
 * 则对象对应的条件则会判断该对象已经不符合复用条件，需要用户使用移除方法将这个对象进行移除。
 * 
 * <p>
 * 复用池中持有着一个条件生成器。每当对象被添加进入复用池中，复用池生成器就会根据指定的对象生成与对象对应的条件。
 * 
 * <p>
 * 条件对象中包含更新和判断两个方法。其中，更新方法用于更新的条件状态，判断方法用于判断该条件对应的对象是否已经满足移除要求。
 * 需要注意的是，条件复用池是被动触发的，本身不具备任何主动判断的功能， 用户必须根据实际情况主动的调用条件的更新方法。
 * 如常见的时间复用池中，用户需要使用一个计时器，定期的调用复用池的全部对象对应条件的更新方法。
 * 
 * <p>
 * 由于复用池的功能原因，复用池不支持维护 <code>null</code> 元素，试图向维护池中添加 <code>null</code>
 * 元素都会引起添加失败。
 * 
 * <p>
 * 虽然复用池维护着对象集合， 但并不建议将复用池当做存储集合来使用。 这是因为复用池的功能决定了它因为作为专门的复用状态维护器，
 * 而不是既维护对象复用条件又维护对象本身。 一种推荐的用法是同时使用集合加复用池，对对象本身和对象的复用条件分别进行维护。
 * 
 * <p>
 * 由于复用池本身不维护对象的持有，所以复用池并没有观察器，
 * 对于需要使用观察器的数据维护结构来说，一种推荐的方法是在使用复用池的同时额外的使用带有观察器的数据模型。
 * 
 * @author DwArFeng
 * @since 0.2.1-beta
 */
package com.dwarfeng.dutil.develop.reuse;