package Figure.Figures.Promotable;

import Figure.Figure;
import Figure.IFigure;
import Figure.Move;
import Figure.Rotation;
import Game.PlayGround;
import Figure.RepetetiveMoves;

import java.awt.*;
import java.util.ArrayList;

public class Silver extends Figure {

    public Silver(int id, Point position, Rotation rotation, boolean promoted, int playerID) {
        super(id, position, rotation, promoted, playerID);
        this.value.set(5,6);
    }

    protected IFigure getSpecificCopy()
    {
        return new Silver(this.id, (Point) this.position.clone(), this.rotation, this.promoted, this.playerID);
    }

    @Override
    public ArrayList<Move> checkTopPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves = new ArrayList<>();

        int dx, dy;

        if(this.isPromoted())
        {
            moves = RepetetiveMoves.repetetiveTopMove(playGround, this);
        }
        else
        {
           if(this.getY() != 0)
           {
               if(this.getX() != 0)
               {
                    dx = dy = -1;

                   if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                       moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
               }

               if(this.getX() != 8)
               {
                   dy = -1;
                   dx = 1;

                   if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                       moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
               }
           }

           if(this.getY() != 8)
           {
               dx = 0;
               dy = 1;

               if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                   moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));

               if(this.getX() != 0)
               {
                   dx = -1;
                   dy = 1;

                   if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                       moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
               }

               if(this.getX() != 8)
               {
                   dx = dy = 1;

                   if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                       moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
               }
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

        int dx, dy;
        // public Move(int fromX,int fromY,int toX,int toY,IFigure figure)


        if(this.isPromoted())
        {
            moves = RepetetiveMoves.repetetiveBotMove(playGround, this);
        }
        else
        {
           if(this.getY() != 0)
           {
               dx = 0;
               dy = -1;

               if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                   moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));

               if(this.getX() != 0)
               {
                   dx = -1;
                   dy = -1;

                   if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                       moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
               }

               if(this.getX() != 8)
               {
                   dx = 1;
                   dy = -1;

                   if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                       moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
               }
           }

           if(this.getY() != 8)
           {
               if(this.getX() != 0)
               {
                   dx = -1;
                   dy = 1;

                   if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                       moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
               }

               if(this.getX() != 8)
               {
                   dx = 1;
                   dy = 1;

                   if(checkFigure(playGround, this.getX(), this.getY(), dx, dy))
                       moves.add(new Move(this.getX(), this.getY(), this.getX() + dx, this.getY() + dy, this));
               }
           }
        }

        if(moves.isEmpty())
            return null;
        else
            return moves;
    }
}
