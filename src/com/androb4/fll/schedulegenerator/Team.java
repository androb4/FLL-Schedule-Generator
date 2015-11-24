package com.androb4.fll.schedulegenerator;

public class Team {
	
	private int team_number;
	private int last_match_minutes;
	
	private int[] matches;
	
	public Team(int team_number) {
		this.team_number = team_number;
	}
	
	public void addMatch(int time_minutes) {
		last_match_minutes = time_minutes;
	}
	
	public int teamNumber() {
		return this.team_number;
	}
}
