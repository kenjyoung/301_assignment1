package ca.ualberta.cs.kjyoung_expenses;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ExpensesListActivity extends Activity {
	private Integer index;
	private TravelClaim claim;
	private ArrayAdapter<Expense> expenseAdapter;
	private ListView expenseList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expenses_list);
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
		TextView claimNameText = (TextView)findViewById(R.id.claimName);
		TextView startDateText = (TextView)findViewById(R.id.startDate);
		TextView endDateText = (TextView)findViewById(R.id.endDate);
		claimNameText.setText(claim.getDescription());
		startDateText.setText(claim.getStartDate().toString());
		endDateText.setText(claim.getEndDate().toString());
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
		Integer expenseIndex=claim.addExpense(new Expense());
		intent.putExtra("claimIndex", index);
		intent.putExtra("expenseIndex", index);
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
