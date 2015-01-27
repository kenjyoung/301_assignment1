package ca.ualberta.cs.kjyoung_expenses;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;

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
	public static Integer addClaim(TravelClaim claim){
		getClaims().add(claim);
		Collections.sort(claims);
		return claims.indexOf(claim);
	}
	
	public static void deleteClaim(TravelClaim claim){
		getClaims().remove(claim);
	}
	
	public static void updateClaim(Integer index, TravelClaim claim){
		getClaims().set(index, claim);
		Collections.sort(claims);
	}
	
	static public void saveClaims() {
			ClaimsListManager.getManager().saveClaims(claims);
	}
}
