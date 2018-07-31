package com.luis.lgameengine.implementation.fileio;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import android.content.Context;
import android.util.Log;

/**
 * 
 * @author Luis Valdes Frances
 */
public class FileIO {
	
	private static FileIO instance;
	public static FileIO getInstance(){
		if(instance == null){
			instance = new FileIO();
		}
		return instance;
	}
	private FileOutputStream fos;
	private FileInputStream fis;

	public void saveData(String data, String fileName, Context context) {
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.write(data.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String data;
	public String loadData(String fileName, Context context){
		data = "";
		try {
			fis = context.openFileInput(fileName);
			
			InputStreamReader reader = new InputStreamReader(fis);
			BufferedReader buffreader = new BufferedReader(reader);
			
			String linea = "";
			while ((linea = buffreader.readLine()) != null) {
				data += linea + "\n";
			}
			return data;
		} catch (Exception e) {
			Log.i("INFO", "No hay datos guadados");
			e.printStackTrace();
			return null;
		}
		
	}
	
	public int[] getIntData(){
		String[] sData = data.split("\n");
		
		//La posicion
		int[] iData = new int[sData.length];
		for(int i = 0; i < iData.length;i++)
			iData[i]=(int)Integer.parseInt(sData[i]);
		return iData;
	}
	
	
	public String getData() {
		return data;
	}
}

