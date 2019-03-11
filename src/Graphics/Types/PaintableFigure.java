package Graphics.Types;

import Figure.IFigure;
import Game.Globals;
import Graphics.IPaintableStructure;
import Graphics.ImageLayer;
import Graphics.RepositionData;
import Graphics.TextLayer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class PaintableFigure implements IPaintableStructure{
    ArrayList<Integer> normalLayers;
    ArrayList<Integer> promotedLayers;
    IFigure figure;
    PaintableObject graphics;

    public PaintableFigure(IFigure figure, ArrayList<Image> images,ArrayList<Integer> normalLayers,ArrayList<Integer> promotedLayers,
                           Point2D figureSize) {
        this.figure = figure;
        this.normalLayers = new ArrayList<>();
        this.normalLayers.addAll(normalLayers);
        this.promotedLayers = new ArrayList<>();
        this.promotedLayers.addAll(promotedLayers);
        this.graphics = new PaintableObject();
        for (Image image : images) {
            this.graphics.addLayer(new ImageLayer(figureSize, image));
        }

        this.graphics.addLayer(new TextLayer(
            ((figure.getId()%2==0)?"\n":"")+figure.getId() + ((figure.getId()%2==0)?"":"\n")+ "\n" + figure.getClass().getSimpleName(),
            Paint.valueOf("red"),
            "-fx-border-color: transparent; -fx-background-color: radial-gradient(center 50% 50%,radius 50%,rgba(0,0,0,0), rgba(0,0,0,0.5)); -fx-background-radius: 5px;",
            figureSize
        ));
        if(!Globals.getInstance().figureDebug())
            this.graphics.getLayer(this.graphics.getLayersCount()-1).hide();
    }

    public PaintableFigure(IFigure figure, ArrayList<Image> images,ArrayList<Integer> normalLayers,ArrayList<Integer> promotedLayers,
                           Point2D topEdge, Point2D figureSize, Point2D figureSpace)
    {
        this(figure,images,normalLayers,promotedLayers,figureSize);
        reDraw(topEdge,figureSize, figureSpace, null);
    }

    public void reDraw(Point2D topEdge, Point2D figureSize, Point2D figureSpace, HashMap<Point, RepositionData> repositionData) {
        switch (this.figure.getRotation())
        {
            case Top:
                this.graphics.setRotation(180.0);
                break;
            case Bottom:
                this.graphics.setRotation(0.0);
                break;
            case Left:
                this.graphics.setRotation(90.0);
                break;
            case Right:
                this.graphics.setRotation(-90.0);
                break;
        }
        Point point = this.figure.getPosition();
        if(repositionData != null && repositionData.get(point) != null) {
            this.graphics.setPosition(repositionData.get(point).getPosition());
            this.graphics.setSize(repositionData.get(point).getSize());
        }
        else {
            this.graphics.setPosition(new Point2D(topEdge.getX() + figureSpace.getX() + point.x * (figureSize.getX() + figureSpace.getX()),
                    topEdge.getY() + point.y * (figureSize.getY() + figureSpace.getY())));
            this.graphics.setSize(figureSize);
        }
        for(int layer: this.normalLayers) {
            this.graphics.setOpacity((this.figure.isPromoted()?0.0:1.0),layer);
        }
        for(int layer: this.promotedLayers) {
            this.graphics.setOpacity((this.figure.isPromoted()?1.0:0.0),layer);
        }

        if(Globals.getInstance().figureDebug())
            this.graphics.getLayer(this.graphics.getLayersCount()-1).show();
        else
            this.graphics.getLayer(this.graphics.getLayersCount()-1).hide();
    }

    @Override
    public void draw(Pane pane) {
        this.graphics.draw(pane);
    }

    @Override
    public void erase(Pane pane) {
        this.graphics.erase(pane);
    }

    @Override
    public void show() {
        this.graphics.show();
    }

    @Override
    public void hide() {
        this.graphics.hide();
    }

    @Override
    public Object getObject() {
        return this.figure;
    }

    @Override
    public PaintableObject getGraphics() {
        return this.graphics;
    }

    @Override
    public void setGraphics(PaintableObject graphics) {
        this.graphics = graphics;
    }

    @Override
    public void setObject(Object object) {
        this.figure = (IFigure)object;
    }
}
