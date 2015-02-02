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


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class DisplayClaimSubmittedActivity extends DisplayClaimActivity {
	//One of three activities that displays the info for a claim and its expenses.
	//Inherits from the class DisplayClaimActivity.
	//This one is for a submitted activity so it does not allow any editing but does allow 
	//sending as an email, along with either returning or accepting which sets the status
	//appropriately.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_display_claim_submitted);
		super.onCreate(savedInstanceState);
	}
	
	public void acceptClick(View view){
		claim.setStatus((byte) 3);
		ClaimsListController.saveClaims();
		Toast.makeText(this, "Claim approved",
	    		Toast.LENGTH_SHORT).show();
		finish();
	}
	
	public void returnClick(View view){
		claim.setStatus((byte) 2);
		ClaimsListController.saveClaims();
		Toast.makeText(this, "Claim returned",
	    		Toast.LENGTH_SHORT).show();
		finish();
	}

	public void sendClick(View view){
		ClaimsListController.sendClaim(index);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_accept_return, menu);
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
