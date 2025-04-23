package table.core;


import lombok.Data;
import table.selection.Selection;
import table.utils.Selections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        // 首先确认当前选区是否存在包含合并过后的单元格
        Set<Cell> mergedCells = containsMergedCell(selection);
        if (!mergedCells.isEmpty()) {
            Selection mergedSelection = Selections.getSelection(mergedCells);
            selection = Selections.merge(selection, mergedSelection);
        }
        int minX = selection.getMinRow();
        int minY = selection.getMinCol();
        int maxX = selection.getMaxRow();
        int maxY = selection.getMaxCol();
        Cell root = grid[minX][minY];
        root.setRowSpan(maxX - minX + 1);
        root.setColSpan(maxY - minY + 1);
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Cell cell = grid[i][j];
                if (cell.equals(root)) {
                    continue;
                }
                cell.setDisplay(false);
                cell.setRoot(root);
            }
        }
    }

    private void confirmSelection(Selection selection) {
        int startX = selection.getMinRow();
        int startY = selection.getMinCol();
        int endX = selection.getMaxRow();
        int endY = selection.getMaxCol();


        int minX = selection.getMinRow();
        int minY = selection.getMinCol();
        if (startX < 0 || startY < 0) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        int maxX = selection.getMaxRow();
        int maxY = selection.getMaxCol();
        if (maxX >= xLength || maxY >= yLength) {
            throw new IllegalArgumentException("x or y is out of bounds");
        }
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Cell cell = grid[i][j];
                if (cell.isMerged()) {
                    Cell displayRoot = cell.getRoot();
                    if (displayRoot != null) {
                        startX = Math.min(startX, displayRoot.getStartRow());
                        startY = Math.min(startY, displayRoot.getStartCol());
                        endX = Math.max(endX, displayRoot.getEndRow());
                        endY = Math.max(endY, displayRoot.getEndCol());
                    }
                }
            }
        }
        selection.setStartRow(startX);
        selection.setStartCol(startY);
        selection.setEndRow(endX);
        selection.setEndCol(endY);

    }

    private Set<Cell> containsMergedCell(Selection selection) {
        HashSet<Cell> cells = new HashSet<>();
        int minX = selection.getMinRow();
        int minY = selection.getMinCol();
        int maxX = selection.getMaxRow();
        int maxY = selection.getMaxCol();
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Cell cell = this.grid[i][j];
                if (cell.isMerged()) {
                    Cell displayRoot = cell.getRoot();
                    if (displayRoot != null) {
                        cells.add(displayRoot);
                    }
                }
            }
        }
        return cells;
    }


    public void merge2(Selection selection){
        mergeSelection(adjustSelection(selection));
    }


    // 调整选区以包含完整的合并单元格
    private Selection adjustSelection(Selection original) {
        Selection adjusted = new Selection(
                original.getStartRow(), original.getStartCol(),
                original.getEndRow(), original.getEndCol());

        boolean changed;
        do {
            changed = false;
            // 检查当前选区内的所有单元格
            for (int r = adjusted.getMinRow(); r <= adjusted.getMaxRow(); r++) {
                for (int c = adjusted.getMinCol(); c <= adjusted.getMaxCol(); c++) {
                    Cell cell = grid[r][c];
                    if (cell.isMerged()) {
                        Cell master = cell.getRoot() != null ? cell.getRoot() : cell;
                        if (!adjusted.containsMerge(master)) {
                            // 扩展选区以包含整个合并区域
                            adjusted.adjust(master.getStartRow(), master.getStartCol());
                            adjusted.adjust(master.getEndRow(), master.getEndCol());
                            changed = true;
                        }
                    }
                }
            }
        } while (changed); // 循环直到选区不再变化

        return adjusted;
    }

    // 合并当前选区
    public void mergeSelection(Selection selection) {
        if (selection == null) {
            throw new IllegalStateException("No selection to merge");
        }

        // 先取消选区内的所有现有合并
        unmergeArea(selection);

        // 设置主单元格
        Cell master = grid[selection.getStartRow()][selection.getStartCol()];
        master.master(
                selection.getStartRow(), selection.getStartCol(),
                selection.getEndRow(), selection.getEndCol());

        // 设置从属单元格
        for (int r = selection.getStartRow(); r <= selection.getEndRow(); r++) {
            for (int c = selection.getStartCol(); c <= selection.getEndCol(); c++) {
                if (r == selection.getStartRow() && c == selection.getStartCol()) continue;
                grid[r][c].slave(master);
            }
        }
    }

    // 取消合并选区内的单元格
    public void unmergeArea(Selection selection) {
        List<Cell> processedMasters = new ArrayList<>();

        for (int r = selection.getStartRow(); r <= selection.getEndRow(); r++) {
            for (int c = selection.getStartCol(); c <= selection.getEndCol(); c++) {
                Cell cell = grid[r][c];
                if (cell.isMerged()) {
                    Cell master = cell.getRoot() != null ? cell.getRoot() : cell;
                    if (!processedMasters.contains(master)) {
                        // 恢复整个合并区域
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
