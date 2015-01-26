package ca.ualberta.cs.kjyoung_expenses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

//This class largely borrowed from https://github.com/abramhindle/student-picker/blob/master/src/ca/softwareprocess/studentpicker/StudentListManager.java, Jan 25 2015
public class ClaimsListManager {
	private Context context;
	private static final String FILENAME = "file.sav";
	
	private static ClaimsListManager claimsListManager=null;
	
	public static void initManager(Context context){
		if(claimsListManager==null){
			if(context==null){
				throw new RuntimeException("missing context for StudentListManager");
			}
			claimsListManager=new ClaimsListManager(context);
		}
	}
	
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
	
}
