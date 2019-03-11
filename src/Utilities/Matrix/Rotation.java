package Utilities.Matrix;

import Figure.IFigure;
import Utilities.Debug.ConsoleResults;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Rotation {
    private static Rotation ourInstance = new Rotation();

    public static Rotation getInstance() {
        return ourInstance;
    }

    private Rotation() {
    }

    public ArrayList<IFigure> rotateFigureList(Figure.Rotation rotation, ArrayList<IFigure> figures, Point center)
    {
        for(IFigure figure: figures)
        {
/*            int x = figure.getX()-center.x, y = figure.getY()-center.y;

            Point position = new Point((int)Math.floor(x*Math.cos(angle) - y*Math.sin(angle)) + center.x,
                                       (int)Math.floor(x*Math.sin(angle) + y*Math.cos(angle)) + center.y);*/

            Point position = new Point();
            AffineTransform.getRotateInstance(Math.toRadians(getAngle(rotation)), center.x, center.y)
                    .transform(figure.getPosition(),position);
            //ConsoleResults.getInstance().figurePointRotation(figure,position);
            figure.setPosition(position);
            figure.setRotation(rotation);
        }
        return figures;
    }
    public Point rotatePoint(Figure.Rotation rotation,Point point,Point center)
    {
        Point position = new Point();
        AffineTransform.getRotateInstance(Math.toRadians(getAngle(rotation)), center.x, center.y)
                .transform(point,position);
        return position;
    }
    private double getAngle(Figure.Rotation rotation)
    {
        double angle = 0.0;
        switch (rotation)
        {
            case Top:
                angle = 180.0;
                break;
            case Left:
                angle = 90.0;
                break;
            case Right:
                angle = -90.0;
                break;
        }
        return angle;
    }
}
