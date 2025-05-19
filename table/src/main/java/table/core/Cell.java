package table.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.record.json.JsonObject;

@Data
public class Cell {
    private int row;
    private int col;
    private int rowSpan;
    private int colSpan;
    private boolean display;
    private Cell mater;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.display = true;
        this.rowSpan = 1;
        this.colSpan = 1;
    }


    public void master(int startRow, int startCol, int endRow, int endCol) {
        this.display = true;
        this.rowSpan = endRow - startRow + 1;
        this.colSpan = endCol - startCol + 1;
    }

    public void slave(Cell master) {
        this.display = false;
        this.mater = master;
        this.rowSpan = 1;
        this.colSpan = 1;
    }

    public void reset() {
        this.display = true;
        this.mater = null;
        this.colSpan = 1;
        this.rowSpan = 1;
    }


    @JsonIgnore
    public int getStartRow() {
        if (mater == null) {
            return row ;
        } else {
            return mater.getStartRow();
        }
    }

    @JsonIgnore
    public int getStartCol() {
        if (mater == null) {
            return col ;
        } else {
            return mater.getStartCol();
        }
    }

    @JsonIgnore
    public int getEndRow() {
        if (mater == null) {
            return row + rowSpan -1;
        } else {
            return mater.getEndRow();
        }
    }

    @JsonIgnore
    public int getEndCol() {
        if (mater == null) {
            return col + colSpan -1;
        } else {
            return mater.getEndCol();
        }
    }

    public boolean isMerged() {
        return !display || rowSpan != 1 || colSpan != 1;
    }

    public Cell getMater() {
        if (mater == null) {
            return this;
        }
        return mater;
    }

    @Override
    public String toString() {
        return JsonObject.from(this);
    }
}
