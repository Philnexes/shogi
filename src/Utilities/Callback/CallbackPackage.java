package Utilities.Callback;

import javax.jws.Oneway;
import java.util.ArrayList;

public class CallbackPackage {
    ArrayList<Object> objects;

    public CallbackPackage()
    {
        this.objects = new ArrayList<>();
    }
    public CallbackPackage(ArrayList<Object> objects)
    {
        this();
        this.objects.addAll(objects);
    }

    public void addObject(Object object)
    {
        this.objects.add(object);
    }

    public Object getObject(int index)
    {
        return this.objects.get(index);
    }
    public ArrayList<Object> getObjects()
    {
        return this.objects;
    }
}
