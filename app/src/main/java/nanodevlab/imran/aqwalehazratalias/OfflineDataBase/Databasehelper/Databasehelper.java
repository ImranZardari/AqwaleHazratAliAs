package nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Databasehelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "db.db";
    public static final int DATABASE_VERSION = 8;

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.setForcedUpgrade(); // just because it is read only database so ForceUpgrade change or
        // remove it is not read only
        Log.d("DatabaseHelper  ", "constructor");
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }
}
