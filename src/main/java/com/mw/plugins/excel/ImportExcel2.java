package com.mw.plugins.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.internal.PackagePropertiesPart;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.mw.base.exception.ServiceException;
import com.mw.utils.Reflections;

public class ImportExcel2 {

	private String fileName;
	private Workbook workbook; // 工作薄对象
	private Sheet sheet;// 工作表对象
	private int headerNum;// 标题行号

	private Date created;
	private Date modified;
	private String lastModifiedBy;
	
	public ImportExcel2() {

	}

	public ImportExcel2(String fileName) throws InvalidFormatException, IOException {
		this(new File(fileName));
	}

	public ImportExcel2(File file) throws InvalidFormatException, IOException {
		this(file.getPath(), new FileInputStream(file));
	}

	public ImportExcel2(MultipartFile multipartFile) throws InvalidFormatException, IOException {
		this(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
	}

	public ImportExcel2(String fileName, InputStream is) throws InvalidFormatException, IOException {
		this.fileName = fileName;
		
		this.workbook = getWorkbook(fileName);

		ZipSecureFile.setMinInflateRatio(0.00000001);

		logger.debug("Initialize success.");
	}

	public void switchSheet(int sheetIndex, int headerNum) {
		this.sheet = this.workbook.getSheetAt(sheetIndex);
		this.headerNum = headerNum;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public Workbook getWorkbook(String fileName) throws FileNotFoundException, IOException {
		Workbook workbook = null;

		try {
			OPCPackage pkg = OPCPackage.open(fileName);
			POIXMLProperties props = new POIXMLProperties(pkg);
			PackagePropertiesPart ppropsPart = props.getCoreProperties().getUnderlyingProperties();

			Date created = ppropsPart.getCreatedProperty().getValue();
			Date modified = ppropsPart.getModifiedProperty().getValue();
			String lastModifiedBy = ppropsPart.getLastModifiedByProperty().getValue();
			
			this.created = created;
			this.modified = modified;
			this.lastModifiedBy = lastModifiedBy;
			
			logger.info("POIXMLProperties-> fileName={}, created={}, modified={}, lastModifiedBy={}", fileName, created, modified, lastModifiedBy);
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}
		
		if (StringUtils.isBlank(fileName)) {
			throw new RuntimeException("Invalid file name.");
		} else if (fileName.toLowerCase().endsWith("xls")) {
			workbook = new HSSFWorkbook(new FileInputStream(new File(fileName)));
		} else if (fileName.toLowerCase().endsWith("xlsx") || fileName.toLowerCase().endsWith("xlsm")) {
			workbook = new XSSFWorkbook(new FileInputStream(new File(fileName)));
		} else {
			throw new RuntimeException("Invalid file type.");
		}

		return workbook;
	}

	public int getNumberOfSheets() {
		return this.workbook.getNumberOfSheets();
	}

	public List<String> getSheetNameList() {
		int numberOfSheets = this.workbook.getNumberOfSheets();

		List<String> sheetNameList = new LinkedList<String>();
		for (int i = 0; i < numberOfSheets; i++) {// 获取每个Sheet表
			Sheet sheet = this.workbook.getSheetAt(i);
			sheetNameList.add(sheet.getSheetName());
		}

		return sheetNameList;
	}

	public List<String> getCellNameList() {
		List<String> cellNameList = new LinkedList<String>();

		Row row = getRow(this.headerNum);
		int physicalNumberOfCells = row.getPhysicalNumberOfCells();
		for (int i = 0; i < physicalNumberOfCells; i++) {
			Cell cell = row.getCell(i);
			cellNameList.add(cell.getStringCellValue());
		}

		return cellNameList;
	}

	/**
	 * 获取行对象
	 * 
	 * @param rownum
	 * @return
	 */
	public Row getRow(int rownum) {
		return this.sheet.getRow(rownum);
	}

	/**
	 * 获取数据行号
	 * 
	 * @return
	 */
	public int getDataRowNum() {
		return headerNum + 1;
	}

	/**
	 * 获取最后一个数据行号
	 * 
	 * @return
	 */
	public int getLastDataRowNum() {
		return this.sheet.getPhysicalNumberOfRows();
	}

	/**
	 * 获取最后一个列号
	 * 
	 * @return
	 */
	public int getLastCellNum() {
		return this.getRow(headerNum).getLastCellNum();
	}
	
	public Date getCreated() {
		return created;
	}

	public Date getModified() {
		return modified;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * 获取单元格值
	 * 
	 * @param row
	 *            获取的行
	 * @param column
	 *            获取单元格列号
	 * @return 单元格值
	 */
	public Object getCellValue(Row row, int column) {
		Object val = "";
		Cell cell = row.getCell(column);
		
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				val = cell.getNumericCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				val = cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				/* val = cell.getCellFormula(); */
				switch (cell.getCachedFormulaResultType()) {
				case Cell.CELL_TYPE_NUMERIC:
					val = cell.getNumericCellValue();
					break;
				case Cell.CELL_TYPE_STRING:
					val = cell.getRichStringCellValue();
					break;
				}
			} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				val = cell.getBooleanCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
				val = cell.getErrorCellValue();
			}
		}
		return val;
	}

