package Graphics.GUI.Prefabs;

import Graphics.GUI.GUIManager;
import Graphics.GUI.GUIManagerMenu;
import Networking.MySQL.Manager;
import Utilities.Window.StageHolder;
import Utilities.Window.StageState;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static Graphics.GUI.Prefabs.MenuKeys.*;

public class GameGUI extends GUIManager {

    protected Manager manager;
    protected StageState stageState;

    public GameGUI(Pane pane, Manager manager, StageState stageState) {
        super(pane);
        this.manager = manager;
        this.stageState = stageState;
        this.stageState.addCallback((d)->reDraw());
        this.stageState.getStage().getScene().getStylesheets().add("Graphics/GUI/Prefabs/basegui.css");
        pane.setId("gui-background");
        create();
    }

    private void create()
    {
        Point2D[] positions = calculatePositions(LoadingScreen);
        addMenu(LoadingScreen.toString(), new LoadingScreen(this.pane,positions[0],positions[1]),true);
        positions = calculatePositions(LoginMenu);
        addMenu(LoginMenu.toString(), new LoginMenu(this.pane,this.manager,positions[0],positions[1]),false);
        positions = calculatePositions(MainMenu);
        addMenu(MainMenu.toString(), new MainMenu(this.pane,this.manager,positions[0],positions[1]),false);
    }
    private Point2D[] calculatePositions(MenuKeys menu)
    {
        Point2D window = this.stageState.getSize();
        switch (menu)
        {
            case MainMenu:
                Point2D position = new Point2D(window.getX()/3,window.getY()/3);
                Point2D size = new Point2D(window.getX()/3,window.getY()/3);
                return new Point2D[]{position,size};
            case LoginMenu:
                position = new Point2D(window.getX()/3,window.getY()/4);
                size = new Point2D(window.getX()/3,window.getY()/2);
                return new Point2D[]{position,size};
            case LoadingScreen:
                position = new Point2D(0,window.getY()-(window.getY()/10));
                size = new Point2D(window.getX(),window.getY()/20);
                return new Point2D[]{position,size};
        }
        return null;
    }

    public void reDraw()
    {
        for(GUIManagerMenu menu: this.menus)
        {
            Point2D[] data = calculatePositions(MenuKeys.valueOf(menu.getKey()));
            menu.getMenu().move(data[0],data[1]);
        }
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
