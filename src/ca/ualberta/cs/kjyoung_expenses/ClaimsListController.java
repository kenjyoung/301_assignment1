package ca.ualberta.cs.kjyoung_expenses;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;

//This class largely borrowed from https://github.com/abramhindle/student-picker/blob/master/src/ca/softwareprocess/studentpicker/StudentListController.java, Jan 25 2015
public class ClaimsListController extends Activity {
	private static ArrayList <TravelClaim> claims=null;
	public static ArrayList <TravelClaim> getClaims(){
		if(claims ==null){
			claims=ClaimsListManager.getManager().loadClaims();
		}
		Collections.sort(claims);
		return claims;
	}
	
	public static void addClaim(TravelClaim claim){
		claims.add(claim);
		Collections.sort(claims);
	}
	
	static public void saveClaims() {
			ClaimsListManager.getManager().saveClaims(claims);
	}
}
