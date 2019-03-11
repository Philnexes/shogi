package Figure;


import Game.PlayGround;
import Game.Player;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Figure implements IFigure {
    protected Point position;
    protected boolean promoted;
    protected boolean canPromote = true;
    protected int id;
    protected Rotation rotation;
    protected Value value;
    protected int playerID;

    public Figure(int id, Point position, Rotation rotation, boolean promoted, int playerID)
    {
        this.id = id;
        this.position = position;
        this.promoted = promoted;
        this.rotation = rotation;
        this.value = new Value();
        this.playerID = playerID;
    }

    protected IFigure getSpecificCopy()
    {
        return null;
    }

    @Override
    public IFigure getCopy()
    {
        IFigure figure = getSpecificCopy();
        figure.setValue(figure.getValue(false),figure.getValue(true));
        return figure;
    }

    @Override
    public double getValue(boolean promoted) {
        return this.value.get(promoted);
    }

    @Override
    public void promote() {
        if(canPromote)
            this.promoted = true;
        else this.promoted = false;
    }

    public ArrayList<Move> getMoves(PlayGround playGround)
    {
        ArrayList<Move> moves = null;

        Rotation r = playGround.getPlayerByID(this.playerID).getRotation();

        if (r == Rotation.Top)
            moves = checkTopPlayerMoves(playGround);
        else if(r == Rotation.Bottom)
            moves = checkBotPlayerMoves(playGround);

        if(moves != null)
            System.out.println("TROLOLO ono to jede");
        else {
            moves = new ArrayList<Move>();
           /* if(r == Rotation.Top) {
                if (playGround.getCell(this.position.x, this.position.y + 1).getFigure().getPlayer() != this.playerID)
                    moves.add(new Move(new Point(this.position.x, this.position.y), new Point(this.position.x, this.position.y + 1), this));
            }
            else if(r == Rotation.Top) {
                if (playGround.getCell(this.position.x, this.position.y - 1).getFigure().getPlayer() != this.playerID)
                    moves.add(new Move(new Point(this.position.x, this.position.y), new Point(this.position.x, this.position.y - 1), this));
            }
            if(!(moves.size() > 0 && moves.get(0).getTo().getX() < 9 && moves.get(0).getTo().getX() >= 0
                    && moves.get(0).getTo().getY() < 9 && moves.get(0).getTo().getY() >= 0))
                moves.clear();

*/
        }

        return moves;
    }


    @Override
    public void demote() {
        this.promoted = false;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setPromotion(boolean promoted)
    {
        if(canPromote)
            this.promoted = promoted;
        else this.promoted = false;
    }

    @Override
    public void setPlayer(int player){this.playerID = player;}

    @Override
    public int getPlayer(){return this.playerID;}

    @Override
    public void setX(int x) {
        this.position.setLocation(x,this.position.getY());
    }

    @Override
    public void setY(int y) {
        this.position.setLocation(this.position.getX(),y);
    }

    @Override
    public void setValue(double normal,double promoted)
    {
        this.value.set(normal,promoted);
    }

    @Override
    public void setPosition(Point position) {
        this.position.setLocation(position);
    }

    @Override
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getX() {
        return (int)this.position.getX();
    }

    @Override
    public int getY() {
        return (int)this.position.getY();
    }

    @Override
    public boolean isPromoted() {
        return this.promoted;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public Rotation getRotation() {
        return this.rotation;
    }

    @Override
    public String toString()
    {
        return this.id + "\t" + this.playerID;
    }

    public boolean checkFigure(PlayGround playGround, int x, int y, int dx, int dy)
    {
        //return playGround.getCell(x + dx, y + dy).getFigure();

        if (playGround.getCell(x + dx, y + dy).getFigure() == null)
            return true;

        if(playGround.getCell(x + dx, y + dy).getFigure().getPlayer() != this.getPlayer())
            return true;

        return false;

    }
}
