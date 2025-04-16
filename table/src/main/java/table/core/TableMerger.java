package table.core;

import table.selection.Selection;
import table.selection.SelectionCorrecter;

public class TableMerger {

    private final SelectionCorrecter correcter;

    public TableMerger() {
        this.correcter = new SelectionCorrecter();
    }

    public void merge(Table table, Selection selection) {
        Selection corrector = correcter.corrector(table, selection);
        table.merge(corrector);

    }

}
