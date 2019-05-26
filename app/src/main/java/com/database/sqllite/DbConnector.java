package com.database.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import model.User;
import model.Writable;

public class DbConnector {
    static SQLiteDatabase db = null;
    DbHandler handler;

    public DbConnector(Context context) {
        if (db == null) {
            String[] tables = new String[2];
            tables[0] = "users";
            tables[1] = "messages";
            List<Pair<String, String>[]> tableColumns = new ArrayList<>();
            Pair<String, String>[] columns = new Pair[6];
            columns[0] = new Pair<>("email", "TEXT");
            columns[1] = new Pair<>("name", "TEXT");
            columns[2] = new Pair<>("tags", "TEXT");
            columns[3] = new Pair<>("classes", "TEXT");
            columns[4] = new Pair<>("type", "CHAR");
            columns[5] = new Pair<>("password", "TEXT");
            tableColumns.add(columns);
            Pair<String, String>[] messageTable = new Pair[3];
            messageTable[0] = new Pair<>("date", "INT");
            messageTable[1] = new Pair<>("topic", "TEXT");
            messageTable[2] = new Pair<>("message", "TEXT");
            tableColumns.add(messageTable);
            DbHandler.setValues("school_app", 6, tables, tableColumns);
            handler = new DbHandler(context);
            db = handler.getDb();
        }
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


    public void addElement(Writable e) {
        Dictionary<String, Object> keyValuePairs = e.getDict();

        if (e.getClass() == User.class) {
            handler.addRow(db, "users", keyValuePairs);
        } else {
            handler.addRow(db, "messages", keyValuePairs);
        }
    }

    public List<Pair<String, String>[]> getTable(String tableName) {
        return handler.getTable(db, tableName);
    }

    public void deleteAllRows(String tableName) {
        handler.deleteAllRows(db, tableName);
    }

    public User getUserByEmail(String email) {
        User user = new User();
        user.loadFromDict(handler.getAllKeysWithSorting(db, "users", "email", email));
        return user;
    }


}
