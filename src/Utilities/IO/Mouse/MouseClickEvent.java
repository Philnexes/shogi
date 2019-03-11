package Utilities.IO.Mouse.DataTypes;

import Utilities.Callback.ICallback;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class MouseClickEvent {
    Pane pane;
    ICallback<MouseClick> callback;
    public MouseClickEvent(Pane pane, ICallback<MouseClick> callback)
    {
        this.pane = pane;
        this.callback = callback;
        this.pane.addEventHandler(MouseEvent.ANY, (MouseEvent event) -> callback.call(new MouseClick(
                new Point2D(event.getX(),event.getY()),
                event.getButton(),
                event.getEventType()
        )));
    }
}
