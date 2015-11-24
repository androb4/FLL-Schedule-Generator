package com.androb4.fll.schedulegenerator;

import java.util.ArrayList;
import java.util.List;

public class FLLScheduleGenerator {

	private static int demo_teams[] = { 16, 118, 254, 441, 610, 624, 1114, 2587, 3847, 5892};
	
	/* Configs */
	private static int kStartTime = 28800;
	private static int kNumFields = 6;
	private static int kNumSets = 3;
	private static int kMatchInterval = 300;
	private static int kMinTimeBetweenLast;
	private static int kBreaks = 0;
	
	public static List<Team> teams = new ArrayList<Team>();
	public static List<Match> matches = new ArrayList<Match>();
	
	public static void main(String[] args) {
		for(int team : demo_teams) {
			teams.add(new Team(team));
		}
		
		for(int i=1; i<=5; i++) {
			matches.add(new Match(i, getRandomTeam().teamNumber()));
		}
		
		for(Match match:matches) {
			System.out.println(match.matchNumber() + " " + match.team());
		}
	}
	
	public static Team getRandomTeam() {
		if(teams == null)
			return null;
		
		if(teams.size() == 0)
			return null;
		
		int randomNumber = (int)(Math.random() * teams.size());
		return teams.get(randomNumber);
	}
}
