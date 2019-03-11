package Game;

import Figure.Figures.Promotable.Knight;
import Figure.Figures.Promotable.Lance;
import Figure.Figures.Promotable.Pawn;
import Figure.Figures.Unpromotable.King;
import Figure.IFigure;
import Figure.Move;
import Figure.Setups.Classic;
import Graphics.GUI.Prefabs.MenuKeys;
import Graphics.PaintableFactoryHolder;
import Networking.MoveTask;
import Networking.MySQL.Manager;
import Utilities.Debug.ConsoleResults;
import Utilities.Figures.Calculations;
import Utilities.IO.Mouse.DataTypes.MouseClick;
import Utilities.IO.Mouse.DataTypes.MouseClickEvent;
import Utilities.Matrix.Rotation;
import Utilities.Timer.QuickTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;


public class Controller {//X: -1 off board, -2 promote, -3 endgame
    HashMap<Integer,Move> lastMoves;
    MouseClickEvent mouseClickEvent;
    Manager manager;
    Point lastClick;
    boolean disabled;

    public Controller(Pane pane,Game game,Player player) {
        this.lastMoves = new HashMap<Integer, Move>();
        this.mouseClickEvent = new MouseClickEvent(pane,(data)->click(data));
        Globals.getInstance().setGame(game);
        this.manager = null;
        this.disabled = false;
        changePlayer(game,player);
    }

    public Controller(Pane pane,Game game,Player player,Manager manager) {
        this.lastMoves = new HashMap<Integer, Move>();
        this.mouseClickEvent = new MouseClickEvent(pane,(data)->click(data));
        Globals.getInstance().setGame(game);
        this.manager = manager;
        this.disabled = false;
        changePlayer(game,player);
    }

    private void click(MouseClick data) {
        //ConsoleResults.getInstance().mouseClickEvent(data);
        if (data.getStateString() != "MOUSE_CLICKED") return;

        Player player = Globals.getInstance().getActualPlayer();
        if (data.getButtonString() == "MIDDLE") {
            Globals.getInstance().rotateDebug();
            PaintableFactoryHolder.getInstance().get().reDraw(player);
            return;
        }
        if (!this.disabled) {
            Game game = Globals.getInstance().getGame();
            Point2D position = data.getPosition();
            Point click = Calculations.getInstance().getPointFromClick(position, PaintableFactoryHolder.getInstance().get());
            //ConsoleResults.getInstance().logln(click);

            if (click.x >= 0 && click.y >= 0) {
                Cell cell = game.getPlayGround().getCell(click);
                if (cell != null) {
                    if (cell.isHighlighted()) {
                        if (cell.getFigure() != null) {
                            if (cell.getFigure().getPlayer() != player.getId()) {
                                attack(game.playGround.getCell(this.lastClick).getFigure(), cell.getFigure(), game, player);
                                if (data.getButtonString() == "SECONDARY")
                                    promote(cell, game, player);
                            }
                        } else {
                            move(game.playGround.getCell(this.lastClick).getFigure(), cell, game, player);
                            if (data.getButtonString() == "SECONDARY")
                                promote(cell, game, player);
                        }
                    } else if (cell.getFigure() != null) {
                        if (cell.getFigure().getPlayer() == player.getId()) {
                            select(cell, game, player);
                        }
                    }
                }
            }
        }
    }

