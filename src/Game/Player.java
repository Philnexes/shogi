package Game;

import Figure.Figure;
import Figure.IFigure;
import Figure.Rotation;

import java.util.ArrayList;

public class Player {
    int id;
    String name;
    Rotation rotation;
    ArrayList<IFigure> figures;

    public Player()
    {
        this(-1,"",Rotation.Bottom);
    }
    public Player(int id,Rotation rotation)
    {
        this(id,"",rotation);
    }
    public Player(int id,String name,Rotation rotation)
    {
        this.id = id;
        this.name = name;
        this.rotation = rotation;
        this.figures = new ArrayList<>();
    }

    public Player getCopy()
    {
        Player p =  new Player(this.id,this.name,this.rotation);
        for(IFigure figure: figures)
        {
            p.addFigure(figure.getCopy());
        }
        return p;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setRotation(Rotation rotation)
    {
        this.rotation = rotation;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public void setFigure(int index,IFigure figure)
    {
        this.figures.set(index,figure);
    }
    public void addFigure(IFigure figure)
    {
        this.figures.add(figure);
    }

    public Rotation getRotation()
    {
        return this.rotation;
    }
    public String getName()
    {
        return this.name;
    }
    public int getId()
    {
        return this.id;
    }
    public IFigure getFigure(int index)
    {
        return this.figures.get(index);
    }
    public ArrayList<IFigure> getFigures() {
        return figures;
    }

    public void clearFigures()
    {
        this.figures.clear();
    }
    public boolean hasFigure(IFigure figure) {
        return figures.contains(figure);
    }
    public double getValue()
    {
        double sum = 0.0;
        for(IFigure figure:this.figures)
        {
            if(figure.getX() >= 0 && figure.getY() >= 0)
                sum += figure.getValue(figure.isPromoted());
        }
        return sum;
    }

    public IFigure getFigureById(int figureId) {
        for (IFigure figure : this.figures) {
            if (figure.getId() == figureId)
                return figure;
        }
        return null;
    }
}
