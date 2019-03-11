package Utilities.Matrix;

import javafx.geometry.Point2D;

import java.awt.*;
import java.util.HashMap;
import Graphics.RepositionData;

public class Rescale {
    private static Rescale ourInstance = new Rescale();

    public static Rescale getInstance() {
        return ourInstance;
    }

    private Rescale() {
    }

    public HashMap<Point, RepositionData> Rescale(HashMap<Point,RepositionData> original,double scale)
    {
        HashMap<Point, RepositionData> rescaled = new HashMap<Point, RepositionData>();
        for (Point key: original.keySet())
        {
            Point2D position = original.get(key).getPosition();
            Point2D size = original.get(key).getSize();
            RepositionData rd = new RepositionData(
                    new Point2D(position.getX()*scale,position.getY()*scale),
                    new Point2D(size.getX()*scale,size.getY()*scale)
            );
            rescaled.put(key,rd);
        }
        return rescaled;
    }
}
