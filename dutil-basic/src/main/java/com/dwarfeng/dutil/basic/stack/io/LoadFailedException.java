package com.dwarfeng.dutil.basic.stack.io;

import com.dwarfeng.dutil.basic.stack.lifecycle.ProcessException;

import java.io.Serial;

/**
 * 读取失败异常。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class LoadFailedException extends ProcessException {

    @Serial
    private static final long serialVersionUID = -8470646904081841007L;

    public LoadFailedException() {
        super();
    }

    public LoadFailedException(String message) {
        super(message);
    }

    public LoadFailedException(Throwable cause) {
        super(cause);
    }

    public LoadFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
