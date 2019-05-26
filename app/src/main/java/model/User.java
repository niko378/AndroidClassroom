package model;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class User implements Writable {
    public String email;
    public String password;
    public char type;
    public String fullName;
    public List<String> tags;
    public List<String> classes;

    @Override
    public Dictionary<String, Object> getDict() {
        Dictionary<String, Object> attributes = new Hashtable<>();
        attributes.put("email", email);
        attributes.put("password", password);
        attributes.put("type", type);
        attributes.put("name", fullName);
        attributes.put("tags", tags);
        attributes.put("classes", classes);
        return attributes;
    }

    public void loadFromDict(Dictionary<String, Object> dict) {
        email = (String) dict.get("email");
        password = (String) dict.get("password");
        type = (char) dict.get("type");
        fullName = (String) dict.get("name");
        tags = (List<String>) dict.get("tags");
        tags = (List<String>) dict.get("classes");
    }
}
