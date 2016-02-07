package no.harm.hmm.no.harm.hmm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HmmDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Hmm.db";

    public HmmDbHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + MessageContract.MessageEntry.TABLE_NAME + " (" +
                        MessageContract.MessageEntry._ID + " INTEGER PRIMARY KEY, " +
                        MessageContract.MessageEntry.COLUMN_NAME_SENDER_ID + " TEXT, " +
                        MessageContract.MessageEntry.COLUMN_NAME_MESSAGE + " TEXT, " +
                        MessageContract.MessageEntry.COLUMN_NAME_LOCAL_TIMESTAMP + " TIMESTAMP);"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(this.getClass().toString(), "Upgrading DB to version " + newVersion + ".");
        switch (newVersion) {
            case 2:
                db.execSQL("ALTER TABLE " + MessageContract.MessageEntry.TABLE_NAME +
                        " ADD COLUMN " + MessageContract.MessageEntry.COLUMN_NAME_LOCAL_TIMESTAMP +
                        " DATETIME;");
                break;
        }
        onCreate(db);
    }

}
