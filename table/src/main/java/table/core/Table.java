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
        int minX = selection.getMinX();
        int minY = selection.getMinY();
        if (minX < 0 || minY < 0) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        int maxX = selection.getMaxX();
        int maxY = selection.getMaxY();
        if (maxX >= xLength || maxY >= yLength) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        Cell root = cells[minX][minY];
        root.setRolSpan(maxX - minX + 1);
        root.setColSpan(maxY - minY + 1);


        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Cell cell = cells[i][j];
                if (cell.equals(root)) {
                    continue;
                }
                if (!cell.isDisplay()){

                }
                cell.setDisplay(false);
                cell.setRoot(root);
            }
        }
    }

    private Selection confirmSelection(Selection selection){

        int minX = selection.getMinX();
        int minY = selection.getMinY();
        if (minX < 0 || minY < 0) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        int maxX = selection.getMaxX();
        int maxY = selection.getMaxY();
        if (maxX >= xLength || maxY >= yLength) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }

        int tempMinX = minX;
        int tempMinY = minY;
        int tempMaxX = maxX;
        int tempMaxY = maxY;


        Cell root = cells[minX][minY];

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Cell cell = cells[i][j];
                if (cell.equals(root)) {
                    continue;
                }
                if (!cell.isDisplay()){

                }
                cell.setDisplay(false);
                cell.setRoot(root);
            }
        }

    }



    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                str.append(JsonObject.from(cells[i][j]));
            }
            str.append("\n");
        }
        return str.toString();
    }
}
