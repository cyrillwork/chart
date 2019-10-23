package com.cyrillwork.chart.service.excel;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * Writes the report to the output stream
 *
 */
public class Writer {
    /**
     * Writes the report to the output stream
     */
    public static void write(HttpServletResponse response, HSSFSheet worksheet)
    {
        try {
            // Retrieve the output stream
            ServletOutputStream outputStream = response.getOutputStream();
            // Write to the output stream
            worksheet.getWorkbook().write(outputStream);
            // Flush the stream
            outputStream.flush();
        } catch (Exception e) {
            System.out.println("Unable to write report to the output stream");
        }
    }
}