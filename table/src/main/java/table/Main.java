package table;

import table.core.Table;
import table.core.TableFactory;
import table.selection.Selection;

public class Main {


    public static void main(String[] args) {
        Table table = TableFactory.create(4, 4);

        Selection selection = new Selection(0, 0, 1, 1);
        table.merge(selection);

        System.out.println(table);
    }
}
