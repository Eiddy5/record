package org.record.month_orient.domain.model.ppt;


import org.record.month_orient.infrastructure.common.IdName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrientTreeNode {
    public String name;
    public String value;
    public List<IdName> persons;
    public List<OrientTreeNode> children = new ArrayList<>();

    public OrientTreeNode(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static OrientTreeNode Use(String type) {
        return new OrientTreeNode(type, null);
    }

    public OrientTreeNode buildTreeFromMap(Map<String, String> eventMap) {
        Map<String, String> targetTypeMap = eventMap.entrySet().stream().filter(entry -> entry.getKey().contains(this.name)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        OrientTreeNode root = this;
        for (Map.Entry<String, String> entry : targetTypeMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String[] parts = key.split("/");
            OrientTreeNode current = root;
            for (String part : parts) {
                current = findOrCreateNode(current, part, null);
            }
            current.value = value;
        }
        return this;
    }

    private OrientTreeNode findOrCreateNode(OrientTreeNode parent, String name, String value) {
        for (OrientTreeNode child : parent.children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        OrientTreeNode newNode = new OrientTreeNode(name, value);
        parent.children.add(newNode);
        return newNode;
    }
}
