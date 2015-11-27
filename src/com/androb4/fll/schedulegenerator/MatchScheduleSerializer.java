package com.androb4.fll.schedulegenerator;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MatchScheduleSerializer {
	private static XSSFWorkbook matchScheduleDocument;
	private static XSSFSheet matchScheduleSheet;
	private static CellStyle style2;
	private static Match[] matchList;
	private static Table[] tableList;
	private static String eventName;
	
	private static void createTitle() {
		CellStyle titleStyle = matchScheduleDocument.createCellStyle();
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		titleStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setFillForegroundColor(HSSFColor.TURQUOISE.index);
		titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
		titleStyle.setBorderTop(CellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
		titleStyle.setBorderRight(CellStyle.BORDER_THIN);
		titleStyle.setFont(getTitleFont());
		
		Row row = matchScheduleSheet.createRow(0);
		Cell cell;
		for(int c=0; c<=7; c++) {
			cell = row.createCell(c);
			cell.setCellValue(eventName + " - Match Schedule");
			cell.setCellStyle(titleStyle);
		}
		matchScheduleSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		
		row = matchScheduleSheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("Match");
		cell.setCellStyle(titleStyle);
		cell = row.createCell(1);
		cell.setCellValue("Match Time");
		cell.setCellStyle(titleStyle);
		
		for(int t=0; t<tableList.length; t++) {
			cell = row.createCell(t+2);
			cell.setCellValue(tableList[t].getName());
			cell.setCellStyle(titleStyle);
		}
		matchScheduleSheet.setRepeatingRows(new CellRangeAddress(0, 1, 0, 0));
	}
	
	public static void serializeSchedule(Match[] matches, Table[] tableList, String eventName) {
		MatchScheduleSerializer.tableList = tableList;
		matchList = matches;
		MatchScheduleSerializer.eventName = eventName;
		//Blank workbook
        matchScheduleDocument = new XSSFWorkbook();
        style2 = matchScheduleDocument.createCellStyle();
        style2.setAlignment(CellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style2.setBorderBottom(CellStyle.BORDER_THIN);
        style2.setBorderTop(CellStyle.BORDER_THIN);
        style2.setBorderLeft(CellStyle.BORDER_THIN);
        style2.setBorderRight(CellStyle.BORDER_THIN);
        style2.setFont(getDefaultFont());
        
        matchScheduleSheet = matchScheduleDocument.createSheet("Match Schedule");
        
        createTitle();
        
        for(int m=0; m<matches.length; m++) {
        	Row row = matchScheduleSheet.createRow(m+2);
        	Cell cell = row.createCell(0);
            cell.setCellValue(matches[m].matchNumber());
            cell.setCellStyle(style2);
            cell = row.createCell(1);
            cell.setCellValue(Util.minutesToTimeString(matches[m].matchTime()));
            cell.setCellStyle(style2);
            
            for(int c=2; c<=tableList.length+1; c++) {
    			cell = row.createCell(c);
    			if(c-1==matchList[m].tables()[0].getNumber()) {
    				cell.setCellValue(matchList[m].teams()[0].teamNumber());
    			}
    			if(c-1==matchList[m].tables()[1].getNumber()) {
    				cell.setCellValue(matchList[m].teams()[1].teamNumber());
    			}
    			cell.setCellStyle(style2);
    		}
        }
        
        autoSizeColumns();
        matchScheduleSheet.setAutobreaks(true);
        setFooter();
        
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
			matchScheduleSheet.setDefaultColumnWidth(10);
		}
	}
	
	private static void setFooter() {
		Footer footer = matchScheduleSheet.getFooter();
	    footer.setCenter("Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages());
	}
	
	private static XSSFFont getTitleFont() {
		XSSFFont font;
		font = matchScheduleDocument.createFont();
		font.setFontHeightInPoints((short)12);
		font.setFontName("Calibri");
		font.setBold(true);
	    return font;
	}
	
	private static XSSFFont getDefaultFont() {
		XSSFFont font;
		font = matchScheduleDocument.createFont();
		font.setFontHeightInPoints((short)12);
		font.setFontName("Calibri");
	    return font;
	}
}
