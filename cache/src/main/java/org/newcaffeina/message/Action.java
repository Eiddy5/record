package org.newcaffeina.message;

public enum Action {

    ADD("add","新增"),
    REMOVE("remove","删除"),
    UPDATE("update","更新"),
    CLEAR("clear","清空"),
    REPLACE("replace","替换"),
    REPLACE_ALL("replaceAll","替换全部");
    private String name;
    private String label;

    Action(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }
}
