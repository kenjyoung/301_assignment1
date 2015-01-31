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

import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	//This is the main activity that opens when the application is started. It loads and then
	//Displays the currently saved list of travel claims int the onCreate method. It also
	//sets the onItemClickListener to detect when an item in the list is clicked. The 
	//onItemClick behavior depends first on whether deleteMode is toggled on (in which case
	//the clicked claim is simply deleted), and second on the current status of the clicked
	//claim. It will open the claim in the appropriate activity depending on this status. 
	//The onStart method simply notifies the adapter for the claimsList that the underlying
	//dataset has changed since whenever the user navigates away from this activity within
	//the application it generally results in a modification to the dataset.
	
	private ListView claimsList;
	private ArrayAdapter<TravelClaim> claimAdapter;
	private Boolean deleteMode=false; 
	private Drawable buttonDefault;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ClaimsListManager.initManager(this.getApplicationContext());
		claimsList = (ListView) findViewById(R.id.claimsList);
		claimAdapter = new ArrayAdapter<TravelClaim>(this,
		R.layout.list_item, ClaimsListController.getClaims());
		claimsList.setAdapter(claimAdapter);
		Expense.setCategories(new ArrayList<String>(Arrays.asList(
				getResources().getStringArray(R.array.expense_categories))));
		Expense.setCurrencies(new ArrayList<String>(Arrays.asList(
				getResources().getStringArray(R.array.currencies))));
		
		claimsList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int index,
					long id){
						 if(deleteMode){
							 ClaimsListController.deleteClaim(index);
							 claimAdapter.notifyDataSetChanged();
							 ClaimsListController.saveClaims();
						 }
						 else{
							 TravelClaim claim=ClaimsListController.getClaim(index);
							 int status=claim.getStatus();
							 Intent intent;
							 if(status==1){
								 intent= new Intent(view.getContext(), 
										 DisplayClaimSubmittedActivity.class);
							 }
							 else if(status==3){
								 intent= new Intent(view.getContext(), 
										 DisplayClaimApprovedActivity.class); 
							 }
							 else{
								 intent= new Intent(view.getContext(), 
										 DisplayClaimInProgressActivity.class);
							 }
							 intent.putExtra("index",index);
							 startActivity(intent);
						 }
					}
		});
		
		
		
	}
	
	protected void onStart(){
		super.onStart();
		claimAdapter.notifyDataSetChanged();
	}
	
	public void addClaim(View view){
		int index=ClaimsListController.addClaim(new TravelClaim());
		Intent intent = new Intent(this, EditClaimActivity.class);
		intent.putExtra("index", index);
		startActivity(intent);
	}
	
	public void toggleDelete(View view){
		deleteMode=!deleteMode;
		
		if(deleteMode){
			buttonDefault=((Button)view).getBackground();
			((Button)view).setBackgroundColor(Color.RED);
		}
		else{
			((Button)view).setBackground(buttonDefault);
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