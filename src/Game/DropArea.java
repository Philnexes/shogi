package Game;

import Figure.IFigure;

import java.util.ArrayList;
import java.util.HashMap;

public class DropArea {
    Player player;
    ArrayList<IFigure> figures;

    public DropArea(Player player)
    {
        this.figures = new ArrayList<>();
        this.player = player;
    }
    public DropArea(Player player,ArrayList<IFigure> figures)
    {
        this.figures = figures;
        this.player = player;
    }

    public DropArea copy(Player player)
    {
        DropArea area =  new DropArea(player);
        for(IFigure f:this.figures) {
            IFigure figure = player.getFigureById(f.getId());
            if(figure != null)
                area.addFigure(figure);
        }
        return area;
    }

    public ArrayList<IFigure> getFigures() {
        return figures;
    }

    public void setFigures(ArrayList<IFigure> figures) {
        this.figures = figures;
    }

    public void addFigure(IFigure figure)
    {
        this.figures.add(figure);
    }
    public void removeFigure(IFigure figure)
    {
        this.figures.remove(figure);
    }
    public void removeFigureById(int id)
    {
        for(IFigure figure:this.figures)
        {
            if(figure.getId() == id)
                this.figures.remove(figure);
        }
    }
}
