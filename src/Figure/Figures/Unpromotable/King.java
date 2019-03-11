package Figure.Figures.Unpromotable;

import Figure.Figure;
import Figure.IFigure;
import Figure.Move;
import Figure.Rotation;
import Figure.RepetetiveMoves;
import Game.PlayGround;
import Utilities.Debug.ConsoleResults;

import java.awt.*;
import java.util.ArrayList;

public class King extends Figure {

    public King(int id, Point position, Rotation rotation, boolean promoted, int playerID) {
        super(id, position, rotation, promoted, playerID);
        this.value.set(Integer.MAX_VALUE/2,0);
        this.canPromote = this.promoted = false;
    }

    protected IFigure getSpecificCopy()
    {
        return new King(this.id, (Point) this.position.clone(), this.rotation, this.promoted, this.playerID);
    }

    @Override
    public ArrayList<Move> checkTopPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves;

        int dx, dy;
        moves = RepetetiveMoves.repetetiveTopMove(playGround, this);

        if(this.getY() != 0)
        {
           if(this.getX() != 0)
           {
               dx = -1;
               dy = -1;

               if(this.checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                   moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
           }

            if(this.getX() != 8)
            {
                dx = 1;
                dy = -1;

                if(this.checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                    moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
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
        ArrayList<Move> moves;

        moves = checkTopPlayerMoves(playGround);


        if(moves.isEmpty())
            return null;
        else
            return moves;
    }

}
