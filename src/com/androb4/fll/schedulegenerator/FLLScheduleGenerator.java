package com.androb4.fll.schedulegenerator;

import java.util.ArrayList;
import java.util.List;

public class FLLScheduleGenerator {

	private static int demo_teams[] = // { 16, 118, 254, 441, 610, 624, 1114,
										// 1484, 2587, 3847, 5892};
	{ 1894, 1896, 1897, 2798, 3583, 6710, 8836, 8848, 11519, 13030, 13033, 14039, 15110, 17603, 17605, 18320, 18321,
			19315, 19966, 20046, 20145, 20185, 20288, 20523, 21240, 21270, 21271, 21272, 22097, 22098,
			22099}; //, 22100 };

	/* Configs */
	private static int kStartTime = 510; // 8:30 in minutes
	private static int kNumMatchesEach = 3;
	private static int kNumFields = 6;
	private static String kTableNames[] = {"This", "is", "a", "table", "name", "PERIOD"};
	private static int kNumSets = 3;
	private static int kMatchInterval = 5;
	private static int kPitTimeMin = 30;
	private static int kMinTimeBetweenLast;
	private static int kBreaks = 0;

	public static Team[] teamList;
	public static List<Match> matchList = new ArrayList<Match>();

	public static void main(String[] args) {
		
		teamList = new Team[TeamListDeserializer.getTeams().length];
		teamList = TeamListDeserializer.getTeams();

		System.out.println(TeamListDeserializer.getTeams().length);
		
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
							matchList.add(new Match(m, t, teams));
							break createSchedule;
						}
					}

					matchList.add(new Match(m, t, teams));
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
			for (Team team : match.teams()) {
				System.out.print(" " + team.teamNumber() + ",");
			}
			System.out.print(" " + match.matchTime());
			System.out.println();
		}
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
