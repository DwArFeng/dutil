package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.KeySetModel;
import com.dwarfeng.dutil.basic.cna.model.MapKeySetModel;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * 代理资源管理器。
 *
 * <p>
 * 通过代理一个 {@link KeySetModel} 来实现具体功能的资源管理器。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public class DelegateResourceHandler implements ResourceHandler {

    /**
     * 该资源管理器的代理键值集合模型。
     */
    protected final KeySetModel<String, Resource> delegate;

    /**
     * 生成一个默认的代理资源管理器。
     */
    public DelegateResourceHandler() {
        this(new MapKeySetModel<>());
    }

    /**
     * 生成一个由指定的键值集合模型代理的资源管理器。
     *
     * @param delegate 指定的集合模型代理。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DelegateResourceHandler(KeySetModel<String, Resource> delegate) {
        Objects.requireNonNull(delegate, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATERESOURCEHANDLER_0));
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SetObserver<Resource>> getObservers() {
        return delegate.getObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(SetObserver<Resource> observer) {
        return delegate.addObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource get(String key) {
        return delegate.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(SetObserver<Resource> observer) {
        return delegate.removeObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAllKey(Collection<?> c) {
        return delegate.containsAllKey(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearObserver() {
        delegate.clearObserver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeKey(Object key) {
        return delegate.removeKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAllKey(Collection<?> c) {
        return delegate.removeAllKey(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAllKey(Collection<?> c) {
        return delegate.retainAllKey(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return delegate.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Resource> iterator() {
        return delegate.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(Resource e) {
        return delegate.add(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        return delegate.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends Resource> c) {
        return delegate.addAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return delegate.retainAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return delegate.removeAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        delegate.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return delegate.hashCode();
    }
}
