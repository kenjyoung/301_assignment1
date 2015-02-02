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
	
	//update claim and return its new index
	public static int updateClaim(int index, GregorianCalendar startDate, 
			GregorianCalendar endDate, String description){
		TravelClaim claim=ClaimsListController.getClaim(index);
		claim.updateInfo(startDate, endDate, description);
		Collections.sort(claims);
		return claims.indexOf(claim);
		
		
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
