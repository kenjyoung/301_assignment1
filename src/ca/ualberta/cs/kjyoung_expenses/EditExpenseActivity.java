package ca.ualberta.cs.kjyoung_expenses;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class EditExpenseActivity extends Activity {
	private Expense expense;
	private int claimIndex;
	private int expenseIndex;
	private DatePicker datePicker;
	private Spinner currencySpinner;
	private Spinner categorySpinner;
	private EditText descriptionText;
	private EditText amountText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_expense);
		ClaimsListManager.initManager(this.getApplicationContext());
		claimIndex=getIntent().getIntExtra("claimIndex",0);
		expenseIndex=getIntent().getIntExtra("expenseIndex",0);
		datePicker=(DatePicker) findViewById(R.id.expenseDate);
		currencySpinner=(Spinner) findViewById(R.id.currencySelector);
		categorySpinner=(Spinner) findViewById(R.id.categorySelector);
		descriptionText=(EditText) findViewById(R.id.descriptionBody);
		amountText=(EditText) findViewById(R.id.currencyBody);
		ArrayAdapter<String> currencyAdapter=new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, 
				Expense.getCurrencies());
		currencySpinner.setAdapter(currencyAdapter);
		ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, 
				Expense.getCategories());
		categorySpinner.setAdapter(categoryAdapter);
		
		expense=ClaimsListController.getClaims().get(claimIndex).getExpenses().get(expenseIndex);
		GregorianCalendar date=expense.getDate();
		String description=expense.getDescription();
		String category=expense.getCategory();
		String currency=expense.getCurrency();
		String amount=expense.getAmount().toString();
		
		
		datePicker.updateDate(date.get(GregorianCalendar.YEAR),date.get(GregorianCalendar.MONTH),
				date.get(GregorianCalendar.DAY_OF_MONTH));
		currencySpinner.setSelection(currencyAdapter.getPosition(currency));
		categorySpinner.setSelection(categoryAdapter.getPosition(category));
		descriptionText.setText(description);
		amountText.setText(amount);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_edit, menu);
		return true;
	}

	public void saveExpense(){
		GregorianCalendar date=new GregorianCalendar(datePicker.getYear(),datePicker.getMonth(),
				datePicker.getDayOfMonth());
		String description=descriptionText.getText().toString();
		String category=(String) categorySpinner.getSelectedItem();
		String currency=(String) currencySpinner.getSelectedItem();
		BigDecimal amount=new BigDecimal(amountText.getText().toString());
		
		Expense expense=new Expense(date, category, description, 
			amount, currency);
		TravelClaim claim=ClaimsListController.getClaims().get(claimIndex);
		claim.updateExpense(expenseIndex,expense);
		ClaimsListController.saveClaims();
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
	
	public void saveClick(View view){
		saveExpense();
	}
}
