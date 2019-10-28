package com.cyrillwork.chart.service.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Date;

public class Layouter {

    /**
     * Builds the report layout.
     * <p>
     * This doesn't have any data yet. This is your template.
     */
    public static void buildReport(HSSFSheet worksheet, int startRowIndex, int startColIndex) {
        // Set column widths
        worksheet.setColumnWidth(0, 5000);
        worksheet.setColumnWidth(1, 5000);
        worksheet.setColumnWidth(2, 5000);
        worksheet.setColumnWidth(3, 5000);
        worksheet.setColumnWidth(4, 5000);

        // Build the title and date headers
        buildTitle(worksheet, startRowIndex, startColIndex);
        // Build the column headers
        buildHeaders(worksheet, startRowIndex, startColIndex);
    }

    /**
     * Builds the report title and the date header
     *
     * @param worksheet
     * @param startRowIndex starting row offset
     * @param startColIndex starting column offset
     */
    public static void buildTitle(HSSFSheet worksheet, int startRowIndex, int startColIndex)
    {
        // Create font style for the report title
        Font fontTitle = worksheet.getWorkbook().createFont();
        fontTitle.setBold(true);

        fontTitle.setFontHeight((short) 280);

        // Create cell style for the report title
        HSSFCellStyle cellStyleTitle = worksheet.getWorkbook().createCellStyle();
        cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTitle.setWrapText(true);
        cellStyleTitle.setFont(fontTitle);

        // Create report title
        HSSFRow rowTitle = worksheet.createRow((short) startRowIndex);
        rowTitle.setHeight((short) 500);
        HSSFCell cellTitle = rowTitle.createCell(startColIndex);
        cellTitle.setCellValue("Messages");
        cellTitle.setCellStyle(cellStyleTitle);

        // Create merged region for the report title
        worksheet.addMergedRegion(new CellRangeAddress(0,0,0,5));

        // Create date header
        HSSFRow dateTitle = worksheet.createRow((short) startRowIndex +1);
        HSSFCell cellDate = dateTitle.createCell(startColIndex);
        cellDate.setCellValue("This report was generated at " + new Date());
    }

    /**
     * Builds the column headers
     *
     * @param worksheet
     * @param startRowIndex starting row offset
     * @param startColIndex starting column offset
     */
    public static void buildHeaders(HSSFSheet worksheet, int startRowIndex, int startColIndex) {
        // Create font style for the headers
        Font font = worksheet.getWorkbook().createFont();
        font.setBold(true);

        // Create cell style for the headers
        HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
        headerCellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.FINE_DOTS);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


        headerCellStyle.setWrapText(true);
        headerCellStyle.setFont(font);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);

        // Create the column headers
        HSSFRow rowHeader = worksheet.createRow((short) startRowIndex +2);
        rowHeader.setHeight((short) 500);

        int colIndex = startColIndex;

        HSSFCell cell1 = rowHeader.createCell(colIndex);
        cell1.setCellValue("Id");
        cell1.setCellStyle(headerCellStyle);
        ++colIndex;

        HSSFCell cell2 = rowHeader.createCell(colIndex);
        cell2.setCellValue("Text");
        cell2.setCellStyle(headerCellStyle);
        ++colIndex;

        HSSFCell cellm = rowHeader.createCell(colIndex);
        cellm.setCellValue("Money");
        cellm.setCellStyle(headerCellStyle);
        ++colIndex;


        HSSFCell cell3 = rowHeader.createCell(colIndex);
        cell3.setCellValue("User");
        cell3.setCellStyle(headerCellStyle);
        ++colIndex;

        HSSFCell cell4 = rowHeader.createCell(colIndex);
        cell4.setCellValue("Date");
        cell4.setCellStyle(headerCellStyle);
        ++colIndex;
    }

}
