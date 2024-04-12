package org.record.month_orient.application.model;

import org.okr.collaborate.service.monthorient.entity.MonthOrientCountEntity;
import org.okr.mapping.entry.common.User;

import java.util.ArrayList;
import java.util.List;

public class MonthOrientManagerModel {
    public Long date;
    public List<MonthOrientDateModel> orientList;

    public static class MonthOrientDateModel {
        public String title;
        public String deptName;
        public Boolean meetStatus = false;
        public Integer invitedTotalCount = 1;
        public Integer invitedFinishCount = 0;
        public List<User> notFinish = new ArrayList<>();
    }
}
