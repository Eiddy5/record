package org.record.month_orient.application.model;

import org.jetbrains.annotations.NotNull;
import org.okr.collaborate.service.monthorient.entry.MonthOrientRunEntry;
import org.okr.mapping.entry.common.User;
import org.okr.mapping.entry.monthorient.PointObject;

import java.util.List;
import java.util.Set;

public class MonthOrientModel {
    public String  slideTempId;
    public String  slideTempName;
    public String  slideRunId;
    public String  slideRunName;
    public String  title;
    public String  describe;
    public Set<User> member;
    public Long    startTime;
    public Long    stopTime;
    public Long    duration;
    public List<PointObject> orientList;
    public MonthOrientRunEntry fillPpt;
    public List<MonthOrientMemberModel> writeUsers;
    public List<MonthOrientMemberModel> unWriteUsers;

}
