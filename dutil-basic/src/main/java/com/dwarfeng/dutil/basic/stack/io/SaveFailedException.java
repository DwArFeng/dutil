package com.dwarfeng.dutil.basic.stack.io;

import com.dwarfeng.dutil.basic.stack.lifecycle.ProcessException;

/**
 * 保存失败异常。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class SaveFailedException extends ProcessException {

    public SaveFailedException() {
        super();
    }

    public SaveFailedException(String message) {
        super(message);
    }

    public SaveFailedException(Throwable cause) {
        super(cause);
    }

    public SaveFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
