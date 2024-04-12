package org.record.month_orient.domain.model.ppt;



import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.record.month_orient.infrastructure.common.IdName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class slideFillInfo {
    private List<SlidePageEntry> slidePageEntryList;
    private String name;
    private String value;
    private List<SlideLayerEntry.Person> persons;

    public static slideFillInfo Use(List<SlidePageEntry> slidePageEntryList) {
        return new slideFillInfo(slidePageEntryList);
    }

    public slideFillInfo(List<SlidePageEntry> slidePageEntryList) {
        this.slidePageEntryList = slidePageEntryList;
    }

    public slideFillInfo(String name, String value, List<SlideLayerEntry.Person> persons) {
        this.name = name;
        this.value = value;
        this.persons = persons;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        if (this.persons == null || this.persons.isEmpty()) return this.value;
        return JSONObject.toJSONString(persons);
    }


    public Map<String, String> extractSlideInfo() {
        List<slideFillInfo> slideInfoList = new ArrayList<>();
        slidePageEntryList.forEach(sp -> {
            List<slideFillInfo> pageInfoList = extractPageInfo(sp);
            slideInfoList.addAll(pageInfoList);
        });
        return slideInfoList.stream().collect(Collectors.toMap(slideFillInfo::getName, slideFillInfo::getValue));
    }

    private List<slideFillInfo> extractPageInfo(SlidePageEntry slidePageEntry) {
        List<slideFillInfo> pageInfoList = new ArrayList<>();
        slidePageEntry.layer.forEach(layer -> {
            slideFillInfo layerInfo = extractLayerInfo(layer);
            if (layerInfo != null) pageInfoList.add(layerInfo);
        });
        return pageInfoList;
    }

    private slideFillInfo extractLayerInfo(SlideLayerEntry layer) {
        if ("fillInput".equals(layer.type)) {
            if (StringUtils.isEmpty(layer.value)) return null;
            return new slideFillInfo(layer.name, layer.value, layer.persons);
        }
        return null;
    }

}
