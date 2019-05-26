package model;

import java.util.Dictionary;

public interface Writable {
    public Dictionary<String, Object> getDict();
    public void loadFromDict(Dictionary<String, Object> dict);
}
