package ca.ualberta.cs.kjyoung_expenses;

import java.util.ArrayList;
import java.util.Collections;

//This class largely borrowed from https://github.com/abramhindle/student-picker/blob/master/src/ca/softwareprocess/studentpicker/StudentListController.java, Jan 25 2015
public class ClaimsListController{
	private static ArrayList <TravelClaim> claims=null;
	public static ArrayList <TravelClaim> getClaims(){
		if(claims ==null){
			claims=ClaimsListManager.getManager().loadClaims();
		}
		Collections.sort(claims);
		return claims;
	}
	
	//add a claim to the claims list and return the index it ends up at after sorting
	public static int addClaim(TravelClaim claim){
		getClaims().add(claim);
		Collections.sort(claims);
		return claims.indexOf(claim);
	}
	
	//this is broken somehow, figure it out
	public static void deleteClaim(int index){
		ClaimsListController.getClaims().remove(index);
	}
	
	public static void updateClaim(int index, TravelClaim claim){
		getClaims().set(index, claim);
		Collections.sort(claims);
	}
	
	public static void sendClaim(int index){
		ClaimsListManager.getManager().sendClaim(claims.get(index));
	}
	
	public static void saveClaims() {
			ClaimsListManager.getManager().saveClaims(claims);
	}
}
