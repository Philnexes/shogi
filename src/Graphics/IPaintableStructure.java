package Graphics;

import Graphics.Types.PaintableObject;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.awt.*;

public interface IPaintableStructure {
    //void ReDraw(Point2D topEdge, Point2D figureSize);

    void draw(Pane pane);
    void erase(Pane pane);

    void show();
    void hide();

    Object getObject();
    PaintableObject getGraphics();

    void setGraphics(PaintableObject graphics);
    void setObject(Object object);
}
