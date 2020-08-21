package nanodevlab.imran.aqwalehazratalias.OfflineDataBase.DataSource;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import nanodevlab.imran.aqwalehazratalias.Fragments.Ziarats;
import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Databasehelper.Databasehelper;
import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Models.DuaModel;

public class DataSource {


    public static final String DUA_TITLE = "Title";
    public static final String DUA_TEXT = "DuaText";

    public static final String DUA_TITLE2 = "ZiaratName";
    public static final String DUA_TEXT2 = "ZiaratText";

    private static Cursor cursor;
    private Databasehelper databaseHelper;


    public DataSource(Context context) {
        databaseHelper = new Databasehelper(context);
    }

    public ArrayList<DuaModel> DuaModelWithTranslation() {

        ArrayList<DuaModel> surahArrayList = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        cursor = db.rawQuery(
                "SELECT Duas.Title,Duas.DuaText FROM Duas",
                null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            DuaModel duaModel = new DuaModel();
            duaModel.setTitle(cursor.getString(cursor.getColumnIndex(DUA_TITLE)));
            duaModel.setDuaText(cursor.getString(cursor.getColumnIndex(DUA_TEXT)));
            surahArrayList.add(duaModel);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return surahArrayList;
    }

    public ArrayList<DuaModel> ZiaratModelWithTranslation() {

        ArrayList<DuaModel> surahArrayList = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        cursor = db.rawQuery(
                "SELECT Ziarat.ZiaratName,Ziarat.ZiaratText FROM Ziarat",
                null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            DuaModel duaModel = new DuaModel();
            duaModel.setTitle(cursor.getString(cursor.getColumnIndex(DUA_TITLE2)));
            duaModel.setDuaText(cursor.getString(cursor.getColumnIndex(DUA_TEXT2)));
            surahArrayList.add(duaModel);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return surahArrayList;
    }

}
