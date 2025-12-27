package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.cna.model.KeySetModel;
import com.dwarfeng.dutil.basic.str.Name;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 资源处理器。
 *
 * <p>
 * 该接口负责处理资源。 <br>
 * 该接口本身是一个 <code>KeySetModel</code>，并没有更多的方法。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public interface ResourceHandler extends KeySetModel<String, Resource> {

    /**
     * 获得指定的键对应的资源。
     *
     * @param name 指定的键对应的名称。
     * @return 指定的键对应的资源。
     */
    default Resource get(Name name) {
        return get(name.getName());
    }

    /**
     * 打开指定键对应的资源的输入流。
     *
     * @param key 指定的键。
     * @return 指定的键对应资源的输入流。
     * @throws IOException              IO 异常。
     * @throws IllegalArgumentException 处理器不存在指定的键。
     */
    default InputStream openInputStream(String key) throws IOException, IllegalArgumentException {
        return get(key).openInputStream();
    }

    /**
     * 打开指定键对应的资源的输入流。
     *
     * @param key 指定的键对应的名称。
     * @return 指定的键对应资源的输入流。
     * @throws IOException              IO 异常。
     * @throws IllegalArgumentException 处理器不存在指定的键。
     */
    default InputStream openInputStream(Name key) throws IOException, IllegalArgumentException {
        return openInputStream(key.getName());
    }

    /**
     * 打开指定键对应的资源的输出流。
     *
     * @param key 指定的键。
     * @return 指定的键对应的资源的输出流。
     * @throws IOException              IO 异常。
     * @throws IllegalArgumentException 处理器不存在指定的键。
     */
    default OutputStream openOutputStream(String key) throws IOException, IllegalArgumentException {
        return get(key).openOutputStream();
    }

    /**
     * 打开指定键对应的资源的输出流。
     *
     * @param key 指定的键对应的名称。
     * @return 指定的键对应的资源的输出流。
     * @throws IOException              IO 异常。
     * @throws IllegalArgumentException 处理器不存在指定的键。
     */
    default OutputStream openOutputStream(Name key) throws IOException, IllegalArgumentException {
        return openOutputStream(key.getName());
    }

    /**
     * 重置指定的键对应的资源。
     *
     * @param key 指定的资源对应的键。
     * @throws IOException              IO 异常。
     * @throws IllegalArgumentException 处理器中不存在指定的键。
     */
    default void reset(String key) throws IOException, IllegalArgumentException {
        get(key).reset();
    }
}
