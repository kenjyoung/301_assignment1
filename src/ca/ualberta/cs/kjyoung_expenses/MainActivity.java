/*Expense Trackers: Keeps track of travel expense claims
    Copyright (C) 2014  Kenny Young kjyoung@ualberta.ca

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>
    */
package ca.ualberta.cs.kjyoung_expenses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ArrayList <TravelClaim> claims;
	private static final String FILENAME = "file.sav";
	private ListView claimsList;
	private ArrayAdapter<TravelClaim> claimAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		claimsList = (ListView) findViewById(R.id.claimsList);
	}
	
	protected void onStart() {
		super.onStart();
		
		//Taken from https://github.com/joshua2ua/lonelyTwitter on Jan 24 2015 and modified
		claims = loadFromFile();
		if(claims==null){
			claims = new ArrayList<TravelClaim>();
		}
		claimAdapter = new ArrayAdapter<TravelClaim>(this,
				R.layout.claim_list_item, claims);
		claimsList.setAdapter(claimAdapter);
	}
	
	public void addClaim(View view){
		Intent intent = new Intent(this, EditClaimActivity.class);
		startActivity(intent);	
	}
	
	
	//Taken from https://github.com/joshua2ua/lonelyTwitter on Jan 24 2015 and modified
	private ArrayList<TravelClaim> loadFromFile() {
		Gson gson = new Gson();
		ArrayList<TravelClaim> claims = new ArrayList<TravelClaim>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader in =new InputStreamReader(fis);
			//Taken from http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/index.html on Jan 20 2015
			Type typeOfT = new TypeToken<ArrayList<TravelClaim>>(){}.getType();
			claims = gson.fromJson(in, typeOfT);
			fis.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return claims;
	}
	
	//Taken from https://github.com/joshua2ua/lonelyTwitter on Jan 24 2015 and modified
	public void saveToFile() {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = openFileOutput(FILENAME, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claims, osw);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}