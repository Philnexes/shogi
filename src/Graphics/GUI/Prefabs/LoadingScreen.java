package Graphics.GUI.Prefabs;

import Graphics.GUI.Components.ProgressBar;
import Graphics.GUI.IGUIMenu;
import Networking.MySQL.Manager;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class LoadingScreen extends Menu{
    public LoadingScreen(Pane pane)
    {
        super(pane);
        create(new Point2D(0,0),new Point2D(0,0));
    }
    public LoadingScreen(Pane pane, Point2D position, Point2D size)
    {
        super(pane);
        create(position, size);
    }

    @Override
    public IGUIMenu create(Point2D position, Point2D size) {
        this.bars.add(new ProgressBar(0,15));
        this.bars.get(0).getStyleClass().add("gui-progressbar");
        move(position, size);
        return this;
    }

    @Override
    public void move(Point2D position, Point2D size) {
        this.position = position;
        this.size = size;
        double dx = position.getX(), dy = position.getY();
        double dw = size.getX(), dh = size.getY();
        this.bars.get(0).setPosition(new Point2D(dx,dy));
        this.bars.get(0).setPrefSize(dw,dh);
    }

    public void setValue(Integer value) {
        for(int i = 0;i < this.bars.size();i++)
        {
            this.bars.get(i).setValue(value);
        }
    }
}
