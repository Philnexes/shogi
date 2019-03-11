package Utilities.IO.Mouse.DataTypes;

import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseClick {
    Point2D position;
    MouseButton button;
    EventType<? extends MouseEvent> state;

    public MouseClick(Point2D position, MouseButton button, EventType<? extends MouseEvent> state)
    {
        this.position = position;
        this.button = button;
        this.state = state;
    }

    public Point2D getPosition()
    {
        return this.position;
    }
    public double getX()
    {
        return this.position.getX();
    }
    public double getY()
    {
        return this.position.getY();
    }
    public MouseButton getButton()
    {
        return this.button;
    }
    public EventType<? extends MouseEvent> getState()
    {
        return this.state;
    }
    public String getStateString()
    {
        return this.state.getName();
    }
    public String getButtonString()
    {
        return this.button.name();
    }

}