    private void promote(Cell cell, Game game, Player player) {
        ConsoleResults.getInstance().logln(cell.getPosition());
        if(cell.getFigure().getPlayer() != player.getId())return;
        switch(player.getRotation())
        {
            case Top:
                if(cell.getPosition().getY() < 6)return;
                break;
            case Bottom:
                if(cell.getPosition().getY() > 2)return;
                break;
            case Right:
                if(cell.getPosition().getX() > 3)return;
                break;
            case Left:
                if(cell.getPosition().getX() < 6)return;
                break;
        }
        cell.getFigure().promote();
        PaintableFactoryHolder.getInstance().get().reDraw(player);
        if(this.manager != null && this.manager.isConnected())
            try {
                this.manager.addMove(game,player,new Move(-2,-2,-2,-2,cell.getFigure()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private void select(Cell cell, Game game, Player player) {
        //ConsoleResults.getInstance().logln(cell.getFigure().getMoves(playGround).size());
        deselect(game.playGround);
        Cell c;
        for(Move move: cell.getFigure().getMoves(game.playGround))
        {
            //ConsoleResults.getInstance().logln(move.toString());
            c = game.playGround.getCell(move.getTo());
            if(c != null)
                c.setHighlight(true);
        }
        this.lastClick = cell.getPosition();
        PaintableFactoryHolder.getInstance().get().reDraw(player);
    }

    private void move(IFigure figure, Cell cell, Game game,Player player) {
        Move move = new Move(figure.getPosition(),cell.getPosition(),figure);
        game.playGround.getCell(figure.getX(),figure.getY()).setFigure(null);
        cell.setFigure(figure);
        figure.setPosition((Point) cell.getPosition().clone());
        checkPromote(move,cell,game,player);
        deselect(game.playGround);
        switchPlayer(game);
        if(this.manager != null && this.manager.isConnected())
            try {
                this.manager.addMove(game,player,move);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private void checkPromote(Move move,Cell cell,Game game, Player player) {
        ConsoleResults.getInstance().logln(move.getTo());
        if(move.getFigure() instanceof Pawn || move.getFigure() instanceof Lance)
        {
            switch (player.rotation)
            {
                case Top:
                    if(move.getTo().getY() < 8)return;
                    break;
                case Bottom:
                    if(move.getTo().getY() > 0)return;
                    break;
                case Right:
                    if(move.getTo().getX() > 0)return;
                    break;
                case Left:
                    if(move.getTo().getX() < 8)return;
                    break;
            }
            promote(cell,game,player);
        }
        else if(move.getFigure() instanceof Knight)
        {
            switch (player.rotation)
            {
                case Top:
                    if(move.getTo().getY() < 7)return;
                    break;
                case Bottom:
                    if(move.getTo().getY() > 1)return;
                    break;
                case Right:
                    if(move.getTo().getX() > 1)return;
                    break;
                case Left:
                    if(move.getTo().getX() < 7)return;
                    break;
            }
            promote(cell,game,player);
        }
    }

    private void attack(IFigure lastClick, IFigure figure, Game game,Player player) {
        Move move = new Move(lastClick.getPosition(),figure.getPosition(),lastClick);
        game.playGround.getCell(lastClick.getPosition()).setFigure(null);
        game.playGround.getCell(figure.getPosition()).setFigure(lastClick);
        lastClick.setPosition((Point) figure.getPosition().clone());
        figure.setPosition(new Point(-1,-1));
        checkPromote(move,game.playGround.getCell(lastClick.getPosition()),game,player);
        deselect(game.playGround);
        if(this.manager != null && this.manager.isConnected())
            try {
                this.manager.addMove(game,player,move);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if(figure instanceof King)
            endgame(move,game,player);
        switchPlayer(game);

    }

    private void endgame(Move move, Game game, Player player) {
        ConsoleResults.getInstance().logln("GameOver - Winner: "+player.getName());
        if(this.manager != null && this.manager.isConnected()){
            try {
                this.manager.addMove(game,player,new Move(-3,-3,-3,-3,move.getFigure()));
                this.manager.finishGame(game);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Globals.getInstance().getGui().show(MenuKeys.MainMenu.toString());
        PaintableFactoryHolder.getInstance().get().clearPaintables();
        PaintableFactoryHolder.getInstance().get().getPane().setId("gui-background");
    }

    private void switchPlayer(Game game)
    {
        ConsoleResults.getInstance().playerIdsLine(game.getPlayGround().getPlayers());
        for(Player playerNext: game.getPlayGround().getPlayers())
        {
            if(playerNext.getId() != Globals.getInstance().getActualPlayer().getId()) {
                changePlayer(game,playerNext);
                break;
            }
        }
    }

    private void deselect(PlayGround playGround)
    {
        for(Cell cell: playGround.getCells().values())
            cell.setHighlight(false);
    }

    /* ToDo control game flow */
    public void changePlayer(Game game,Player player)
    {
        if(Globals.getInstance().getActualPlayer() != null && player != null)
            ConsoleResults.getInstance().logln(Globals.getInstance().getActualPlayer().getId()+" -> "+player.getId() + "("+Globals.getInstance().getLoggedPlayer().getId()+")");
        Globals.getInstance().setActualPlayer(player);
        PaintableFactoryHolder.getInstance().get().reDraw(player);
        if(this.manager != null && this.manager.isConnected())
        {
            this.disabled = (player.getId() != Globals.getInstance().getLoggedPlayer().getId());
            ConsoleResults.getInstance().logln("Disabled: "+this.disabled);
            if(this.disabled)
            {
                Timer moveSync =  new Timer();
                moveSync.schedule(
                new MoveTask(this.manager,game,player,this.lastMoves.get(player),(data)->{
                    int m = 0;
                    ArrayList<Move> moves = (ArrayList<Move>)data.getObject(1);
                    if(moves.size() != 0) {
                        for (Move move : moves) {
                            if (this.lastMoves.get(player.getId()) != null && this.lastMoves.get(player.getId()).toString() == move.toString())
                                break;
                            m++;
                        }
                        for(int i = m-1;i >= 0;i--)
                        {
                            Point to = moves.get(i).getTo();
                            ConsoleResults.getInstance().logln(moves.get(i).getFigure().getId()+" - "+to.toString());
                            switch (to.x)
                            {
                                case -3:
                                    endgame(moves.get(i),game,player);
                                    break;
                                case -2:
                                    player.getFigureById(moves.get(i).getFigure().getId()).promote();
                                    break;
                                default:
                                    to = Rotation.getInstance().rotatePoint(player.rotation,moves.get(i).getTo(), Classic.getInstance().getCenter());
                                    Cell cell = game.playGround.getCell(to.x,to.y);
                                    IFigure figure = cell.getFigure();
                                    if(figure != null)
                                    {
                                        if(figure.getPlayer() != player.getId()) {
                                            figure.setPosition(new Point(-1, -1));
                                        }
                                    }
                                    player.getFigureById(moves.get(i).getFigure().getId()).setPosition(new Point(to.x,to.y));
                                    cell.figure = player.getFigureById(moves.get(i).getFigure().getId());
                                    break;
                            }
                            if(this.lastMoves.containsKey(player.getId()))
                                this.lastMoves.remove(player.getId());
                            this.lastMoves.put(player.getId(),moves.get(i));
                        }
                        Platform.runLater(()->
                        {
                            ConsoleResults.getInstance().logln("Net change by "+player.getId());
                            moveSync.purge();
                            switchPlayer(game);
                        });
                    }
                }),250,250);
            }
        }
    }
}
