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

import java.text.DateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public abstract class DisplayClaimActivity extends Activity {
	//Base class which defines basic behavior of all the claim display screens.
	//onCreate method grabs the views which display all the info for the claim and sets them
	//to show the info for the claim currently being displayed based on the index passed via
	//the intent. On start method instructs all the info to update since when the user
	//navigates away from this activity it generally means they are changing some aspect of
	//the claim info or the expense list.
	
	protected int index;
	protected TravelClaim claim;
	protected ArrayAdapter<Expense> expenseAdapter;
	protected ListView expenseList;
	protected TextView claimNameText;
	protected TextView startDateText;
	protected TextView endDateText;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		claimNameText = (TextView)findViewById(R.id.claimName);
		startDateText = (TextView)findViewById(R.id.startDate);
		endDateText = (TextView)findViewById(R.id.endDate);
		ClaimsListManager.initManager(this.getApplicationContext());
		index=getIntent().getIntExtra("index",0);
		claim=ClaimsListController.getClaim(index);
		expenseList=(ListView) findViewById(R.id.expensesList);
		expenseAdapter = new ArrayAdapter<Expense>(this,
				R.layout.list_item, claim.getExpenses());
		expenseList.setAdapter(expenseAdapter);
	}
	
	protected void onStart(){
		super.onStart();
		claim=ClaimsListController.getClaim(index);
		claimNameText.setText(claim.getDescription());
		DateFormat formatter=DateFormat.getDateInstance();
		startDateText.setText(formatter.format(claim.getStartDate().getTime()));
		endDateText.setText(formatter.format(claim.getEndDate().getTime()));
		expenseAdapter.notifyDataSetChanged();
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
