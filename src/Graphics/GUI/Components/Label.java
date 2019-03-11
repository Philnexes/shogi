package Graphics.GUI.Components;

import Utilities.Callback.ICallback;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class Label extends javafx.scene.control.Label {
    ICallback<MouseEvent> callback;

    public Label(Point2D position, Point2D size, String text)
    {
        super(text);
        resizeLabel(position,size);
    }
    public Label(Point2D position, Point2D size, String text, Node graphic) {
        super(text,graphic);
        resizeLabel(position,size);
    }

    public Label(Point2D position, Point2D size, String text, ICallback<MouseEvent> callback)
    {
        super(text);
        resizeLabel(position,size);
        setCallback(callback);
    }
    public Label(Point2D position, Point2D size, String text, Node graphic, ICallback<MouseEvent> callback) {
        super(text,graphic);
        resizeLabel(position,size);
        setCallback(callback);
    }

    public Label(String label) {
        super(label);
    }

    public void resizeLabel(Point2D position,Point2D size)
    {
        setPosition(position);
        setSize(size);
    }
    public void setCallback(ICallback<MouseEvent> callback)
    {
        this.callback = callback;
        this.setOnMouseClicked(event -> this.callback.call(event));
    }
    public void setPosition(Point2D position)
    {
        this.setLayoutX(position.getX());
        this.setLayoutY(position.getY());
    }
    public void setSize(Point2D size)
    {
        this.setWidth(size.getX());
        this.setHeight(size.getY());
    }

    public Point2D getPosition()
    {
        return new Point2D(this.getLayoutX(),this.getLayoutY());
    }
    public Point2D getSize()
    {
        return new Point2D(this.getWidth(),this.getHeight());
    }
    public ICallback<MouseEvent> getCallback()
    {
        return this.callback;
    }
}
