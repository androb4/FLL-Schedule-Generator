package com.androb4.fll.schedulegenerator;

import java.util.ArrayList;
import java.util.List;

public class Team {
	
	private int teamNumber;
	private String teamName;
	private String teamOrganization;
	
	private List<Match> matches = new ArrayList<Match>();
	
	public Team() {
	}
	
	public Team(int teamNumber) {
		this.teamNumber = teamNumber;
	}
	
	public void addMatch(Match match) {
		matches.add(match);
	}
	
	public int teamNumber() {
		return this.teamNumber;
	}
	
	public String teamName() {
		return this.teamName;
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
	
	public void setTeamNumber(int teamNumber) {
		this.teamNumber = teamNumber;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public void setTeamOrganization(String teamOrganization) {
		this.teamOrganization = teamOrganization;
	}
	
	public String getTeamOrganization() {
		return teamOrganization;
	}
}
