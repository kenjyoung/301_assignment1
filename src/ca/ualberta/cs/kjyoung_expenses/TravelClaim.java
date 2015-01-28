package ca.ualberta.cs.kjyoung_expenses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class TravelClaim implements Comparable<TravelClaim>{
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private String description;
	private Byte status;
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
	public void setStatus(Byte status) {
		this.status = status;
	}
	public HashMap <String, BigDecimal> getTotals(){
		HashMap<String, BigDecimal> totals= new HashMap<String, BigDecimal>();
		Expense expense;
		for(Integer i=0;i<this.expenses.size();i++){
			expense=expenses.get(i);
			String currency=expense.getCurrency();
			BigDecimal amount=expense.getAmount();
			if(totals.containsKey(currency)){
				totals.put(currency,amount.add((BigDecimal) totals.get(currency)));
			}
		}
		return new HashMap<String, BigDecimal>();
	}
	public void send(String email){
		
	}
	//add an expense item and return its index after the expense list is sorted
	public Integer addExpense(Expense expense){
		expenses.add(expense);
		Collections.sort(expenses);
		return expenses.indexOf(expense);
	}
	public void removeExpense(Expense expense){
		expenses.remove(expense);
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
			str+=currency.toString()+" "+currency;
		
			while(i.hasNext()){
				currency=i.next();
				amount=totals.get(currency);
				str+=", "+amount.toString()+" "+currency;
			}
		}
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
		str+="\nstatus: "+status;
		return str;
	}
	@Override
	public int compareTo(TravelClaim other) {
		return this.getStartDate().compareTo(other.getStartDate());
	}
}
