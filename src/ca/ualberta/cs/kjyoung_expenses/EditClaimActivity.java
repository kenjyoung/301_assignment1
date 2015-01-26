package ca.ualberta.cs.kjyoung_expenses;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class EditClaimActivity extends Activity {
	private Integer index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_claim);
		ClaimsListManager.initManager(this.getApplicationContext());
		index=getIntent().getIntExtra("index",0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_claim, menu);
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
	
	public void saveClaim(View view){
		DatePicker startDP=(DatePicker) findViewById(R.id.startDate);
		DatePicker endDP=(DatePicker) findViewById(R.id.endDate);
		EditText descriptionText=(EditText) findViewById(R.id.claimDescriptionBody);
		Date startDate=new Date(startDP.getYear()-1900,startDP.getMonth(),
				startDP.getDayOfMonth());
		Date endDate=new Date(endDP.getYear()-1900,endDP.getMonth(),
				endDP.getDayOfMonth());
		String description=descriptionText.getText().toString();
		
		TravelClaim claim=new TravelClaim(startDate,endDate,description);
		ClaimsListController.updateClaim(index, claim);
		finish();
	}
}
