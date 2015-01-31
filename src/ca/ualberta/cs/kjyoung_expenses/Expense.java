package ca.ualberta.cs.kjyoung_expenses;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Expense implements Comparable<Expense>{
	private GregorianCalendar date;
	private String category;
	private String description;
	private BigDecimal amount;
	private String currency;
	private static ArrayList <String> currencies;
	private static ArrayList <String> categories;
	
	public Expense() {
		super();
		this.date=new GregorianCalendar();
		this.category="Unspecified";
		this.description="Expense";
		this.amount=new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_EVEN);
		this.currency="NA";
	}
	public Expense(GregorianCalendar date, String category, String description, 
			BigDecimal amount, String currency) {
		super();
		this.date=date;
		this.category=category;
		this.description=description;
		this.amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
		this.currency=currency;
	}
	public GregorianCalendar getDate() {
		return date;
	}
	public void setDate(GregorianCalendar date) {
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
		this.amount = amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
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

	public static ArrayList<String> getCategories() {
		return categories;
	}
	
	//add a new category to the list of supported categories
	public static void addCategory(String category) {
		categories.add(category);
	}
	
	//add a new currency to the list of supported currencies
	public static void addCurrency(String currency) {
		currencies.add(currency);
	}
	
	public static void setCurrencies(ArrayList<String> currencies){
		Expense.currencies=currencies;
	}
	
	public static void setCategories(ArrayList<String> categories){
		Expense.categories=categories;
		
	}
	
	@Override
	public int compareTo(Expense other) {
		return this.getDate().compareTo(other.getDate());
	}
	
	public String toString(){
		String str= this.getDescription()+"\n";
		str+="category: "+this.getCategory()+"\n";
		str+="Amount: "+this.getAmount().toString()+" "+this.getCurrency()+"\n";
		DateFormat formatter=DateFormat.getDateInstance();
		str+="Date: "+formatter.format(this.getDate().getTime());
		return str;
	}
	
}
