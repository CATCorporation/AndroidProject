package com.example.emerich.mamoyenne.BddPack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Emerich on 26/01/2015.
 */
public class MyBddClass extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "moyenne.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME_MATS = "matiere";
    private static final String TABLE_NAME_NOTE = "notes";

    public SQLiteDatabase db;

    private SQLiteStatement insertMatiere;
    private SQLiteStatement insertNote;

    private static final String INSERTM = "insert into " + TABLE_NAME_MATS + " (name,coef) values (?,?)";

    private static final String INSERTN= "insert into " + TABLE_NAME_NOTE + " (note,semestre, id_mat) values (?,?,?)";

    public MyBddClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public long insert_matiere(String name, String coef) {
        this.insertMatiere.bindString(1, name);
        this.insertMatiere.bindString(2, coef);
        return this.insertMatiere.executeInsert();
    }


    public long insert_note(String note, String semestre, String id_mat) {
        this.insertNote.bindString(1, note);
        this.insertNote.bindString(2, semestre);
        this.insertNote.bindString(3, id_mat);
        return this.insertNote.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + TABLE_NAME_MATS + " (_id INTEGER PRIMARY KEY, name TEXT, coef INTEGER )");
            db.execSQL("CREATE TABLE " + TABLE_NAME_NOTE + " (_id INTEGER PRIMARY KEY, note REAL, semestre INTEGER, " +
                    "id_mat INTEGER NOT NULL CONSTRAINT fk_id_mat REFERENCES "+ TABLE_NAME_MATS + " (_id) );");
            db.execSQL("CREATE VIEW Moyenne as select ma.name,  ma.coef, " +
                       "(select sum(note) from "+TABLE_NAME_NOTE+" as no where no.id_mat=ma._id)" +
                        "from "+TABLE_NAME_MATS+" as ma;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
        this.db = this.getWritableDatabase();
        this.insertMatiere = this.db.compileStatement(INSERTM);
        this.insertNote = this.db.compileStatement(INSERTN);
    }

    public void closeDatabase() {
        db.close();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            checkDB = null;
        }
        return checkDB != null ? true : false;
    }
}
