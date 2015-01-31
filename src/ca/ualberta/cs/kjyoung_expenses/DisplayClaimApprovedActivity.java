package ca.ualberta.cs.kjyoung_expenses;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DisplayClaimApprovedActivity extends DisplayClaimActivity {
	//One of three activities that displays the info for a claim and its expenses.
	//Inherits from the class DisplayClaimActivity..
	//This one is for an approved activity and hence it is read only, all editing capabilities
	//are removed and the only action available is to send the claim's info via email
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_display_claim_approved);
		super.onCreate(savedInstanceState);
	}
	
	public void sendClick(View view){
		ClaimsListController.sendClaim(index);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_claim_accepted, menu);
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
