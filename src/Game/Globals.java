package Game;

import Game.*;
import Graphics.GUI.Prefabs.GameGUI;
import Networking.MySQL.Manager;

import java.util.ArrayList;

public class Globals {
    private static Globals ourInstance = new Globals();

    public static Globals getInstance() {
        return ourInstance;
    }

    ArrayList<Manager> managers;
    GameGUI gui;
    Game game;
    Player actualPlayer;
    Player loggedPlayer;
    int debug;
    boolean jar;

    private Globals() {
        this.debug = 0;
        this.managers = new ArrayList<>();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }

    public void setActualPlayer(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    public Player getLoggedPlayer() {
        return loggedPlayer;
    }

    public void setLoggedPlayer(Player loggedPlayer) {
        this.loggedPlayer = loggedPlayer;
    }

    public boolean getDebug()
    {
        return this.debug > 0;
    }

    public void setDebug(int debug)
    {
        this.debug = debug;
    }

    public GameGUI getGui() {
        return gui;
    }

    public void setGui(GameGUI gui) {
        this.gui = gui;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }
    public void addManager(Manager manager)
    {
        this.managers.add(manager);
    }
    public void clearManagers()
    {
        this.managers.clear();
    }


    public boolean isJar() {
        return jar;
    }

    public void setJar(boolean jar) {
        this.jar = jar;
    }

    public boolean figureDebug()
    {
        return  this.debug == 1 || this.debug == 3;
    }
    public boolean cellDebug()
    {
        return  this.debug == 2 || this.debug == 3;
    }

    public void rotateDebug() {
        this.debug++;
        if(this.debug > 3)
            this.debug=0;
    }
}
