package Networking.MySQL;

import Figure.Move;
import Figure.Rotation;
import Game.PlayGround;
import Game.Player;
import Game.Game;
import Utilities.Debug.ConsoleResults;

import java.sql.*;
import java.util.ArrayList;

public class Manager {
    Connection conn;
    boolean connected;
    private String url,user,pass;

    public Manager(String server, String user, String pass, String database){
        String myDriver = "com.mysql.jdbc.Driver";//"org.gjt.mm.mysql.Driver";
        String myUrl = "jdbc:mysql://"+server+"/"+database;
        try {
            Class.forName(myDriver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.url = myUrl;
        this.user = user;
        this.pass = pass;

    }

    public boolean isConnected() {
        return connected;
    }

    public Game newGame(PlayGround playGround) throws SQLException {
        Game game = new Game(0,playGround);
        Statement st = conn.createStatement();
        st.executeUpdate("INSERT INTO game(Type) VALUES ('Classic');");
        ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID();");
        if(rs.next())
            game.setId(rs.getInt(1));
        st.close();
        for(Player player: game.getPlayGround().getPlayers())
        {
            try {
                addPlayerToGame(player,game,"Player");
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        return game;
    }
    public void newPlayer(Player player,String pass,String salt) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("INSERT INTO player(name,pass,salt) VALUES ('"+player.getName()+"','"+pass+"','"+salt+"');");
        ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID();");
        if(rs.next())
            player.setId(rs.getInt(1));
        st.close();
    }

    public Player login(String name,String pass) throws SQLException {
        Player player = new Player();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM player WHERE Name='"+name+"' AND Pass='"+pass+"';");
        if(rs.next())
        {
            player.setId(rs.getInt("Id_p"));
            player.setName(rs.getString("Name"));
        }
        st.close();
        return player;
    }

    public int addMove(Game game,Player player,Move move) throws SQLException {
        int id = 0;
        Statement st = conn.createStatement();
        st.executeUpdate("INSERT INTO move(Game,Player,FromX,FromY,ToX,ToY,FigureId) VALUES ("+game.getId()+","+player.getId()+","+
                move.getFrom().getX()+","+move.getFrom().getY()+","+
                move.getTo().getX()+","+move.getTo().getY()+","+
                move.getFigure().getId()+");");
        ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID();");
        if(rs.next())
            id = rs.getInt(1);
        st.close();
        return id;
    }
    public void addPlayerToGame(Player player,Game game,String role) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("INSERT INTO results(Game,Player,Role,Finished) VALUES ("+game.getId()+","+player.getId()+",'"+role+"',0);");
        st.close();
    }

    public void removePlayerFromGame(Player player,Game game) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("UPDATE results SET Finished=1 WHERE Game="+game.getId()+" AND Player="+player.getId()+";");
        st.close();
    }

    public void finishGame(Game game) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("UPDATE results SET Finished=1 WHERE Game="+game.getId()+";");
        st.executeUpdate("UPDATE game SET Finished=NOW() WHERE Id_g="+game.getId()+";");
        for(Player player: game.getPlayGround().getPlayers())
        {
            try {
                st.executeUpdate("UPDATE results SET Score=" + player.getValue() + " WHERE Game=" + game.getId() + " AND Player=" + player.getId() + ";");
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        st.close();
    }

    public ArrayList<Move> getMoves(Game game, Player player) throws SQLException {
        System.out.println("Game: "+game.getId()+" - Player: "+player.getId());
        ArrayList<Move> moves = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM move WHERE Game="+game.getId()+" AND Player="+player.getId()+" ORDER BY Id_m DESC;");
        while(rs.next())
        {
            //ConsoleResults.getInstance().logln(rs.getInt("FigureId"));
            //ConsoleResults.getInstance().playerFiguresIdLine(player);
            moves.add(new Move( rs.getInt("FromX"),rs.getInt("FromY"),
                                rs.getInt("ToX"),rs.getInt("ToY"),
                                player.getFigureById(rs.getInt("FigureId"))));
        }
        st.close();
        return  moves;
    }

    public void reconnect() throws SQLException {
            this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
            this.connected = true;
    }

    public Game findGame() throws SQLException {
        Game game = new Game();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM game HAVING (SELECT COUNT(Player) as players FROM results WHERE Game = Id_g) < 2;");
        if(rs.next())
        {
            game.setId(rs.getInt("Id_g"));
        }
        st.close();
        return game;
    }

    public ArrayList<Player> getPlayers(Game g) throws SQLException {
        ArrayList<Player> players = new ArrayList<Player>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM results LEFT JOIN player ON Player = Id_p WHERE Game = "+g.getId()+";");
        while(rs.next())
        {
            Player player = new Player();
            player.setId(rs.getInt("Id_p"));
            player.setName(rs.getString("Name"));
            player.setRotation(Rotation.Top);
            players.add(player);
        }
        st.close();
        return players;
    }
    public void close()
    {
        this.connected = false;
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
