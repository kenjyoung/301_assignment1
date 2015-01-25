package ca.ualberta.cs.kjyoung_expenses;

import java.util.ArrayList;

public class ClaimsListController {
	private static ArrayList <TravelClaim> claims=null;
	public static ArrayList <TravelClaim> getClaims(){
		if(claims ==null){
			claims=new ArrayList <TravelClaim>();
		}
		return claims;
	}
}
