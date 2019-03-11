package Networking;

import Figure.Move;
import Game.Player;
import Game.Game;
import Networking.MySQL.Manager;
import Utilities.Callback.Callback;
import Utilities.Callback.CallbackPackage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class MoveManager {
    HashMap<Integer, Move> lastMoves;
    Manager manager;
    Game game;
    Timer moveRetrive;

    public MoveManager(Manager manager, Game game)
    {
        this.lastMoves = new HashMap<>();
        this.manager = manager;
        this.game = game;
        this.moveRetrive = new Timer();
    }

    public Game getGame()
    {
        return this.game;
    }

    public void updatePlayer(Player player)
    {
        /*MoveTask update = new MoveTask(this.manager, this.game, player, this.lastMoves.get(player), new Callback<CallbackPackage>() {
            @Override
            public void call(CallbackPackage data) {
                UpdatePlayerMoves((Player)data.GetObject(0),(ArrayList<Move>)data.GetObject(1) );
            }
        });*/
        MoveTask update = new MoveTask(this.manager, this.game, player, this.lastMoves.get(player),
                data -> updatePlayerMoves((Player)data.getObject(0),(ArrayList<Move>)data.getObject(1) ));
        this.moveRetrive.scheduleAtFixedRate(update,1000,1000);
    }

    private void updatePlayerMoves(Player player, ArrayList<Move> moves)
    {
        try {
            int m = 0;
            if(moves.size() != 0) {
                for (Move move : moves) {
                    if (this.lastMoves.get(player.getId()).toString() == move.toString())
                        break;
                    m++;
                }
                for(int i = m-1;i >= 0;i--)
                {
                    player.getFigureById(moves.get(i).getFigure().getId()).setPosition(new Point(
                            moves.get(i).getTo().x,
                            moves.get(i).getTo().y
                    ));
                    this.lastMoves.put(player.getId(),moves.get(i));
                }
            }
        }
        catch (Exception ex){ System.out.println(ex.getMessage()); }
        this.moveRetrive.cancel();
    }

    public void setGame(Game game)
    {
        this.game = game;
    }
}
