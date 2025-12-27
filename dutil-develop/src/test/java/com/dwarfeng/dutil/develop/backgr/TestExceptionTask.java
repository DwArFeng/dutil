package com.dwarfeng.dutil.develop.backgr;

import java.util.Objects;

class TestExceptionTask extends AbstractTask {

    private Exception nextException = null;

    public TestExceptionTask() {
    }

    public Exception getNextException() {
        return nextException;
    }

    public void setNextException(Exception nextException) {
        this.nextException = nextException;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void todo() throws Exception {
        if (Objects.nonNull(nextException)) {
            throw nextException;
        }
    }
}
