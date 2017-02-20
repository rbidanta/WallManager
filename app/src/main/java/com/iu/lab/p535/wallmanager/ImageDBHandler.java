package com.iu.lab.p535.wallmanager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jaidev on 02/19/17.
 */
public class ImageDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "imageDB.db";

    // declare TABLE constants in single place to make edits
    private static final String TABLE = "Image";
    private static final String COLUMN_IMAGE_ID = "image_id";
    private static final String COLUMN_IMAGE_PATH = "path";

    public ImageDBHandler(Context context){ //, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Write your create string here.
        String createSQL = "CREATE TABLE " + TABLE + " (" +
                COLUMN_IMAGE_ID + " INTEGER PRIMARY KEY, "+
                COLUMN_IMAGE_PATH + " TEXT)";
        db.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addImage(Image image){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_IMAGE_ID, image.getImageID());
        cv.put(COLUMN_IMAGE_PATH, image.getPath());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE, null, cv);

        db.close();
    }

    public void deleteImage(String path){
        String sqlQuery = "DELETE FROM "+ TABLE + " WHERE " +
                COLUMN_IMAGE_PATH + " = \"" + path + "\"" ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);

        db.close();

    }

}
