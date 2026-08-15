package com.dwarfeng.dutil.basic.stack.io;

import com.dwarfeng.dutil.basic.stack.lifecycle.ProcessException;

import java.io.Serial;

/**
 * 保存失败异常。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class SaveFailedException extends ProcessException {

    @Serial
    private static final long serialVersionUID = 3737844081796959804L;

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

    public SaveFailedException(
            String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
