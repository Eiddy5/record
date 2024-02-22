package com.engine.ext.jooq.config;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JOOQ {
    private static final String URL = "jdbc:mysql://localhost:3060";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static DSLContext use() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return DSL.using(connection, SQLDialect.MARIADB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
