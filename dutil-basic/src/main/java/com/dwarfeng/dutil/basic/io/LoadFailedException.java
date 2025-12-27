package com.dwarfeng.dutil.basic.io;

import com.dwarfeng.dutil.basic.prog.ProcessException;

/**
 * 读取失败异常。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class LoadFailedException extends ProcessException {

    private static final long serialVersionUID = -1688596142629630154L;

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
