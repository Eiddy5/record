package org.record.month_orient.domain.model.ppt;

import java.util.Map;

public class SlideFillInfoResult {

    enum ResultType {
        EVALUATION("完成度", "Evaluation"),
        FEELING("感悟", "Feeling"),
        HELP("都来帮", "Help"),
        NAVIGATION("领航员", "Navigation"),
        ORIENT("导向", "Orient"),
        RESULT("总结", "Result");
        private String name;
        private String label;

        ResultType(String name, String label) {
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

    public OrientTreeNode evaluationTreeNode;
    public OrientTreeNode feelingTreeNode;
    public OrientTreeNode orientTreeNode;
    public OrientTreeNode resultTreeNode;
    public OrientTreeNode helpTreeNode;
    public OrientTreeNode navigationTreeNode;

    public static SlideFillInfoResult Use() {
        return new SlideFillInfoResult();
    }

    public SlideFillInfoResult buildSlideInfoResult(Map<String, String> slideInfo) {
        helpTreeNode = OrientTreeNode.Use(ResultType.HELP.getName()).buildTreeFromMap(slideInfo);
        orientTreeNode = OrientTreeNode.Use(ResultType.ORIENT.getName()).buildTreeFromMap(slideInfo);
        resultTreeNode = OrientTreeNode.Use(ResultType.RESULT.getName()).buildTreeFromMap(slideInfo);
        navigationTreeNode = OrientTreeNode.Use(ResultType.NAVIGATION.getName()).buildTreeFromMap(slideInfo);
        evaluationTreeNode = OrientTreeNode.Use(ResultType.EVALUATION.getName()).buildTreeFromMap(slideInfo);
        feelingTreeNode = OrientTreeNode.Use(ResultType.FEELING.getName()).buildTreeFromMap(slideInfo);
        return this;
    }

}
