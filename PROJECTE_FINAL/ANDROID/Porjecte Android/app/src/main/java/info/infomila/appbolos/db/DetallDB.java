package info.infomila.appbolos.db;

import android.provider.BaseColumns;

/**
 * Created by alber
 */

public class DetallDB {

    public DetallDB(){}

    public static class partidaDetall implements BaseColumns {
        public static final String TABLE_NAME = "detallPartida";
        public static final String COLUMN_NAME_PARTIDA_ID = "partida_id";
        public static final String COLUMN_NAME_NUM_ENTRADA = "entrada";
        public static final String COLUMN_NAME_DATA_ENTRADA = "data_entrada";
        public static final String COLUMN_NAME_SOCI_ID = "soci_id";
        public static final String COLUMN_NAME_SOCI_NOM = "soci_nom";
        public static final String COLUMN_NAME_SOCI_TAG = "soci_tag";
        public static final String COLUMN_NAME_NUM_CARAMBOLES = "caramboles";
    }

    public static final String PARTIDA_DETALL_CREATE_ENTRIES =
            "CREATE TABLE " + partidaDetall.TABLE_NAME + " (" +
                    partidaDetall._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    partidaDetall.COLUMN_NAME_PARTIDA_ID + " INTEGER," +
                    partidaDetall.COLUMN_NAME_NUM_ENTRADA + " INTEGER," +
                    partidaDetall.COLUMN_NAME_DATA_ENTRADA + " DATETIME," +
                    partidaDetall.COLUMN_NAME_SOCI_ID + " INTEGER," +
                    partidaDetall.COLUMN_NAME_SOCI_NOM + " TEXT," +
                    partidaDetall.COLUMN_NAME_SOCI_TAG + " TEXT," +
                    partidaDetall.COLUMN_NAME_NUM_CARAMBOLES + " INTEGER );";

    public static final String DETALL_PARTIDA_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + partidaDetall.TABLE_NAME;

}
