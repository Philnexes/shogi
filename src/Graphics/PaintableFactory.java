package Graphics;

import Figure.Figures.Promotable.*;
import Figure.Figures.Unpromotable.Gold;
import Figure.Figures.Unpromotable.King;
import Figure.IFigure;
import Game.Cell;
import Game.Globals;
import Game.PlayGround;
import Game.Player;
import Graphics.Types.*;
import Utilities.Callback.CallbackPackage;
import Utilities.Callback.ICallback;
import Utilities.Debug.ConsoleResults;
import Utilities.IO.XML.XMLElement;
import Utilities.IO.XML.XMLFileReader;
import Utilities.Matrix.Rescale;
import Utilities.Timer.QuickTimer;
import Utilities.Window.StageState;
import com.sun.nio.zipfs.ZipPath;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import sun.awt.GlobalCursorManager;

public class PaintableFactory {
    //region Variables
    ArrayList<IPaintableStructure> paintables;
    HashMap<String,Image> images;
    HashMap<Point,RepositionData> skinFiguresDefinitions;
    HashMap<Point,RepositionData> skinCellsDefinitions;
    Point2D fullSize;
    Point2D cellSize;
    Point2D cellSpace;
    Point2D figureSize;
    Point2D figureSpace;
    Point2D playGroundSize;
    Point2D playGroundPosition;
    String skin, skinDirectory;
    Pane pane;
    StageState stageState;
    int progress;
    boolean loaded,xmlLoaded,skinLoaded;
    //endregion

    public PaintableFactory(String skinDirectory, String skin, Pane pane, Stage stage,
                            Point2D cellSize, Point2D cellSpace,
                            Point2D figureSize, Point2D figureSpace,
                            Point2D playGroundSize, Point2D playGroundPosition,
                            Point2D fullSize)
    {
        this.fullSize = fullSize;
        this.cellSize = cellSize;
        this.cellSpace = cellSpace;
        this.figureSize = figureSize;
        this.figureSpace = figureSpace;
        this.playGroundSize= playGroundSize;
        this.playGroundPosition = playGroundPosition;
        this.images = new HashMap<>();
        this.skinCellsDefinitions = new HashMap<>();
        this.skinFiguresDefinitions = new HashMap<>();
        this.paintables = new ArrayList<>();
        this.skinDirectory = skinDirectory;
        this.skin = skin;
        this.pane = pane;
        this.progress = 0;
        this.loaded = false;
        /* Locked Ratio 875:617 */
        this.stageState = new StageState(stage,this.fullSize,(d)->reDraw(Globals.getInstance().getActualPlayer()));
        this.stageState.check();
    }

    public PaintableFactory(String skinDirectory, String skin, Pane pane, Stage stage,
                            Point2D cellSize, Point2D cellSpace,
                            Point2D figureSize, Point2D figureSpace,
                            Point2D playGroundSize, Point2D playGroundPosition,
                            Point2D fullSize, ICallback<PaintableFactory> loaded, ICallback<Integer> progress)
    {
        this.fullSize = fullSize;
        this.cellSize = cellSize;
        this.cellSpace = cellSpace;
        this.figureSize = figureSize;
        this.figureSpace = figureSpace;
        this.playGroundSize= playGroundSize;
        this.playGroundPosition = playGroundPosition;
        this.images = new HashMap<>();
        this.skinCellsDefinitions = new HashMap<>();
        this.skinFiguresDefinitions = new HashMap<>();
        this.paintables = new ArrayList<>();
        this.skinDirectory = skinDirectory;
        this.skin = skin;
        this.pane = pane;
        this.progress = 0;
        this.xmlLoaded = this.skinLoaded = this.loaded = false;
        /* Locked Ratio 875:617 */
        this.stageState = new StageState(stage,this.fullSize,(d)->reDraw(Globals.getInstance().getActualPlayer()));
        this.stageState.check();
        loadSkin(progress,loaded);
    }

