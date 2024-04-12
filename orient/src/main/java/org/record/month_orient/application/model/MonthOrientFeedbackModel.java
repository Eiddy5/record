package org.record.month_orient.application.model;

import org.okr.mapping.conf.monthorient.FeedbackType;
import org.okr.mapping.entry.common.User;
import org.x7.json.JsonObjects;

public class MonthOrientFeedbackModel {
    public FeedbackType type;
    public String describe;
    public JsonObjects images;
    public String phone;
}
