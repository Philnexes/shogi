package Figure;

import Game.PlayGround;
import Game.Player;

import java.awt.Point;
import java.util.ArrayList;

public interface IFigure {

    IFigure getCopy();

    ArrayList<Move> getMoves(PlayGround playGround);
    double getValue(boolean promoted);

    void promote();
    void demote();

    void setId(int id);
    void setX(int x);
    void setY(int y);
    void setPlayer(int id);
    void setValue(double normal,double promoted);
    void setPosition(Point position);
    void setRotation(Rotation rotation);
    void setPromotion(boolean promoted);
    ArrayList<Move> checkTopPlayerMoves(PlayGround playGround);
    ArrayList<Move> checkBotPlayerMoves(PlayGround playGround);

    int getId();
    int getX();
    int getY();
    boolean isPromoted();
    Point getPosition();
    Rotation getRotation();
    int getPlayer();
    boolean checkFigure(PlayGround playGround, int x, int y, int dx, int dy);

    String toString();
}
