package com.androb4.fll.schedulegenerator;

public class Match {
	private int matchNumber;
	private int matchTime;
	private String table;
	private int team;
	
	public Match(int matchNumber, int team) {
		this.matchNumber = matchNumber;
		this.team = team;
	}
	
	public int matchNumber() {
		return this.matchNumber;
	}
	
	public int team() {
		return this.team;
	}
	
	public int matchTime() {
		return this.matchTime;
	}
}
