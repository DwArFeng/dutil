package com.dwarfeng.dutil.develop.timer;

import java.util.Objects;

class TestExceptionPlan extends AbstractPlan {

    private Exception nextException = null;

    public TestExceptionPlan() {
        super(0);
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
            Exception dejavu = nextException;
            nextException = null;
            throw dejavu;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long updateNextRunTime() {
        return 0;
    }
}
