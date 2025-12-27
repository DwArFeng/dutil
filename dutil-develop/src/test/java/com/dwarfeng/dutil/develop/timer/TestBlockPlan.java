package com.dwarfeng.dutil.develop.timer;

import com.dwarfeng.dutil.basic.io.CT;

final class TestBlockPlan extends AbstractPlan {

    private final long blockTime;

    public TestBlockPlan() {
        this(1000L);
    }

    public TestBlockPlan(long blockTime) {
        super(0);
        this.blockTime = blockTime;
    }

    @Override
    protected void todo() throws Exception {
        CT.trace("Timer tools is testing: block start...");
        Thread.sleep(blockTime);
        CT.trace("Timer tools is testing: block end!");
    }

    @Override
    protected long updateNextRunTime() {
        return 0;
    }
}
