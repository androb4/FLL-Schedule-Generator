package com.androb4.fll.schedulegenerator;

import java.util.ArrayList;
import java.util.List;

public class Team {
	
	private int team_number;
	private String teanName;
	private int last_match_minutes;
	
	private List<Match> matches = new ArrayList<Match>();
	
	public Team(int team_number) {
		this.team_number = team_number;
	}
	
	public void addMatch(Match match) {
		matches.add(match);
	}
	
	public int teamNumber() {
		return this.team_number;
	}
	
	public List<Match> matches() {
		return this.matches;
	}
	
	public Match lastMatch() {
		if(matches.size() == 0) {
			return null;
		}
		return matches.get(matches.size()-1);
	}
	
	public int lastMatchTime() {
		if(lastMatch() != null) {
			return lastMatch().matchTime();
		}
		return 0;
	}
}
