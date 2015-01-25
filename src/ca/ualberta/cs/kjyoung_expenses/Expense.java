package ca.ualberta.cs.kjyoung_expenses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Expense {
	private Date date;
	private String category;
	private String description;
	private BigDecimal amount;
	private String currency;
	private static ArrayList <String> currencies;
	private static ArrayList <String> categories;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public static ArrayList<String> getCurrencies() {
		return currencies;
	}
	public static void setCurrencies(ArrayList<String> currencies) {
		Expense.currencies = currencies;
	}
	public static ArrayList<String> getCategories() {
		return categories;
	}
	public static void setCategories(ArrayList<String> categories) {
		Expense.categories = categories;
	}
	
}
