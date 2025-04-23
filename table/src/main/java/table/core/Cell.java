package table.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import json.JsonObject;
import lombok.Data;

import java.util.Objects;

@Data
public class Cell {
    private int row;
    private int col;
    private int rowSpan;
    private int colSpan;
    private boolean display;
    private Cell root;

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
        this.root = master;
        this.rowSpan = 1;
        this.colSpan = 1;
    }

    public void reset() {
        this.display = true;
        this.root = null;
        this.colSpan = 1;
        this.rowSpan = 1;
    }


    @JsonIgnore
    public int getStartRow() {
        if (root == null) {
            return row ;
        } else {
            return root.getStartRow();
        }
    }

    @JsonIgnore
    public int getStartCol() {
        if (root == null) {
            return col ;
        } else {
            return root.getStartCol();
        }
    }

    @JsonIgnore
    public int getEndRow() {
        if (root == null) {
            return row + colSpan -1;
        } else {
            return root.getEndRow();
        }
    }

    @JsonIgnore
    public int getEndCol() {
        if (root == null) {
            return col + rowSpan -1;
        } else {
            return root.getEndCol();
        }
    }

    public boolean isMerged() {
        return !display || rowSpan != 1 || colSpan != 1;
    }

    public Cell getRoot() {
        if (root == null) {
            return this;
        }
        return root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col && rowSpan == cell.rowSpan && colSpan == cell.colSpan && display == cell.display;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, rowSpan, colSpan, display);
    }

    @Override
    public String toString() {
        return JsonObject.from(this);
    }
}
