package com.dwarfeng.dutil.develop.logger;

import java.io.OutputStream;

class TestLoggerInfo implements LoggerInfo {

    private final String key;
    private final OutputStream out;

    public TestLoggerInfo(String key, OutputStream out) {
        this.key = key;
        this.out = out;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Logger newLogger() {
        return new OutputStreamLogger(out);
    }
}
