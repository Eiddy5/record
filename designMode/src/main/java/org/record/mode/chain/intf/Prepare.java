package org.record.mode.chain.intf;

public interface Prepare {
    public Prepare addNext(Prepare next);

    public void doPrepare();
}
