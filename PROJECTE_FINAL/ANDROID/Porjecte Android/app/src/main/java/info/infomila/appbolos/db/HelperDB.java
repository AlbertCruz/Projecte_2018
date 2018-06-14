package info.infomila.appbolos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.infomila.appbolos.models.DetallPartida;

/**
 * Created by alber
 */

public class HelperDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DetallPartida.db";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DetallDB.PARTIDA_DETALL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DetallDB.DETALL_PARTIDA_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public int insertDetall(DetallPartida dp) {
        dp.setDataEntrada(new Date());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DetallDB.partidaDetall.COLUMN_NAME_PARTIDA_ID, dp.getPartidaId());
        values.put(DetallDB.partidaDetall.COLUMN_NAME_NUM_ENTRADA, dp.getEntrada());
        values.put(DetallDB.partidaDetall.COLUMN_NAME_DATA_ENTRADA, sdf.format(dp.getDataEntrada()));
        values.put(DetallDB.partidaDetall.COLUMN_NAME_SOCI_ID, dp.getSociId());
        values.put(DetallDB.partidaDetall.COLUMN_NAME_SOCI_NOM, dp.getSociNom());
        values.put(DetallDB.partidaDetall.COLUMN_NAME_SOCI_TAG, dp.getSociNom());
        values.put(DetallDB.partidaDetall.COLUMN_NAME_NUM_CARAMBOLES, dp.getCaramboles());

        return (int)db.insert(DetallDB.partidaDetall.TABLE_NAME, null, values);
    }

    public List<DetallPartida> selectAllDetallEntrades(String pIDPartida) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<DetallPartida> llEntrades = new ArrayList<DetallPartida>();

        if (pIDPartida == null || pIDPartida.length() == 0 ){
            return llEntrades;
        }

        String[] projection = {
                DetallDB.partidaDetall._ID,
                DetallDB.partidaDetall.COLUMN_NAME_PARTIDA_ID,
                DetallDB.partidaDetall.COLUMN_NAME_NUM_ENTRADA,
                DetallDB.partidaDetall.COLUMN_NAME_DATA_ENTRADA,
                DetallDB.partidaDetall.COLUMN_NAME_SOCI_ID,
                DetallDB.partidaDetall.COLUMN_NAME_SOCI_NOM,
                DetallDB.partidaDetall.COLUMN_NAME_SOCI_TAG,
                DetallDB.partidaDetall.COLUMN_NAME_NUM_CARAMBOLES,
        };

        String selection = DetallDB.partidaDetall.COLUMN_NAME_PARTIDA_ID + " = ?";
        String[] selectionArgs = { pIDPartida };

        Cursor cursor = db.query(
                DetallDB.partidaDetall.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                DetallDB.partidaDetall.COLUMN_NAME_NUM_ENTRADA + " ASC"
        );

        while (cursor.moveToNext()) {
            int partidaID = cursor.getInt(cursor.getColumnIndexOrThrow(DetallDB.partidaDetall.COLUMN_NAME_PARTIDA_ID));
            int numEntrada = cursor.getInt(cursor.getColumnIndexOrThrow(DetallDB.partidaDetall.COLUMN_NAME_NUM_ENTRADA));

            String strDataEntrada = cursor.getString(cursor.getColumnIndexOrThrow(DetallDB.partidaDetall.COLUMN_NAME_DATA_ENTRADA));
            Date dataEntrada = null;
            try {
                dataEntrada = sdf.parse(strDataEntrada);
            } catch (ParseException e) { }

            int sociId = cursor.getInt(cursor.getColumnIndexOrThrow(DetallDB.partidaDetall.COLUMN_NAME_SOCI_ID));
            String nomSoci = cursor.getString(cursor.getColumnIndexOrThrow(DetallDB.partidaDetall.COLUMN_NAME_SOCI_NOM));
            String tagSoci = cursor.getString(cursor.getColumnIndexOrThrow(DetallDB.partidaDetall.COLUMN_NAME_SOCI_TAG));
            int numCaramboles = cursor.getInt(cursor.getColumnIndexOrThrow(DetallDB.partidaDetall.COLUMN_NAME_NUM_CARAMBOLES));
            DetallPartida detall = new DetallPartida(partidaID, numEntrada, dataEntrada, sociId, nomSoci, tagSoci, numCaramboles);
            llEntrades.add(detall);
        }

        return llEntrades;
    }

    public void deleteDetallPartida(int idPartida) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = DetallDB.partidaDetall.COLUMN_NAME_PARTIDA_ID + " LIKE ?";
        String[] selectionArgs = { ""+idPartida};
        db.delete(DetallDB.partidaDetall.TABLE_NAME, selection, selectionArgs);
    }

    public void deleteLastDetallPartida() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT last_insert_rowid();",null);
        if (c != null && c.moveToFirst())
        {
            int lastId = (int)c.getLong(0);
            String selection = DetallDB.partidaDetall._ID + " LIKE ?";
            String[] selectionArgs = { ""+lastId};
            db.delete(DetallDB.partidaDetall.TABLE_NAME, selection, selectionArgs);
        }
    }
}
