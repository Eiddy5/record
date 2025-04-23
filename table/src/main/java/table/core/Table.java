package table.core;


import lombok.Data;
import table.selection.Selection;

import java.util.ArrayList;
import java.util.List;

@Data
public class Table {
    private final int xLength;
    private final int yLength;
    private final Cell[][] grid;

    public Table(int xLength, int yLength) {
        this.xLength = xLength;
        this.yLength = yLength;
        this.grid = new Cell[this.xLength][this.yLength];
    }


    public void addCell(Cell cell) {
        if (cell.getRow() >= xLength || cell.getCol() >= yLength) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        grid[cell.getRow()][cell.getCol()] = cell;
    }

    public void addCell(int x, int y, Cell cell) {
        if (x >= xLength || y >= yLength) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        grid[x][y] = cell;
    }


    public void merge(Selection selection) {
        if (selection == null) {
            throw new IllegalStateException("No selection to merge");
        }
        mergeArea(adjustSelection(selection));
    }


    private Selection adjustSelection(Selection original) {
        Selection adjusted = original.copy();

        boolean changed;
        do {
            changed = false;
            adjusted.normalize();
            for (int r = adjusted.getMinRow(); r <= adjusted.getMaxRow(); r++) {
                for (int c = adjusted.getMinCol(); c <= adjusted.getMaxCol(); c++) {
                    Cell cell = grid[r][c];
                    if (cell.isMerged()) {
                        Cell master = cell.getMater();
                        if (!adjusted.containsMerge(master)) {
                            adjusted.adjust(master.getStartRow(), master.getStartCol());
                            adjusted.adjust(master.getEndRow(), master.getEndCol());
                            changed = true;
                        }
                    }
                }
            }

        } while (changed);

        System.out.println("Adjusted selection: " + adjusted);
        return adjusted;
    }


    public void mergeArea(Selection selection) {

        Cell master = grid[selection.getStartRow()][selection.getStartCol()];
        master.master(selection.getStartRow(), selection.getStartCol(),
                selection.getEndRow(), selection.getEndCol());

        for (int r = selection.getStartRow(); r <= selection.getEndRow(); r++) {
            for (int c = selection.getStartCol(); c <= selection.getEndCol(); c++) {
                if (r == selection.getStartRow() && c == selection.getStartCol()) continue;
                grid[r][c].slave(master);
            }
        }
    }

    public void unmergeArea(Selection selection) {
        List<Cell> processedMasters = new ArrayList<>();

        for (int r = selection.getStartRow(); r <= selection.getEndRow(); r++) {
            for (int c = selection.getStartCol(); c <= selection.getEndCol(); c++) {
                Cell cell = grid[r][c];
                if (cell.isMerged()) {
                    Cell master = cell.getMater() != null ? cell.getMater() : cell;
                    if (!processedMasters.contains(master)) {
                        for (int mr = master.getStartRow(); mr <= master.getEndRow(); mr++) {
                            for (int mc = master.getStartCol(); mc <= master.getEndCol(); mc++) {
                                grid[mr][mc].reset();
                            }
                        }
                        processedMasters.add(master);
                    }
                }
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                char c = grid[i][j].isDisplay() ? '1' : '0';
                str.append(c).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}
