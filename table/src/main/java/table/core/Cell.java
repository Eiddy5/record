package table.core;

import json.JsonObject;
import lombok.Data;

@Data
public class Cell {
    private int x;
    private int y;
    private int rolSpan;
    private int colSpan;
    private boolean display;
    private Cell root;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.display = true;
        this.rolSpan = 0;
        this.colSpan = 0;
        this.root = null;
    }


    @Override
    public String toString() {
        return JsonObject.from(this);
    }
}
