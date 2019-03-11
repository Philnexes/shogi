package Graphics;

public class PaintableFactoryHolder {
    private static PaintableFactoryHolder ourInstance = new PaintableFactoryHolder();

    public static PaintableFactoryHolder getInstance() {
        return ourInstance;
    }

    PaintableFactory paintableFactory;

    public PaintableFactoryHolder()
    {

    }

    public PaintableFactoryHolder(PaintableFactory paintableFactory) {
        this.paintableFactory = paintableFactory;
    }

    public PaintableFactory get()
    {
        return this.paintableFactory;
    }
    public void set(PaintableFactory paintableFactory)
    {
        this.paintableFactory = paintableFactory;
    }

}
