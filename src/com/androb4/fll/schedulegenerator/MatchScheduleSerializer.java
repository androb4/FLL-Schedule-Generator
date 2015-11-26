package com.androb4.fll.schedulegenerator;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MatchScheduleSerializer {
	private static XSSFWorkbook matchScheduleDocument;
	private static XSSFSheet matchScheduleSheet;
	private static CellStyle style2;
	private static Table[] tableList;
	
	private static void createTitle() {
		style2.setAlignment(CellStyle.ALIGN_CENTER);
	    style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    
		matchScheduleSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		Cell cell = matchScheduleSheet.createRow(0).createCell(0);
		cell.setCellValue("Event Name");
		cell.setCellStyle(style2);
		Row row = matchScheduleSheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("Match");
		cell.setCellStyle(style2);
		cell = row.createCell(1);
		cell.setCellValue("Match Time");
		cell.setCellStyle(style2);
		
		for(int t=0; t<tableList.length; t++) {
			cell = row.createCell(t+2);
			cell.setCellValue(tableList[t].getName());
			cell.setCellStyle(style2);
		}
		matchScheduleSheet.setRepeatingRows(new CellRangeAddress(0, 1, 0, 0));
	}
	
	public static void serializeSchedule(Match[] matches, Table[] tableList) {
		
		MatchScheduleSerializer.tableList = tableList;
		
		//Blank workbook
        matchScheduleDocument = new XSSFWorkbook();
        style2 = matchScheduleDocument.createCellStyle();
        		
        //Create a blank sheet
        matchScheduleSheet = matchScheduleDocument.createSheet("Match Schedule");
        
        createTitle();
        
        for(int m=0; m<matches.length; m++) {
        	Row row = matchScheduleSheet.createRow(m+2);
        	Cell cell = row.createCell(0);
            cell.setCellValue(matches[m].matchNumber());
            cell.setCellStyle(style2);
            cell = row.createCell(1);
            cell.setCellValue(matches[m].matchTime());
            cell.setCellStyle(style2);
            
            for(int i=0; i<matches[i].teams().length; i++) {
            	cell = row.createCell(matches[m].tables()[i].getNumber()+1);
                cell.setCellValue(matches[m].teams()[i].teamNumber());
                cell.setCellStyle(style2);
            }
        }
        
        autoSizeColumns();
        
        matchScheduleSheet.setAutobreaks(true);
        
        PrintSetup printSetup = matchScheduleSheet.getPrintSetup();
        printSetup.setFitHeight((short)0);
        printSetup.setFitWidth((short)1);
        matchScheduleSheet.setFitToPage(true);
        
        try
        {
            FileOutputStream out = new FileOutputStream(new File("howtodoinjava_demo.xlsx"));
            matchScheduleDocument.write(out);
            out.close();
            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	private static void autoSizeColumns() {
		for(int i=0; i<=7; i++) {
			matchScheduleSheet.setDefaultColumnWidth(10);;
		}
	}
}
