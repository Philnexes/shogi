package Game;

import Figure.IFigure;
import Figure.Move;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PlayGround
{
    Point size;
    ArrayList<Player> players;
    ArrayList<DropArea> dropAreas;
    HashMap<Point,Cell> cells;

    public PlayGround(int width,int height)
    {
        this.size = new Point(width,height);
        this.players = new ArrayList<>();
        this.cells = new HashMap<>();
        this.dropAreas = new ArrayList<DropArea>();

        for(int y = 0;y < height;y++)
            for (int x = 0; x < width; x++) {
                Point p = new Point(x, y);
                cells.put(p, new Cell(p));
            }

    }
    public PlayGround(int width, int height, Collection<Cell> cells, ArrayList<Player> players, ArrayList<DropArea> dropAreas)
    {
        this.size = new Point(width,height);
        this.players = new ArrayList<>();
        this.cells = new HashMap<>();
        this.dropAreas = new ArrayList<DropArea>();
        for(Cell cell: cells)
        {
            Cell c = cell.getCopy();
            this.cells.put(c.getPosition(),c);
        }
        for(Player player: players)
        {
            Player p = player.getCopy();
            this.players.add(p);
            DropArea area = new DropArea(player);
            for(DropArea d: dropAreas)
            {
                if(d.player.getId() == p.getId())
                    area = d.copy(player);
            }
            this.dropAreas.add(area);
        }
    }

    public PlayGround GetCopy()
    {
        return new PlayGround((int)this.size.getX(), (int)this.size.getY(), this.cells.values(), this.players, this.dropAreas);
    }

    public void reMap()
    {
        for(Cell c: this.cells.values())
            c.setFigure(null);
        for(Player p: this.players)
            for(IFigure f: p.getFigures())
                this.getCell(f.getX(),f.getY()).setFigure(f);
    }

    public HashMap<Point,Cell> getCells()
    {
        return this.cells;
    }
    public Cell getCell(int x,int y)
    {
        return this.cells.get(new Point(x,y));
    }
    public Cell getCell(Point point)
    {
        return this.cells.get(point);
    }
    public Player getPlayer(int index)
    {
        return this.players.get(index);
    }
    public ArrayList<Player> getPlayers()
    {
        return this.players;
    }
    public Player getPlayerByID(int playerID)
    {
        for (Player i : getPlayers())
            if(i.getId() == playerID)
                return i;

        return null;
    }

    public ArrayList<DropArea> getDropAreas() {
        return dropAreas;
    }

    public void addDropArea(DropArea area)
    {
        this.dropAreas.add(area);
    }

    public void addPlayer(Player player)
    {
        this.players.add(player);
        addDropArea(new DropArea(player));
    }

    public void clearPlayers()
    {
        this.players.clear();
        this.dropAreas.clear();
    }

    public void executeMove(Move move) {
        /* ToDo move figure */


    }
}
