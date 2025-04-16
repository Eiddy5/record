package table;

import table.core.TableMerger;
import table.core.Table;
import table.core.TableFactory;
import table.selection.Selection;

public class Main {


    public static void main(String[] args) {

        TableMerger tableMerger = new TableMerger();

        Table table = TableFactory.create(4, 4);

        Selection selection = new Selection(0, 0, 1, 1);

        tableMerger.merge(table, selection);

        System.out.println(table);
    }
}
