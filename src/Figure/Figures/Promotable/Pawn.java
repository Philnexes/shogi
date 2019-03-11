package Figure.Figures.Promotable;

import Figure.Figure;
import Figure.IFigure;
import Figure.Move;
import Figure.Rotation;
import Game.PlayGround;
import Figure.RepetetiveMoves;
import Game.Player;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends Figure {

    public Pawn(int id, Point position, Rotation rotation, boolean promoted, int playerID) {
        super(id, position, rotation, promoted, playerID);
        this.value.set(1,7);
    }

    protected IFigure getSpecificCopy()
    {
        return new Pawn(this.id, (Point) this.position.clone(), this.rotation, this.promoted, this.playerID);
    }

    @Override
    public ArrayList<Move> checkTopPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves = new ArrayList<>();

        // public Move(int fromX,int fromY,int toX,int toY,IFigure figure)

        if(this.isPromoted())
        {
            moves = RepetetiveMoves.repetetiveTopMove(playGround, this);
        }
        else
        {
            if(this.getY() != 8)
            {
                if(checkFigure(playGround, this.getX(), this.getY(), 0, +1))
                    moves.add(new Move(this.getX(), this.getY(), this.getX(), this.getY() + 1, this));
            }
        }

        if(moves.isEmpty())
            return null;
        else
            return moves;
    }

    @Override
    public ArrayList<Move> checkBotPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves = new ArrayList<>();

        // public Move(int fromX,int fromY,int toX,int toY,IFigure figure)


        if(this.isPromoted())
        {
            moves = RepetetiveMoves.repetetiveBotMove(playGround, this);
        }
        else
        {
            if(this.getY() != 0)
            {
                if(checkFigure(playGround, this.getX(), this.getY(), 0, -1))
                    moves.add(new Move(this.getX(), this.getY(), this.getX(), this.getY() - 1, this));
            }
        }

        if(moves.isEmpty())
            return null;
        else
            return moves;
    }
}
