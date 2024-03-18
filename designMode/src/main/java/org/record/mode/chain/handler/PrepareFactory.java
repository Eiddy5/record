package org.record.mode.chain.handler;

import org.record.mode.chain.intf.Prepare;

public class PrepareFactory implements Prepare {
    private Prepare next;

    @Override
    public Prepare addNext(Prepare next) {
        this.next = next;
        return next;
    }

    @Override
    public void doPrepare() {
        if (next != null) {
            next.doPrepare();
        }
    }

    public static PrepareFactory Build() {
        return new PrepareFactory();
    }
}
