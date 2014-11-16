package com.xek6ae.PurinApp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Schnittstelle für SQLiteDatabase
 * Methoden zur Abfrage, Erstellung und Datenausgabe der Datenbank
 *
 * Created by xek6ae on 20.08.14.
 */
public class SQLiteHandler extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "PurinDatabase_1.db";
    private static final int DATABASE_VERSION = 2;
    private static String DATABASE_PATH = "";

    private final Context myContext;


    public SQLiteHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DATABASE_PATH = "/data/data/"+context.getPackageName()+"/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void importIfNotExist() throws IOException{
        if(checkExist()){
            // do nothing or checking updates?
            Log.d("XEK", String.valueOf(getReadableDatabase().getVersion()));
        }else{
            // Create a default database to overwrite it
            //this.getReadableDatabase();
            Log.d("XEK", "delete and open");
            try{
                deleteDatabases();
                getReadableDatabase();  // geht auch ohne
                close();                // kompatibilität mit anderen geräten
                copyDataBase();
            }catch (IOException e){
                throw new Error("Error copying database!");
            }
        }
    }

    public boolean checkExist(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DATABASE_PATH+DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            Log.d("XEK", checkDB.getPath());
        }catch (SQLiteException e){
             e.printStackTrace();
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer
        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    //todo: brauch ich ein rückgabe wert? true wenn alles gelöscht?
    private void deleteDatabases(){
        String[] databaseList = myContext.databaseList();
        if(databaseList != null){
            for(int i=0; i<databaseList.length; i++) {
                Log.d("XEK", databaseList[i]);
                myContext.deleteDatabase(databaseList[i]);
            }
        }
    }

    public ArrayList<String> getAllLabelsArrayList(){
        ArrayList<String> labels = new ArrayList<String>();
        //SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH+DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        String selectQuery = "SELECT name FROM gericht ORDER BY name ASC"; //"SELECT "+KEY_NAME+" FROM "+TABLE;
        //db = SQLiteDatabase.openDatabase(DATABASE_PATH+DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                labels.add(cursor.getString(0));    // wenn SQL * hat, dann spalte angeben
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

    /**
       @param mode Int not NULL
       @param search String Searchtag
       @param type String Categorie
       @return ArrayList with String-arrays
     */
    public ArrayList<String[]> getDataByMode(int mode, String search, int type){
        ArrayList<String[]> list = new ArrayList<String[]>();
        String[] row;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = null;

        if(mode == 0){
            query = "SELECT name, kategorie, purinwert FROM gericht WHERE name LIKE '%"+search+"%' ORDER BY name ASC";
        }
        if(mode == 1){
            query = "SELECT name, kategorie, purinwert FROM gericht WHERE kategorie='"+search+"' ORDER BY name ASC;";
        }
        if(mode == 2){
            if(type == SearchActivity.PURIN_S){
                query = "SELECT name, kategorie, purinwert FROM gericht WHERE purinwert<=20 ORDER BY purinwert ASC";
            }
            if(type == SearchActivity.PURIN_M){
                query = "SELECT name, kategorie, purinwert FROM gericht WHERE purinwert>20 AND purinwert<50 ORDER BY purinwert ASC";
            }
            if(type == SearchActivity.PURIN_L){
                query = "SELECT name, kategorie, purinwert FROM gericht WHERE purinwert>=50 ORDER BY purinwert ASC";
            }

        }
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                row = new String[3]; //ERNSTHAFT?! warum muss hier ne neue instanz hin? ansonsten "overrides"
                for(int i=0; i<3; i++){
                    row[i] = cursor.getString(i);
                    //Log.d("XEK", "Input "+row[i]);
                }
                list.add(row);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    //TODO: mit in getDataByMode implementieren?
    public String[] getDataArray(String name){
        String[] string = new String[5];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM gericht WHERE name='"+name+"';", null);

        if(cursor.moveToFirst()){
            do{
                for(int i=0; i<5; i++){
                    string[i]=cursor.getString(i);
                }
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return string;
    }

    public int countEntries(){
        int e;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM gericht", null);
        if(cursor.moveToFirst()){
            e = cursor.getInt(0);
        }else{
            e = -1;
        }
        cursor.close();
        db.close();
        return e;
    }

    public void insertValue(int value){
        int tageswert;
        int anzahl = 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT tageswert, anzahl FROM nutzer WHERE erstellt=date()", null);

        if(cursor.moveToFirst()){
            tageswert = cursor.getInt(0)+value;
            anzahl = cursor.getInt(1)+1;
            db.execSQL("UPDATE nutzer SET tageswert="+tageswert+", anzahl="+anzahl+" WHERE erstellt=date()");
        }else{
            db.execSQL("INSERT INTO nutzer (tageswert, anzahl) VALUES ("+value+","+anzahl+")");
        }
    }

    public ArrayList<String[]> getDateValues(int mode){
        ArrayList<String[]> list = new ArrayList<String[]>();
        String query = null;
        String[] row;
        SQLiteDatabase db = this.getReadableDatabase();

        if(mode==0){
            //heute
            query = "SELECT tageswert, anzahl, erstellt FROM nutzer WHERE erstellt=date('now')";
        }if(mode==1){
            //letzten 10 tage
            query = "SELECT tageswert, anzahl, erstellt FROM nutzer WHERE erstellt>=date('now', '-10 day') ORDER BY erstellt DESC";
        }if(mode==2){
            //letzten monat
            query = "SELECT tageswert, anzahl, erstellt FROM nutzer WHERE erstellt>=date('now', 'start of month', '-1 month') and erstellt<=date('now', 'start of month', '-1 day') ORDER BY erstellt DESC";
        }
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                row = new String[3];
                for(int i=0; i<3; i++){
                    row[i] = cursor.getString(i);
                }
                list.add(row);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}
