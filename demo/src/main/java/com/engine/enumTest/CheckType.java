package com.engine.enumTest;

public enum CheckType {

    file("file", "文件", 0),
    meeting("meeting", "会议", 2),
    message("message", "消息", 1),
    phone("phone", "电话", 3);
    private String name;
    private String label;
    private int value;


    private CheckType(String name, String label, int value) {
        this.name = name;
        this.label = label;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getLabel() {
        return this.label;
    }

    public int getOrdinal() {
        return this.value;
    }

}
