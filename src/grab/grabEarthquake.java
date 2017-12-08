package grab;
import java.io.*;
import java.net.*;

public class grabEarthquake {
	
	public static void generateURL() {
		int i;
		String urlRoot = "https://www.emsc-csem.org/Earthquake/?view=", url;
		for(i = 2000; i <= 2000; i++) {
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
		
//		Step by step precision
		String min1Result = Result.substring(18000, 65000);
		indexST = min1Result.indexOf("my_pagelist_0");
		indexED = min1Result.indexOf("</tbody>");
		String min2Result = min1Result.substring(indexST - 23, indexED);
		indexST = min2Result.indexOf("</thead><tbody");
		String min3Result = min2Result.substring(indexST + 26);
		
		circleFilter(min3Result, 0, 0);
//		System.out.println(min3Result);
//		System.out.println("\n\n\n\n\n\n\n");
	}
	public static void circleFilter(String dataSet, int prev, int count) {
		
//		Find every piece of data row
		int curr = dataSet.indexOf("</tr>", prev);
		if (curr != -1) {
//			System.out.printf("%d ", prev);
			curr = curr + 6;
			dataFilter(dataSet.substring(prev, curr - 1));
			count++;
			circleFilter(dataSet, curr, count);
		} else {
//			Check data count
//			System.out.printf("\nall ---> %d ", count);
		}
	}
	public static void dataFilter(String dataPiece) {
		
//		Filter out the data
//		System.out.printf("%s\n", dataPiece);
		int index, id, depth;
		String date, time, latitude, longitude;
		double mag;
		
		index = dataPiece.indexOf("id=") + 4;
		id = Integer.valueOf(dataPiece.substring(index, index + 6)).intValue();
		
		index = dataPiece.indexOf("&#160;&#160;&#160;");
		date = dataPiece.substring(index - 10, index);
		time = dataPiece.substring(index + 18, index + 26);
		
		index = dataPiece.indexOf(".", index + 30);
		if (dataPiece.charAt(index - 2) == '>')
			latitude = dataPiece.substring(index - 1, index + 3) + dataPiece.charAt(index + 33);
		else
			latitude = dataPiece.substring(index - 2, index + 3) + dataPiece.charAt(index + 33);
		
		index = dataPiece.indexOf(".", index + 30);
		if (dataPiece.charAt(index - 2) == '>')
			longitude = dataPiece.substring(index - 1, index + 3) + dataPiece.charAt(index + 33);
		else if (dataPiece.charAt(index - 3) == '>')
			longitude = dataPiece.substring(index - 2, index + 3) + dataPiece.charAt(index + 33);
		else
			longitude = dataPiece.substring(index - 3, index + 3) + dataPiece.charAt(index + 33);
		
		index = dataPiece.indexOf("tabev3", index + 30);
		if (dataPiece.charAt(index + 9) == '<')
			depth = Integer.valueOf(dataPiece.substring(index + 8, index + 9)).intValue();
		else if (dataPiece.charAt(index + 10) == '<')
			depth = Integer.valueOf(dataPiece.substring(index + 8, index + 10)).intValue();
		else
			depth = Integer.valueOf(dataPiece.substring(index + 8, index + 11)).intValue();
		
		index = dataPiece.indexOf(".", index + 30);
		mag = Double.parseDouble(dataPiece.substring(index - 1, index + 2));
		
		System.out.printf("%d %s %s %s %s %d %.1f\n", id, date, time, latitude, longitude, depth, mag);
	}
}
