package Graphics.GUI.Prefabs;

import Figure.Rotation;
import Figure.Setups.Classic;
import Game.Controller;
import Game.PlayGround;
import Game.Player;
import Game.Game;
import Game.Globals;
import Graphics.GUI.Components.Button;
import Graphics.GUI.IGUIMenu;
import Graphics.PaintableFactory;
import Graphics.PaintableFactoryHolder;
import Networking.JoinTask;
import Networking.MySQL.Manager;
import Utilities.Debug.ConsoleResults;
import Utilities.Timer.QuickTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import jdk.nashorn.internal.objects.Global;

import java.sql.SQLException;
import java.util.Timer;

public class MainMenu extends Menu {

    Manager manager;

    public MainMenu(Pane pane,Manager manager)
    {
        super(pane);
        this.manager = manager;
        create(new Point2D(0,0),new Point2D(0,0));
    }
    public MainMenu(Pane pane,Manager manager,Point2D position,Point2D size)
    {
        super(pane);
        this.manager = manager;
        create(position, size);
    }

    @Override
    public IGUIMenu create(Point2D position, Point2D size)
    {
        this.buttons.add(new Button("Two players",data-> twoPlayers()));
        this.buttons.add(new Button("Muliplayer",data-> multiPlayer()));
        //this.buttons.add(new Button("Settings",data-> settings()));
        this.buttons.add(new Button("Logout",data-> logout()));
        this.buttons.add(new Button("Exit",data-> exit()));
        for(Button button: this.buttons)
            button.getStyleClass().add("gui-button");
        move(position,size);
        return this;
    }


    @Override
    public void move(Point2D position, Point2D size) {
        this.position = position;
        this.size = size;
        double dx = position.getX(), dy = position.getY();
        double dw = size.getX(), dh = size.getY()/5;
        double space = dh/3;
        //Point2D s = new Point2D(dw,dh);
        for(int i = 0;i < this.buttons.size();i++)
        {
            this.buttons.get(i).setPosition(new Point2D(dx,dy+(dh+space)*i));
            this.buttons.get(i).setMinSize(dw,dh);
            this.buttons.get(i).setStyle("-fx-font-size: "+dh/2+"px;");
        }/*
        if(this.manager != null && this.manager.isConnected())
        {
            this.buttons.get(1).setDisable(false);
        }
        else
        {
            this.buttons.get(1).setDisable(true);
        }*/
    }

    private void logout() {
        if(this.getOpacity() > 0) {
        /* ToDo Logout player */
            ConsoleResults.getInstance().logln("Logout!");
            this.hide();
            Globals.getInstance().getGui().show(MenuKeys.LoginMenu.toString());
        }
    }

    private void exit() {
        if(this.getOpacity() > 0) {
        /* ToDo Exit App */
            ConsoleResults.getInstance().logln("Exit!");
            if(this.manager.isConnected())
            {
                try {
                    this.manager.finishGame(Globals.getInstance().getGame());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.exit(0);
        }
    }

    private void settings() {
        if(this.getOpacity() > 0) {
        /* ToDo Settings Menu */
            ConsoleResults.getInstance().logln("Settings!");
        }
    }

    private void multiPlayer() {
        if(this.getOpacity() > 0) {
        /* ToDo Networking game */
            ConsoleResults.getInstance().logln("Multi!");
            if(this.manager.isConnected()){
                try {
                    Game g;
                    Player p1 = Globals.getInstance().getLoggedPlayer();
                    PlayGround pg = new PlayGround(9, 9);
                    pg.addPlayer(p1);
                    p1.getFigures().addAll(Classic.getInstance().getCopy(p1));

                    if((g = this.manager.findGame()).getId() > 0) {
                        this.hide();
                        Player p2 = this.manager.getPlayers(g).get(0);
                        this.manager.addPlayerToGame(p1,g,"Player");

                        pg.addPlayer(p2);
                        p2.getFigures().addAll(Classic.getInstance().getCopy(p2));

                        pg.reMap();//Map figures to cells
                        g.setPlayGround(pg);
                        Controller controller = new Controller(this.pane,g, p2,this.manager);

                        PaintableFactoryHolder.getInstance().get().newPaintable(pg, p2);
                        this.hide();
                        PaintableFactoryHolder.getInstance().get().reDraw(Globals.getInstance().getActualPlayer());
                    }
                    else
                    {
                        g = this.manager.newGame(pg);
                        final Game fg = g;
                        Timer join = new Timer();
                        join.scheduleAtFixedRate(
                        new JoinTask(this.manager,g,p1, (Player p2) ->{
                            ConsoleResults.getInstance().logln(p2.getId()+". "+p2.getName()+" "+p2.getRotation().toString());
                            join.purge();
                            pg.addPlayer(p2);
                            p2.getFigures().addAll(Classic.getInstance().getCopy(p2));
                            pg.reMap();
                            Controller controller = new Controller(this.pane, fg, p1,this.manager);
                            PaintableFactoryHolder.getInstance().get().newPaintable(pg, p1);
                            Globals.getInstance().getGui().getMenu(MenuKeys.MainMenu.toString()).hide();
                            PaintableFactoryHolder.getInstance().get().reDraw(Globals.getInstance().getActualPlayer());
                        }),0,250);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {
                    this.manager.reconnect();
                    this.hide();
                    Globals.getInstance().getGui().show(MenuKeys.LoginMenu.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void twoPlayers() {
        if(this.getOpacity() > 0) {
        /* ToDo 2 players game */
            ConsoleResults.getInstance().logln("Double!");

            PlayGround pg = new PlayGround(9, 9);
            Player p1 = new Player(1, Rotation.Top);//Change to rotate
            pg.addPlayer(p1);
            p1.getFigures().addAll(Classic.getInstance().getCopy(p1));

            Player p2 = new Player(2, Rotation.Bottom);//Change to rotate
            pg.addPlayer(p2);
            p2.getFigures().addAll(Classic.getInstance().getCopy(p2));

            pg.reMap();//Map figures to cells
            Controller controller = new Controller(this.pane, new Game(0, pg), p2);
            PaintableFactoryHolder.getInstance().get().newPaintable(pg, null);
            this.hide();
            PaintableFactoryHolder.getInstance().get().reDraw(Globals.getInstance().getActualPlayer());
        }
    }

}
