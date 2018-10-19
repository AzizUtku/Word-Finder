package com.azutka.wordfinder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private final Context myContext;
	static final String DB_ADI = "dbEN.au9db";
	static final int DB_VERSION = 1;
	private static String DB_YOLU = "/data/data/com.azutka.wordfinder/databases/";

	private SQLiteDatabase myDataBase;

	public DataBaseHelper(Context context) {

		super(context, DB_ADI, null, 1);
		this.myContext = context;
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {

		} else {

			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("DB is not copied");

			}
		}

	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_YOLU + DB_ADI;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.NO_LOCALIZED_COLLATORS);

		} catch (SQLiteException e) {

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {

		InputStream myGirdi = myContext.getAssets().open(DB_ADI);

		String ciktiIsmi = DB_YOLU + DB_ADI;

		OutputStream myCikti = new FileOutputStream(ciktiIsmi);

		byte[] buffer = new byte[1024];
		int uzunluk;
		while ((uzunluk = myGirdi.read(buffer)) > 0) {
			myCikti.write(buffer, 0, uzunluk);
			Log.d("DBHELPER", "1024 byte Kopyalandi");
		}

		myCikti.flush();
		myCikti.close();
		myGirdi.close();

	}

	public void openDataBase() throws SQLException {

		String myYol = DB_YOLU + DB_ADI;
		myDataBase = SQLiteDatabase.openDatabase(myYol, null,
				SQLiteDatabase.NO_LOCALIZED_COLLATORS);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int eskiversiyon, int yeniversiyon) {

	}

}

