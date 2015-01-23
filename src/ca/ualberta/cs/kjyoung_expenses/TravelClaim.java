package ca.ualberta.cs.kjyoung_expenses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TravelClaim {
	public Date startDate;
	public Date endDate;
	public String description;
	public Byte status;
	public ArrayList<Expense> expenses;
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
	public Map <String, BigDecimal> getTotals(){
		return new HashMap<String, BigDecimal>();
	}
	public void send(String email){
		
	}
	public void addExpense(Expense expense){
		
	}
	public void removeExpense(Expense expense){
		
	}
}
