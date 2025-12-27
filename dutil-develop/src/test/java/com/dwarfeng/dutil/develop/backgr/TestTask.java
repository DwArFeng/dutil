package com.dwarfeng.dutil.develop.backgr;

import com.dwarfeng.dutil.basic.io.CT;

final class TestTask extends AbstractTask {

    private final long blockTime;

    public TestTask() {
        this(1000L);
    }

    public TestTask(long blockTime) {
        this.blockTime = blockTime;
    }

    @Override
    protected void todo() throws Exception {
        CT.trace("Background tools is testing: block start...");
        Thread.sleep(blockTime);
        CT.trace("Background tools is testing: block end!");
    }
}
