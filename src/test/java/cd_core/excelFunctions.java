package cd_core;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.Reporter;

public class excelFunctions {
	public Workbook wb;
	
	public excelFunctions(String filePath) {
		try {
			File file = new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			wb = new HSSFWorkbook(inputStream);	
		}
		catch(Exception e) {
			Reporter.log("Error in opening the excel file");
		}
	}
	public String getCellData(String sheetName, int RowId, int ColId) {
		Sheet sheet = wb.getSheet(sheetName);
		DataFormatter formatter = new DataFormatter();
		String val = formatter.formatCellValue(sheet.getRow(RowId).getCell(ColId));
		return val.trim();	
	}
	public void closeWorkBook() {
		try {
			wb.close();
		}
		catch(Exception e) {
			Reporter.log("Error in closing the workbook");
		}
	}
}
