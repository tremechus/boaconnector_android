package com.sqlboaconnector.api;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseProvider {

    public SQLiteDatabase open();

    public void close(SQLiteDatabase db);
}
