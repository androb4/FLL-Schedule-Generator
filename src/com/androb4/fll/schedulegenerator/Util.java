package com.androb4.fll.schedulegenerator;

public class Util {
	public static String minutesToTimeString(int minutes) {
		int hour = minutes/60;
		int minute = minutes%60;
		return String.valueOf(hour<=12?hour:hour-12) + ":" + String.format("%02d", minute) + " " + (hour<12?"AM":"PM");
	}
	
	public static int timeStringToMinutes(String time) {
		
		return 500;
	}
}
