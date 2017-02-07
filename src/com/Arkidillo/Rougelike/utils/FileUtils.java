package com.Arkidillo.Rougelike.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	private FileUtils() {
	}
	
	public static String loadAsString(String file){
		StringBuilder result = new StringBuilder(); //String concatenation normally has to reallocate each time -> lots of wasted time. This allocates a large portion of memory, so we dont need to reallocate
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String buffer = "";
			while((buffer = reader.readLine()) != null){
				result.append(buffer + '\n');
			}

			reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return result.toString();
	}

}
