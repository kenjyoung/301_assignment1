package ca.ualberta.cs.kjyoung_expenses;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ExpensesListActivity extends Activity {
	private Integer index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expenses_list);
		index=getIntent().getIntExtra("index",0);
		TravelClaim claim=ClaimsListController.getClaims().get(index);
		GregorianCalendar startDate=claim.getStartDate();
		GregorianCalendar endDate=claim.getEndDate();
		String description=claim.getDescription();
		ClaimsListManager.initManager(this.getApplicationContext());
		TextView claimName= (TextView)findViewById(R.id.claimName);
		claimName.setText(claim.getDescription());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expenses_list, menu);
		return true;
	}
	
	public void editClaim(View view){
		 Intent intent= new Intent(this, EditClaimActivity.class);
		 intent.putExtra("index",index);
		 startActivity(intent);
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
