package mysql.jooq.conf;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JooqConfig {
    private static final String URL = "jdbc:mysql://localhost:3060/dynamic";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static DSLContext use() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return DSL.using(connection, SQLDialect.MYSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        JooqConfig.use().createTableIfNotExists("test")
                .column("id", SQLDataType.VARCHAR(255))
                .column("name", SQLDataType.VARCHAR(255))
                .execute();
    }

}
