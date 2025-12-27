package com.dwarfeng.dutil.basic.prog;

/**
 * 过程失败异常。
 *
 * <p>
 * 该异常表示某个方法的处理过程中，由其它的某个异常引发过程出现错误， 无法继续进行。该异常是一个用于中转异常，通常而言，使用该异常的
 * {@link #getCause()} 法可以获得过程中发生的真正异常。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class ProcessException extends Exception {

    private static final long serialVersionUID = -7882535182191168599L;

    public ProcessException() {
        super();
    }

    public ProcessException(String message, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }
}
