package Utilities.Window;

import Game.Player;
import Utilities.Callback.ICallback;
import Utilities.Debug.ConsoleResults;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.toRadians;

public class StageState {
    Stage stage;
    ArrayList<Scene> scenes;
    double x,y,w,h;
    ArrayList<ICallback<Void>> callbacks;
    Point2D ratio;

    public StageState(Stage stage, Point2D ratio, ICallback<Void> callback) {
        this.stage = stage;
        this.scenes = new ArrayList<>();
        this.callbacks = new ArrayList<>();
        this.callbacks.add(callback);
        this.ratio = ratio;
        this.x = stage.getX();
        this.y = stage.getY();
        this.w = stage.getWidth();
        this.h = stage.getHeight();
        this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.w = newVal.doubleValue();
            if(checkRatio())
                for (ICallback<Void> event: this.callbacks)
                    event.call(null);
        });
        this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.h = newVal.doubleValue();
            if(checkRatio())
                for (ICallback<Void> event: this.callbacks)
                    event.call(null);
        });
        this.stage.xProperty().addListener((obs, oldVal, newVal) -> {
            this.x = newVal.doubleValue();
        });
        this.stage.yProperty().addListener((obs, oldVal, newVal) -> {
            this.y = newVal.doubleValue();
        });
    }
    public Point2D getPosition()
    {
        //return new Point2D(x,y);
        return new Point2D(this.stage.getX(),this.stage.getY());
    }
    public Point2D getSize()
    {
        //ConsoleResults.getInstance().logln(x+", "+y);
        //return new Point2D(w,h);
        return new Point2D(this.stage.getWidth(),this.stage.getHeight());
    }

    public Stage getStage() {
        return this.stage;
    }

    public Point2D getRatio()
    {
        return this.ratio;
    }
    public void setRatio(Point2D ratio)
    {
        this.ratio = ratio;
        checkRatio();
    }

    private boolean checkRatio() {
        double ratio = this.ratio.getX()/this.ratio.getY();
        int cw = (int)this.w, ch = (int)Math.round(this.h*ratio);
        ConsoleResults.getInstance().resizeComputationResults(this.ratio,ratio,(int)this.w,(int)this.h,cw,(int)this.h);
        if (cw != ch)
        {
            //this.h = (this.w/this.ratio.getX())*this.ratio.getY();
            //this.w = (this.h/this.ratio.getY())*this.ratio.getX();
            this.stage.setWidth((int)Math.round(this.h*ratio));
            this.stage.setHeight(this.h);
            return true;
        }
        return true;
        /*double  drw = this.w%this.ratio.getX(),
                drh = this.h%this.ratio.getY();
        if(drw != drh)
        {
            if(drw < drh)
            {
                this.stage.setWidth(drh*this.ratio.getY());
                this.stage.setHeight(drh*this.ratio.getX());
            }
            else
            {
                this.stage.setWidth(drw*this.ratio.getY());
                this.stage.setHeight(drw*this.ratio.getX());
            }
        }*/
    }

    public void check() {
        checkRatio();
    }

    public void addCallback(ICallback<Void> callback)
    {
        this.callbacks.add(callback);
    }

    public void removeCallback(ICallback<Void> callback)
    {
        this.callbacks.remove(callback);
    }

    public void clearCallbacks()
    {
        this.callbacks.clear();
    }
}
