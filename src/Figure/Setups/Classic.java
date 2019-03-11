package Figure.Setups;

import Figure.Figures.Promotable.*;
import Figure.Figures.Unpromotable.*;
import Figure.IFigure;

import Figure.Rotation;
import Game.Player;
import Utilities.Generator.Generator;

import java.awt.*;
import java.util.ArrayList;

public class Classic implements IFigureSetup {
    private static Classic ourInstance = new Classic();

    public static Classic getInstance() {
        return ourInstance;
    }

    ArrayList<IFigure> figures;
    Generator generator;

    private Classic() {
        this.figures = new ArrayList<>();
        this.generator = Generator.getInstance();

        int playerID = 0;


        for(int x = 0; x < 9; x++)
            this.figures.add(new Pawn(this.generator.getNewId(), new Point(x, 6), Rotation.Bottom, false, playerID));


        this.figures.add(new Lance(this.generator.getNewId(),new Point(0,8),Rotation.Bottom,false, playerID));
        this.figures.add(new Lance  (this.generator.getNewId(),new Point(8,8),Rotation.Bottom,false, playerID));
        this.figures.add(new Rook   (this.generator.getNewId(),new Point(7,7),Rotation.Bottom,false, playerID));
        this.figures.add(new Knight (this.generator.getNewId(),new Point(1,8),Rotation.Bottom,false, playerID));
        this.figures.add(new Knight (this.generator.getNewId(),new Point(7,8),Rotation.Bottom,false, playerID));
        this.figures.add(new Silver (this.generator.getNewId(),new Point(6,8),Rotation.Bottom,false, playerID));
        this.figures.add(new Silver (this.generator.getNewId(),new Point(2,8),Rotation.Bottom,false, playerID));
        this.figures.add(new Gold   (this.generator.getNewId(),new Point(3,8),Rotation.Bottom,false, playerID));
        this.figures.add(new Gold   (this.generator.getNewId(),new Point(5,8),Rotation.Bottom,false, playerID));
        this.figures.add(new King   (this.generator.getNewId(),new Point(4,8),Rotation.Bottom,false, playerID));
        this.figures.add(new Bishop (this.generator.getNewId(),new Point(1,7),Rotation.Bottom,false, playerID));
       // this.figures.add(new Pawn (this.generator.getNewId(),new Point(5,5),Rotation.Bottom,false, playerID));
        // this.figures.add(new King   (this.generator.getNewId(),new Point(4,7),Rotation.Bottom,false, playerID));
        //this.figures.add(new Bishop (this.generator.getNewId(),new Point(0,8),Rotation.Bottom,true, playerID));
      // this.figures.add(new King (this.generator.getNewId(),new Point(7,8),Rotation.Bottom,false, playerID));
    }

    @Override
    public ArrayList<IFigure> getCopy(Player player) {
        ArrayList<IFigure> copied = new ArrayList<>();
        this.generator.reset();
        for(IFigure figure: this.figures)
        {
            IFigure copy = figure.getCopy();
            copy.setId(this.generator.getNewId());
            copy.setPlayer(player.getId());
            copied.add(copy);
        }
        return Utilities.Matrix.Rotation.getInstance().rotateFigureList(player.getRotation(),copied, getCenter());
    }

    @Override
    public Point getCenter() {
        return new Point(4,4);
    }

    @Override
    public Point getPlaygroundSize() {
        return new Point(9,9);
    }

    @Override
    public int getFiguresCount() {
        return this.figures.size();
    }
}
