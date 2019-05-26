package model;

import java.util.Dictionary;
import java.util.Hashtable;

public class Message implements Writable{
    public String topic = "";
    public String body = "";

    @Override
    public Dictionary<String, Object> getDict() {
        Dictionary<String, Object> attributes = new Hashtable<>();
        attributes.put("topic", topic);
        attributes.put("body", body);
        return attributes;
    }

    public void loadFromDict(Dictionary<String, Object> dict) {
        topic = (String) dict.get("topic");
        body = (String) dict.get("body");
    }
}