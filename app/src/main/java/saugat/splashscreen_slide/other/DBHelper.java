package saugat.splashscreen_slide.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by Saugat Jonchhen on 11/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "SplashScreen.db";
    protected static final String LOGIN_TABLE_NAME = "login";
    protected static final String LOGIN_COLUMN_ID = "id";
    protected static final String LOGIN_COLUMN_NAME = "uName";
    protected static final String LOGIN_COLUMN_PWD = "password";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        /*getWritableDatabase().execSQL("create table if not exists login " +
                "(id integer primary key, uName text,password text)");*/
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table if not exists login " +
                        "(id integer, uName text,password text)"
        );
        //sqLiteDatabase.execSQL("insert into login values(1,'a','a')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS login");
        onCreate(sqLiteDatabase);
    }

    public boolean registerNewUser (String uName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uName", uName);
        contentValues.put("password", password);
        db.insert("login", null, contentValues);
        return true;
    }

    /*public Integer deleteUser (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("login",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }*/

    public boolean ValidUser (String uName, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        //String sql = "SELECT id FROM login WHERE uName = '"+uName+"' AND password ='"+password+"'";
        String sql = "Select count(*) from login where uName='"+uName+"' and password = '"+password+"'" ;
        SQLiteStatement stm = getWritableDatabase().compileStatement(sql);
        long l = stm.simpleQueryForLong();
        stm.close();
        if(l==0){
            return false;
        }else
            return true;
    }

}
