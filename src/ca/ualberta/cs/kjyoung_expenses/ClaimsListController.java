package ca.ualberta.cs.kjyoung_expenses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;


public class ClaimsListController{
	//This class largely borrowed from https://github.com/abramhindle/student-picker/blob/master/src/ca/softwareprocess/studentpicker/StudentListController.java, Jan 25 2015
	//It follows a singleton pattern to make an ArrayList of travel claims accessible
	//to the whole application. It also provides several methods to manipulate this list,
	//as well as to load from disk and send contained claims via email.
	
	private static ArrayList <TravelClaim> claims=null;
	
	//singleton pattern for accessing controller
	public static ArrayList <TravelClaim> getClaims(){
		if(claims ==null){
			claims=ClaimsListManager.getManager().loadClaims();
		}
		Collections.sort(claims);
		return claims;
	}
	
	public static TravelClaim getClaim(int index){
		return ClaimsListController.getClaims().get(index);
	}
	
	//add a claim to the claims list and return the index it ends up at after sorting
	public static int addClaim(TravelClaim claim){
		getClaims().add(claim);
		Collections.sort(claims);
		return claims.indexOf(claim);
	}
	
	//delete a claim with a given index in the list
	public static void deleteClaim(int index){
		ClaimsListController.getClaims().remove(index);
	}
	
	public static void updateClaim(int index, GregorianCalendar startDate, 
			GregorianCalendar endDate, String description){
		ClaimsListController.getClaims().get(index).updateInfo(
				startDate, endDate, description);
		Collections.sort(claims);
		
	}
	
	//call the manager to open a claim at a given index in a mail app
	public static void sendClaim(int index){
		ClaimsListManager.getManager().sendClaim(claims.get(index));
	}
	
	//call the manager to save all the claims to disk
	public static void saveClaims() {
		ClaimsListManager.getManager().saveClaims(claims);
	}
}
