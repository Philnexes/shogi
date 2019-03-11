package Figure.Figures.Unpromotable;

import Figure.Figure;
import Figure.IFigure;
import Figure.Move;
import Figure.Rotation;
import Figure.RepetetiveMoves;
import Game.PlayGround;

import java.awt.*;
import java.util.ArrayList;

public class Gold extends Figure {

    public Gold(int id, Point position, Rotation rotation, boolean promoted, int playerID) {
        super(id, position, rotation, promoted, playerID);
        this.value.set(6,0);
        this.canPromote = this.promoted = false;
    }

    protected IFigure getSpecificCopy()
    {
        return new Gold(this.id, (Point) this.position.clone(), this.rotation, this.promoted, this.playerID);
    }

    @Override
    public ArrayList<Move> checkTopPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves;

        // public Move(int fromX,int fromY,int toX,int toY,IFigure figure)
        moves = RepetetiveMoves.repetetiveTopMove(playGround, this);

        if(moves.isEmpty())
            return null;
        else
            return moves;
    }

    @Override
    public ArrayList<Move> checkBotPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves;

        // public Move(int fromX,int fromY,int toX,int toY,IFigure figure)
        moves = RepetetiveMoves.repetetiveBotMove(playGround, this);

        if(moves.isEmpty())
            return null;
        else
            return moves;
    }
}
