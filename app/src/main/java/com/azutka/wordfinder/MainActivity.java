package com.azutka.wordfinder;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static DataBaseHelper levellerveritabani;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		levellerveritabani = new DataBaseHelper(this);
	
  

		
		Thread acilisekrani = new Thread() {
		
			public void run() {
				try {
					SQLiteDatabase database;
				   	try {

						levellerveritabani.createDataBase();

					}

					catch (IOException ioe) {

						throw new Error("DB is not created");

					}

					try

					{

						levellerveritabani.openDataBase();

					} catch (SQLException sqle) {

						throw new Error("DB is not opened");

					}
					
					database = levellerveritabani.getWritableDatabase();
					 sleep(2000);
					startActivity(new Intent("com.azutka.wordfinder.SPLASH"));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		acilisekrani.start();
	}
	
 	private boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
		&& cm.getActiveNetworkInfo().isAvailable()
		&& cm.getActiveNetworkInfo().isConnected()) {
		return true;
		}
		else {
		Log.v("Internet", "Internet Connection Not Present");
		return false;
		} }
	
}
