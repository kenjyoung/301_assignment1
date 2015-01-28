package ca.ualberta.cs.kjyoung_expenses;

import java.util.GregorianCalendar;
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
		TravelClaim claim=ClaimsListController.getClaims().get(index);
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
		GregorianCalendar startDate=new GregorianCalendar(startDP.getYear(),startDP.getMonth(),
				startDP.getDayOfMonth());
		GregorianCalendar endDate=new GregorianCalendar(endDP.getYear(),endDP.getMonth(),
				endDP.getDayOfMonth());
		String description=descriptionText.getText().toString();
		
		TravelClaim claim=new TravelClaim(startDate,endDate,description);
		ClaimsListController.updateClaim(index, claim);
		ClaimsListController.saveClaims();
		finish();
	}
}
