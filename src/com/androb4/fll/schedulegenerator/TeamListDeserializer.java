package com.androb4.fll.schedulegenerator;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TeamListDeserializer {
	private static XSSFWorkbook teamListDocument;
	private static XSSFSheet teamListSheet;
	public static List<Team> teamList = new ArrayList<Team>();

	private static void readTeamDocument() {
		FileInputStream teamListFile;
		try {
			teamListFile = new FileInputStream(new File("2015-FLL-Hamilton-Team-List.xlsx"));
			teamListDocument = new XSSFWorkbook(teamListFile);
			teamListFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		teamListSheet = teamListDocument.getSheetAt(0);
	}
	
	private static int getNumTeams() {
		if (teamListSheet == null) {
			readTeamDocument();
		}
		
		int numTeams = 0;
		for(int i=1; i<teamListSheet.getPhysicalNumberOfRows(); i++) {
			if(teamListSheet.getRow(i).getCell(0).getNumericCellValue() != 0)
				numTeams++;
		}
		return numTeams;
	}

	public static void deserializeTeams() {
		if (teamListSheet == null) {
			readTeamDocument();
		}
		
		for(int i=1; i<=getNumTeams(); i++) {
			Team team = new Team();
			for(int c=0; c<2; c++) {
				Cell cell = teamListSheet.getRow(i).getCell(c);
				if(c==0)
					team.setTeamNumber((int)cell.getNumericCellValue());
				else if(c==1)
					team.setTeamName(cell.getStringCellValue());
				else if(c==2)
					team.setTeamName(cell.getStringCellValue());
			}
			teamList.add(team);
		}
	}

	public static Team[] getTeams() {
		if (teamListSheet == null) {
			deserializeTeams();
		}
		return teamList.toArray(new Team[teamList.size()]);
	}

}
