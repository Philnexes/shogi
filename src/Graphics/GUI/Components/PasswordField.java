package Graphics.GUI.Components;

import Utilities.Callback.ICallback;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;

public class PasswordField extends javafx.scene.control.PasswordField {
    ICallback<KeyEvent> callback;

    public PasswordField(Point2D position, Point2D size) {
        super();
        resizePasswordField(position,size);
    }

    public PasswordField(Point2D position, Point2D size, ICallback<KeyEvent> callback) {
        super();
        resizePasswordField(position,size);
        setCallback(callback);
    }

    public PasswordField() {
        super();
    }

    public void resizePasswordField(Point2D position,Point2D size)
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
