package Utilities.Figures;


import Graphics.PaintableFactory;
import javafx.geometry.Point2D;

import java.awt.*;
import java.util.HashMap;

public class Calculations {
    private static Calculations ourInstance = new Calculations();

    public static Calculations getInstance() {
        return ourInstance;
    }

    private Calculations() {
    }

    /* ToDo Joe: Add figures functions here!!! */
    /* Can Ignore implementation */
    public void checkMove(/* Some inputs */) {
        /* Some code */
        /* return some output*/
    }

    public Point getPointFromClick(Point2D click, PaintableFactory factory) {
        HashMap<String,Point2D> points = factory.getCalculatedPoints();
        double nx = click.getX()-points.get("playGroundPosition").getX();
        nx /= points.get("cellSize").getX()+points.get("cellSpace").getX();
        double ny = click.getY()-points.get("playGroundPosition").getY();
        ny /= points.get("cellSize").getY()+points.get("cellSpace").getY();
        return new Point((int)nx,(int)ny);
    }
}
