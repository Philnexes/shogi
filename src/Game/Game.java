package Game;

import Figure.Move;

public class Game {
    int id;
    PlayGround playGround;

    public Game()
    {
    }
    public Game(int id)
    {
        this.id = id;
    }
    public Game(int id,PlayGround playGround)
    {
        this.id = id;
        this.playGround = playGround;
    }

    public void executeMove(Move move)
    {
        this.playGround.executeMove(move);
    }

    public void setPlayGround(PlayGround playGround)
    {
        this.playGround = playGround;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public PlayGround getPlayGround() {
        return this.playGround;
    }
    public int getId()
    {
        return this.id;
    }
}
