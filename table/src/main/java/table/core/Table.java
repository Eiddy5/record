package table.core;


import json.JsonObject;
import lombok.Data;
import table.selection.Selection;

@Data
public class Table {
    private final int xLength;
    private final int yLength;
    private final Cell[][] cells;

    public Table(int xLength, int yLength) {
        this.xLength = xLength;
        this.yLength = yLength;
        this.cells = new Cell[this.xLength][this.yLength];
    }


    public void addCell(Cell cell) {
        if (cell.getX() >= xLength || cell.getY() >= yLength) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        cells[cell.getX()][cell.getY()] = cell;
    }

    public void addCell(int x, int y, Cell cell) {
        if (x >= xLength || y >= yLength) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        cells[x][y] = cell;
    }


    public void merge(Selection selection) {
        confirmSelection(selection);
        int minX = selection.getMinX();
        int minY = selection.getMinY();
        int maxX = selection.getMaxX();
        int maxY = selection.getMaxY();
        Cell root = cells[minX][minY];
        root.setRolSpan(maxX - minX + 1);
        root.setColSpan(maxY - minY + 1);
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Cell cell = cells[i][j];
                if (cell.equals(root)) {
                    continue;
                }
                cell.setDisplay(false);
                cell.setRoot(root);
            }
        }
    }

    private void confirmSelection(Selection selection) {
        int startX = selection.getMinX();
        int startY = selection.getMinY();
        int endX = selection.getMaxX();
        int endY = selection.getMaxY();


        int minX = selection.getMinX();
        int minY = selection.getMinY();
        if (startX < 0 || startY < 0) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        int maxX = selection.getMaxX();
        int maxY = selection.getMaxY();
        if (maxX >= xLength || maxY >= yLength) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        Cell root = cells[minX][minY];
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Cell cell = cells[i][j];
                if (cell.isMerged()) {
                    // 需要扩大范围
                    Cell displayRoot = cell.getRoot();
                    if (displayRoot != null) {
                        startX = Math.min(startX, displayRoot.getStartX());
                        startY = Math.min(startY, displayRoot.getStartY());
                        endX = Math.max(endX, displayRoot.getEndX());
                        endY = Math.max(endY, displayRoot.getEndY());
                    }
                }
            }
        }
        selection.setStartX(startX);
        selection.setStartY(startY);
        selection.setEndX(endX);
        selection.setEndY(endY);

    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                str.append(String.valueOf(cells[i][j].isDisplay()).charAt(0)).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}
