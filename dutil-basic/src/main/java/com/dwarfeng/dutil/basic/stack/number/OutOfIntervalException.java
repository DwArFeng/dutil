package com.dwarfeng.dutil.basic.stack.number;

import java.io.Serial;

/**
 * 代表一个数超过一个区间的异常。
 *
 * @author DwArFeng
 * @since 0.1.3-beta
 */
public class OutOfIntervalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8368555059267474868L;

    public OutOfIntervalException() {
    }

    public OutOfIntervalException(String message) {
        super(message);
    }

    public OutOfIntervalException(Throwable cause) {
        super(cause);
    }

    public OutOfIntervalException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfIntervalException(
            String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
