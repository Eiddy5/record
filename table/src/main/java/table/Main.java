package table;

import table.core.TableMerger;
import table.core.Table;
import table.core.TableFactory;
import table.selection.Selection;

public class Main {


    public static void main(String[] args) {


        Table table = TableFactory.create(4, 4);

        Selection selection = new Selection(0, 0, 1, 1);


        table.merge(selection);


        Selection selection1 = new Selection(1, 2, 2, 1);

        table.merge(selection1);

        System.out.println(table);
    }
}
