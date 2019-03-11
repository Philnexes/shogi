package Figure.Figures.Promotable;

import Figure.Figure;
import Figure.IFigure;
import Figure.Move;
import Figure.Rotation;
import Figure.RepetetiveMoves;
import Game.PlayGround;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends Figure {

    public Knight(int id, Point position, Rotation rotation, boolean promoted, int playerID) {
        super(id, position, rotation, promoted, playerID);
        this.value.set(8,10);
    }

    protected IFigure getSpecificCopy()
    {
        return new Knight(this.id, (Point) this.position.clone(), this.rotation, this.promoted, this.playerID);
    }

    @Override
    public ArrayList<Move> checkBotPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves = new ArrayList<>();

        if(this.isPromoted())
        {
            moves = RepetetiveMoves.repetetiveTopMove(playGround, this);
        }
        else
        {
            if(this.getX() != 0
                    && this.getY() > 1
                    && checkFigure(playGround, this.getX(), this.getY(), -1, -2))
                moves.add(new Move(this.getX(), this.getY(), this.getX() - 1, this.getY() - 2, this));

            if(this.getX() != 8
                    && this.getY() > 1
                    && checkFigure(playGround, this.getX(), this.getY(), 1, -2)
                   /* && playGround.getCell(this.getX() + 1, this.getY() - 2).getFigure() == null*/)
                moves.add(new Move(this.getX(), this.getY(), this.getX() + 1, this.getY() - 2, this));
        }

        if(moves.isEmpty())
            return null;
        else
            return moves;
    }

    @Override
    public ArrayList<Move> checkTopPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves = new ArrayList<>();

        if(this.isPromoted())
        {
            moves = RepetetiveMoves.repetetiveBotMove(playGround, this);
        }
        else
        {
            if(this.getX() != 0
                    && this.getY() < 7
                    && checkFigure(playGround, this.getX(), this.getY(), -1, 2))
                moves.add(new Move(this.getX(), this.getY(), this.getX() - 1, this.getY() + 2, this));

            if(this.getX() != 8
                    && this.getY() < 7
                    && checkFigure(playGround, this.getX(), this.getY(), 1, 2))
                moves.add(new Move(this.getX(), this.getY(), this.getX() + 1, this.getY() + 2, this));
        }

        if(moves.isEmpty())
            return null;
        else
            return moves;
    }
}
