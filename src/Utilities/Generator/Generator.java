package Utilities.Generator;

public class Generator {
    private static Generator ourInstance = new Generator();

    private static int id = 1;

    public static Generator getInstance() {
        return ourInstance;
    }

    private Generator() {
    }

    public int getNewId()
    {
        return id++;
    }

    public void reset() {
        id = 1;
    }
}
