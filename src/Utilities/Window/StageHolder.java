package Utilities.Window;

import java.util.ArrayList;
import java.util.HashMap;

public class StageHolder {
    private static StageHolder ourInstance = new StageHolder();

    public static StageHolder getInstance() {
        return ourInstance;
    }

    HashMap<String,StageState> stages;

    private StageHolder() {
    }

    public void addStageState(String key,StageState stageState)
    {
        this.stages.put(key,stageState);
    }
    public StageState getStageState(String key)
    {
        return this.stages.get(key);
    }
    public ArrayList<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<>();
        keys.addAll(this.stages.keySet());
        return keys;
    }
    public HashMap<String,StageState> getStagesStates()
    {
        return this.stages;
    }
}
