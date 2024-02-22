package mysql.jooq.intf;

import org.jooq.DSLContext;

public interface JooqRunnable {
    void run(DSLContext dslContext);
}
