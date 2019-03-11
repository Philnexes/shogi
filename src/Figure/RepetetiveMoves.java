package Figure;

import Figure.IFigure;
import Figure.Move;
import Game.PlayGround;
import java.util.ArrayList;

public class RepetetiveMoves
{
    public static ArrayList<Move> repetetiveTopMove(PlayGround playGround, IFigure figure)
    {
        ArrayList<Move> moves = new ArrayList<>();

        int dx, dy;

        if(figure.getY() != 0)
        {
            dx = 0;
            dy = -1;

            if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));
        }

        if(figure.getX() != 0)
        {
            dx = -1;
            dy = 0;

            if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));

            if(figure.getY() != 8)
            {
                dx = -1;
                dy = 1;

                if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                    moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));
            }
        }

        if(figure.getY() != 8)
        {
            dx = 0;
            dy = 1;

            if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));

            if(figure.getX() != 8)
            {
                dx = 1;
                dy = 1;

                if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                    moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));
            }
        }

        if(figure.getX() != 8)
        {
            dx = 1;
            dy = 0;

            if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));
        }


        if(moves.isEmpty())
            return null;
        else
            return moves;
    }

    public static ArrayList<Move> repetetiveBotMove(PlayGround playGround, IFigure figure)
    {
        ArrayList<Move> moves = new ArrayList<>();

        int dx, dy;

        if(figure.getY() != 0)
        {
            dx = 0;
            dy = -1;

            if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));

            if(figure.getX() != 0)
            {
                dx = -1;
                dy = -1;

                if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                    moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));
            }

            if(figure.getX() != 8)
            {
                dx = 1;
                dy = -1;

                if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                    moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));
            }
        }

        if(figure.getX() != 0)
        {
            dx = -1;
            dy = 0;

            if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));
        }

        if(figure.getX() != 8)
        {
            dx = 1;
            dy = 0;

            if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));
        }

        if(figure.getY() != 8)
        {
            dx = 0;
            dy = 1;

            if(figure.checkFigure(playGround, figure.getX(), figure.getY(), dx, dy))
                moves.add(new Move(figure.getX(), figure.getY(), figure.getX() + dx, figure.getY() + dy, figure));
        }


        if(moves.isEmpty())
            return null;
        else
            return moves;
    }

}
