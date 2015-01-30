package ca.ualberta.cs.kjyoung_expenses;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

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
import android.widget.AdapterView.OnItemClickListener;

public class ExpensesListActivity extends Activity {
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
		setContentView(R.layout.activity_expenses_list);
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
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd");
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
