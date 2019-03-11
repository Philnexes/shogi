package Graphics;

import javafx.geometry.Point2D;

public interface ILayer {
    boolean isVisible();
    void show();
    void hide();

    void setPosition(Point2D point);
    Point2D getPosition();
    void setSize(Point2D point);
    Point2D getSize();

    void setRotation(double rotation);
    void setOpacity(double opacity);
    void setData(Object data);
    void setObject(Object object);

    Object getData();
    Object getObject();
    double getOpacity();
    double getRotation();
}
