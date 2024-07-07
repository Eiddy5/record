package org.ignite.client.cache;

import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.QueryIndex;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class QueryEntityFactory {
    private static final LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
    private static final LinkedHashMap<String, QueryIndex> groupIndexes = new LinkedHashMap<>();
    private static final List<QueryIndex> indexes = new ArrayList<>();


    public static QueryEntity create(Class<?> keyType, Class<?> valueType) {
        QueryEntity queryEntity = new QueryEntity();
        queryEntity.setKeyType(keyType.getName());
        queryEntity.setValueType(valueType.getName());

        for (Field field : valueType.getDeclaredFields()) {
            QuerySqlField sqlField = field.getAnnotation(QuerySqlField.class);
            if (sqlField != null) {
                String fieldName = field.getName();
                fieldMap.put(fieldName, field.getType().getName());

                if (sqlField.index()) {
                    QueryIndex index = new QueryIndex();
                    index.setName(fieldName);
                    LinkedHashMap<String, Boolean> indexFields = new LinkedHashMap<>();
                    indexFields.put(fieldName, true);
                    index.setFields(indexFields);
                    indexes.add(index);
                }

                for (QuerySqlField.Group group : sqlField.orderedGroups()) {
                    String groupName = group.name();
                    QueryIndex groupIndex = groupIndexes.computeIfAbsent(groupName, k -> {
                        QueryIndex idx = new QueryIndex();
                        idx.setName(k);
                        idx.setFields(new LinkedHashMap<>());
                        return idx;
                    });
                    groupIndex.getFields().put(fieldName, !group.descending());
                }
            }
        }

        indexes.addAll(groupIndexes.values());
        queryEntity.setFields(fieldMap);
        queryEntity.setIndexes(indexes);
        return queryEntity;
    }

}
