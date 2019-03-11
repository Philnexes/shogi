package Graphics.Types;

import Graphics.ILayer;
import Graphics.IPaintable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.util.ArrayList;

public class PaintableObject implements IPaintable {

    Point2D position;
    Point2D size;
    ArrayList<ILayer> layers;
    double rotation;
    double opacity;
    boolean hidden;

    public PaintableObject()
    {
        this.layers = new ArrayList<>();
        this.position = new Point2D(0,0);
        this.size = new Point2D(0,0);
        this.rotation = 0.0;
        this.opacity = 1.0;
        this.hidden = false;
    }

    public PaintableObject(Point2D position, Point2D size)
    {
        this.layers = new ArrayList<>();
        this.position = position;
        this.size = size;
        this.opacity = 1.0;
        this.rotation = 0.0;
        this.hidden = false;
    }

    public PaintableObject(Point2D position, Point2D size, double rotation)
    {
        this.layers = new ArrayList<>();
        this.position = position;
        this.size = size;
        this.opacity = 1.0;
        this.rotation = rotation;
        this.hidden = false;
    }

    public PaintableObject(Point2D position, Point2D size, double rotation, double opacity)
    {
        this.layers = new ArrayList<>();
        this.position = position;
        this.size = size;
        this.opacity = opacity;
        this.rotation = rotation;
        this.hidden = false;
    }

    @Override
    public int getLayersCount() {
        return layers.size();
    }

    @Override
    public ArrayList<ILayer> getLayers() {
        return this.layers;
    }

    @Override
    public void draw(Pane pane) {
        for(ILayer layer: this.layers)
        {
            pane.getChildren().add((Node)layer.getObject());
        }
    }

    @Override
    public void erase(Pane pane) {
        for(ILayer layer: this.layers)
        {
            pane.getChildren().remove(layer.getObject());
        }
    }

    @Override
    public void show() {
        for(ILayer layer: this.layers)
        {
            layer.show();
        }
        this.hidden = false;
    }

    @Override
    public void hide() {
        for(ILayer layer: this.layers)
        {
            layer.hide();
        }
        this.hidden = true;
    }

    @Override
    public boolean isVisible() {
        return this.hidden;
    }

    @Override
    public void show(int layer) {
        this.hidden = false;
        this.layers.get(layer).show();
    }

    @Override
    public void hide(int layer) {
        this.layers.get(layer).hide();
        checkVisible();
    }

    private void checkVisible()
    {
        boolean visible = false;
        for(ILayer l: layers)
        {
            visible = l.isVisible();
            if(visible)
                break;
        }
        this.hidden = !visible;
    }

    @Override
    public double getRotation() {
        return this.rotation;
    }

    @Override
    public double getRotation(int layer) {
        return this.layers.get(layer).getRotation();
    }

    @Override
    public double getOpacity() {
        return this.opacity;
    }

    @Override
    public double getOpacity(int layer) {
        return this.layers.get(layer).getOpacity();
    }

    @Override
    public ILayer getLayer(int layer) {
        return this.layers.get(layer);
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public Point2D getSize() {
        return this.size;
    }


    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
        for(ILayer layer: layers)
        {
            layer.setRotation(rotation);
        }
    }

    @Override
    public void setRotation(double rotation, int layer) {
        this.layers.get(layer).setRotation(rotation);
    }

    @Override
    public void setOpacity(double opacity) {
        this.opacity = opacity;
        for(ILayer layer: layers)
        {
            layer.setOpacity(opacity);
        }
        checkVisible();
    }

    @Override
    public void setOpacity(double opacity, int layer) {
        this.layers.get(layer).setOpacity(opacity);
        checkVisible();
    }

    @Override
    public void setLayer(int index,ILayer layer) {
        this.layers.set(index,layer);
        checkVisible();
    }

    @Override
    public void setPosition(Point2D position) {
        this.position = position;
        for(ILayer layer: layers)
        {
            layer.setPosition(position);
        }
    }

    @Override
    public void setSize(Point2D size) {
        this.size = size;
        for(ILayer layer: layers)
        {
            layer.setSize(size);
        }
    }


    @Override
    public void addLayer(ILayer layer) {
        this.layers.add(layer);
        checkVisible();
    }
}
