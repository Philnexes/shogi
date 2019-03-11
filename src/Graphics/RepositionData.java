package Graphics;

import javafx.geometry.Point2D;

public class RepositionData {
    Point2D position;
    Point2D size;

    public RepositionData(Point2D position,Point2D size)
    {
        this.position = position;
        this.size = size;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getSize() {
        return size;
    }

    public void setSize(Point2D size) {
        this.size = size;
    }
}
