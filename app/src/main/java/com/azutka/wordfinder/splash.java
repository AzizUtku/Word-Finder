package com.azutka.wordfinder;


import com.searchboxsdk.android.StartAppSearch;
import com.startapp.android.publish.StartAppAd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class splash extends Activity{
	private StartAppAd startAppAd = new StartAppAd(this);
	public ArrayList<String> liste1=new ArrayList<String>();
	final static public List<String> Listekelimeler = new ArrayList();
	String Harfduzenle;
	int harficeriyormu1;
	int harfne;
	ListView list1;
	double surum = 1.0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		//StartAppSearch.init(this);


		final TextView lblKac = (TextView)findViewById(R.id.textView2);
		final ListView listBox1 = (ListView)findViewById(R.id.listView1);
		list1=(ListView)findViewById(R.id.listView1);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_white_text,R.id.list_content,liste1);
		list1.setAdapter(adapter);
		final TextView lblHarf = (TextView)findViewById(R.id.textView4);
		final EditText textBox1 = (EditText)findViewById(R.id.editText1);
		// textBox1.setText(textBox1.getText().toString().toLowerCase());

		final Button btnBul = (Button)findViewById(R.id.button1);



		final RelativeLayout mainLayout;
		mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

		btnBul.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub



				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
				liste1.clear();
				kackelime=0;

			/*	for(int k = 0 ; k < Kelimeler1.Listekelimeler.size() ; k++)
				{
					Harfduzenle = textBox1.getText().toString().toLowerCase();

					String kelime = Kelimeler1.Listekelimeler.get(k);
					int kelimeuzunlugu = Kelimeler1.Listekelimeler.get(k).length();
					int txtharfsayisi = Harfduzenle.length();
			        KelimeBul(kelime, txtharfsayisi, kelimeuzunlugu,adapter);

				}*/



				try {
					Cursor cursor = levelgetir();
					while (cursor.moveToNext()) {

						Harfduzenle = textBox1.getText().toString().toLowerCase();

						String kelime = cursor.getString((cursor.getColumnIndex("kelime")));
						int kelimeuzunlugu = cursor.getString((cursor.getColumnIndex("kelime"))).length();
						int txtharfsayisi = Harfduzenle.length();
						KelimeBul(kelime, txtharfsayisi, kelimeuzunlugu,adapter);

					}




				} finally {
					MainActivity.levellerveritabani.close();

				}


				lblKac.setText(Integer.toString(kackelime) + " Word Found");

			}
		});


		textBox1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

				if(textBox1.length()>50){
					Context context5 = getApplicationContext();
					CharSequence text5 = "You can enter up to 50 characters!";
					int duration5 = Toast.LENGTH_LONG;

					Toast toast5 = Toast.makeText(context5, text5, duration5);
					toast5.show();
					textBox1.setText(textBox1.getText().toString().substring(0,50));
				}
				lblHarf.setText("( " + Integer.toString(textBox1.length()) + " Letter )" );

			}
		});




	}

	@Override
	public void onResume(){
		super.onResume();
		startAppAd.onResume();
	}

	@Override
	public void onBackPressed() {
		startAppAd.onBackPressed();
		super.onBackPressed();
	}

	@Override
	public void onPause() {
		super.onPause();
		startAppAd.onPause();
	}
	private String[] SELECT = {"kelime"};



	private Cursor levelgetir() {
		SQLiteDatabase db = MainActivity.levellerveritabani.getReadableDatabase();
		Cursor cursor = db.query("kelimeler", SELECT, null, null, null, null,
				null);
		startManagingCursor(cursor);
		return cursor;
	}
	private void levelkayityukle(Cursor cursor) {

		while (cursor.moveToNext()) {
			Listekelimeler.add(cursor.getString((cursor.getColumnIndex("kelime"))));
		}
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





	@Override
	public boolean onCreateOptionsMenu (Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater menutanim = getMenuInflater();
		menutanim.inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){

			case R.id.menuHakkinda:
				Context context = getApplicationContext();
				CharSequence text = "Azutka | Aziz Utku Ka��tc� v1.0 2013\nYou can generate meaningful words from mixed letters with Azutka Word Finder\nazutkakelimebulucu.blogspot.com | azutkawordfinder@hotmail.com";
				int duration = Toast.LENGTH_LONG;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				return true;
		}
		return false;
	}

	public int kackelime=0;

	private void KelimeBul (String kelime, int txtharfsayisi, int kelimeuzunlugu,ListAdapter adapter) {


		list1.setAdapter(adapter);
		if (kelimeuzunlugu ==  3 && txtharfsayisi >= 3)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);


				}


			}

			if (harficeriyormu1 >= 3)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {

				liste1.add(Integer.toString(3) + " " + kelime);
				kackelime++;
				// }
			}
		}




		else if (kelimeuzunlugu ==  4 && txtharfsayisi >=  4)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);

				}


			}

			if (harficeriyormu1 >= 4)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {

				liste1.add(Integer.toString(4) + " " + kelime);
				kackelime++;
				// }
			}
		}





		else if (kelimeuzunlugu ==  5 && txtharfsayisi >= 5)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);

				}


			}

			if (harficeriyormu1 >= 5)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {

				liste1.add(Integer.toString(5) + " " + kelime);
				kackelime++;
				// }
			}
		}




		else if (kelimeuzunlugu ==  6 && txtharfsayisi >= 6)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);

				}


			}

			if (harficeriyormu1 >= 6)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {

				liste1.add(Integer.toString(6) + " " + kelime);
				kackelime++;
				// }
			}
		}





		else if (kelimeuzunlugu ==  7 && txtharfsayisi >= 7)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);

				}


			}

			if (harficeriyormu1 >= 7)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {
				liste1.add(Integer.toString(7) + " " + kelime);
				kackelime++;
				// }
			}
		}




		else if (kelimeuzunlugu ==  8 && txtharfsayisi >= 8)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);

				}


			}

			if (harficeriyormu1 >= 8)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {
				liste1.add(Integer.toString(8) + " " + kelime);
				kackelime++;
				// }
			}
		}





		else if (kelimeuzunlugu ==  9 && txtharfsayisi >= 9)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);

				}


			}

			if (harficeriyormu1 >= 9)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {
				liste1.add(Integer.toString(9) + " " + kelime);
				kackelime++;
				// }
			}
		}




		if (kelimeuzunlugu ==  10 && txtharfsayisi >= 10)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);

				}


			}

			if (harficeriyormu1 >= 10)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {
				liste1.add(Integer.toString(10) + " " + kelime);
				kackelime++;
				// }
			}
		}





		if (kelimeuzunlugu ==  11 && txtharfsayisi >= 11)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);

				}


			}

			if (harficeriyormu1 >= 11)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {
				liste1.add(Integer.toString(11) + " " + kelime);
				kackelime++;
				// }
			}
		}




		if (kelimeuzunlugu ==  12 && txtharfsayisi >= 12)
		{
			harficeriyormu1 = 0;

			for (int i = 0; i < kelimeuzunlugu; i++)
			{
				harfne = Harfduzenle.indexOf(kelime.substring(i, i+1));
				if (Harfduzenle.indexOf(kelime.substring(i,i+1)) != -1)
				{
					harficeriyormu1++;
					//Harfduzenle.substring(harfne, harfne + 1);
					Harfduzenle = Harfduzenle.substring(0, harfne) + Harfduzenle.substring(harfne + 1);

				}


			}

			if (harficeriyormu1 >= 12)
			{
				//  if (Properties.Settings.Default.�retilecekKelimeler.IndexOf("(i)") != -1)
				//    {
				liste1.add(Integer.toString(12) + " " + kelime);
				kackelime++;
				// }
			}
		}

		list1.setAdapter(adapter);
		//  list1.refreshDrawableState();






	}
}
