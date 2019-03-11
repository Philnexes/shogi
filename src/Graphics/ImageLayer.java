package Graphics;


import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLayer implements ILayer {

    ImageView image;

    public ImageLayer(Image image)
    {
        this.image = new ImageView(image);
        this.image.setPreserveRatio(true);
        this.image.setSmooth(true);
        this.image.setCache(true);
        //System.out.println(image);
    }

    public ImageLayer(Point2D size,Image image)
    {
        this(image);
        setSize(size);
    }
    public ImageLayer(Point2D position,Point2D size,Image image)
    {
        this(size,image);
        setPosition(position);
    }

    public ImageLayer(Point2D position,Point2D size,Image image, double rotation)
    {
        this(position,size,image);
        setRotation(rotation);
    }

    public ImageLayer(Point2D position,Point2D size,Image image, double rotation, double opacity)
    {
        this(position,size,image,rotation);
        setOpacity(opacity);
    }

    @Override
    public boolean isVisible() {
        return image.getOpacity() == 1.0;
    }

    @Override
    public void show() {
        this.image.setOpacity(1.0);
    }

    @Override
    public void hide() {
        this.image.setOpacity(0.0);
    }

    @Override
    public void setPosition(Point2D point) {
        this.image.setX(point.getX());
        this.image.setY(point.getY());
    }

    @Override
    public Point2D getPosition() {
        return new Point2D(image.getX(),image.getY());
    }

    @Override
    public void setSize(Point2D point) {
        this.image.setFitWidth(point.getX());
        this.image.setFitHeight(point.getY());
    }

    @Override
    public Point2D getSize() {
        return new Point2D(this.image.getFitWidth(),this.image.getFitHeight());
    }

    @Override
    public void setRotation(double rotation) {
        this.image.setRotate(rotation);
    }

    @Override
    public void setOpacity(double opacity) {
        this.image.setOpacity(opacity);
    }

    @Override
    public void setData(Object data) {
        this.image.setImage((Image)data);
    }

    @Override
    public void setObject(Object object) {
        this.image = (ImageView)object;
    }

    @Override
    public Object getData() {
        return this.image.getImage();
    }

    @Override
    public Object getObject() {
        return this.image;
    }

    @Override
    public double getOpacity() {
        return this.image.getOpacity();
    }

    @Override
    public double getRotation() {
        return this.image.getRotate();
    }
}
