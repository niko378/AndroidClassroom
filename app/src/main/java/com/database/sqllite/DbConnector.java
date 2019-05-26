package com.database.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DbConnector {
    SQLiteDatabase db;
    DbHandler handler;

    public DbConnector(Context context) {
        String[] tables = new String[1];
        tables[0] = "users";
        List<Pair<String, String>[]> tableColumns = new ArrayList<>();
        Pair<String, String>[] columns = new Pair[6];
        columns[0] = new Pair<>("email", "TEXT");
        columns[1] = new Pair<>("name", "TEXT");
        columns[2] = new Pair<>("tags", "TEXT");
        columns[3] = new Pair<>("classes", "TEXT");
        columns[4] = new Pair<>("type", "CHAR");
        columns[5] = new Pair<>("password", "TEXT");
        tableColumns.add(columns);
        DbHandler.setValues("school_app", 4, tables, tableColumns);
        handler = new DbHandler(context);
        db = handler.getDb();
    }

    public void addTags(List<String> tagNames, String email) throws Exception{
        String listOfNames = "";
        for (String name : tagNames) {
            listOfNames = listOfNames.concat(name + ";");
        }
        String existingKeys = handler.getStringValue(db, "users", "tags", "email", email);
        if (existingKeys == null) {
            throw new Exception("column not found");
        }
        listOfNames = existingKeys + listOfNames;
        handler.setStringValue(db, "users", "tags", listOfNames, "email", email);
    }

    public void addUser(String email, String name, List<String> tags, List<String> classes, String type, String password) {
        String listOfTags = "";
        for (String tagName : tags) {
            listOfTags = listOfTags.concat(tagName + ";");
        }
        String listOfClasses = "";
        for (String className : classes) {
            listOfClasses = listOfClasses.concat(className + ";");
        }
        Pair<String, String>[] keyValuePairs = new Pair[6];
        keyValuePairs[0] = new Pair<>("email", email);
        keyValuePairs[1] = new Pair<>("name", name);
        keyValuePairs[2] = new Pair<>("tags", listOfTags);
        keyValuePairs[3] = new Pair<>("classes", listOfClasses);
        keyValuePairs[4] = new Pair<>("type", type);
        keyValuePairs[5] = new Pair<>("password", password);


        handler.addRow(db, "users", keyValuePairs);
    }

    public List<Pair<String, String>[]> getTable(String tableName) {
        return handler.getTable(db, tableName);
    }

    public void deleteAllRows(String tableName) {
        handler.deleteAllRows(db, tableName);
    }


}
