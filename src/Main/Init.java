package Main;

import Game.Globals;
import Graphics.GUI.Prefabs.GameGUI;
import Graphics.GUI.Prefabs.LoadingScreen;
import Graphics.GUI.Prefabs.Menu;
import Graphics.GUI.Prefabs.MenuKeys;
import Graphics.PaintableFactory;
import Graphics.PaintableFactoryHolder;
import Networking.MySQL.Manager;
import Utilities.Debug.ConsoleResults;
import Utilities.Window.StageState;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.Global;

import java.sql.SQLException;

public class Init {
    PaintableFactory paintableFactory;
    GameGUI gameGUI;
    Manager manager;
    Pane pane;
    Stage stage;
    StageState stageState;
    String skin;

    public Init(Pane pane,Stage stage,String skin)
    {
        Platform.setImplicitExit(false);
        this.pane = pane;
        this.stage = stage;
        this.skin = skin;

        if(this.skin == "Classic" || this.skin == "Origins" || this.skin == "StarCraft" || this.skin == "Legendary")
        {
            pane.setStyle("-fx-background-color: #666666");
        }
        stage.show();

        /* Vytvoření grafiky */
        this.paintableFactory = new PaintableFactory(
                Globals.getInstance().isJar()?"Graphics/Skins/":"src/Graphics/Skins/",
                this.skin,
                pane,
                stage,
                new Point2D(212,210),  //Cell
                new Point2D(25,12),    //Cell Space
                new Point2D(192,210),  //Figure
                new Point2D( 40,12),   //Figure Space
                new Point2D(2117,2117),//PlaygroundS
                new Point2D(675,168), //Position
                new Point2D(3500,2468) //Full size
        );
        PaintableFactoryHolder.getInstance().set(this.paintableFactory);
        this.stageState = this.paintableFactory.getStageState();

        this.manager = new Manager("dbsys.cs.vsb.cz","gai0006","ANwmTboCzz", "gai0006");
        Globals.getInstance().addManager(this.manager);

        /* Vytvoření menu */
        this.gameGUI = new GameGUI(this.pane,this.manager,this.stageState);
        Globals.getInstance().setGui(this.gameGUI);
        this.gameGUI.draw();
        //this.gameGUI.show(MenuKeys.MainMenu.toString());

        /* Load Skins */
        this.paintableFactory.loadSkin(progress->{
            try {
                ((LoadingScreen)this.gameGUI.getMenu(MenuKeys.LoadingScreen.toString())).setValue(progress);
            }catch (Exception ex){}
        },paintableFactory->{
            ConsoleResults.getInstance().logln("Loaded");
            this.gameGUI.hide(MenuKeys.LoadingScreen.toString());
            this.gameGUI.show(MenuKeys.LoginMenu.toString());
        });
        /* Networking */
        new Thread(()->{
            try {
                this.manager.reconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
