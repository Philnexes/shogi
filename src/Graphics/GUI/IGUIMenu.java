package Graphics.GUI;

import javafx.geometry.Point2D;

public interface IGUIMenu {
    IGUIMenu create(Point2D position, Point2D size);
    void move(Point2D position, Point2D size);
    void draw();
    void erase();

    void show();
    void hide();
    void setVisible(boolean visible);
    void setOpacity(double opacity);
    double getOpacity();
}
