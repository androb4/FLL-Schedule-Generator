package com.androb4.fll.schedulegenerator;

import java.util.ArrayList;
import java.util.List;

public class FLLScheduleGenerator {

	/* Configs */
	private static int kStartTime = 510; // 8:30 in minutes
	private static int kNumMatchesEach = 3;
	private static int kNumFields = 6;
	private static String kTableNames[] = {"Table 1", "Table 2", "Table 3", "Table 4", "Table 5", "Table 6"};
	private static int kNumSets = 3;
	private static int kMatchInterval = 5;
	private static int kPitTimeMin = 5;
	private static int kMinTimeBetweenLast;
	private static int kBreaks = 0;

	public static Team[] teamList;
	public static Table[] tableList = new Table[kTableNames.length];
	public static List<Match> matchList = new ArrayList<Match>();

	public static void main(String[] args) {

		for(int i=0; i<tableList.length; i++) {
			tableList[i] = new Table(i+1, kTableNames[i]);
		}
		
		teamList = new Team[TeamListDeserializer.getTeams().length];
		teamList = TeamListDeserializer.getTeams();
		
		while (!isEnoughMatches()) {
			matchList.clear();
			for (Team team3 : teamList) {
				team3.matches().clear();
			}
			int m = 1;
			int t = kStartTime;
			int kLastMatchTime = kStartTime - kMatchInterval;
			Team[] teams = new Team[2];
			createSchedule:
			while (!isEnoughMatches()) {
				if (t == kLastMatchTime + kMatchInterval) {
					for (int i = 0; i <teams.length; i++) {
						teams[i] = getRandomTeam(teams);
						long time = System.currentTimeMillis();
						while (((t - teams[i].lastMatchTime()) < kPitTimeMin) || ((kNumMatchesEach - teams[i].matches().size()) <= 0)) {
							teams[i] = getRandomTeam(teams);
							if (System.currentTimeMillis() - time > 50) {
								break createSchedule;
							}
						}
						//System.out.println(isLastTeam());
						if (isLastTeam() && teamList.length % 2 != 0) {
							teams[1] = getRandomTeam(teams);
							matchList.add(new Match(m, t, teams, new Table[]{tableList[0], tableList[1]}));
							break createSchedule;
						}
					}

					matchList.add(new Match(m, t, teams, new Table[]{tableList[3*((m-1)%3)-((m-1)%3)], tableList[2*((m-1)%3)+1]}));
					kLastMatchTime = t;
					m++;
				}
				t++;
			}
			System.out.println(matchList.size());
		}
		
		// Print out schedule for debugging
		for (Match match : matchList) {
			System.out.print(match.matchNumber() + ",");
			for(int t=0; t<match.teams().length; t++) {
				System.out.print(match.tables()[t].getName() + " " + match.teams()[t].teamNumber() + ", ");
			}
			System.out.print(match.matchTime());
			System.out.println();
		}
		
		MatchScheduleSerializer.serializeSchedule(matchList.toArray(new Match[matchList.size()]), tableList);
	}

	public static Team getRandomTeam() {
		int randomNumber = (int) (Math.random() * teamList.length);
		return teamList[randomNumber];
	}

	public static Team getRandomTeam(Team[] excludeTeams) {
		Team team = getRandomTeam();
		boolean done = true;
		if(excludeTeams[0] == null) {
			return team;
		}
		do {
			team = getRandomTeam();
			done = true;
			for(Team excludeTeam:excludeTeams) {
				if(excludeTeam != null && team == excludeTeam) {
					done = false;
				}
			}
		} while(!done);
		return team;
	}

	public static boolean isEnoughMatches() {
		for (Team team : teamList) {
			if ((kNumMatchesEach - team.matches().size()) > 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean isLastTeam() {
		int count = 0;
		for (Team team : teamList) {
			if (kNumMatchesEach - team.matches().size() > 0) {
				count++;
			}
		}
		return count<2;
	}
}
