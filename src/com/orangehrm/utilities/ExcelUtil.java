package com.orangehrm.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

/**
 * Excel utility using Apache POI (WorkbookFactory) to read/write xlsx files.
 */
public class ExcelUtil {

	protected String filePath;
	protected String sheetName;
	protected Workbook workbook;
	protected Sheet sheet;

	public ExcelUtil(String filePath, String sheetName) {
		this.filePath = filePath;
		this.sheetName = sheetName;

		File file = new File(filePath);
		if (!file.exists() || file.length() == 0) {
			throw new RuntimeException("Excel file not found or empty: " + file.getAbsolutePath());
		}

//        try (FileInputStream fis = new FileInputStream(filePath)) {
//        	// Use WorkbookFactory instead of direct XSSFWorkbook/HSSFWorkbook
//        	if (filePath.endsWith(".xlsx")) {
//				workbook = new XSSFWorkbook(fis);
//			} else if (filePath.endsWith(".xls")) {
//				workbook = new HSSFWorkbook(fis);
//			} else {
//				throw new RuntimeException("Unsupported Excel file type: " + filePath);
//			}
//        	
//            sheet = workbook.getSheet(sheetName);
//            if (sheet == null) {
//                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in file: " + filePath);
//            }
//            
//        } catch (EncryptedDocumentException | IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to open Excel file: " + filePath, e);
//        }
		try (FileInputStream fis = new FileInputStream(filePath)) {
			// Use WorkbookFactory instead of direct XSSFWorkbook/HSSFWorkbook
			workbook = WorkbookFactory.create(fis);

			sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in file: " + filePath);
			}

		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to open Excel file: " + filePath, e);
		}
	}

	public String getCellData(int row, int col) {
		Row r = sheet.getRow(row);
		if (r == null)
			return "";
		Cell cell = r.getCell(col);
		if (cell == null)
			return "";
		return new DataFormatter().formatCellValue(cell);
	}

	/**
	 * Returns number of rows that have content (uses last row index + 1).
	 */
	public int getRowCount() {
//        return sheet.getLastRowNum() + 1;
		return sheet.getPhysicalNumberOfRows(); // safer than lastRowNum+1
	}

	/**
	 * Returns column count in the header (row 0). Returns 0 if row 0 is missing.
	 */
	public int getColumnCount() {
		Row header = sheet.getRow(0);
//            return header == null ? 0 : header.getLastCellNum();
		return header == null ? 0 : header.getPhysicalNumberOfCells(); // safer
	}

	public List<String> getRowData(int rowIndex) {
		List<String> data = new ArrayList<>();
		Row row = sheet.getRow(rowIndex);
		if (row == null)
			return data;
		for (int c = 0; c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			data.add(cell == null ? "" : new DataFormatter().formatCellValue(cell));
		}
		return data;
	}

	public void setCellData(int rowIndex, int colIndex, String value) {
		try {
			Row row = sheet.getRow(rowIndex);
			if (row == null)
				row = sheet.createRow(rowIndex);
			Cell cell = row.getCell(colIndex);
			if (cell == null)
				cell = row.createCell(colIndex);
			cell.setCellValue(value);

			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				workbook.write(fos);
//                fos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to write to Excel file: " + filePath, e);
		}
	}

	public void close() {
		try {
			if (workbook != null)
				workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}