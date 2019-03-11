package Graphics.Types;

import Graphics.IPaintableStructure;
import Graphics.ImageLayer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PaintableBackground implements IPaintableStructure {
    Point2D position;
    Point2D size;
    PaintableObject graphics;
    ArrayList<Image> images;

    public PaintableBackground(ArrayList<Image> images, Stage stage, Point2D position,Point2D size) {
        this.graphics = new PaintableObject();
        this.images = new ArrayList<Image>();
        this.images.addAll(images);
        for (Image image : images) {
            this.graphics.addLayer(new ImageLayer(size, image));
        }
        reDraw(position,size);
    }

    public void reDraw(Point2D position,Point2D size) {
        this.position = position;
        this.size = size;
        this.graphics.setPosition(this.position);
        this.graphics.setSize(this.size);
    }

    @Override
    public void draw(Pane pane) {
        this.graphics.draw(pane);
    }

    @Override
    public void erase(Pane pane) {
        this.graphics.erase(pane);
    }

    @Override
    public void show() {
        this.graphics.show();
    }

    @Override
    public void hide() {
        this.graphics.hide();
    }

    @Override
    public Object getObject() {
        return this.images;
    }

    @Override
    public PaintableObject getGraphics() {
        return this.graphics;
    }

    @Override
    public void setGraphics(PaintableObject graphics) {
        this.graphics = graphics;
    }

    @Override
    public void setObject(Object object) {
        this.images = (ArrayList<Image>)object;
    }
}
