package com.bustripper.bustripper.UserInterface;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bustripper.bustripper.Entity.Account;


public interface IAccountDAO {
    void addUser(Account user);

    void updateUser(Account user);

    Account Authenticate(String username, String password);

    boolean isEmailExists(String email);
    boolean isUsernameExists(String username);


}
