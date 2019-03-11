package Figure.Setups;

import Figure.IFigure;
import Figure.Rotation;
import Game.Player;

import java.awt.*;
import java.util.ArrayList;

public interface IFigureSetup {
    ArrayList<IFigure> getCopy(Player player);
    Point getCenter();
    Point getPlaygroundSize();
    int getFiguresCount();
}
