package Graphics.Types;

import Game.Cell;
import Game.Globals;
import Game.Player;
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

public class PaintableCell implements IPaintableStructure {
    ArrayList<Integer> normalLayers;
    ArrayList<Integer> highlightedLayers;
    ArrayList<Integer> highlightedEnemyLayers;
    Cell cell;
    PaintableObject graphics;

    public PaintableCell(Cell cell, ArrayList<Image> images, ArrayList<Integer> normalLayers, ArrayList<Integer> highlightedLayers, ArrayList<Integer> highlightedEnemyLayers,
                         Point2D cellSize)
    {
        this.cell = cell;
        this.normalLayers = new ArrayList<>();
        this.normalLayers.addAll(normalLayers);
        this.highlightedLayers = new ArrayList<>();
        this.highlightedLayers.addAll(highlightedLayers);
        this.highlightedEnemyLayers = new ArrayList<>();
        this.highlightedEnemyLayers.addAll(highlightedEnemyLayers);
        this.graphics = new PaintableObject();
        for(Image image: images) {
            this.graphics.addLayer(new ImageLayer(cellSize,image));
        }
        this.graphics.addLayer(new TextLayer(
                "[ "+cell.getPosition().x+" , "+cell.getPosition().y+" ]",
                Paint.valueOf("red"),
                "-fx-border-color: transparent; -fx-background-color: radial-gradient(center 50% 50%,radius 50%,rgba(0,0,0,0), rgba(0,0,0,0.5)); -fx-background-radius: 5px;",
                cellSize
        ));
        if(!Globals.getInstance().cellDebug())
            this.graphics.getLayer(this.graphics.getLayersCount()-1).hide();
    }

    public PaintableCell(Cell cell, ArrayList<Image> images, ArrayList<Integer> normalLayers, ArrayList<Integer> highlightedLayers, ArrayList<Integer> highlightedEnemyLayers,
                         Point2D topEdge, Point2D cellSize, Point2D cellSpace,Player player)
    {
        this(cell,images,normalLayers,highlightedLayers,highlightedEnemyLayers,cellSize);
        reDraw(topEdge,cellSize,cellSpace,player, null);
    }

    public void reDraw(Point2D topEdge, Point2D cellSize, Point2D cellSpace, Player player, HashMap<Point, RepositionData> repositionData) {
        Point point = this.cell.getPosition();
        if(repositionData != null && repositionData.get(point) != null)
        {
            this.graphics.setPosition(repositionData.get(point).getPosition());
            this.graphics.setSize(repositionData.get(point).getSize());
        }
        else {
            this.graphics.setPosition(new Point2D(topEdge.getX() + point.x * (cellSize.getX() + cellSpace.getX()),
                                                  topEdge.getY() + point.y * (cellSize.getY() + cellSpace.getY())));
            this.graphics.setSize(cellSize);
        }
        boolean highlighted = this.cell.isHighlighted();
        boolean enemy = this.cell.isHighlighted() && (this.cell.getFigure() != null);
        if(enemy)
        {
            enemy = !player.hasFigure(this.cell.getFigure());
        }
        for(int layer: this.normalLayers) {
            this.graphics.setOpacity((highlighted?0.0:1.0),layer);
        }
        for(int layer: this.highlightedLayers) {
            this.graphics.setOpacity(((highlighted&&!enemy)?1.0:0.0),layer);
        }
        for(int layer: this.highlightedEnemyLayers) {
            this.graphics.setOpacity(((highlighted&&enemy)?1.0:0.0),layer);
        }

        if(Globals.getInstance().cellDebug())
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
        return this.cell;
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
        this.cell = (Cell) object;
    }
}
