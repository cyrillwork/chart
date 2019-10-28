package com.cyrillwork.chart.service.excel;

import com.cyrillwork.chart.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.List;

@Slf4j
public class FillManager {

    /**
     * Fills the report with content
     *
     * @param worksheet
     * @param startRowIndex starting row offset
     * @param startColIndex starting column offset
     * @param datasource the data source
     */
    public static void fillReport(HSSFSheet worksheet, int startRowIndex, int startColIndex, List<Message> datasource) {
        int i;
        // Row offset
        startRowIndex += 2;

        // Create cell style for the body
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
        bodyCellStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyCellStyle.setWrapText(true);


        HSSFCell firstCell = null;
        HSSFCell lastCell  = null;

        // Create body
        for (i = startRowIndex; i+startRowIndex-2< datasource.size()+2; i++)
        {
            int startIndex  = startColIndex;
            // Create a new row
            HSSFRow row = worksheet.createRow((short) i + 1);

            // Id
            HSSFCell cell1 = row.createCell(startIndex);
            cell1.setCellValue(datasource.get(i-2).getId());
            cell1.setCellStyle(bodyCellStyle);
            ++startIndex;

            // Text
            HSSFCell cell2 = row.createCell(startIndex);
            cell2.setCellValue(datasource.get(i-2).getText());
            cell2.setCellStyle(bodyCellStyle);
            ++startIndex;

            // Money
            HSSFCell cellMoney = row.createCell(startIndex);
            String str = datasource.get(i-2).getText();
            str = str.replaceAll("[^0-9]+", " ");

            int number = Integer.parseInt(str.trim());
            cellMoney.setCellValue( number );
            cellMoney.setCellStyle(bodyCellStyle);

            if(firstCell == null)
            {
                firstCell = cellMoney;
            }
            else
            {
                lastCell = cellMoney;
            }
            ++startIndex;

            // User
            HSSFCell cell3 = row.createCell(startIndex);
            cell3.setCellValue(datasource.get(i-2).getUser().getUsername());
            cell3.setCellStyle(bodyCellStyle);
            ++startIndex;

            // Date
            HSSFCell cell4 = row.createCell(startIndex);
            cell4.setCellValue(datasource.get(i-2).getCreateAt().toString());
            cell4.setCellStyle(bodyCellStyle);
            ++startIndex;
        }

        if ((lastCell != null) && (firstCell != null))
        {
            HSSFRow row = worksheet.createRow((short) i + 1);
            HSSFCell cell2 = row.createCell(startColIndex + 2);
            //=SUM(C4:C7)
            //cell2.setCellFormula();

            String formula = String.format("SUM(%s:%s)",
                    firstCell.getAddress().toString(),
                    lastCell.getAddress().toString()
            );

            log.info(formula);

            cell2.setCellFormula(formula);
            cell2.setCellStyle(bodyCellStyle);
        }

    }
}