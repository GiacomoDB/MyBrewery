package brewery.giacomo.com.mybrewery.Adapters;

/**
 * Created by giaco on 27/05/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import brewery.giacomo.com.mybrewery.model.Beer;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_BEER = "beer";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_ABV = "abv";
    public static final String COLUMN_IBU = "ibu";
    public static final String COLUMN_GLASS = "glass";
    public static final String COLUMN_AVAILABLED = "availabled";
    public static final String COLUMN_STYLED = "styled";
    public static final String COLUMN_ORGANIC = "organic";
    public static final String COLUMN_ICON_PATH = "icon_path";
    public static final String COLUMN_MEDIUM_PATH = "medium_path";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CREATE_DATE = "create_date";
    public static final String COLUMN_UPDATE_DATE = "update_date";
    public static final String COLUMN_AVAILABLE = "available";
    public static final String COLUMN_FAVOURITE = "favourite";

    private static final String DATABASE_NAME = "brewery";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_BEER + "( " + COLUMN_ID
            + " TEXT, "+ COLUMN_NAME
            + " TEXT, " + COLUMN_DESC
            + " TEXT, " + COLUMN_ABV
            + " REAL, " + COLUMN_IBU
            + " INTEGER, " + COLUMN_GLASS
            + " INTEGER, " + COLUMN_AVAILABLED
            + " INTEGER, " + COLUMN_STYLED
            + " INTEGER, " + COLUMN_ORGANIC
            + " TEXT, " + COLUMN_ICON_PATH
            + " TEXT, " + COLUMN_MEDIUM_PATH
            + " TEXT, " + COLUMN_STATUS
            + " TEXT, " + COLUMN_CREATE_DATE
            + " TEXT, " + COLUMN_UPDATE_DATE
            + " TEXT, " + COLUMN_AVAILABLE
            + " TEXT, " + COLUMN_FAVOURITE
            + " INTEGER );";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEER);
        onCreate(db);
    }

    //insert beer data into table
    public void createBeer(Beer bee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, String.valueOf(bee.getId()));
        values.put(COLUMN_NAME, String.valueOf(bee.getName()));
        values.put(COLUMN_DESC, bee.getDescription());
        values.put(COLUMN_ABV, Double.valueOf(bee.getAbv()));
        values.put(COLUMN_GLASS, Integer.valueOf(bee.getGlasswareld()));
        values.put(COLUMN_AVAILABLED, Integer.valueOf(bee.getAvailabled()));
        values.put(COLUMN_STYLED, Integer.valueOf(bee.getStyled()));
        values.put(COLUMN_ORGANIC, String.valueOf(bee.getIsOrganic()));

        values.put(COLUMN_ICON_PATH, String.valueOf(bee.getIconPath()));
        values.put(COLUMN_MEDIUM_PATH, String.valueOf(bee.getMediumPath()));
        values.put(COLUMN_STATUS, String.valueOf(bee.getStatus()));
        values.put(COLUMN_CREATE_DATE, String.valueOf(bee.getCreateDate()));
        values.put(COLUMN_UPDATE_DATE, String.valueOf(bee.getUpdateDate()));
        values.put(COLUMN_AVAILABLE, String.valueOf(bee.getAvailableDesc()));

        // insert row
        db.insert(TABLE_BEER, null, values);

    }
    /**
     * Getting all data from a specif beer (by ID)
     */
    public Beer getBeer(int beer_id)  {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_BEER + " WHERE "
                + COLUMN_ID + " = " + beer_id;
        //Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Beer bee = new Beer();
        bee.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
        Log.i("anme", bee.getName());
        bee.setDescription(c.getString(c.getColumnIndex(COLUMN_DESC)));
        bee.setAbv((c.getDouble(c.getColumnIndex(COLUMN_ABV))));
        bee.setIBU((c.getInt(c.getColumnIndex(COLUMN_IBU))));
        bee.setGlasswareld((c.getInt(c.getColumnIndex(COLUMN_GLASS))));
        bee.setAvailabled((c.getInt(c.getColumnIndex(COLUMN_AVAILABLED))));
        bee.setStyled(c.getInt(c.getColumnIndex(COLUMN_STYLED)));
        bee.setIsOrganic(c.getString(c.getColumnIndex(COLUMN_ORGANIC)));
        bee.setIconPath(c.getString(c.getColumnIndex(COLUMN_ICON_PATH)));
        bee.setMediumPath(c.getString(c.getColumnIndex(COLUMN_MEDIUM_PATH)));
        bee.setStatus(c.getString(c.getColumnIndex(COLUMN_STATUS)));
        bee.setCreateDate(c.getString(c.getColumnIndex(COLUMN_CREATE_DATE)));
        bee.setUpdateDate(c.getString(c.getColumnIndex(COLUMN_UPDATE_DATE)));
        bee.setAvailableDesc(c.getString(c.getColumnIndex(COLUMN_AVAILABLE)));
        bee.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
        c.close();
        return bee;
    }
    /*
    * printing all tables for logging purpose only*/
    public String getTableAsString(SQLiteDatabase db, String tableName) {
        db = this.getReadableDatabase();
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst()) {
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name : columnNames) {
                    tableString += String.format("%s: %s\n", name, allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";
            } while (allRows.moveToNext());
        }
        allRows.close();
        return tableString;
    }
    //get number of beers saved
    public int getTableRows(SQLiteDatabase db, String tableName) {
        db = this.getReadableDatabase();
        Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null);
        int cnt = allRows.getCount();
        allRows.close();
        return cnt;
    }

    //getting all beers to populate the list
    public ArrayList<Beer> getAllBeers() {
        ArrayList<Beer> beers = new ArrayList<Beer>();
        String selectQuery = "SELECT  * FROM " + TABLE_BEER;
        // Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Beer bee = new Beer();
                bee.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
                bee.setDescription(c.getString(c.getColumnIndex(COLUMN_DESC)));
                bee.setAbv((c.getDouble(c.getColumnIndex(COLUMN_ABV))));
                bee.setIBU((c.getInt(c.getColumnIndex(COLUMN_IBU))));
                bee.setGlasswareld((c.getInt(c.getColumnIndex(COLUMN_GLASS))));
                bee.setAvailabled((c.getInt(c.getColumnIndex(COLUMN_AVAILABLED))));
                bee.setStyled(c.getInt(c.getColumnIndex(COLUMN_STYLED)));
                bee.setIsOrganic(c.getString(c.getColumnIndex(COLUMN_ORGANIC)));
                bee.setIconPath(c.getString(c.getColumnIndex(COLUMN_ICON_PATH)));
                bee.setMediumPath(c.getString(c.getColumnIndex(COLUMN_MEDIUM_PATH)));
                bee.setStatus(c.getString(c.getColumnIndex(COLUMN_STATUS)));
                bee.setCreateDate(c.getString(c.getColumnIndex(COLUMN_CREATE_DATE)));
                bee.setUpdateDate(c.getString(c.getColumnIndex(COLUMN_UPDATE_DATE)));
                bee.setAvailableDesc(c.getString(c.getColumnIndex(COLUMN_AVAILABLE)));
                bee.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                beers.add(bee);
            } while (c.moveToNext());
        }
        return beers;
    }
    //check if beer is favourite(by id)
    public int checkFavourite(int beer_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_BEER + " WHERE " + COLUMN_ID + " = " + beer_id;
        Log.i("database", selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        int fav=0;
        if (c.moveToFirst()) {
            do {
                fav = c.getInt(c.getColumnIndex(COLUMN_FAVOURITE));
            } while (c.moveToNext());
        }
        Log.i("database", String.valueOf(fav));
        if (fav==1){
            return 0;
        }else{
            return 1;
        }
    }
    //set  favourite value passed from activity
    public int setAsFavourite(int beer_id, int value) {
        SQLiteDatabase db = this.getReadableDatabase();
        String applyQuery = "UPDATE " + TABLE_BEER + " SET " + COLUMN_FAVOURITE+ " = " +value+ " WHERE " + COLUMN_ID + " = " + beer_id;
        db.execSQL(applyQuery);
        return 1;
    }
    //get a list of beers which are favourite( fav = 1)
    public ArrayList<Beer> getFavourites() {
        ArrayList<Beer> beers = new ArrayList<Beer>();
        String selectQuery = "SELECT * " + " FROM " + TABLE_BEER + " WHERE "
                + COLUMN_FAVOURITE + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Beer bee = new Beer();
                bee.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
                bee.setDescription(c.getString(c.getColumnIndex(COLUMN_DESC)));
                bee.setAbv((c.getDouble(c.getColumnIndex(COLUMN_ABV))));
                bee.setIBU((c.getInt(c.getColumnIndex(COLUMN_IBU))));
                bee.setGlasswareld((c.getInt(c.getColumnIndex(COLUMN_GLASS))));
                bee.setAvailabled((c.getInt(c.getColumnIndex(COLUMN_AVAILABLED))));
                bee.setStyled(c.getInt(c.getColumnIndex(COLUMN_STYLED)));
                bee.setIsOrganic(c.getString(c.getColumnIndex(COLUMN_ORGANIC)));
                bee.setIconPath(c.getString(c.getColumnIndex(COLUMN_ICON_PATH)));
                bee.setMediumPath(c.getString(c.getColumnIndex(COLUMN_MEDIUM_PATH)));
                bee.setStatus(c.getString(c.getColumnIndex(COLUMN_STATUS)));
                bee.setCreateDate(c.getString(c.getColumnIndex(COLUMN_CREATE_DATE)));
                bee.setUpdateDate(c.getString(c.getColumnIndex(COLUMN_UPDATE_DATE)));
                bee.setAvailableDesc(c.getString(c.getColumnIndex(COLUMN_AVAILABLE)));
                bee.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                beers.add(bee);
            } while (c.moveToNext());
        }
        c.close();
        return beers;
    }
}