	/**
	 * 获取导入数据列表
	 * 
	 * @param cls
	 *            导入对象类型
	 * @param groups
	 *            导入分组
	 */
	public <E> List<E> getDataList(Class<E> cls, int... groups) throws InstantiationException, IllegalAccessException {
		List<Object[]> annotationList = Lists.newArrayList();
		// Get annotation field
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs) {
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					for (int g : groups) {
						if (inGroup) {
							break;
						}
						for (int efg : ef.groups()) {
							if (g == efg) {
								inGroup = true;
								annotationList.add(new Object[] { ef, f });
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] { ef, f });
				}
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms) {
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					for (int g : groups) {
						if (inGroup) {
							break;
						}
						for (int efg : ef.groups()) {
							if (g == efg) {
								inGroup = true;
								annotationList.add(new Object[] { ef, m });
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] { ef, m });
				}
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField) o1[0]).sort()).compareTo(new Integer(((ExcelField) o2[0]).sort()));
			};
		});
		// log.debug("Import column count:"+annotationList.size());
		// Get excel data
		List<E> dataList = Lists.newArrayList();
		for (int i = this.getDataRowNum(); i < this.getLastDataRowNum(); i++) {
			E e = (E) cls.newInstance();
			int column = 0;
			Row row = this.getRow(i);
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList) {
				Object val = this.getCellValue(row, column++);
				if (val != null) {
					ExcelField ef = (ExcelField) os[0];
					// Get param type and type cast
					Class<?> valType = Class.class;
					if (os[1] instanceof Field) {
						valType = ((Field) os[1]).getType();
					} else if (os[1] instanceof Method) {
						Method method = ((Method) os[1]);
						if ("get".equals(method.getName().substring(0, 3))) {
							valType = method.getReturnType();
						} else if ("set".equals(method.getName().substring(0, 3))) {
							valType = ((Method) os[1]).getParameterTypes()[0];
						}
					}
					logger.debug("Import value type: [" + i + "," + column + "] " + valType);
					try {
						if (valType == String.class) {
							String s = String.valueOf(val.toString());
							if (StringUtils.endsWith(s, ".0")) {
								val = StringUtils.substringBefore(s, ".0");
							} else {
								val = String.valueOf(val.toString());
							}
						} else if (valType == Integer.class) {
							val = Double.valueOf(val.toString()).intValue();
						} else if (valType == Long.class) {
							val = Double.valueOf(val.toString()).longValue();
						} else if (valType == Double.class) {
							val = Double.valueOf(val.toString());
						} else if (valType == Float.class) {
							val = Float.valueOf(val.toString());
						} else if (valType == Date.class) {
							val = DateUtil.getJavaDate((Double) val);
						} else {
							if (ef.fieldType() != Class.class) {
								val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
							} else {
								val = Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), "fieldtype." + valType.getSimpleName() + "Type")).getMethod("getValue", String.class).invoke(null, val.toString());
							}
						}
					} catch (NumberFormatException nfe) {
						logger.info("NumberFormatException Error at get cell value-> row={}, column={}, val={}", i, column, val);
						if (StringUtils.isBlank(val.toString())) {
							val = null;
						} else {
							throw new RuntimeException(nfe);
						}
					} catch (Exception ex) {
						logger.info("Exception Error at get cell value-> row={}, column={}, val={}", i, column, val);
						throw new RuntimeException(ex);
					}
					// set entity value
					if (os[1] instanceof Field) {
						Reflections.invokeSetter(e, ((Field) os[1]).getName(), val);
					} else if (os[1] instanceof Method) {
						String mthodName = ((Method) os[1]).getName();
						if ("get".equals(mthodName.substring(0, 3))) {
							mthodName = "set" + StringUtils.substringAfter(mthodName, "get");
						}
						Reflections.invokeMethod(e, mthodName, new Class[] { valType }, new Object[] { val });
					}
				}
				sb.append(val + ", ");
			}
			dataList.add(e);
			logger.debug("Read success: [" + i + "] " + sb.toString());
		}
		return dataList;
	}

	public List<Map<String, Object>> getDataMapListDesc(int maxReadRowLength, boolean isDateDataMode) {
		logger.info("getDataMapListDesc-> fileName={}, sheetName={}", this.fileName, sheet.getSheetName());
		
		
		List<String> cellNameList = getCellNameList();
		int cellLength = cellNameList.size();

		Integer rowReading = 0;
		Integer startRowNb = headerNum + 1;
		Integer endRowNb = null;
		int lastDataRowNb = getLastDataRowNum();
		int firstDataRowNb = getDataRowNum();
		boolean isDataRow = false;

		// get startRowNb
		for (int rowNb = lastDataRowNb; rowNb > firstDataRowNb; rowNb--) {// 從最後一行開始遍歷,遍歷maxReadRowLength筆記錄,最小到數據起始行.
			Row row = getRow(rowNb);
			if (row == null) {
				continue;
			}

			for (int cellNb = 0; cellNb < cellLength; cellNb++) {
				try {
					String cellName = cellNameList.get(cellNb);
					Object cellValue = getCellValue(row, cellNb);
					if (cellValue != null && StringUtils.isNotBlank(cellValue.toString()) && StringUtils.isNotBlank(cellName)) {
						if (!(isDateDataMode && cellNb == 0)) {// first column is date.
							isDataRow = true;
						}
						if (endRowNb == null) {
							endRowNb = rowNb + 1;
						}
					}
				} catch (Exception e) {
					logger.warn("parseData-> rowNb={}, cellNb={}", rowNb, cellNb);
					throw e;
				}
			}

			if (isDataRow) {
				rowReading++;
			}

			if (rowReading >= maxReadRowLength) {
				startRowNb = rowNb;
				break;
			}
		}
		
		List<Map<String, Object>> dataMapList = new LinkedList<Map<String, Object>>();
		
		for (int rowNb = startRowNb; rowNb < endRowNb; rowNb++) {
			Row row = getRow(rowNb);
			if (row == null) { // TODO
				continue;
			}

			Map<String, Object> map = new LinkedHashMap<String, Object>();
			dataMapList.add(map);

			for (int cellNb = 0; cellNb < cellLength; cellNb++) {
				String cellName = cellNameList.get(cellNb);
				cellName = StringUtils.trim(cellName);
				if (isDateDataMode && cellNb == 0) {
					cellName = "DataDate";
				}

				if (StringUtils.isBlank(cellName)) {
					continue;
				}

				try{
					Object cellValue = getCellValue(row, cellNb);
					if (isDateDataMode && cellValue != null && StringUtils.isNotBlank(cellValue.toString())) {
						if (cellNb == 0) {
							map.put(cellName, parseDate(cellValue.toString()));
						} else {
							try{
								map.put(cellName, parseDouble(cellValue.toString()));
							}catch(Exception e){
								map.put(cellName, cellValue.toString());
							}
							
						}
					} else {
						map.put(cellName, cellValue);
					}
				}catch(Exception e){
					logger.warn("rowNbCellNb-> fileName={}, sheetName={}, rowNb={}, cellNb={}", this.fileName, sheet.getSheetName(), rowNb, cellNb);
					throw e;
				}
			}
		}

		logger.info("getDataMapListDesc-> fileName={}, sheetName={}, firstDataRowNb={}, lastDataRowNb={}, startRowNb={}, endRowNb={}", this.fileName, sheet.getSheetName(), firstDataRowNb, lastDataRowNb, startRowNb, endRowNb);
		
		return dataMapList;
	}

	public Date parseDate(String val) {
		try {
			return DateUtils.round(DateUtil.getJavaDate(Double.parseDouble(val)), Calendar.MINUTE);
		} catch (Exception e) {
			logger.warn(String.format("ImportExcel parseDate-> val=%s", val));
			throw new ServiceException("ko", "ParseDate error!");
		}
	}

	public Double parseDouble(String val) {
		try {
			return Double.parseDouble(val.toString());
		} catch (Exception e) {
			logger.warn(String.format("ImportExcel parseDouble-> val=%s", val));
			throw new ServiceException("ko", "parseDouble error!");
		}
	}
	
	public Double toDouble(Object val){
		if(val != null){
			if(StringUtils.isBlank(val.toString())){
				return null;
			}
			
			Double doubleVal = Double.parseDouble(val.toString());
			return doubleVal;
		}
		
		return null;
	}

	private static Logger logger = LoggerFactory.getLogger(ImportExcel2.class);

	/**
	 * 导入测试
	 */
	public static void main(String[] args) throws Exception {

		// ImportExcel2 ei = new ImportExcel2("W://Operations/數據中心 Data Center/數據上傳專用 data upload/測試數據/zh/20150721.xls", 1, 0);
		// System.out.print(sheet.getPhysicalNumberOfRows());
		//
		// for (int i = 0; i <= sheet.getLastRowNum(); i++) {
		// Row row = sheet.getRow(i);
		// if (row == null) {
		// System.err.println("ReadCells(): Row " + i + " was null!");
		// System.exit(2);
		// }
		// }

		String fileName = "D://Work/Project/BI/2017/OP/OP Monthly Formal Data Tagname list_20170322_toXing.xlsx";
		ImportExcel2 importExcel2 = new ImportExcel2(fileName);

		List<String> sheetNameList = importExcel2.getSheetNameList();
		for (int i = 1; i < sheetNameList.size(); i++) {
			String sheetName = sheetNameList.get(i);
			Sheet sheet = importExcel2.workbook.getSheet(sheetName);
			System.out.println(sheetName + ";" + sheet.getPhysicalNumberOfRows());
			
			System.out.println("LastModifiedBy = " + importExcel2.getLastModifiedBy());
			System.out.println("Created = " + importExcel2.getCreated());
			System.out.println("Modified = " + importExcel2.getModified());

			importExcel2.switchSheet(i, 0);
			List<String> cellNameList = importExcel2.getCellNameList();
			System.out.println(cellNameList);

			List<Map<String, Object>> dataMapList = importExcel2.getDataMapListDesc(10, true);
			for (Map<String, Object> dataMap : dataMapList) {
				System.out.println(dataMap);
			}
		}

		// ImportExcel2 ei = new ImportExcel2("D://Work/Project/BI/2017/OP/OP Monthly Formal Data Tagname list_20170322_toXing.xlsx", 1, 0);
		// System.out.print(sheet.getPhysicalNumberOfRows());
		//
		// for (int i = 0; i <= sheet.getLastRowNum(); i++) {
		// Row row = sheet.getRow(i);
		// if (row == null) {
		// System.err.println("ReadCells(): Row " + i + " was null!");
		// System.exit(2);
		// }
		// }
		
		DecimalFormat df = new DecimalFormat("0.00");
		String dfString = df.format(1.00771320506E9);
		System.out.println(dfString);
		System.out.println(Double.parseDouble(dfString));
		System.out.println(Double.parseDouble("1007713205.06"));

	}
}
