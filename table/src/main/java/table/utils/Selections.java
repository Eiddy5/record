package table.utils;

import table.core.Cell;
import table.selection.Selection;

import java.util.Set;

public class Selections {

    public static Selection getSelection(Set<Cell> mergedCells) {
        int minX = -1;
        int minY = -1;
        int maxX = -1;
        int maxY = -1;

        for (Cell cell : mergedCells) {
            minX = minX == -1 ? cell.getStartRow() : Math.min(minX, cell.getStartRow());
            minY = minY == -1 ? cell.getStartCol() : Math.min(minY, cell.getStartCol());
            maxX = maxX == -1 ? cell.getEndRow() : Math.max(maxX, cell.getEndRow());
            maxY = maxY == -1 ? cell.getEndCol() : Math.max(maxY, cell.getEndCol());
        }
        return new Selection(minX, minY, maxX, maxY);
    }

    public static Selection merge(Selection selection, Selection mergedSelection) {
        return new Selection(
                Math.min(selection.getStartRow(), mergedSelection.getStartRow()),
                Math.min(selection.getStartCol(), mergedSelection.getStartCol()),
                Math.max(selection.getEndRow(), mergedSelection.getEndRow()),
                Math.max(selection.getEndCol(), mergedSelection.getEndCol()));
    }
}
