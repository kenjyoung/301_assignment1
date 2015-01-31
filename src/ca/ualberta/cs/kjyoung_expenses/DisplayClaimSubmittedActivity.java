package ca.ualberta.cs.kjyoung_expenses;

import java.text.DateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayClaimSubmittedActivity extends Activity {
	//One of three activities that displays the info for a claim and its expenses.
	//These might benefit from setting up an inheritance hierarchy but it didn't seem worthwhile
	//for the time being. The onStart method ensures that all the displayed data is up to date
	//whenever this activity is returned to.
	//This one is for a submitted activity so it does not allow any editing but does allow 
	//sending as an email, along with either returning or accepting which sets the status
	//appropriately.
	
	private int index;
	private TravelClaim claim;
	private ArrayAdapter<Expense> expenseAdapter;
	private ListView expenseList;
	private TextView claimNameText;
	private TextView startDateText;
	private TextView endDateText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_claim_submitted);
		claimNameText = (TextView)findViewById(R.id.claimName);
		startDateText = (TextView)findViewById(R.id.startDate);
		endDateText = (TextView)findViewById(R.id.endDate);
		ClaimsListManager.initManager(this.getApplicationContext());
		index=getIntent().getIntExtra("index",0);
		claim=ClaimsListController.getClaims().get(index);
		expenseList=(ListView) findViewById(R.id.expensesList);
		expenseAdapter = new ArrayAdapter<Expense>(this,
				R.layout.list_item, claim.getExpenses());
		expenseList.setAdapter(expenseAdapter);
	}
	
	protected void onStart(){
		super.onStart();
		claim=ClaimsListController.getClaims().get(index);
		claimNameText.setText(claim.getDescription());
		DateFormat formatter=DateFormat.getDateInstance();
		startDateText.setText(formatter.format(claim.getStartDate().getTime()));
		endDateText.setText(formatter.format(claim.getEndDate().getTime()));
		expenseAdapter.notifyDataSetChanged();
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_accept_return, menu);
		return true;
	}
	
	public void sendClick(View view){
		ClaimsListController.sendClaim(index);
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
