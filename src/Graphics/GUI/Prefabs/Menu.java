package Graphics.GUI.Prefabs;

import Graphics.GUI.Components.*;
import Graphics.GUI.IGUIMenu;
import javafx.geometry.Point2D;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class Menu implements IGUIMenu {
    Pane pane;
    ArrayList<Button> buttons;
    ArrayList<Label> labels;
    ArrayList<Input> inputs;
    ArrayList<ProgressBar> bars;
    Background background;
    double opacity;
    Point2D position;
    Point2D size;

    public Menu(Pane pane)
    {
        this.buttons = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.inputs = new ArrayList<>();
        this.bars = new ArrayList<>();
        this.pane = pane;
        this.opacity = 0.0;
    }

    @Override
    public void draw() {
        for(Label label: labels)
            this.pane.getChildren().add(label);
        for(Button button: buttons)
            this.pane.getChildren().add(button);
        for(Input input: inputs)
            this.pane.getChildren().add(input);
        for(ProgressBar bar: bars)
            this.pane.getChildren().add(bar);
    }

    @Override
    public void erase() {
        for(Label label: labels)
            this.pane.getChildren().remove(label);
        for(Button button: buttons)
            this.pane.getChildren().remove(button);
        for(Input input: inputs)
            this.pane.getChildren().remove(input);
        for(ProgressBar bar: bars)
            this.pane.getChildren().remove(bar);
    }

    @Override
    public void show() {
        this.setOpacity(1.0);
        this.move(this.position,this.size);
    }

    @Override
    public void hide() {
        this.setOpacity(0.0);
    }

    @Override
    public void setVisible(boolean visible)
    {
        this.setOpacity(visible?1.0:0.0);
    }

    @Override
    public void setOpacity(double opacity) {
        this.opacity = opacity;

        for(Label label: labels)
            label.setOpacity(opacity);
        for(Button button: buttons)
            button.setOpacity(opacity);
        for(Input input: inputs)
            input.setOpacity(opacity);
        for(ProgressBar bar: bars)
            bar.setOpacity(opacity);
    }

    @Override
    public double getOpacity() {
        return this.opacity;
    }
}
