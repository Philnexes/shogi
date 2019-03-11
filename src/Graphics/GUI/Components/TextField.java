package Graphics.GUI.Components;

import Utilities.Callback.ICallback;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;

public class TextField extends javafx.scene.control.TextField{
    ICallback<KeyEvent> callback;

    public TextField()
    {
        super();
    }
    public TextField(String text)
    {
        super(text);
    }
    public TextField(Point2D position, Point2D size, String text)
    {
        super(text);
        resizeTextField(position,size);
    }
    public TextField(Point2D position, Point2D size) {
        super();
        resizeTextField(position,size);
    }

    public TextField(Point2D position, Point2D size, String text, ICallback<KeyEvent> callback)
    {
        super(text);
        resizeTextField(position,size);
        setCallback(callback);
    }
    public TextField(Point2D position, Point2D size, ICallback<KeyEvent> callback) {
        super();
        resizeTextField(position,size);
        setCallback(callback);
    }

    public void resizeTextField(Point2D position,Point2D size)
    {
        setPosition(position);
        setSize(size);
    }
    public void setCallback(ICallback<KeyEvent> callback)
    {
        this.callback = callback;
        this.setOnKeyReleased(event -> this.callback.call(event));
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
    public ICallback<KeyEvent> getCallback()
    {
        return this.callback;
    }
}
