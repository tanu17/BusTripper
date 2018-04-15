package com.bustripper.bustripper.UserInterface;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.bustripper.bustripper.Entity.Account;

public class SQLiteDBHelper extends SQLiteOpenHelper implements IAccountDAO{
    //DATABASE NAME
    public static final String DATABASE_NAME = "Bus.Tripper.Accounts";
    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;
    //TABLE NAME
    public static final String TABLE_ACCOUNTS = "Accounts";
    //TABLE ACCOUNTS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";
    //COLUMN username
    public static final String KEY_USER_NAME = "username";
    //COLUMN email
    public static final String KEY_EMAIL = "email";
    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_ACCOUNTS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_PASSWORD + " TEXT, "
            + KEY_EMAIL + " TEXT"
            + " ) ";


    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
    }

    //using this method we can add users to user table
    public void addUser(Account user) {
        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        //create content values to insert
        ContentValues values = new ContentValues();
        //Put username in  @values
        values.put(KEY_USER_NAME, user.getUsername());
        //Put password in  @values
        values.put(KEY_PASSWORD, user.getPassword());
        //Put email in  @values
        values.put(KEY_EMAIL, user.getEmail());
        // insert row
        long todo_id = db.insert(TABLE_ACCOUNTS, null, values);
    }

    public void updateUser(Account user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_NAME, user.getUsername());
        contentValues.put(KEY_PASSWORD, user.getPassword());
        contentValues.put(KEY_EMAIL, user.getEmail());
        db.update(TABLE_ACCOUNTS, contentValues, "userName = ? ", new String[] {
                user.getUsername() } );
    }

    public Account Authenticate(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNTS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_PASSWORD, KEY_EMAIL},//Selecting columns want to query
                KEY_USER_NAME + "=?",
                new String[]{username},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            Account user1 = new Account(cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (password.equals(user1.getPassword())) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNTS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_PASSWORD, KEY_EMAIL},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNTS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_PASSWORD, KEY_EMAIL},//Selecting columns want to query
                KEY_USER_NAME + "=?",
                new String[]{username},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }
}