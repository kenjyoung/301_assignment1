package ca.ualberta.cs.kjyoung_expenses;

import java.text.DateFormat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class DisplayClaimInProgressActivity extends DisplayClaimActivity {
	//One of three activities that displays the info for a claim and its expenses.
	//Inherits from the class DisplayClaimActivity.
	//This one is for an in progress/returned claim so it allows editing of the claim name and dates
	//via an edit button, as well as addition and removal of expenses via two buttons, and editing
	//of individual expenses simply by clicking on them. In addition it provides a send button to
	//send the claim info and expenses as an email via an appropriate app, along with a submit
	//button which sets the status to submitted.
	
	private boolean deleteMode=false;
	private Drawable buttonDefault;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_display_claim_in_progress);
		super.onCreate(savedInstanceState);
		
		expenseList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int expenseIndex,
					long id){
						 if(deleteMode){
							 claim.removeExpense(expenseIndex);
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
		claim=ClaimsListController.getClaim(index);
		claimNameText.setText(claim.getDescription());
		DateFormat formatter=DateFormat.getDateInstance();
		startDateText.setText(formatter.format(claim.getStartDate().getTime()));
		endDateText.setText(formatter.format(claim.getEndDate().getTime()));
		expenseAdapter.notifyDataSetChanged();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expenses_list, menu);
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
