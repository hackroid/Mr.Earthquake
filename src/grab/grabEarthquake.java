//*********Data collection complete, please do not restart it***********

package grab;
import java.io.*;
import java.net.*;
import java.sql.*;

import com.mysqlJDBC;

public class grabEarthquake {
//	The website limits its data to 2000 pages, 
//	50 earthquakes on every page, total 100k pieces.
//	Just for mainpage, in time descending order, and real-time update
	
	static Connection conn = mysqlJDBC.getConnection();
	static Statement  stmt = mysqlJDBC.getStatement(conn);
	
	public static void generateURL() {
		int i;
		String urlRoot = "https://www.emsc-csem.org/Earthquake/?view=", url;
		for(i = 68; i <= 2000; i++) {
			url = urlRoot + String.valueOf(i);
//			System.out.printf("%s\n", url);
			get_Info(url);
		}
	}
	
	public static void get_Info(String url) {
		
//		Catch html content
		String Result="";
		BufferedReader in = null;
		int indexST, indexED;
		try {
			URL realURL = new URL(url);
			URLConnection uc = realURL.openConnection();
			uc.connect();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				Result += line + '\n';
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
//			Step by step precision
			String min1Result = Result.substring(18000, 65000);
			indexST = min1Result.indexOf("my_pagelist_0");
			indexED = min1Result.indexOf("</tbody>");
			String min2Result = min1Result.substring(indexST - 23, indexED);
			indexST = min2Result.indexOf("</thead><tbody");
			String min3Result = min2Result.substring(indexST + 26);
			
//			System.out.println(min3Result);
			circleFilter(min3Result, 0, 0);
		} catch (Exception e) {
			
		}

	}
	public static void circleFilter(String dataSet, int prev, int count) {
		
//		Find every piece of data row
		int curr = dataSet.indexOf("<tr", prev);
		while (curr != -1) {
			if (dataSet.substring(curr + 8, curr + 9).equals("t")) {
				curr = dataSet.indexOf("<tr", curr + 10);
			} else 
				break;
		}
			
		prev = dataSet.indexOf("</tr>", curr);
		if (curr != -1) {
//			System.out.printf("%d ", prev);
			dataFilter(dataSet.substring(curr, prev + 5));
			count++;
			circleFilter(dataSet, prev, count);
		} else {
//			Check data count
//			System.out.printf("\nall ---> %d ", count);
		}
	}
	public static void dataFilter(String dataPiece) {
		
//		Filter out the data
//		System.out.printf("%s\n", dataPiece);
		int index, id, depth;
		String date, time, latitudetemp, longitudetemp, region;
		float mag, latitude, longitude;
		
		index = dataPiece.indexOf("id=") + 4;
		id = Integer.valueOf(dataPiece.substring(index, index + 6)).intValue();
		
		index = dataPiece.indexOf("&#160;&#160;&#160;");
		date = dataPiece.substring(index - 10, index);
		time = dataPiece.substring(index + 18, index + 26);
		
		index = dataPiece.indexOf(".", index + 30);
		if (dataPiece.charAt(index - 2) == '>')
			latitude = Float.parseFloat(dataPiece.substring(index - 1, index + 3));
		else
			latitude = Float.parseFloat(dataPiece.substring(index - 2, index + 3));
		if (dataPiece.charAt(index + 33) == 'S')
			latitude = 0 - latitude;
		
		index = dataPiece.indexOf(".", index + 30);
		if (dataPiece.charAt(index - 2) == '>')
			longitude = Float.parseFloat(dataPiece.substring(index - 1, index + 3)) + dataPiece.charAt(index + 33);
		else if (dataPiece.charAt(index - 3) == '>')
			longitude = Float.parseFloat(dataPiece.substring(index - 2, index + 3)) + dataPiece.charAt(index + 33);
		else
			longitude = Float.parseFloat(dataPiece.substring(index - 3, index + 3)) + dataPiece.charAt(index + 33);
		if (dataPiece.charAt(index + 33) == 'W')
			longitude = 0 - longitude;
		
		index = dataPiece.indexOf("tabev3", index + 30);
		if (dataPiece.charAt(index + 9) == '<')
			depth = Integer.valueOf(dataPiece.substring(index + 8, index + 9)).intValue();
		else if (dataPiece.charAt(index + 10) == '<')
			depth = Integer.valueOf(dataPiece.substring(index + 8, index + 10)).intValue();
		else
			depth = Integer.valueOf(dataPiece.substring(index + 8, index + 11)).intValue();
		
		index = dataPiece.indexOf(".", index + 30);
		mag = Float.parseFloat(dataPiece.substring(index - 1, index + 2));
		
		index = dataPiece.indexOf("&#160;", index + 4);
		region = dataPiece.substring(index + 6, dataPiece.indexOf("<", index));
		
		System.out.printf("%d %s %s %.2f %.2f %d %.1f %s\n", id, date, time, latitude, longitude, depth, mag, region);
		pushData.push(id, date, time, latitude, longitude, depth, mag, region, stmt);
	}
}
