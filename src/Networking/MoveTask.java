package Networking;

import Figure.Move;
import Game.Player;
import Game.Game;
import Networking.MySQL.Manager;
import Utilities.Callback.CallbackPackage;
import Utilities.Callback.ICallback;
import javafx.application.Platform;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;

public class MoveTask extends TimerTask {
    Manager manager;
    Game game;
    Player player;
    Move lastMove;
    ArrayList<Move> moves;
    boolean captured;
    int iteration;
    ICallback<CallbackPackage> callback;

    public MoveTask(Manager manager, Game game, Player player, Move lastMove, ICallback<CallbackPackage> callback)
    {
        this.game = game;
        this.manager = manager;
        this.player = player;
        this.lastMove = lastMove;
        this.captured = false;
        this.iteration = 0;
        this.callback = callback;
    }

    public ArrayList<Move> getMoves()
    {
        return this.moves;
    }

    public boolean hasNewMoves()
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
            System.out.println("MTI> "+this.iteration);
            this.moves = this.manager.getMoves(this.game,this.player);
            if(this.moves.size() > 0 && ((this.lastMove != null && this.moves.get(0).toString() != this.lastMove.toString()) || this.lastMove == null))
            {
                this.captured = true;
                CallbackPackage pack = new CallbackPackage();
                pack.addObject(this.player);
                pack.addObject(moves);
                Platform.runLater(() -> callback.call(pack));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.iteration++;
    }
}
