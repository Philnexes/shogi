package Figure;

import java.awt.Point;

public class Move {
    Point from;
    Point to;
    IFigure figure;

    public Move(int fromX,int fromY,int toX,int toY,IFigure figure)
    {
        this.from = new Point(fromX,fromY);
        this.to = new Point(toX,toY);
        this.figure = figure;
    }

    public Move(Point from,Point to,IFigure figure)
    {
        this.from = (Point)from.clone();
        this.to = (Point)to.clone();
        this.figure = figure;
    }

    public void setFrom(Point from)
    {
        this.from = from;
    }
    public void setTo(Point to)
    {
        this.to = to;
    }
    public void setFigure(IFigure figure)
    {
        this.figure = figure;
    }

    public Point getFrom()
    {
        return this.from;
    }
    public Point getTo()
    {
        return this.to;
    }
    public IFigure getFigure()
    {
        return this.figure;
    }

    public String toString()
    {
        return figure.getId()+": "+from.x+","+from.y+" => "+to.x+","+to.y;
    }
}
