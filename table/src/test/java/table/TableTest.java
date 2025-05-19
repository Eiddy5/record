package table;

import org.junit.jupiter.api.Test;
import table.core.Table;
import table.core.TableFactory;
import table.selection.Selection;

public class TableTest {
    @Test
    public void test() {

        Table table = TableFactory.create(10, 10);

        System.out.println("---------table----------");
        System.out.println(table);
        Selection selection = new Selection(4, 3, 4, 5);
        System.out.println("---------selection----------");
        System.out.println(selection);
        table.merge(selection);
        System.out.println("---------merge table----------");
        System.out.println(table);

        Selection selection1 = new Selection(1, 2, 2, 1);
        System.out.println("---------selection----------");
        System.out.println(selection1);
        table.merge(selection1);
        System.out.println("---------merge table----------");
        System.out.println(table);


        Selection selection2 = new Selection(3, 3, 4, 4);
        System.out.println("---------selection----------");
        System.out.println(selection2);
        table.merge(selection2);
        System.out.println("---------merge table----------");
        System.out.println(table);

        Selection selection3 = new Selection(2, 3, 2, 2);
        System.out.println("---------selection----------");
        System.out.println(selection3);
        table.merge(selection3);
        System.out.println("---------merge table----------");
        System.out.println(table);

        Selection selection4 = new Selection(3, 3, 2, 4);
        System.out.println("---------selection----------");
        System.out.println(selection4);
        table.merge(selection4);
        System.out.println("---------merge table----------");
        System.out.println(table);

        Selection selection5 = new Selection(7, 3, 8, 9);
        System.out.println("---------selection----------");
        System.out.println(selection5);
        table.merge(selection5);
        System.out.println("---------merge table----------");
        System.out.println(table);


        Selection selection6 = new Selection(8, 2, 2, 7);
        System.out.println("---------selection----------");
        System.out.println(selection6);
        table.merge(selection6);
        System.out.println("---------merge table----------");
        System.out.println(table);

    }
}
