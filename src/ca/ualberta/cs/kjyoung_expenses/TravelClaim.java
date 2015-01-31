package ca.ualberta.cs.kjyoung_expenses;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class TravelClaim implements Comparable<TravelClaim>{
	//This class maintains a list of travel expenses sorted by date called a travel claim. 
	//In addition to the list of expenses it stores basic claim info (date range, status, 
	//description). It also contains several methods for operating on the claim data and 
	//displaying it in various ways.
	
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private String description;
	private Byte status; //this is stored as a numerical value to allow easier computation
	private ArrayList<Expense> expenses;
	
	public ArrayList<Expense> getExpenses() {
		return expenses;
	}
	
	public TravelClaim(GregorianCalendar startDate, GregorianCalendar endDate, String description) {
		super();
		this.startDate=startDate;
		this.endDate=endDate;
		this.description=description;
		this.status=0;
		this.expenses=new ArrayList <Expense>();
	}
	
	public TravelClaim(){
		super();
		this.startDate=new GregorianCalendar();
		this.endDate=new GregorianCalendar();
		this.description="Travel";
		this.status=0;
		this.expenses=new ArrayList <Expense>();
	}
	
	public GregorianCalendar getStartDate() {
		return startDate;
	}
	
	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}
	
	public GregorianCalendar getEndDate() {
		return endDate;
	}
	
	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Byte getStatus() {
		return status;
	}
	
	//return the string representing the interpretation of a specific status
	public String getStatusString(){
		String status;
		switch(this.getStatus()){
		case 0: status="in progress";
				break;
		case 1: status="submitted";
				break;
		case 2: status="returned";
				break;
		case 3: status="approved";
				break;
		default: status="unknown";
				break;
		}
		return status;
	}
	
	public void setStatus(Byte status) {
		this.status = status;
	}
	
	public Expense getExpense(int index){
		return this.getExpenses().get(index);
	}
	
	//convenient way to change basic info when editing claim information
	public void updateInfo(GregorianCalendar startDate, GregorianCalendar endDate, String description){
		this.startDate=startDate;
		this.endDate=endDate;
		this.description=description;
	}
	
	public void updateExpense(int index, GregorianCalendar date, String category, 
			String description, BigDecimal amount, String currency){
		getExpenses().get(index).updateInfo(date, category, description, amount, currency);
		Collections.sort(expenses);
	}
	
	//return a map from each currency used in an expense to the total value of that currency
	//spend over the whole claim
	public HashMap <String, BigDecimal> getTotals(){
		HashMap<String, BigDecimal> totals= new HashMap<String, BigDecimal>();
		Expense expense;
		for(int i=0;i<this.getExpenses().size();i++){
			expense=expenses.get(i);
			String currency=expense.getCurrency();
			BigDecimal amount=expense.getAmount();
			if(totals.containsKey(currency)){
				totals.put(currency,amount.add(totals.get(currency)));
			}
			else{
				totals.put(currency, amount);
			}
		}
		return totals;
	}
	
	//add an expense item and return its index after the expense list is sorted
	public int addExpense(Expense expense){
		expenses.add(expense);
		Collections.sort(expenses);
		return expenses.indexOf(expense);
	}
	
	public void removeExpense(int index){
		expenses.remove(index);
	}
	
	//writes out the description along with currency totals and status on separate lines
	//for convenient listing of claims
	public String toString(){
		HashMap<String, BigDecimal> totals=this.getTotals();
		Set<String> currencies=totals.keySet();
		String str=this.getDescription()+"\ntotals: ";
		Iterator<String> i=currencies.iterator();
		
		if(!i.hasNext()){
			str+="No expenses listed";
		}
		else{
			String currency=i.next();
			BigDecimal amount=totals.get(currency);
			str+=amount.toString()+" "+currency;
		
			while(i.hasNext()){
				currency=i.next();
				amount=totals.get(currency);
				str+=", "+amount.toString()+" "+currency;
			}
		}
		str+="\nstatus: "+getStatusString();
		DateFormat formatter=DateFormat.getDateInstance();
		str+="\n"+formatter.format(this.getStartDate().getTime())+" to "+
				formatter.format(this.getEndDate().getTime());
		return str;
	}
	
	@Override
	public int compareTo(TravelClaim other) {
		return this.getStartDate().compareTo(other.getStartDate());
	}
}
