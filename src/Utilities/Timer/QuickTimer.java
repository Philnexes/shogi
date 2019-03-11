package Utilities.Timer;

import Utilities.Callback.ICallback;

import java.util.Timer;
import Utilities.Timer.CallbackTimerTask;

public class QuickTimer extends Timer {
    int delay;
    int repeat = -1;
    ICallback<Void> callback;

    public QuickTimer(int delay, ICallback<Void> callback)
    {
        this.delay = delay;
        this.callback = callback;
    }
    public QuickTimer(int delay,int repeat, ICallback<Void> callback)
    {
        this(delay,callback);
        this.repeat = repeat;
    }

    public void Start()
    {
        if(this.repeat >= 0) {
            this.scheduleAtFixedRate(new CallbackTimerTask((data)->this.callback.call(null)),this.delay,this.repeat);
        }
        else
            this.schedule(new CallbackTimerTask((data)->this.callback.call(null)),this.delay);
    }
    public void Stop()
    {
        this.cancel();
    }
    public void Clear()
    {
        this.purge();
    }
}
