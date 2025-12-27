package com.dwarfeng.dutil.basic.cna;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

/**
 * ID 重复异常。
 *
 * <p>
 * 当某些类中包含一些具有 ID 值的集合，且不允许其 ID 值重复。当向这些类中试图添加已经存在的 ID 时通常会抛出该异常。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
class DuplicateIdException extends Exception {

    private static final long serialVersionUID = -1961152389935867480L;

    private final int id;

    /**
     * 生成一个默认的 ID 重复异常。
     */
    public DuplicateIdException() {
        this(0, null, null);
    }

    /**
     * 生成一个具有指定 ID 号的 ID 异常。
     *
     * @param id 指定的 ID 号。
     */
    public DuplicateIdException(int id) {
        this(id, null, null);
    }

    /**
     * 生成一个具有指定 ID 号，指定的异常信息的 ID 重复异常。
     *
     * @param id      指定的 ID 号。
     * @param message 指定的异常信息。
     */
    public DuplicateIdException(int id, String message) {
        this(id, message, null);
    }

    /**
     * 生成一个具有指定的 ID 号，指定的发生原因的 ID 重复异常。
     *
     * @param id    指定的 ID 号。
     * @param cause 指定的发生原因。
     */
    public DuplicateIdException(int id, Throwable cause) {
        this(id, null, cause);
    }

    /**
     * 生成一个具有指定的 ID 号，指定的异常信息，指定的异常发生原因的 ID 重复异常。
     *
     * @param id      指定的 ID 号。
     * @param message 指定的异常信息。
     * @param cause   指定的异常发生原因。
     */
    public DuplicateIdException(int id, String message, Throwable cause) {
        super(message, cause);
        this.id = id;
    }

    /**
     * 返回该 ID 重复异常的重复 ID 号。
     *
     * @return ID 号。
     */
    public int getID() {
        return this.id;
    }

    @Override
    public String getMessage() {
        if (super.getMessage() == null || super.getMessage().equals(""))
            return DwarfUtil.getExceptionString(ExceptionStringKey.DUPLICATEIDEXCEPTION_0) + getID();
        return super.getMessage();
    }
}
