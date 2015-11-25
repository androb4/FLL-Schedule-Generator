package com.androb4.fll.schedulegenerator;

import java.util.ArrayList;
import java.util.List;

public class FLLScheduleGenerator {

	private static int demo_teams[] = { 16, 118, 254, 441, 610, 624, 1114, 1484, 2587, 3847, 5892};
	
	/* Configs */
	private static int kStartTime = 510; // 8:30 in minutes
	private static int kNumMatchesEach = 3;
	private static int kNumFields = 6;
	private static String kTableNames[] = {"This", "is", "a", "table", "name", "PERIOD"};
	private static int kNumSets = 3;
	private static int kMatchInterval = 5;
	private static int kMinTimeBetweenLast;
	private static int kBreaks = 0;
	private static boolean isScheduleDone = false;
	
	public static List<Team> teamList = new ArrayList<Team>();
	public static List<Match> matchList = new ArrayList<Match>();
	
	public static void main(String[] args) {
		for(int team : demo_teams) {
			teamList.add(new Team(team));
		}
		
		int m = 1;
		int t = kStartTime;
		int kLastMatchTime = kStartTime - kMatchInterval;
		Team[] teams = new Team[2];
		while(!isEnoughMatches()) {
			if(t == kLastMatchTime+kMatchInterval) {
				teams[0] = getRandomTeam();
				teams[1] = getRandomTeam(teams[0]);
				matchList.add(new Match(m, t, teams));
				kLastMatchTime = t;
				m++;
			}
			t++;
		}
		
		
		// Print out schedule for debugging
		for(Match match:matchList) {
			System.out.print(match.matchNumber());
			for(Team team:match.teams()) {
				System.out.print(" " + team.teamNumber());
			}
			System.out.print(" " + match.matchTime());
			System.out.println();
		}
		
		System.out.println(isEnoughMatches());
	}
	
	public static Team getRandomTeam() {
		int randomNumber = (int)(Math.random() * teamList.size());
		return teamList.get(randomNumber);
	}
	
	public static Team getRandomTeam(Team excludeTeam) {
		Team team = getRandomTeam();
		while(excludeTeam == team) {
			team = getRandomTeam();
		}
		return team;
	}
	
	public static boolean isEnoughMatches() {
		for(Team team:teamList) {
			if((kNumMatchesEach - team.matches().length) > 0) {
				return false;
			}
		}
		return true;
	}
}
