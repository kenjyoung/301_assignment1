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

import java.util.GregorianCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class EditClaimActivity extends Activity {
	//This activity allows the user to edit the basic info (date range and description) of a
	//clicked claim, or a newly created claim. On create initializes all the displayed info
	//to be equal to the info currently contained in the claim located at the index passed
	//via the intent.
	private int index;
	DatePicker startDP;
	DatePicker endDP;
	EditText descriptionText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_claim);
		ClaimsListManager.initManager(this.getApplicationContext());
		index=getIntent().getIntExtra("index",0);
		startDP=(DatePicker) findViewById(R.id.startDate);
		endDP=(DatePicker) findViewById(R.id.endDate);
		descriptionText=(EditText) findViewById(R.id.claimDescriptionBody);
		TravelClaim claim=ClaimsListController.getClaim(index);
		GregorianCalendar startDate=claim.getStartDate();
		GregorianCalendar endDate=claim.getEndDate();
		String description=claim.getDescription();
		startDP.updateDate(startDate.get(GregorianCalendar.YEAR),startDate.get(GregorianCalendar.MONTH),
				startDate.get(GregorianCalendar.DAY_OF_MONTH));
		endDP.updateDate(endDate.get(GregorianCalendar.YEAR),endDate.get(GregorianCalendar.MONTH),
				endDate.get(GregorianCalendar.DAY_OF_MONTH));
		descriptionText.setText(description);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_claim, menu);
		return true;
	}

	public void onPause(){
		super.onPause();
		saveClaim();
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
	
	public void saveClaim(){
		
		GregorianCalendar startDate=new GregorianCalendar(startDP.getYear(),startDP.getMonth(),
				startDP.getDayOfMonth());
		GregorianCalendar endDate=new GregorianCalendar(endDP.getYear(),endDP.getMonth(),
				endDP.getDayOfMonth());
		if(startDate.compareTo(endDate)>0){
			Toast.makeText(this, "End date is before start date, date not updated."
					,Toast.LENGTH_SHORT).show();
			startDate=ClaimsListController.getClaim(index).getStartDate();
			endDate=ClaimsListController.getClaim(index).getEndDate();
			
		}
		String description=descriptionText.getText().toString();
		
		//update the claim and modify index to reflect its new location
		index=ClaimsListController.updateClaim(index, startDate, endDate, description);
		ClaimsListController.saveClaims();
	}
	
	public void saveClick(View view){
		saveClaim();
		finish();
	}
}
