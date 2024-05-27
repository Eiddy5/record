package org.domain;

public class IdCount {
    public String id;

    public int count;


    public static IdCount Of(String id, int count) {
        IdCount idCount = new IdCount();
        idCount.id = id;
        idCount.count = count;
        return idCount;
    }
}
