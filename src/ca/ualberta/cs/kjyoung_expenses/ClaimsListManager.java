package ca.ualberta.cs.kjyoung_expenses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class ClaimsListManager {
	//This class largely borrowed from https://github.com/abramhindle/student-picker/blob/master/src/ca/softwareprocess/studentpicker/StudentListManager.java, Jan 25 2015
	//Like ClaimsListController it follows a singleton pattern. It provides an interface layer
	//between the ClaimsListContoller and the android OS. It handles the actual saving and 
	//loading of claims, along with sending emails. It must be initialized with a context from
	//a running activity in order to work.
	
	private Context context;
	private static final String FILENAME = "file.sav";
	private static ClaimsListManager claimsListManager=null;
	
	//provide the manager with a context nessesary for completing its operations
	public static void initManager(Context context){
		if(claimsListManager==null){
			if(context==null){
				throw new RuntimeException("missing context for StudentListManager");
			}
			claimsListManager=new ClaimsListManager(context);
		}
	}
	
	//singleton pattern for accessing manager
	public static ClaimsListManager getManager(){
		if(claimsListManager==null){
			throw new RuntimeException("Did not initialize Manager.");
		}
		return claimsListManager;
	}
	
	public ClaimsListManager(Context context){
		this.context=context;
	}
	
	//Taken from https://github.com/joshua2ua/lonelyTwitter on Jan 24 2015 and modified
	//uses gson to load saved claims from disk and returns the loaded list
	public ArrayList<TravelClaim> loadClaims(){
		Gson gson = new Gson();
		ArrayList<TravelClaim> claims = new ArrayList<TravelClaim>();
		try {
			FileInputStream fis = context.openFileInput(FILENAME);
			InputStreamReader in =new InputStreamReader(fis);
			//Taken from http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/index.html on Jan 20 2015
			Type typeOfT = new TypeToken<ArrayList<TravelClaim>>(){}.getType();
			claims = gson.fromJson(in, typeOfT);
			fis.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return claims;
	}
	
	//Taken from https://github.com/joshua2ua/lonelyTwitter on Jan 24 2015 and modified
	//uses gson to save the entire claims list to disk for later retrieval
	public void saveClaims(ArrayList<TravelClaim> claims){
		Gson gson = new Gson();
		try {
			FileOutputStream fos = context.openFileOutput(FILENAME, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claims, osw);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//create a string containing all the info for the given claim and its expenses and
	//send it to an email app chosen by the user as the body of an email
	public void sendClaim(TravelClaim claim){
		String body;
		DateFormat formatter=DateFormat.getDateInstance();
		body="Travel Claim: "+claim.getDescription()+"\n";
		body+=formatter.format(claim.getStartDate().getTime())+" to "+
				formatter.format(claim.getEndDate().getTime())+"\n\n";
		ArrayList<Expense> expenses=claim.getExpenses();
		for(int i=0;i<expenses.size();i++){
			Expense curExpense=expenses.get(i);
			body+="    -"+formatter.format(curExpense.getDate().getTime())+": ";
			body+=curExpense.getDescription()+", ";
			body+=curExpense.getCategory()+", ";
			body+=curExpense.getAmount().toString()+" ";
			body+=curExpense.getCurrency()+"\n";
		}
		body+="\nStatus: "+claim.getStatusString()+"\n";
		
		// taken from http://stackoverflow.com/a/2197841 and modified Jan 30 2015
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_SUBJECT, "Travel Claim: "+claim.getDescription());
		i.putExtra(Intent.EXTRA_TEXT, body);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
		    context.startActivity(i);
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(context, "No email client found.",
		    		Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
