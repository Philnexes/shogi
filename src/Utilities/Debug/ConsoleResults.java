package Utilities.Debug;

import Figure.IFigure;
import Game.Cell;
import Game.PlayGround;
import Game.Player;
import Graphics.PaintableFactory;
import Graphics.RepositionData;
import Utilities.IO.Mouse.DataTypes.MouseClick;
import Utilities.IO.XML.XMLElement;
import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_zh_CN;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.geometry.Point2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConsoleResults {
    private static ConsoleResults ourInstance = new ConsoleResults();

    public static ConsoleResults getInstance() {
        return ourInstance;
    }

    private ConsoleResults() {
    }

    public void log(Object text)
    {
        System.out.print(text);
    }
    public void logln(Object text)
    {
        System.out.println(text);
    }
    public void logPlaygroundCells(PlayGround playGround)  {
        for(Cell c : playGround.getCells().values())
        {
            System.out.println(c.toString());
        }
    }

    public void paintableCount(PaintableFactory paintableFactory)
    {
        logln("Factory paintables: " + paintableFactory.getPaintables().size());
    }
    public void figurePointRotation(IFigure figure, Point position)
    {
        logln("--------------------------------");
        logln(figure.getId()+ " - " + figure.getClass().getName());
        logln(figure.getPosition());
        logln(position);
    }

    public void mouseClickEvent(MouseClick data) {
        logln("----------- MouseClick ----------");
        log("Position: ");
        logln(data.getPosition());
        log("Button: ");
        logln(data.getButtonString());
        log("State: ");
        logln(data.getStateString());
    }
    public void resizeComputationResults(Point2D fullsite, double ratio, int w, int h, int cw, int ch)
    {
        logln(fullsite.toString()+" r: "+ratio+"\t"+w+"("+cw+"), "+h+"("+ch+")");
    }

    public void repositionData(String prefix,HashMap<Point, RepositionData> data) {
        logln(prefix+": "+data.size());
        for (Point key: data.keySet())
                logln(key.x+", "+key.y +
                    " P["+data.get(key).getPosition().getX()+", "+ data.get(key).getPosition().getY() +
                    "] S["+data.get(key).getSize().getX()+", "+ data.get(key).getSize().getY() +"]");
    }

    public void xmlElementsDump(ArrayList<XMLElement> data, String prefix) {
        for(XMLElement e: data)
        {
            logln(prefix+"+"+e.getName());
            if(e.getParent() != null)
            logln(prefix+"|Parent: "+e.getParent().getName());
            logln(prefix+"|-+Attributes: "+e.getAttributes().getLength());
            for(int i = 0;i < e.getAttributes().getLength();i++)
            {
                logln(prefix+"| |"+e.getAttributes().getQName(i)+ " = "+e.getAttributes().getValue(i));
            }
            logln(prefix+"|-+Childs: "+e.getChildrens().size());
            xmlElementsDump(e.getChildrens(),prefix+"| |");
        }
    }

    public void playerFiguresIdLine(Player player) {
        log(player.getFigures().size()+": ");
        for(IFigure figure: player.getFigures())
            log(figure.getId()+", ");
        log("\n");
    }

    public void playerIdsLine(ArrayList<Player> players) {
        log(players.size()+": ");
        for(Player player: players)
            log(player.getId()+", ");
        log("\n");
    }
}
