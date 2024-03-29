package com.androb4.fll.schedulegenerator;

import java.util.ArrayList;
import java.util.List;

public class Match {
	private int matchNumber;
	private int matchTime;
	private Table[] tables;
	private List<Team> teams = new ArrayList<Team>();
	
	public Match(int matchNumber, int matchTime) {
		this.matchNumber = matchNumber;
		this.matchTime = matchTime;
	}
	
	public Match(int matchNumber, int matchTime, Team teams[]) {
		for(Team team:teams) {
			this.teams.add(team);
			team.addMatch(this);
		}
		this.matchNumber = matchNumber;
		this.matchTime = matchTime;
	}
	
	public Match(int matchNumber, int matchTime, Team teams[], Table[] tables) {
		for(Team team:teams) {
			this.teams.add(team);
			team.addMatch(this);
		}
		this.matchNumber = matchNumber;
		this.matchTime = matchTime;
		this.tables = tables;
	}
	
	public int matchNumber() {
		return this.matchNumber;
	}
	
	public Team[] teams() {
		return this.teams.toArray(new Team[this.teams.size()]);
	}
	
	public int matchTime() {
		return this.matchTime;
	}
	
	public Table[] tables() {
		return this.tables;
	}
}
