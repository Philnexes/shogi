package Graphics;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public class TextLayer implements ILayer {

    Label label;
    /* ToDo optimise constructors */
    public TextLayer(Point2D position)
    {
        this.label = new Label();
        setPosition(position);
    }
    public TextLayer(String text)
    {
        this.label = new Label();
        setData(text);
    }
    public TextLayer(Point2D position, String text)
    {
        this.label = new Label();
        setPosition(position);
        setData(text);
    }
    public TextLayer(String text, Point2D size)
    {
        this.label = new Label();
        setSize(size);
        setData(text);
    }
    public TextLayer(String text, String style,Point2D size)
    {
        this.label = new Label();
        setSize(size);
        setData(text);
        this.label.setStyle(style);
    }
    public TextLayer(Point2D position,Point2D size, String text)
    {
        this.label = new Label();
        setPosition(position);
        setSize(size);
        setData(text);
    }
    public TextLayer(Point2D position,Point2D size, String text, String style)
    {
        this.label = new Label();
        setPosition(position);
        setSize(size);
        setData(text);
        this.label.setStyle(style);
    }
    public TextLayer(String text, Paint color, String style,Point2D size)
    {
        this.label = new Label();
        setSize(size);
        setData(text);
        this.label.setStyle(style);
        this.label.setTextFill(color);
    }
    public TextLayer(Point2D position, Point2D size, String text, Paint color, String style)
    {
        this.label = new Label();
        setPosition(position);
        setSize(size);
        setData(text);
        this.label.setStyle(style);
        this.label.setTextFill(color);
    }
    public TextLayer(Point2D position, String text, String style)
    {
        this.label = new Label();
        setPosition(position);
        setData(text);
        this.label.setStyle(style);
    }
    public TextLayer(Point2D position, String text, String style, double rotation)
    {
        this.label = new Label();
        setPosition(position);
        setRotation(rotation);
        setData(text);
        this.label.setStyle(style);
    }
    public TextLayer(Point2D position, Point2D size, String text, String style, double rotation)
    {
        this.label = new Label();
        setPosition(position);
        setRotation(rotation);
        setSize(size);
        setData(text);
        this.label.setStyle(style);
    }

    @Override
    public boolean isVisible() {
        return this.label.getOpacity() == 1.0;
    }

    @Override
    public void show() {
        this.label.setOpacity(1.0);
    }

    @Override
    public void hide() {
        this.label.setOpacity(0.0);
    }

    @Override
    public void setPosition(Point2D point) {
        this.label.setLayoutX(point.getX());
        this.label.setLayoutY(point.getY());
    }

    @Override
    public Point2D getPosition() {
        return new Point2D(this.label.getLayoutX(),this.label.getLayoutY());
    }

    @Override
    public void setSize(Point2D point) {
        this.label.setPrefSize(point.getX(),point.getY());
    }

    @Override
    public Point2D getSize() {
        return new Point2D(this.label.getMaxWidth(),this.label.getMaxHeight());
    }

    @Override
    public void setRotation(double rotation) {
        this.label.setRotate(rotation);
    }

    @Override
    public void setOpacity(double opacity) {
        this.label.setOpacity(opacity);
    }

    @Override
    public void setData(Object data) {
        this.label.setText((String)data);
    }

    @Override
    public void setObject(Object object) {
        this.label = (Label)object;
    }

    @Override
    public Object getData() {
        return this.label.getText();
    }

    @Override
    public Object getObject() {
        return this.label;
    }

    @Override
    public double getOpacity() {
        return this.label.getOpacity();
    }

    @Override
    public double getRotation() {
        return this.label.getRotate();
    }
}
