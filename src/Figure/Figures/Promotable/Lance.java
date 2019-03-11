package Figure.Figures.Promotable;

import Figure.Figure;
import Figure.IFigure;
import Figure.Move;
import Figure.Rotation;
import Figure.RepetetiveMoves;
import Game.PlayGround;
import Utilities.Debug.ConsoleResults;

import java.awt.*;
import java.util.ArrayList;

public class Lance extends Figure {

    public Lance(int id, Point position, Rotation rotation, boolean promoted, int playerID) {
        super(id, position, rotation, promoted, playerID);
        this.value.set(3,6);
    }

    protected IFigure getSpecificCopy()
    {
        return new Lance(this.id, (Point) this.position.clone(), this.rotation, this.promoted, this.playerID);
    }

    @Override
    public ArrayList<Move> checkTopPlayerMoves(PlayGround playGround)
    {
        ArrayList<Move> moves = new ArrayList<>();
        boolean end = false;

        if(this.isPromoted())
        {
            moves = RepetetiveMoves.repetetiveTopMove(playGround, this);
        }
        else
        {
            int i = 1;
            while(true)
            {
                if(this.getY() + i < 9 && this.checkFigure(playGround, this.getX(), this.getY(), 0, i) && !end)
                {
                    moves.add(new Move(this.getX(), this.getY(), this.getX(), this.getY() + i, this));
                    if(playGround.getCell(this.getX(), this.getY() + i).getFigure() != null)
                        end = true;
                }
                else
                    end = true;

                if(end)
                    break;

                ++i;
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
        boolean end = false;

        if(this.isPromoted())
        {
            moves = RepetetiveMoves.repetetiveBotMove(playGround, this);
        }
        else
        {
            int i = 1;
            while(true)
            {
                if(this.getY() - i >= 0
                        && this.checkFigure(playGround, this.getX(), this.getY(), 0, -i) && !end)
                {
                    moves.add(new Move(this.getX(), this.getY(), this.getX(), this.getY() - i, this));
                    if(playGround.getCell(this.getX(), this.getY() - i).getFigure() != null)
                        end = true;
                }
                else
                    end = true;

                if(end)
                    break;

                ++i;
            }
        }

        if(moves.isEmpty())
            return null;
        else
            return moves;
    }
}
