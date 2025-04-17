package table.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public int getStartX() {
        if (root == null) {
            return x;
        } else {
            return root.getStartX();
        }
    }

    @JsonIgnore
    public int getStartY() {
        if (root == null) {
            return y;
        } else {
            return root.getStartY();
        }
    }

    @JsonIgnore
    public int getEndX() {
        if (root == null) {
            return x + colSpan - 1;
        } else {
            return root.getEndX();
        }
    }

    @JsonIgnore
    public int getEndY() {
        if (root == null) {
            return y + rolSpan - 1;
        } else {
            return root.getEndY();
        }
    }

    public boolean isMerged() {
        return !display || rolSpan != 0 || colSpan != 0;
    }

    public Cell getRoot() {
        if (root == null) {
            return this;
        }
        return root;
    }

    @Override
    public String toString() {
        return JsonObject.from(this);
    }
}
