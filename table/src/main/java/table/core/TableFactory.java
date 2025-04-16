package table.core;

public class TableFactory {


    public static Table create(int row, int col) {
        Table table = new Table(row, col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Cell cell = new Cell(i, j);
                table.addCell(i, j, cell);
            }
        }
        return table;
    }

}
