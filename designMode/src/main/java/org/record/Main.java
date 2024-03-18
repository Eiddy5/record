package org.record;

import org.record.mode.chain.Study;
import org.record.mode.chain.handler.BrushPrepare;
import org.record.mode.chain.handler.PrepareFactory;
import org.record.mode.chain.handler.WashPrepare;
import org.record.mode.chain.intf.Prepare;

public class Main {
    public static void main(String[] args) {

        Study study = new Study();
        Prepare build = PrepareFactory.Build();
        build.addNext(new WashPrepare()).addNext(new BrushPrepare());
        build.doPrepare();
        study.study();
    }
}