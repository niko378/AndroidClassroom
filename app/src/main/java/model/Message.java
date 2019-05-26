package model;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

public class Message implements Writable{
    public String topic = "";
    public String body = "";
    public Date date;

    @Override
    public Dictionary<String, Object> getDict() {
        Dictionary<String, Object> attributes = new Hashtable<>();
        attributes.put("topic", topic);
        attributes.put("message", body);
        attributes.put("date", (int) date.getTime()/1000);
        return attributes;
    }

    public void loadFromDict(Dictionary<String, Object> dict) {
        topic = (String) dict.get("topic");
        body = (String) dict.get("body");
        date = new Date(((int)dict.get("date"))*1000L);
    }
}
