package Graphics.GUI.Components;

import Utilities.Callback.ICallback;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


public class Button extends javafx.scene.control.Button{
    ICallback<MouseEvent> callback;

    public Button(String text, ICallback<MouseEvent> callback)
    {
        super(text);
        setCallback(callback);
    }
    public Button(Point2D position, Point2D size, String text, ICallback<MouseEvent> callback)
    {
        super(text);
        resizeButton(position,size);
        setCallback(callback);
    }
    public Button(Point2D position, Point2D size, String text, Node graphic, ICallback<MouseEvent> callback) {
        super(text,graphic);
        resizeButton(position,size);
        setCallback(callback);
    }
    public void resizeButton(Point2D position,Point2D size)
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