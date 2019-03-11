package Graphics;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public interface IPaintable {
    int getLayersCount();
    ArrayList<ILayer> getLayers();

    void draw(Pane pane);
    void erase(Pane pane);
    void show();
    void hide();
    boolean isVisible();
    void show(int layer);
    void hide(int layer);

    double getRotation();
    double getRotation(int layer);
    double getOpacity();
    double getOpacity(int layer);
    ILayer getLayer(int layer);
    Point2D getPosition();
    Point2D getSize();

    void setRotation(double rotation);
    void setRotation(double rotation,int layer);
    void setOpacity(double opacity);
    void setOpacity(double opacity,int layer);
    void setLayer(int index, ILayer layer);
    void setPosition(Point2D position);
    void setSize(Point2D size);
    void addLayer(ILayer layer);
}
