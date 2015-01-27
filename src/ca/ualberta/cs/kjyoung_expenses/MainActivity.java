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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView claimsList;
	private ArrayAdapter<TravelClaim> claimAdapter;
	private Boolean deleteMode=false; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ClaimsListManager.initManager(this.getApplicationContext());
		claimsList = (ListView) findViewById(R.id.claimsList);
		claimAdapter = new ArrayAdapter<TravelClaim>(this,
		R.layout.claim_list_item, ClaimsListController.getClaims());
		claimsList.setAdapter(claimAdapter);
		
		claimsList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int index,
					long id){
						 if(deleteMode){
							 //if deleteMode is on delete teh clicked claim
						 }
						 else{
							 Intent intent= new Intent(view.getContext(), EditClaimActivity.class);
							 intent.putExtra("index",index);
							 startActivity(intent);
							 claimAdapter.notifyDataSetChanged();
							 ClaimsListController.saveClaims();
						 }
					}
		});
		
	}
	
	protected void onStart(){
		super.onStart();
		claimAdapter.notifyDataSetChanged();
	}
	
	public void addClaim(View view){
		Integer index=ClaimsListController.addClaim(new TravelClaim());
		Intent intent = new Intent(this, EditClaimActivity.class);
		intent.putExtra("index", index);
		startActivity(intent);
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