    /* create PaintableStructure from defined classes */
    public void newPaintable(Object object,Object data)
    {
        ArrayList<Integer> normalLayers = new ArrayList<>();
        ArrayList<Integer> highlightedLayers = new ArrayList<>();
        ArrayList<Image> images = new ArrayList<>();
        IPaintableStructure instance = null;

        if(object instanceof Cell)
        {
            ArrayList<Integer> enemyLayers = new ArrayList<>();
            images.add(this.images.get(this.skin+"/Playground/Cell/normal"));
            images.add(this.images.get(this.skin+"/Playground/Cell/highlighted"));
            images.add(this.images.get(this.skin+"/Playground/Cell/enemy"));
            normalLayers.add(0);
            highlightedLayers.add(1);
            enemyLayers.add(2);
            if(data == null || !(data instanceof Player)) data = new Player();
            instance = new PaintableCell((Cell)object,images,normalLayers,highlightedLayers,enemyLayers,
                                         playGroundPosition,this.cellSize,this.cellSpace,(Player)data);
            instance.draw(this.pane);
            this.paintables.add(instance);
            return;
        }
        else if(object instanceof IFigure)
        {
            int normal = 0,highlighted = 1;
            if(this.images.containsKey(this.skin+"/Figures/base"))
            {
                images.add(this.images.get(this.skin+"/Figures/base"));
                normal++;
                highlighted++;
            }
            if(object instanceof Pawn)
            {
                images.add(this.images.get(this.skin+"/Figures/pawn"));
                images.add(this.images.get(this.skin+"/Figures/pawn_promoted"));
                normalLayers.add(normal);
                highlightedLayers.add(highlighted);
            }
            else if(object instanceof Bishop)
            {
                images.add(this.images.get(this.skin+"/Figures/bishop"));
                images.add(this.images.get(this.skin+"/Figures/bishop_promoted"));
                normalLayers.add(normal);
                highlightedLayers.add(highlighted);
            }
            else if(object instanceof Gold)
            {
                images.add(this.images.get(this.skin+"/Figures/gold_general"));
                normalLayers.add(normal);
            }
            else if(object instanceof King)
            {
                images.add(this.images.get(this.skin+"/Figures/king"));
                normalLayers.add(normal);
            }
            else if(object instanceof Knight)
            {
                images.add(this.images.get(this.skin+"/Figures/knight"));
                images.add(this.images.get(this.skin+"/Figures/knight_promoted"));
                normalLayers.add(normal);
                highlightedLayers.add(highlighted);
            }
            else if(object instanceof Lance)
            {
                images.add(this.images.get(this.skin+"/Figures/lance"));
                images.add(this.images.get(this.skin+"/Figures/lance_promoted"));
                normalLayers.add(normal);
                highlightedLayers.add(highlighted);
            }
            else if(object instanceof Rook)
            {
                images.add(this.images.get(this.skin+"/Figures/rook"));
                images.add(this.images.get(this.skin+"/Figures/rook_promoted"));
                normalLayers.add(normal);
                highlightedLayers.add(highlighted);
            }
            else if(object instanceof Silver)
            {
                images.add(this.images.get(this.skin+"/Figures/silver_general"));
                images.add(this.images.get(this.skin+"/Figures/silver_general_promoted"));
                normalLayers.add(normal);
                highlightedLayers.add(highlighted);
            }
            instance = new PaintableFigure((IFigure) object,images,normalLayers,highlightedLayers,
                                            playGroundPosition,this.figureSize,this.figureSpace);
            instance.draw(this.pane);
            this.paintables.add(instance);
            return;
        }
        else if(object instanceof Player)
        {
            for(IFigure figure: ((Player)object).getFigures())
                newPaintable(figure,null);
        }
        else if(object instanceof PlayGround)
        {
            /*images.add(this.images.get(this.skin+"/Playground/table"));
            instance = new PaintableBackground(images,this.stageState.getStage(), new Point2D(0,0),this.stageState.getSize());
            instance.draw(this.pane);
            this.paintables.add(instance);*/
            this.pane.setId("game-background");

            for(Cell cell: ((PlayGround)object).getCells().values())
                if(data != null && data instanceof Player)
                    newPaintable(cell, data);
                else
                    newPaintable(cell, new Player());
            for(Player player: ((PlayGround)object).getPlayers())
                newPaintable(player,null);
        }
    }


