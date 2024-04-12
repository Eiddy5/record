package org.record.month_orient.domain.model;

import org.apache.commons.lang3.StringUtils;
import org.record.month_orient.domain.model.ppt.OrientTreeNode;
import org.record.month_orient.infrastructure.repository.mysql.monthorient.MonthOrientContentObject;

import java.util.List;
import java.util.UUID;

public class MonthOrientContentModel {

    private String userId;
    private String userName;
    private String monthOrientRunId;
    private String content;
    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMonthOrientRunId() {
        return monthOrientRunId;
    }

    public void setMonthOrientRunId(String monthOrientRunId) {
        this.monthOrientRunId = monthOrientRunId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 递归提取ppt中的导向内容、总结、感悟
     * 但是不适用都来帮事件和领航员事件(他俩比较特殊，需要单独处理)
     * 总结生成的数据中多出来一条数据，需要删除，可以自行查看删除那一条
     *
     * @param treeNode         SlideFillInfoResult 中的具体的treeNode 例如:OrientTreeNode 获取的就是orient数据
     * @param parentId         递归的父id
     * @param maxDepth         最大递归深度
     * @param contentEntryList 收集到的对象
     */
    public void facBuildOrient(OrientTreeNode treeNode, String parentId, int maxDepth, List<MonthOrientContentObject> contentEntryList) {
        String id = UUID.randomUUID().toString();
        if (parentId != null) {
            MonthOrientContentObject contentEntry = new MonthOrientContentObject();
            contentEntry.id = id;
            // 类型
            contentEntry.type = treeNode.name;
            contentEntry.content = treeNode.value;
            contentEntry.parentId = parentId;
            contentEntryList.add(contentEntry);
        }
        if (maxDepth > 1) {
            treeNode.children.forEach(node -> {
                facBuildOrient(node, id, maxDepth - 1, contentEntryList);
            });
        }
    }
}
