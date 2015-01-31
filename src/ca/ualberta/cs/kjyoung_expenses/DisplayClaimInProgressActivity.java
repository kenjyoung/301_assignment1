package ca.ualberta.cs.kjyoung_expenses;

import java.text.DateFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class DisplayClaimInProgressActivity extends Activity {
	//One of three activities that displays the info for a claim and its expenses.
	//These might benefit from setting up an inheritance hierarchy but it didn't seem worthwhile
	//for the time being. The onStart method ensures that all the displayed data is up to date
	//whenever this activity is returned to.
	//This one is for an in progress/returned claim so it allows editing of the claim name and dates
	//via an edit button, as well as addition and removal of expenses via two buttons, and editing
	//of individual expenses simply by clicking on them. In addition it provides a send button to
	//send the claim info and expenses as an email via an appropriate app, along with a submit
	//button which sets the status to submitted.
	
	private int index;
	private TravelClaim claim;
	private ArrayAdapter<Expense> expenseAdapter;
	private ListView expenseList;
	private TextView claimNameText;
	private TextView startDateText;
	private TextView endDateText;
	private boolean deleteMode=false;
	private Drawable buttonDefault;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_claim_in_progress);
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
		
		expenseList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int expenseIndex,
					long id){
						 if(deleteMode){
							 claim.getExpenses().remove(expenseIndex);
							 expenseAdapter.notifyDataSetChanged();
							 ClaimsListController.saveClaims();
						 }
						 else{
							 Intent intent= new Intent(view.getContext(), EditExpenseActivity.class);
							 intent.putExtra("claimIndex", index);
							 intent.putExtra("expenseIndex", expenseIndex);
							 startActivity(intent);
						 }
					}
		});
		
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
		getMenuInflater().inflate(R.menu.expenses_list, menu);
		return true;
	}
	
	public void editClaim(View view){
		 Intent intent= new Intent(this, EditClaimActivity.class);
		 intent.putExtra("index",index);
		 startActivity(intent);
	}
	
	public void addExpense(View view){
		Intent intent= new Intent(this, EditExpenseActivity.class);
		int expenseIndex=claim.addExpense(new Expense());
		intent.putExtra("claimIndex", index);
		intent.putExtra("expenseIndex", expenseIndex);
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
	
	public void sendClick(View view){
		ClaimsListController.sendClaim(index);
	}
	
	public void submitClick(View view){
		claim.setStatus((byte) 1);
		ClaimsListController.saveClaims();
		Toast.makeText(this, "Claim submitted",
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
