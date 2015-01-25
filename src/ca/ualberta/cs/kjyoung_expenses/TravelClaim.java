package ca.ualberta.cs.kjyoung_expenses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TravelClaim {
	private Date startDate;
	private Date endDate;
	private String description;
	private Byte status;
	private ArrayList<Expense> expenses;
	public TravelClaim(Date startDate, Date endDate, String description, ArrayList<Expense> expenses) {
		super();
		this.startDate=startDate;
		this.endDate=endDate;
		this.description=description;
		this.expenses=expenses;
		this.status=0;
	}
	public TravelClaim(Date startDate, Date endDate, String description) {
		super();
		this.startDate=startDate;
		this.endDate=endDate;
		this.description=description;
		this.status=0;
		this.expenses=new ArrayList <Expense>();
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
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
	public void addExpense(Expense expense){
		expenses.add(expense);
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
}
