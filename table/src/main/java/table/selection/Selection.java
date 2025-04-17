package table.selection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Selection {
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public int getMinX() {
        return Math.min(startX, endX);
    }

    public int getMaxX() {
        return Math.max(startX, endX);
    }

    public int getMinY() {
        return Math.min(startY, endY);
    }

    public int getMaxY() {
        return Math.max(startY, endY);
    }
}
