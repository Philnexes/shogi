package Graphics.GUI.Components;

import Utilities.Callback.ICallback;
import com.sun.javafx.sg.prism.NGNode;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.LightBase;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.util.Date;

public class Input extends HBox {
    public Input()
    {
        super();
    }
    public Input(String label,String type)
    {
        this.setPadding(new Insets(10,0,0,10));
        this.setAlignment(Pos.CENTER);
        Label l = new Label(label);
        l.getStyleClass().add("gui-input-label");
        this.getChildren().add(l);
        switch (type)
        {
            case "text":
                TextField t = new TextField();
                l.getStyleClass().add("gui-input-input");
                this.getChildren().add(t);
                break;
            case "pass":
                PasswordField p = new PasswordField();
                l.getStyleClass().add("gui-input-input");
                this.getChildren().add(p);
                break;
        }
    }
    public void resizeInput(Point2D position, Point2D size)
    {
        setPosition(position);
        setSize(size);
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

    public String getText() {
        return ((TextInputControl)this.getChildren().get(1)).getText();
    }
}
