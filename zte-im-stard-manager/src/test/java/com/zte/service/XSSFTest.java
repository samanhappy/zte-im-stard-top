package com.zte.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class XSSFTest {

	@Test
	public void writeFile() throws Exception {
		Workbook wb = new XSSFWorkbook(new FileInputStream(new File(
				"D:/test/11.xlsx"))); // or new HSSFWorkbook();
		CreationHelper creationHelper = wb.getCreationHelper();
		Sheet sheet = wb.getSheet("Sheet1");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow((short) 1);
		// Create a cell and put a value in it.
		Cell cell = row.createCell((short) 0);
		cell.setCellValue(1);

		// numeric value
		row.createCell(1).setCellValue(1.2);

		// plain string value
		row.createCell(2).setCellValue("This is a string cell");

		// rich text string
		RichTextString str = creationHelper.createRichTextString("Apache");
		Font font = wb.createFont();
		font.setItalic(true);
		font.setUnderline(Font.U_SINGLE);
		str.applyFont(font);
		row.createCell(3).setCellValue(str);

		// boolean value
		row.createCell(4).setCellValue(true);

		// formula
		row.createCell(5).setCellFormula("SUM(A1:B1)");

		// date
		CellStyle style = wb.createCellStyle();
		style.setDataFormat(creationHelper.createDataFormat().getFormat(
				"m/d/yy h:mm"));
		cell = row.createCell(6);
		cell.setCellValue(new Date());
		cell.setCellStyle(style);

		// hyperlink
		row.createCell(7).setCellFormula("SUM(A1:B1)");
		cell.setCellFormula("HYPERLINK(\"http://google.com\",\"Google\")");

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("D:/test/111.xlsx");
		wb.write(fileOut);
		fileOut.close();
	}
}
