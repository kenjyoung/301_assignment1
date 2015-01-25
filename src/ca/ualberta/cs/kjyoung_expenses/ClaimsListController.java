package ca.ualberta.cs.kjyoung_expenses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClaimsListController extends Activity {
	private static ArrayList <TravelClaim> claims=null;
	private static final String FILENAME = "file.sav";
	public static ArrayList <TravelClaim> getClaims(){
		if(claims ==null){
			claims = loadFromFile();
			if(claims==null){
				claims = new ArrayList<TravelClaim>();
			}
		}
		Collections.sort(claims);
		return claims;
	}
	public static void addClaim(TravelClaim claim){
		claims.add(claim);
		Collections.sort(claims);
	}
	
	//Taken from https://github.com/joshua2ua/lonelyTwitter on Jan 24 2015 and modified
		private static ArrayList<TravelClaim> loadFromFile() {
			Gson gson = new Gson();
			ArrayList<TravelClaim> claims = new ArrayList<TravelClaim>();
			try {
				FileInputStream fis = openFileInput(FILENAME);
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
		public void saveToFile() {
			Gson gson = new Gson();
			try {
				FileOutputStream fos = openFileOutput(FILENAME, 0);
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
