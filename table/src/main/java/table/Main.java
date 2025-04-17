package table;

import table.core.TableMerger;
import table.core.Table;
import table.core.TableFactory;
import table.selection.Selection;

public class Main {


    public static void main(String[] args) {


        Table table = TableFactory.create(6, 6);

        System.out.println("---------table----------");
        System.out.println(table);
        Selection selection = new Selection(0, 0, 1, 1);
        table.merge(selection);
        System.out.println("---------merge table----------");
        System.out.println(table);

        Selection selection1 = new Selection(1, 2, 2, 1);
        table.merge(selection1);
        System.out.println("---------merge table----------");
        System.out.println(table);


        Selection selection2 = new Selection(3, 3, 4, 4);
        table.merge(selection2);
        System.out.println("---------merge table----------");
        System.out.println(table);

        Selection selection3 = new Selection(2, 3, 3, 2);
        table.merge(selection3);
        System.out.println("---------merge table----------");
        System.out.println(table);

    }
}
