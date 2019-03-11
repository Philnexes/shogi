package Networking;

import Game.*;
import Networking.MySQL.Manager;
import Utilities.Callback.ICallback;
import Utilities.Debug.ConsoleResults;
import javafx.application.Platform;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;

public class JoinTask extends TimerTask {
    Manager manager;
    Game game;
    Player player;
    boolean captured;
    int iteration;
    ICallback<Player> callback;

    public JoinTask(Manager manager, Game game, Player player, ICallback<Player> callback)
    {
        this.game = game;
        this.manager = manager;
        this.player = player;
        this.captured = false;
        this.iteration = 0;
        this.callback = callback;
    }

    public boolean hasNewPlayer()
    {
        return this.captured;
    }

    public int getIteration()
    {
        return this.iteration;
    }

    @Override
    public void run() {
        if(this.captured)return;
        if(this.manager == null || !this.manager.isConnected())
        {
            Platform.runLater(() -> callback.call(null));
        }
        try {
            /* ToDo resync request from server */
            System.out.println("JTI> "+this.iteration);
            ArrayList<Player> players = this.manager.getPlayers(this.game);
            ConsoleResults.getInstance().logln(players.size());
            for(Player p: players) {
                ConsoleResults.getInstance().logln(this.game.getId() + " - "+p.getId()+". "+p.getName()+" "+p.getRotation());
                if (p.getId() != this.player.getId()) {
                    this.captured = true;
                    this.player = p;
                    Platform.runLater(() -> callback.call(player));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.iteration++;
    }
}
