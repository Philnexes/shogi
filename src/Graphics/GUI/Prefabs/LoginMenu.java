package Graphics.GUI.Prefabs;

import Figure.Rotation;
import Game.Globals;
import Game.Player;
import Graphics.GUI.Components.Button;
import Graphics.GUI.Components.Input;
import Graphics.GUI.Components.PasswordField;
import Graphics.GUI.Components.TextField;
import Graphics.GUI.IGUIMenu;
import Networking.MySQL.Manager;
import Utilities.Debug.ConsoleResults;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class LoginMenu extends Menu{
    Manager manager;

    public LoginMenu(Pane pane, Manager manager)
    {
        super(pane);
        this.manager = manager;
        create(new Point2D(0,0),new Point2D(0,0));
    }
    public LoginMenu(Pane pane,Manager manager,Point2D position,Point2D size)
    {
        super(pane);
        this.manager = manager;
        create(position, size);
    }

    @Override
    public IGUIMenu create(Point2D position, Point2D size)
    {
        this.inputs.add(new Input("Nickname","text"));
        this.inputs.add(new Input("Password","pass"));

        this.buttons.add(new Button("Login", data-> login()));
        this.buttons.add(new Button("Register",data-> register()));
        this.buttons.add(new Button("Offline", data-> offline()));
        this.buttons.add(new Button("Exit",data-> exit()));
        for(Button button: this.buttons)
            button.getStyleClass().add("gui-button");

        for(Input input: this.inputs)
            input.getStyleClass().add("gui-input");

        move(position,size);
        return this;
    }

    private void login() {
        if(getOpacity() > 0) {
            if(manager.isConnected())
            {
                try {
                    Player p = manager.login(inputs.get(0).getText(),inputs.get(1).getText());
                    if(p.getId() > 0) {
                        hide();
                        Globals.getInstance().getGui().show(MenuKeys.MainMenu.toString());
                        Globals.getInstance().setLoggedPlayer(p);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void register() {
        if(this.getOpacity() > 0) {
            if(this.manager.isConnected())
            {
                try {
                    Player p = new Player(0,this.inputs.get(0).getText(), Rotation.Bottom);
                    this.manager.newPlayer(p,this.inputs.get(1).getText(),"");
                    if(p.getId() > 0) {
                        this.hide();
                        Globals.getInstance().getGui().show(MenuKeys.MainMenu.toString());
                        Globals.getInstance().setLoggedPlayer(p);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void offline() {
        if(this.getOpacity() > 0) {
            Globals.getInstance().setLoggedPlayer(new Player(0,this.inputs.get(0).getText(),Rotation.Bottom));
            this.hide();
            Globals.getInstance().getGui().show(MenuKeys.MainMenu.toString());
        }
    }

    private void exit() {
        if(this.getOpacity() > 0) {
            System.exit(0);
        }
    }

    @Override
    public void move(Point2D position, Point2D size) {
        this.position = position;
        this.size = size;
        double dx = position.getX(), dy = position.getY();
        double dw = size.getX(), dh = size.getY()/6;
        double space = dh/4;
        double y = dy;
        //Point2D s = new Point2D(dw,dh);
        for(int i = 0;i < this.inputs.size();i++)
        {
            this.inputs.get(i).setPosition(new Point2D(dx,y));
            this.inputs.get(i).setMaxSize(dw,dh);
            this.inputs.get(i).setMinSize(dw,dh);
            this.inputs.get(i).setPrefSize(dw,dh);
            this.inputs.get(i).setStyle("-fx-font-size: "+dh/2+"px;");
            y += dh+space;
        }
        for(int i = 0;i < this.buttons.size();i++)
        {
            /*this.buttons.get(i).setPosition(new Point2D(dx,y));*/
            this.buttons.get(i).resizeRelocate(dx,y,dw,dh);
            this.buttons.get(i).setPrefSize(dw,dh);
            this.buttons.get(i).setStyle("-fx-font-size: "+dh/2+"px;");
            y += dh+space;
        }
    }

}
