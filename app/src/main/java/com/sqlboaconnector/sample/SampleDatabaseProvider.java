package com.sqlboaconnector.sample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sqlboaconnector.api.DatabaseProvider;

/**
 * Created by trevor on 3/6/2015.
 */
public class SampleDatabaseProvider implements DatabaseProvider {

    private SQLiteDatabase db;
    private Context context;

    public SampleDatabaseProvider(Context context) {
        this.context = context;
    }

    @Override
    public SQLiteDatabase open() {
        ensureDB();
        return db;
    }

    @Override
    public void close(SQLiteDatabase db) {
    }

    private void ensureDB() {
        if (db != null) {

            if (!db.isOpen()) {
                db = context.openOrCreateDatabase("sample.db", Context.MODE_PRIVATE, null);
            }
            return;
        }

        db = context.openOrCreateDatabase("sample.db", Context.MODE_PRIVATE, null);

        // Put in some sample data
        db.execSQL("create table if not exists foo (id int primary key, name varchar, description varchar)");
    }
}
