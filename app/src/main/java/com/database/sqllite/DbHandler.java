package com.database.sqllite;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    // Database Version
    private static int dbVersion;
    // Database Name
    private static String dbName;
    // Contacts table name
    private static String[] tableNames;

    // Tables columns
    private static List<Pair<String, String>[]> dbColumns;


    public static void setValues(String dbNameNew, int dbVersionNew, String[] tableNamesNew, List<Pair<String, String>[]> dbColumnsNew) {
        dbName = dbNameNew;
        tableNames = tableNamesNew;
        dbColumns = dbColumnsNew;
        dbVersion = dbVersionNew;

    }

    public DbHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < tableNames.length; i++) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + tableNames[i] + "(";
            Log.d("LOOP", i + " " + tableNames[i] + " " + dbColumns.get(i)[0].first);
            for (int j = 0; j < dbColumns.get(i).length; j++) {
                if (j == 0) {
                    CREATE_CONTACTS_TABLE = CREATE_CONTACTS_TABLE.concat(dbColumns.get(i)[j].first +
                            " " + dbColumns.get(i)[j].second + " PRIMARY KEY, ");
                } else {
                    CREATE_CONTACTS_TABLE = CREATE_CONTACTS_TABLE.concat(dbColumns.get(i)[j].first +
                            " " + dbColumns.get(i)[j].second + ", ");
                }
            }
            CREATE_CONTACTS_TABLE = CREATE_CONTACTS_TABLE.substring(0, CREATE_CONTACTS_TABLE.length()-2).concat(")");
            try {
                db.execSQL(CREATE_CONTACTS_TABLE);
            } catch (Exception e) {
                Log.d("CREATE", "EXCEPTION: " + CREATE_CONTACTS_TABLE);
                return;
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("UPDATE", "START UPDATE");
        for (int i = 0; i < tableNames.length; i++) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + tableNames[i]);
        }
        // Creating tables again
        onCreate(db);
    }

    public String getStringValue(SQLiteDatabase db, String table, String key, String sortingField, String sortingValue) {
        String[] params = new String[1];
        params[0] = sortingValue;
        Cursor cursor = db.rawQuery("SELECT " + key + " FROM " + table + " " +
                "WHERE " + sortingField + " = ?", params);
        if (cursor.moveToNext()) {
            return cursor.getString(0);
        }
        return null;
    }

    public Dictionary<String,Object> getAllKeysWithSorting(SQLiteDatabase db, String table, String sortingField, String sortingValue) {
        String[] params = new String[1];
        params[0] = sortingValue;
        Cursor cursor = db.rawQuery("SELECT * FROM " + table + " " +
                "WHERE " + sortingField + " = ?", params);
        Dictionary<String, Object> dict = null;
        if (cursor.moveToNext()) {
            dict = new Hashtable<>();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                dict.put(cursor.getColumnName(i), cursor.getString(i));
            }
        }
        return dict;
    }

    public void setStringValue(SQLiteDatabase db, String table, String key, String value, String sortingField, String sortingValue) {
        db.execSQL("UPDATE " + table + " SET " + key + " = '" + value + "'" +
                " WHERE " + sortingField + " = '" + sortingValue + "'");
    }

    public void addRow(SQLiteDatabase db, String table, Dictionary<String, Object> keyValuePairs) {

        String query = "INSERT INTO " + table + " (";
        String values = "(";
        Enumeration<String> keys = keyValuePairs.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            query = query.concat(key + ", ");
            values = values.concat("'" + keyValuePairs.get(key).toString() + "', ");
        }
        values = values.substring(0, values.length()-2).concat(")");
        query = query.substring(0, query.length()-2).concat(") VALUES ").concat(values);

        db.execSQL(query);

    }

    public List<Pair<String, String>[]> getTable(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, new String[0]);
        ArrayList<Pair<String, String>[]> table = new ArrayList<>();
        while (cursor.moveToNext()) {
            Pair<String, String>[] row = new Pair[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row[i] = new Pair<>(cursor.getColumnName(i), cursor.getString(i));
            }
            table.add(row);
        }
        return table;
    }

    public SQLiteDatabase getDb() {
        return getWritableDatabase();
    }

    public void deleteAllRows(SQLiteDatabase db, String tableName) {
        db.execSQL("DELETE FROM " + tableName);
    }
}
