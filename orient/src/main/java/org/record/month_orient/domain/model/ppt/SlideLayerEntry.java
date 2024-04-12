package org.record.month_orient.domain.model.ppt;


import org.record.month_orient.infrastructure.common.IdName;

import java.util.List;

public class SlideLayerEntry {
    public String type;
    public String name;
    public String value;
    public String content;
    public Double width;
    public Double height;
    public Boolean fill;
    public Boolean select;
    public String fillColor;
    public Double letterSpacing;
    public Double lineHeight;
    public Boolean bold;
    public Double radii;
    public Double x;
    public Double y;
    public Double fontSize;
    public String fontStyle;
    public String fontColor;
    public List<Person> persons;

    public static class Person extends IdName {
        public String parentId;
        public Integer seq;
        public String type;
        public Boolean isUser;
        public String orgId;
        public String orgName;
        public String userid;
        public String userName;
        public String key;
        public String title;
        public Boolean isLeaf;
        public Boolean checked;
        public Boolean expanded;
        public Boolean selected;
    }

}
