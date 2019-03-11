package Figure.Figures.Promotable;

import Figure.Figure;
import Figure.IFigure;
import Figure.Move;
import Figure.Rotation;
import Game.PlayGround;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends Figure
{

    public Bishop(int id, Point position, Rotation rotation, boolean promoted, int playerID)
    {
        super(id, position, rotation, promoted, playerID);
        this.value.set(6, 10);
    }

    protected IFigure getSpecificCopy()
    {
        return new Bishop(this.id, (Point) this.position.clone(), this.rotation, this.promoted, this.playerID);
    }

    @Override
    public ArrayList<Move> checkTopPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves = new ArrayList<>();

        int i = 1;
        Boolean[] end = new Boolean[4];
        Arrays.fill(end, Boolean.FALSE);
        while(true)
        {
            if(this.getX() + i < 9
                    && this.getY() + i < 9
                    && this.checkFigure(playGround, this.getX(), this.getY(), i, i)
                    && !end[0] )
            {
                moves.add(new Move(this.getX(), this.getY(), this.getX() + i, this.getY() + i, this));

                if(playGround.getCell(this.getX() + i, this.getY() + i).getFigure() != null)
                    end[0] = Boolean.TRUE;
            }

            else
                end[0] = Boolean.TRUE;

            if(this.getX() + i < 9
                    && this.getY() - i >= 0
                    && this.checkFigure(playGround, this.getX(), this.getY(), i, -i)
                    && !end[1])
            {
                moves.add(new Move(this.getX(), this.getY(), this.getX() + i, this.getY() - i, this));

                if(playGround.getCell(this.getX() + i, this.getY() - i).getFigure() != null)
                    end[1] = Boolean.TRUE;
            }
            else
                end[1] = Boolean.TRUE;

            if(this.getX() - i >= 0
                    && this.getY() + i < 9
                    && this.checkFigure(playGround, this.getX(), this.getY(), -i, i)
                    && !end[2])
            {
                moves.add(new Move(this.getX(), this.getY(), this.getX() - i, this.getY() + i, this));
                if(playGround.getCell(this.getX() - i, this.getY() + i).getFigure() != null)
                    end[2] = Boolean.TRUE;
            }
            else
                end[2] = Boolean.TRUE;

            if(this.getX() - i >= 0
                    && this.getY() - i >= 0
                    && this.checkFigure(playGround, this.getX(), this.getY(), -i, -i)
                    && !end[3])
            {
                moves.add(new Move(this.getX(), this.getY(), this.getX() - i, this.getY() - i, this));
                if(playGround.getCell(this.getX() - i, this.getY() - i).getFigure() != null)
                    end[3] = Boolean.TRUE;

            }
            else
                end[3] = Boolean.TRUE;

            if(!Arrays.asList(end).contains(false))
                break;
            ++i;
        }

        if(this.isPromoted())
        {
            if(this.getY() != 0
                    && this.checkFigure(playGround, this.getX(), this.getY(), 0, -1))
                moves.add(new Move(this.getX(), this.getY(), this.getX(), this.getY() - 1, this));

            if(this.getX() != 8
                    && this.checkFigure(playGround, this.getX(), this.getY(), 1, 0))
                moves.add(new Move(this.getX(), this.getY(), this.getX() + 1, this.getY(), this));

            if(this.getY() != 8
                    && this.checkFigure(playGround, this.getX(), this.getY(), 0, 1))
                moves.add(new Move(this.getX(), this.getY(), this.getX(), this.getY() + 1, this));

            if(this.getX() != 0
                    && this.checkFigure(playGround, this.getX(), this.getY(), -1, 0))
                moves.add(new Move(this.getX(), this.getY(), this.getX() - 1, this.getY(), this));
        }

        if(moves.isEmpty())
            return null;
        else
            return moves;

    }

    @Override
    public ArrayList<Move> checkBotPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves;

        moves = checkTopPlayerMoves(playGround);

        if(moves.isEmpty())
            return null;
        else
            return moves;
    }

}