    /* calls ReDraw functions on PaintableStructures to update scene */
    public void reDraw(Player player)
    {
        Point2D data = this.stageState.getSize();
        double scaleRatio = data.getX()/this.fullSize.getX();
        Point2D calcPlayGroundPosition = new Point2D(this.playGroundPosition.getX()*scaleRatio,this.playGroundPosition.getY()*scaleRatio);
        Point2D calcFigureSize = new Point2D(this.figureSize.getX()*scaleRatio,this.figureSize.getY()*scaleRatio);
        Point2D calcFigureSpace = new Point2D(this.figureSpace.getX()*scaleRatio,this.figureSpace.getY()*scaleRatio);
        Point2D calcCellSpace = new Point2D(this.cellSpace.getX()*scaleRatio,this.cellSpace.getY()*scaleRatio);
        Point2D calcCellSize = new Point2D(this.cellSize.getX()*scaleRatio,this.cellSize.getY()*scaleRatio);
        HashMap<Point,RepositionData> figuresDefinitions = Rescale.getInstance().Rescale(this.skinFiguresDefinitions,scaleRatio);
        HashMap<Point,RepositionData> cellsDefinitions = Rescale.getInstance().Rescale(this.skinFiguresDefinitions,scaleRatio);

        /*ConsoleResults.getInstance().logln(scaleRatio);
        ConsoleResults.getInstance().logln(calcPlayGroundPosition);
        ConsoleResults.getInstance().logln(calcFigureSize);
        ConsoleResults.getInstance().logln(calcFigureSpace);
        ConsoleResults.getInstance().logln(calcCellSize);
        ConsoleResults.getInstance().logln(calcCellSpace);*/

        for(IPaintableStructure paintable: this.paintables) {
            if (paintable instanceof PaintableFigure)
                ((PaintableFigure)paintable).reDraw(calcPlayGroundPosition,calcFigureSize,calcFigureSpace,figuresDefinitions);
            else if(paintable instanceof PaintableCell)
                ((PaintableCell)paintable).reDraw(calcPlayGroundPosition,calcCellSize,calcCellSpace,player,cellsDefinitions);
            else if(paintable instanceof PaintableBackground)
                ((PaintableBackground) paintable).reDraw(new Point2D(0,0),this.stageState.getSize());

        }
    }
    public void loadSkin(ICallback<Integer> progress,ICallback<PaintableFactory> hasLoaded)
    {
        new Thread(() -> {
            loadSkin(this.skinDirectory +this.skin+"/", progress, data -> {
                if(this.xmlLoaded && this.skinLoaded && this.loaded == false) {
                    this.loaded = true;
                    Platform.runLater(()->hasLoaded.call(this));
                }
            });
        }).start();
        //new QuickTimer(0,d -> ).Start();
    }
    private void loadSkin(String directory,ICallback<Integer> progress, ICallback<Void> callback) {
        if (directory.endsWith("GUI/")) return;
        if(!Globals.getInstance().isJar())
        {
            //DEV
            for(File file: new File(directory).listFiles())
            {
                if (file.isFile()) {
                    String key = directory.substring(this.skinDirectory.length(), directory.length()) + file.getName().substring(0, file.getName().length() - 4);
                    System.out.println(key);
                    if(file.getName().endsWith(".png") && !file.getName().endsWith("table.png")) {
                        this.images.put(key, new Image("file:" + directory + file.getName()));
                        this.progress++;
                    }
                    else if(file.getName().endsWith(".css")) {
                        Platform.runLater(()->this.pane.getStylesheets().add(directory.replaceFirst("^src/", "") + file.getName()));
                        this.progress++;
                    }
                    else if(file.getName().endsWith("skin.xml") && callback != null)
                    {
                        try {
                            new XMLFileReader(directory + file.getName(), (data) -> {
                                //ConsoleResults.getInstance().xmlElementsDump(data,"");
                                for(XMLElement element: data)
                                    handleElement(element);
                                ConsoleResults.getInstance().repositionData("Cells",this.skinCellsDefinitions);
                                ConsoleResults.getInstance().repositionData("Figures",this.skinFiguresDefinitions);
                                this.xmlLoaded = true;
                                callback.call(null);
                            }).readFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        }
                    }
                    if(progress != null)
                        progress.call(this.progress);
                }
                else if(file.isDirectory())
                {
                    loadSkin(directory+file.getName()+"/",progress,null);
                }
            }
            if(callback != null) {
                this.skinLoaded = true;
                callback.call(null);
            }
        }
        else
        {
            //JAR
            CodeSource src = PaintableFactory.class.getProtectionDomain().getCodeSource();
            ZipInputStream zip = null;
            try {
                zip = new ZipInputStream(src.getLocation().openStream());
                ZipEntry ze = null;
                while ((ze = zip.getNextEntry()) != null) {
                    {
                        String file = ze.getName();
                        if(file.contains("GUI/"))continue;
                        if(file.startsWith(directory) && file.length() > directory.length()) {
                            System.out.println(file+"|");
                            if(file.endsWith("/")) {
                                //System.out.println("Dir: "+file);
                                loadSkin(file, progress, null);
                            }
                            else {
                                //System.out.println(directory.length()+"\t"+file.length());
                                file = file.substring(directory.length(),file.length());
                                //System.out.println("File: "+file);
                                String key = directory.substring(this.skinDirectory.length(), directory.length()) + file.substring(0, file.length() - 4);
                                System.out.println(key);
                                if (file.endsWith(".png") && !file.endsWith("table.png")) {
                                    try {
                                        this.images.put(key, new Image(getClass().getResource("/" + directory + file).openStream()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    this.progress++;
                                } else if (file.endsWith(".css")) {
                                    this.pane.getStylesheets().add(directory.replaceFirst("^src/", "") + file);
                                    this.progress++;
                                } else if (file.endsWith("skin.xml") && callback != null) {
                                    try {
                                        new XMLFileReader("/"+directory + file, (data) -> {
                                            //ConsoleResults.getInstance().xmlElementsDump(data,"");
                                            for (XMLElement element : data)
                                                handleElement(element);
                                            ConsoleResults.getInstance().repositionData("Cells", this.skinCellsDefinitions);
                                            ConsoleResults.getInstance().repositionData("Figures", this.skinFiguresDefinitions);
                                            this.xmlLoaded = true;
                                            callback.call(null);
                                        }).readFileJar();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (SAXException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (progress != null)
                                    progress.call(this.progress);
                            }
                        }
                    }
                    if (callback != null) {
                        this.skinLoaded = true;
                        callback.call(null);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleElement(XMLElement element) {
        switch (element.getName())
        {
            case "skin":
                if(element.getAttributes().getValue("setup").equalsIgnoreCase("Classic"))
                    for (XMLElement e : element.getChildrens())
                        handleElement(e);
                break;
            case "description":
                ConsoleResults.getInstance().logln("Desc: "+element.getText());
                break;
            case "author":
                ConsoleResults.getInstance().logln("Author: "+element.getText());
                break;
            case "builder":
                ConsoleResults.getInstance().logln("Builder: "+element.getText());
                break;
            case "version":
                ConsoleResults.getInstance().logln("Version: "+element.getText());
                break;
            case "appversion":
                ConsoleResults.getInstance().logln("AppVer: "+element.getText());
                break;
            case "settings":
                for (XMLElement e: element.getChildrens())
                    handleElement(e);
                break;
            case "playground":
                this.playGroundPosition = getPoint2D("position",element.getAttributes(),this.playGroundPosition);
                this.playGroundSize = getPoint2D("size",element.getAttributes(),this.playGroundSize);
                for (XMLElement e: element.getChildrens())
                    handleElement(e);
                break;
            case "figure":
                if(element.getParent() != null && element.getParent().getName() == "settings")
                {
                    this.figureSize = getPoint2D("size",element.getAttributes(),this.figureSize);
                    this.figureSpace = getPoint2D("space",element.getAttributes(),this.figureSpace);
                }
                else
                {
                    Point position = getPoint(null,element.getAttributes(),new Point(-1,-1));
                    if((position.getX()+position.getY()) > -1)
                        this.skinFiguresDefinitions.put(position,new RepositionData(
                            getPoint2D("position",element.getAttributes(),calcPosition(position)),
                            getPoint2D("size",element.getAttributes(),this.figureSize)
                        ));
                }
                break;
            case "cell":
                if(element.getParent() != null && element.getParent().getName() == "settings")
                {
                    this.cellSize = getPoint2D("size",element.getAttributes(),this.cellSize);
                    this.cellSpace = getPoint2D("space",element.getAttributes(),this.cellSpace);
                }
                else
                {
                    Point position = getPoint(null,element.getAttributes(),new Point(-1,-1));
                    if((position.getX()+position.getY()) > -1)
                        this.skinCellsDefinitions.put(position,new RepositionData(
                                getPoint2D("position",element.getAttributes(),calcPosition(position)),
                                getPoint2D("size",element.getAttributes(),this.figureSize)
                        ));
                }
                break;
        }
    }

    private Point2D calcPosition(Point position) {
        return new Point2D(
            this.playGroundPosition.getX()+this.figureSpace.getX() + position.x*(this.figureSize.getX()+this.figureSpace.getX()),
            this.playGroundPosition.getY()+ position.y*(this.figureSize.getY()+this.figureSpace.getY())
        );
    }

    private String getString(String name,AttributesImpl attributes)
    {
        return attributes.getValue(name);
    }
    private int getInt(String name,AttributesImpl attributes,int actual)
    {
        String text = getString(name,attributes);
        if(text == null)return actual;
        int value = actual;
        try {
            value = Integer.parseInt(text);
        }catch (NumberFormatException ex){}
        return value;
    }
    private double getDouble(String name,AttributesImpl attributes,double actual)
    {
        String text = getString(name,attributes);
        if(text == null)return actual;
        double value = actual;
        try {
            value = Double.parseDouble(text);
        }catch (NumberFormatException ex){}
        return value;
    }
    private Point getPoint(String name, AttributesImpl attributes, Point actual)
    {
        if(name != null)
            return new Point(getInt(name+"X",attributes,actual.x), getInt(name+"Y",attributes,actual.y));
        else
            return new Point(getInt("x",attributes,actual.x), getInt("y",attributes,actual.y));
    }
    private Point2D getPoint2D(String name, AttributesImpl attributes, Point2D actual)
    {
        if(name != null)
            return new Point2D(getDouble(name+"X",attributes,actual.getX()), getDouble(name+"Y",attributes,actual.getY()));
        else
            return new Point2D(getDouble("x",attributes,actual.getX()), getDouble("y",attributes,actual.getY()));
    }

    public HashMap<String,Point2D> getCalculatedPoints()
    {
        HashMap<String,Point2D> points = new HashMap<>();
        Point2D data = this.stageState.getSize();
        double scaleRatio = data.getX()/this.fullSize.getX();
        Point2D calcPlayGroundPosition = new Point2D(this.playGroundPosition.getX()*scaleRatio,this.playGroundPosition.getY()*scaleRatio);
        Point2D calcFigureSize = new Point2D(this.figureSize.getX()*scaleRatio,this.figureSize.getY()*scaleRatio);
        Point2D calcFigureSpace = new Point2D(this.figureSpace.getX()*scaleRatio,this.figureSpace.getY()*scaleRatio);
        Point2D calcCellSpace = new Point2D(this.cellSpace.getX()*scaleRatio,this.cellSpace.getY()*scaleRatio);
        Point2D calcCellSize = new Point2D(this.cellSize.getX()*scaleRatio,this.cellSize.getY()*scaleRatio);
        points.put("stageState",data);
        points.put("playGroundPosition",calcPlayGroundPosition);
        points.put("figureSize",calcFigureSize);
        points.put("figureSpace",calcFigureSpace);
        points.put("cellSpace",calcCellSpace);
        points.put("cellSize",calcCellSize);
        return points;
    }
    public Point2D getCellSize() {
        return this.cellSize;
    }
    public Point2D getCellSpace() {
        return this.cellSpace;
    }
    public Point2D getFigureSize() {
        return this.figureSize;
    }
    public Point2D getFigureSpace() {
        return this.figureSpace;
    }
    public Point2D getPlayGroundSize()
    {
        return this.playGroundSize;
    }
    public IPaintableStructure getPaintable(int index)
    {
        return this.paintables.get(index);
    }
    public ArrayList<IPaintableStructure> getPaintables()
    {
        return this.paintables;
    }
    public Image getImage(String name)
    {
        return images.get(name);
    }
    public HashMap<String,Image> getImages()
    {
        return this.images;
    }
    public Point2D getFullSize() {
        return fullSize;
    }
    public StageState getStageState() {
        return stageState;
    }
    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
    public void setStageState(StageState stageState) {
        this.stageState = stageState;
    }
    public void setFullSize(Point2D fullSize) {
        this.fullSize = fullSize;
    }
    public void setPaintables(ArrayList<IPaintableStructure> paintables)
    {
        this.paintables = paintables;
    }
    public void setImages(HashMap<String,Image> images)
    {
        this.images = images;
    }
    public void setCellSize(Point2D cellSize)
    {
        this.cellSize = cellSize;
    }
    public void setCellSpace(Point2D cellSpace)
    {
        this.cellSpace = cellSpace;
    }
    public void setFigureSize(Point2D figureSize)
    {
        this.figureSize = figureSize;
    }
    public void setFigureSpace(Point2D figureSpace)
    {
        this.figureSpace = figureSpace;
    }
    public void setPlayGroundSize(Point2D playGroundSize)
    {
        this.playGroundSize = playGroundSize;
    }

    public void addPaintable(IPaintableStructure paintable)
    {
        this.paintables.add(paintable);
    }
    public void addImage(String name,Image image)
    {
        this.images.put(name,image);
    }

    public void clearPaintables()
    {
        for(IPaintableStructure paintable: this.paintables)
        {
            paintable.erase(this.pane);
        }
        this.paintables.clear();
    }
    public void removePaintable(IPaintable paintable)
    {
        paintable.erase(this.pane);
        this.paintables.remove(paintable);
    }
    public void removePaintable(int index)
    {
        this.paintables.get(index).erase(this.pane);
        this.paintables.remove(index);
    }
}
