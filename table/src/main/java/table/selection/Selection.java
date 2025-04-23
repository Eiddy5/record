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


    public void adjust(int row, int col) {
        adjust();
        this.startRow = Math.min(this.startRow, row);
        this.startCol = Math.min(this.startCol, col);
        this.endRow = Math.max(this.endRow, row);
        this.endCol = Math.max(this.endCol, col);
    }

    private void adjust() {
        if (startRow > endRow) {
            int temp = startRow;
            startRow = endRow;
            endRow = temp;
        }
        if (startCol > endCol) {
            int temp = startCol;
            startCol = endCol;
            endCol = temp;
        }
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
        // todo 有问题
        return Math.max(startRow, endRow);
    }

    public int getMinCol() {
        return Math.min(startCol, endCol);
    }

    public int getMaxCol() {
        return Math.max(startCol, endCol);
    }
}
