package org.record.mode.chain.handler;

import org.record.mode.chain.intf.Prepare;

public class WashPrepare implements Prepare {
    private Prepare next;

    @Override
    public Prepare addNext(Prepare next) {
        this.next = next;
        return next;
    }

    @Override
    public void doPrepare() {
        System.out.println("洗脸");
        if (next != null) {
            next.doPrepare();
        }
    }
}
