package Game;

import Figure.IFigure;

import java.awt.*;

public class Cell {
    IFigure figure;
    Point position;
    boolean highlighted;

    public Cell(Point point) {
        this.position = point;
        this.highlighted = false;
    }

    public Cell(int x, int y) {
        this.position = new Point(x, y);
        this.highlighted = false;
    }

    public Cell getCopy()
    {
        Cell c = new Cell(this.position);
        c.setHighlight(this.highlighted);
        c.setFigure(this.figure.getCopy());
        return c;
    }
    public boolean isHighlighted()
    {
        return this.highlighted;
    }
    public void setHighlight(boolean highlighted)
    {
        this.highlighted = highlighted;
    }
    public Point getPosition(){
        return this.position;
    }
    public void setPosition(Point position)
    {
        this.position = position;
    }
    public void setFigure(IFigure figure)
    {
        this.figure = figure;
    }
    public IFigure getFigure()
    {
        return this.figure;
    }
    public String toString() { return this.position.toString() +"\t"+this.highlighted+"\t"+(this.figure!=null?this.figure.toString():"");}
}
