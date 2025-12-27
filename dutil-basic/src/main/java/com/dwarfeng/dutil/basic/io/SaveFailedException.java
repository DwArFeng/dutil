package com.dwarfeng.dutil.basic.io;

import com.dwarfeng.dutil.basic.prog.ProcessException;

/**
 * 保存失败异常。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class SaveFailedException extends ProcessException {

    private static final long serialVersionUID = 6937158291009825017L;

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
