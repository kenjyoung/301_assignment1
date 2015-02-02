/*Expense Trackers: Keeps track of travel expense claims
    Copyright (C) 2014  Kenny Young kjyoung@ualberta.ca

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>
    */
package ca.ualberta.cs.kjyoung_expenses;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Expense implements Comparable<Expense>{
	//This class represents the individual expense items contained within a travel claim.
	//it contains all the basic info required for an expense item along with methods to 
	//operate on it. It also contains two static attributes for defining the selectable
	//currencies and categories for an expense item, at the moment this list is statically
	//retrieved from the string resource file but could easily be changed to allow the user
	//to modify it at runtime. The toString method returns a string listing all the expense
	//info in a human readable form to be printed to the expenses list.
	
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
	
	public void updateInfo(GregorianCalendar date, String category, String description, 
			BigDecimal amount, String currency){
		this.date=date;
		this.category=category;
		this.description=description;
		this.amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
		this.currency=currency;
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
