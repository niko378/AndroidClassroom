package model;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class User implements Writable {
    public final static String DEFAULT_TAGS = "Informations générales;";

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
        String listOfTags = "";
        if (tags != null) {
            for (String tagName : tags) {
                listOfTags = listOfTags.concat(tagName + ";");
            }
        }
        String listOfClasses = "";
        if (classes != null) {
            for (String className : classes) {
                listOfClasses = listOfClasses.concat(className + ";");
            }
        }
        attributes.put("tags", listOfTags);
        attributes.put("classes", listOfClasses);
        return attributes;
    }

    @Override
    public void loadFromDict(Dictionary<String, Object> dict) {
        email = (String) dict.get("email");
        password = (String) dict.get("password");
        type = ((String) dict.get("type")).charAt(0);
        fullName = (String) dict.get("name");
        tags = new ArrayList<>();
        for (String tag : ((String) dict.get("tags")).split(";")) {
            tags.add(tag);
        }
        classes = new ArrayList<>();
        for (String singleClass : ((String) dict.get("classes")).split(";")) {
            classes.add(singleClass);
        }
    }

    public void loadDefaultTags() {
        tags = new ArrayList<>();
        for (String tag : DEFAULT_TAGS.split(";")) {
            tags.add(tag);
        }
    }
}
