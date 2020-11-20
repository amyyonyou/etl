package com.mw.plugins.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.monitorjbl.xlsx.StreamingReader;
import com.mw.base.exception.ServiceException;

public class ImportExcel3 {

	private String fileName;
	private Workbook workbook;

	private List<String> cellNameList; // current sheet
	private List<Map<String, Object>> dataMapList;

	public ImportExcel3() {

	}

	public ImportExcel3(String fileName) {
		this.fileName = fileName;
		try {
			InputStream is = new FileInputStream(new File(fileName));
			workbook = StreamingReader.builder()
					.rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
					.bufferSize(4096) // buffer size to use when reading InputStream to file (defaults to 1024)
					.open(is); // InputStream or File for XLSX file (required)
		} catch (FileNotFoundException e) {
			throw new ServiceException(fileName, e);
		}
	}

	public void close() {
		try {
			this.workbook.close();
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	public List<String> getSheetNameList() {
		List<String> list = Lists.newLinkedList();
		for (Sheet sheet : workbook) {
			list.add(sheet.getSheetName());
		}
		return list;
	}

	public void readDateData(String sheetName, int headerNb, int maxReadRowLength) throws FileNotFoundException {
		Sheet sheet = workbook.getSheet(sheetName);
		logger.info("readDateData-> fileName={}, sheetName={}, headerNb={}, maxReadRowLength={}", fileName, sheet.getSheetName(), headerNb, maxReadRowLength);

		cellNameList = Lists.newLinkedList();
		List<Map<String, Object>> dataMapListTmp = Lists.newLinkedList();
		int r = 0;
		for (Row row : sheet) {
			r++;
			if (r == headerNb) {
				for (Cell cell : row) {
					String cellName = cell.getStringCellValue();
					System.out.println(cellName);
					cellNameList.add(cellName);
				}
				
				System.out.println(cellNameList);
			} else if (r > headerNb) {
				Map<String, Object> dataMap = Maps.newLinkedHashMap();
				boolean isDataRow = false;
				
				int c = 0;
				for (Cell cell : row) {
					if(c == 0){
						dataMap.put(/*cellNameList.get(c)*/ "DataDate", cell.getDateCellValue());
					}else{
						if(cell.getCellType() == CellType.BLANK.getCode()){
							dataMap.put(cellNameList.get(c), null);
						}else{
							isDataRow = true;
							try{
								dataMap.put(cellNameList.get(c), cell.getNumericCellValue());
							}catch(Exception e){
								logger.info("fileName={}, sheetName={}, row={}, cell={}", fileName, sheetName, r, c);
								throw new ServiceException(e);
							}
						}
					}
					
					c++;
				}
				
				if(isDataRow){
					dataMapListTmp.add(dataMap);
				}
			}
		}
		
		int dataMapListTmpSize = dataMapListTmp.size();
		int startRowNb = dataMapListTmpSize - maxReadRowLength;
		if(startRowNb < 0){
			startRowNb = 0;
		}
		
		dataMapList = dataMapListTmp.subList(startRowNb, dataMapListTmpSize);
		
		System.out.println(dataMapList.get(0));
		System.out.println(dataMapList.get(dataMapList.size()-1));
		logger.info("dataMapListTmpSize={}, dataMapListSize={}", dataMapListTmpSize, dataMapList.size());
	}

	public List<String> getCellNameList() {
		return cellNameList;
	}

	public void setCellNameList(List<String> cellNameList) {
		this.cellNameList = cellNameList;
	}

	public List<Map<String, Object>> getDataMapList() {
		return dataMapList;
	}

	public void setDataMapList(List<Map<String, Object>> dataMapList) {
		this.dataMapList = dataMapList;
	}

	private static Logger logger = LoggerFactory.getLogger(ImportExcel3.class);

	/**
	 * 导入测试
	 */
	public static void main(String[] args) throws Exception {
		//String fileName = "\\\\10.225.8.42\\groupdata$\\Operations\\數據中心 Data Center\\生產數據 Production Data\\This Year\\working file\\Monthly\\KPI support report\\電費計算.xlsx";
		//String fileName = "\\\\10.225.8.42\\groupdata$\\DataGroup\\BI-Data\\CS\\KPI\\BI Data V4.9_sent.xlsx";
		String fileName = "D:\\Test\\BI Data V4.9_sent.xlsx";
		ImportExcel3 importExcel3 = new ImportExcel3(fileName);

		List<String> sheetNameList = importExcel3.getSheetNameList();
		System.out.println(sheetNameList);

		importExcel3.readDateData("Customer_Service", 1, 9999);
		importExcel3.close();
		
		
		
		
	}
}
