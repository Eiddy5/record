package table.selection;

import lombok.AllArgsConstructor;
import lombok.Data;
import table.core.Cell;

@Data
@AllArgsConstructor
public class Selection {
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;


    public void normalize(){
        int minRow = Math.min(startRow, endRow);
        int minCol = Math.min(startCol, endCol);
        int maxRow = Math.max(startRow, endRow);
        int maxCol = Math.max(startCol, endCol);
        startRow = minRow;
        startCol = minCol;
        endRow = maxRow;
        endCol = maxCol;
    }

    public void adjust(int row, int col) {
        this.startRow = Math.min(this.startRow, row);
        this.startCol = Math.min(this.startCol, col);
        this.endRow = Math.max(this.endRow, row);
        this.endCol = Math.max(this.endCol, col);
    }


    public boolean containsMerge(Cell master) {
        return startRow <= master.getStartRow() &&
                startCol <= master.getStartCol() &&
                endRow >= master.getEndRow() &&
                endCol >= master.getEndCol();
    }

    public int getMinRow() {
        return Math.min(startRow, endRow);
    }

    public int getMaxRow() {
        return Math.max(startRow, endRow);
    }

    public int getMinCol() {
        return Math.min(startCol, endCol);
    }

    public int getMaxCol() {
        return Math.max(startCol, endCol);
    }

    public Selection copy() {
        return new Selection(startRow, startCol, endRow, endCol);
    }
}
