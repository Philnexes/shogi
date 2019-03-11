package Main;

import Figure.IFigure;
import Figure.Move;
import Figure.Rotation;
import Figure.Setups.Classic;
import Game.Controller;
import Game.Globals;
import Game.Game;
import Game.PlayGround;
import Game.Player;
import Graphics.PaintableFactory;
import Graphics.PaintableFactoryHolder;
import Networking.MySQL.Manager;
import Utilities.Debug.ConsoleResults;
import Utilities.IO.Mouse.DataTypes.MouseClickEvent;
import Utilities.Timer.QuickTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.xml.bind.annotation.XmlElementDecl;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Globals.getInstance().setJar(false);
        //Globals.getInstance().setDebug(false);
        Pane root = new Pane();
        primaryStage.setTitle("Šhogi");
        primaryStage.setScene(new Scene(root, 709, 500));

        /* Nastavení výchozího skinu */
        Init init = new Init(root,primaryStage,"Classic"); //Normal version
        //Init init = new Init(root,primaryStage,"Origins");    //Origins edition
        //Init init = new Init(root,primaryStage,"StarCraft");  //DLC
        //Init init = new Init(root,primaryStage,"Legendary");  //Pre-order
    }
    @Override
    public void stop(){
        for(Manager manager:Globals.getInstance().getManagers())
            if(manager.isConnected()) {
                Game game = Globals.getInstance().getGame();
                Player player = Globals.getInstance().getLoggedPlayer();
                try {
                    if (game.getPlayGround().getPlayerByID(player.getId()) != null) {
                        manager.addMove(Globals.getInstance().getGame(), Globals.getInstance().getActualPlayer(), new Move(-3, -3, -3, -3, player.getFigure(0)));
                        manager.finishGame(game);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                manager.close();
            }
        Globals.getInstance().clearManagers();

        for (Thread t : Thread.getAllStackTraces().keySet())
        {  if (t.getState()==Thread.State.RUNNABLE)
            t.interrupt();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
