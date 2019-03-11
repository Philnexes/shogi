package Graphics.GUI.Components;

import Utilities.Callback.ICallback;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class ProgressBar extends javafx.scene.control.ProgressBar {
    ICallback<MouseEvent> callback;
    private double maxValue;

    public ProgressBar(double value,double maxValue)
    {
        super(value/maxValue);
        this.maxValue = maxValue;
    }
    public ProgressBar(Point2D position, Point2D size, double value, double maxValue)
    {
        this(value,maxValue);
        resizeLabel(position,size);
    }

    public ProgressBar(Point2D position, Point2D size, double value, double maxValue, ICallback<MouseEvent> callback)
    {
        this(value,maxValue);
        resizeLabel(position,size);
        setCallback(callback);
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
    public void setValue(double value)
    {
        this.progressProperty().set(value/this.maxValue);
    }
    public void setMaxValue(double value)
    {
        this.maxValue = value;
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
