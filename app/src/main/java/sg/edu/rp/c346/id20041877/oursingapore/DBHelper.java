package sg.edu.rp.c346.id20041877.oursingapore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "singaporeisland.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_ISLAND = "Island";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_DESCRIPTION = "description";
	private static final String COLUMN_SQUAREKM = "squarekm";
	private static final String COLUMN_STARS = "stars";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String createIslandTableSql = "CREATE TABLE " + TABLE_ISLAND + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_NAME + " TEXT, "
				+ COLUMN_DESCRIPTION + " TEXT, "
				+ COLUMN_SQUAREKM + " INTEGER, "
				+ COLUMN_STARS + " INTEGER )";
		db.execSQL(createIslandTableSql);
		Log.i("info", createIslandTableSql + "\ncreated tables");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISLAND);
		onCreate(db);
	}

	public long insertIsland(String name, String description, int squarekm, int stars) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_DESCRIPTION, description);
		values.put(COLUMN_SQUAREKM, squarekm);
		values.put(COLUMN_STARS, stars);

		long result = db.insert(TABLE_ISLAND, null, values);

		db.close();
        Log.d("SQL Insert","" + result);
        return result;
	}

	public ArrayList<Island> getAllIslands() {
		ArrayList<Island> islandlist = new ArrayList<Island>();
		String selectQuery = "SELECT " + COLUMN_ID + ","
				+ COLUMN_NAME + "," + COLUMN_DESCRIPTION + ","
				+ COLUMN_SQUAREKM + ","
				+ COLUMN_STARS + " FROM " + TABLE_ISLAND;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String description = cursor.getString(2);
				int squarekm = cursor.getInt(3);
				int stars = cursor.getInt(4);

				Island newIsland = new Island(id, name, description, squarekm, stars);
				islandlist.add(newIsland);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return islandlist;
	}

	/*
	public ArrayList<Island> getAllIslandsByStars(int starsFilter) {
		ArrayList<Island> islandlist = new ArrayList<Island>();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_SQUAREKM, COLUMN_STARS};
		String condition = COLUMN_STARS + ">= ?";
		String[] args = {String.valueOf(starsFilter)};

		Cursor cursor;
		cursor = db.query(TABLE_ISLAND, columns, condition, args, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String description = cursor.getString(2);
				int squarekm = cursor.getInt(3);
				int stars = cursor.getInt(4);

				Island newIsland = new Island(id, name, description, squarekm, stars);
				islandList.add(newIsland);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return islandlist;
	}
	 */

	public int updateSong(Island data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_SQUAREKM, data.getSquareKm());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_ISLAND, values, condition, args);
        db.close();
        return result;
    }

    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ISLAND, condition, args);
        db.close();
        return result;
    }

	public ArrayList<String> getYears() {
		ArrayList<String> codes = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns = {COLUMN_SQUAREKM};

		Cursor cursor;
		cursor = db.query(true, TABLE_ISLAND, columns, null, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				codes.add(cursor.getString(0));
			}
			while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return codes;
	}

	public ArrayList<Island> getAllIslandByStar(int starFilter) {
		ArrayList<Island> islandlist = new ArrayList<Island>();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_SQUAREKM, COLUMN_STARS};
		String condition = COLUMN_SQUAREKM + "= ?";
		String[] args = {String.valueOf(starFilter)};

		Cursor cursor;
		cursor = db.query(TABLE_ISLAND, columns, condition, args, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String description = cursor.getString(2);
				int squarekm = cursor.getInt(3);
				int stars = cursor.getInt(4);

				Island newIsland = new Island(id, name, description, squarekm, stars);
				islandlist.add(newIsland);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return islandlist;
	}
}
