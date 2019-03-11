package Utilities.Timer;

import Utilities.Callback.CallbackPackage;
import Utilities.Callback.ICallback;

import java.util.TimerTask;

public class CallbackTimerTask extends TimerTask {

    ICallback<CallbackPackage> callback;
    CallbackPackage data;

    public CallbackTimerTask(ICallback<CallbackPackage> callback)
    {
        this.callback = callback;
        this.data = null;
    }

    public CallbackTimerTask(ICallback<CallbackPackage> callback,CallbackPackage data)
    {
        this(callback);
        this.data = data;
    }

    public void SetData(CallbackPackage data)
    {
        this.data = data;
    }

    public CallbackPackage GetData()
    {
        return this.data;
    }


    @Override
    public void run() {
        this.callback.call(this.data);
    }
}
