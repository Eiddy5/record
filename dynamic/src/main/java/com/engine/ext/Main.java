package com.engine.ext;

import com.engine.ext.jooq.config.JOOQ;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.ArrayList;
import java.util.Collection;


public class Main {
    public static void main(String[] args) {
        ArrayList<Field<?>> fields = new ArrayList<>();
        fields.add(DSL.field("a", SQLDataType.VARCHAR(100)));
        fields.add(DSL.field("b", SQLDataType.VARCHAR(100)));

        createTable("check_point_task", fields);
    }

    public static void createTable(String tableName, Collection<? extends Field<?>> fields) {
        JOOQ.use().createTableIfNotExists(tableName).columns(fields).execute();
    }
}
