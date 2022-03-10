package com.jointthinker.module.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HSSFManager {
	private short colspan = 0;
	private int rowNum = 0;
	
	public static void main(String[] args) {
		
	}
	public  Object getValue(String dataindex,Map map) {
		
		Object returnvalue = null;
		Set keySet = map.keySet();
		Iterator it = keySet.iterator();
		while(it.hasNext()) {
			
			String key = (String)it.next();
			Object value = map.get(key);
			if(dataindex.equals(key)){
				
				returnvalue=value;
			}
		}
		return returnvalue;
	}
	
	public void generateExcel2007Data(XSSFWorkbook book, String excelhead, String filename, JSONArray view) throws JSONException {
		
		short offset = 0;
		List headers = new ArrayList();//报表表头
		List datains = new ArrayList();//字段
		XSSFFont font2 = book.createFont();
		font2.setFontName("宋体");
		font2.setFontHeight((short) 200);
		XSSFCellStyle cellstyle = book.createCellStyle();// 创建普通单元格style
		String[] excelstr = excelhead.split(",");
		for(int i=0; i<excelstr.length; i++) {
			
			String excel = excelstr[i];
			String[] substr = excel.split(":");
			datains.add(substr[0]);
			headers.add(substr[1]);
		}
		// 写标题
		XSSFSheet sheet = this.generateHeaderRows2007(book, filename, headers, false);
		int rowNum = 2;
		//根据两个字段取值
		if (view!=null && view.length()>0){
			for(int j=0; j<view.length(); j++) {
				JSONObject record=view.getJSONObject(j);
				XSSFRow rowItem = sheet.createRow(rowNum++);
				offset = 0;
				for(int i=0; i<datains.size(); i++) {
					String dataindex = (String)datains.get(i);
					String returnstr = null;
					Object returnvalue=null;
					if(record.has(dataindex)){
						returnvalue = record.get(dataindex);
						if(returnvalue != null) {
							returnstr = returnvalue.toString();
							returnstr = returnstr.replaceAll("</?[^>]+>","");
							returnstr = returnstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
							returnstr = returnstr.replace("&lt;", "<");
							returnstr = returnstr.replace("&gt;", ">");
							returnstr = returnstr.replace("&quot;", "\"");
							returnstr = returnstr.replace("&nbsp;", "");
							returnstr = returnstr.replaceAll(",}$", "}");
							returnstr = returnstr.replace("\r", "\\r");
							returnstr = returnstr.replace("\n", "\\n");
						}
					}
					generateCell2007(book, rowItem, offset++, returnstr, font2, cellstyle,XSSFCellStyle.ALIGN_GENERAL, false);
					//System.out.println("dataindex:" + dataindex  + "---value:" + returnstr);
					if(returnvalue != null) {
						int size=0;
						if(isContainChinese(returnvalue.toString())){
							size = returnvalue.toString().length()*512;
						}else{
							size = returnvalue.toString().length()*256;
						}
						
						if(sheet.getColumnWidth(i)<size&&size<10000){
							sheet.setColumnWidth(i, size);
						}
					}
					
					//sheet.autoSizeColumn((short)i);
				}
				
			}			
		}
	};
	public static boolean isContainChinese(String str) {

	        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	        Matcher m = p.matcher(str);
	        if (m.find()) {
	            return true;
	        }
	        return false;
	}
	public void generateExcel2007Data(XSSFWorkbook book, String rowtitle, String excelhead, String filename, List labelList, List valueList) {
		
		short offset = 0;
		List headers = new ArrayList();//报表表头
		List datains = new ArrayList();//字段
		XSSFFont font2 = book.createFont();
		font2.setFontName("宋体");
		font2.setFontHeight((short) 200);
		XSSFCellStyle cellstyle = book.createCellStyle();// 创建普通单元格style
		String[] excelstr = excelhead.split(",");
		for(int i=0; i<excelstr.length; i++) {
			
			String excel = excelstr[i];
			String[] substr = excel.split(":");
			datains.add(substr[0]);
			headers.add(substr[1]);
		}
		
		List<String> title = new ArrayList<String>();// 标题名字
		List<String> title_size = new ArrayList<String>();// 标题大小 
		String[] excelstr1 = rowtitle.split(",");
		for (int k=0; k<excelstr1.length; k++) {
			title.add(excelstr1[k].split(":")[0]);
			title_size.add(excelstr1[k].split(":")[1]);
			colspan += Short.parseShort(excelstr1[k].split(":")[1]);
		}
		
		// 写标题
		XSSFSheet sheet = this.generateHeaderRows2007(book, filename, false);
		this.generateHeaderTitle(sheet, title, title_size, false);//第一层标题
		this.generateHeaderTitle(sheet, headers, null, false);//第二层标题
		//根据两个字段取值
		if(labelList != null && labelList.size()>0) {
			for(int j=0; j<valueList.size(); j++) {
				
				XSSFRow rowItem = sheet.createRow(rowNum++);
				offset = 0;
				List subValueList = (List)valueList.get(j);
				Map map = new HashMap();
				if(subValueList != null && subValueList.size()>0) {
					for(int k=0; k<subValueList.size(); k++) {
						
						String label = (String)labelList.get(k);
						Object value = subValueList.get(k);
						map.put(label, value);
					}					
				}
				for(int i=0; i<datains.size(); i++) {
					
					String dataindex = (String)datains.get(i);
					Object returnvalue = getValue(dataindex, map);
					String returnstr = null;
					if(returnvalue != null) {
						returnstr = returnvalue.toString();
						returnstr = returnstr.replaceAll("</?[^>]+>","");
						returnstr = returnstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
						returnstr = returnstr.replace("&lt;", "<");
						returnstr = returnstr.replace("&gt;", ">");
						returnstr = returnstr.replace("&quot;", "\"");
						returnstr = returnstr.replace("&nbsp;", "");
						returnstr = returnstr.replaceAll(",}$", "}");
						returnstr = returnstr.replace("\r", "\\r");
						returnstr = returnstr.replace("\n", "\\n");
						

					}
					generateCell2007(book, rowItem, offset++, returnstr, font2, cellstyle,XSSFCellStyle.ALIGN_GENERAL, false);
					//System.out.println("dataindex:" + dataindex  + "---value:" + returnstr);
				}
			}			
		}
	}
	
	public void generateExcel2007Data(XSSFWorkbook book, String excelhead, String filename, List labelList, List valueList) {
		
		short offset = 0;
		List headers = new ArrayList();//报表表头
		List datains = new ArrayList();//字段
		XSSFFont font2 = book.createFont();
		font2.setFontName("宋体");
		font2.setFontHeight((short) 200);
		XSSFCellStyle cellstyle = book.createCellStyle();// 创建普通单元格style
		String[] excelstr = excelhead.split(",");
		for(int i=0; i<excelstr.length; i++) {
			
			String excel = excelstr[i];
			String[] substr = excel.split(":");
			datains.add(substr[0]);
			headers.add(substr[1]);
		}
		// 写标题
		XSSFSheet sheet = this.generateHeaderRows2007(book, filename, headers, false);
		int rowNum = 2;
		//根据两个字段取值
		if(labelList != null && labelList.size()>0) {
			for(int j=0; j<valueList.size(); j++) {
				
				XSSFRow rowItem = sheet.createRow(rowNum++);
				offset = 0;
				List subValueList = (List)valueList.get(j);
				Map map = new HashMap();
				if(subValueList != null && subValueList.size()>0) {
					for(int k=0; k<subValueList.size(); k++) {
						
						String label = (String)labelList.get(k);
						Object value = subValueList.get(k);
						map.put(label, value);
					}					
				}
				for(int i=0; i<datains.size(); i++) {
					
					String dataindex = (String)datains.get(i);
					Object returnvalue = getValue(dataindex, map);
					String returnstr = null;
					if(returnvalue != null) {
						
						
						returnstr = returnvalue.toString();
						returnstr = returnstr.replaceAll("</?[^>]+>","");
						returnstr = returnstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
						returnstr = returnstr.replaceAll("\'","\\\\'");
						returnstr = returnstr.replace("&lt;", "<");
						returnstr = returnstr.replace("&gt;", ">");
						returnstr = returnstr.replace("&quot;", "\"");
						returnstr = returnstr.replace("&nbsp;", "");
						returnstr = returnstr.replaceAll(",}$", "}");
						returnstr = returnstr.replace("\r", "\\r");
						returnstr = returnstr.replace("\n", "\\n");
						

					}
					generateCell2007(book, rowItem, offset++, returnstr, font2, cellstyle,XSSFCellStyle.ALIGN_GENERAL, false);
					//System.out.println("dataindex:" + dataindex  + "---value:" + returnstr);
				}
			}			
		}
	}
	
	/* public void generateExcelData(HSSFWorkbook book, String excelhead, String filename, List labelList, List valueList) {
		
		short offset = 0;
		List headers = new ArrayList();//报表表头
		List datains = new ArrayList();//字段
		HSSFFont font2 = book.createFont();
		font2.setFontName("宋体");
		font2.setFontHeight((short) 200);
		HSSFCellStyle cellstyle = book.createCellStyle();// 创建普通单元格style
		//cellstyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
		//cellstyle.setLocked(true);
		//cellstyle.setWrapText(true);
		String[] excelstr = excelhead.split(",");
		for(int i=0; i<excelstr.length; i++) {
			
			String excel = excelstr[i];
			String[] substr = excel.split(":");
			datains.add(substr[0]);
			headers.add(substr[1]);
		}
		// 写标题
		HSSFSheet sheet = this.generateHeaderRows(book, filename, headers, false);
		int rowNum = 2;
		//根据两个字段取值
		if(labelList != null && labelList.size()>0) {
			for(int j=0; j<valueList.size(); j++) {
				
				HSSFRow rowItem = sheet.createRow(rowNum++);
				offset = 0;
				List subValueList = (List)valueList.get(j);
				Map map = new HashMap();
				if(subValueList != null && subValueList.size()>0) {
					for(int k=0; k<subValueList.size(); k++) {
						
						String label = (String)labelList.get(k);
						Object value = subValueList.get(k);
						map.put(label, value);
					}					
				}
				for(int i=0; i<datains.size(); i++) {
					
					String dataindex = (String)datains.get(i);
					Object returnvalue = getValue(dataindex, map);
					String returnstr = null;
					if(returnvalue != null) {
						
						
						returnstr = returnvalue.toString();
						returnstr = returnstr.replaceAll("</?[^>]+>","");
						returnstr = returnstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
						returnstr = returnstr.replace("&lt;", "<");
						returnstr = returnstr.replace("&gt;", ">");
						returnstr = returnstr.replace("&quot;", "\"");
						returnstr = returnstr.replace("&nbsp;", "");
						returnstr = returnstr.replaceAll(",}$", "}");
						returnstr = returnstr.replace("\r", "\\r");
						returnstr = returnstr.replace("\n", "\\n");
						

					}
					generateCell(book, rowItem, offset++, returnstr, font2, cellstyle,HSSFCellStyle.ALIGN_GENERAL, false);
					//System.out.println("dataindex:" + dataindex  + "---value:" + returnstr);
				}
			}			
		}
	}
	*/
	protected void generateCell2007(XSSFWorkbook book, XSSFRow row, short offset,String value, short fontSize, short align, XSSFFont font,XSSFCellStyle style) {
		generateCell2007(book, row, offset, value, font, style, align, false);
	}
	
	/* protected void generateCell(HSSFWorkbook book, HSSFRow row, short offset,String value, short fontSize, short align, HSSFFont font,HSSFCellStyle style) {
		generateCell(book, row, offset, value, font, style, align, false);
	}*/

	protected void generateCell2007(XSSFWorkbook book, XSSFRow row, short offset,String value, XSSFFont font, XSSFCellStyle style, short align,boolean isNumeric) {

		style.setFont(font);
		if (align != -1)
			style.setAlignment(align); // 文字对齐方式
		XSSFCell cell = row.createCell(offset);
		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(style);
		if (!isNumeric) {
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			//cell.setCellValue(value);
			cell.setCellValue(new XSSFRichTextString(value).getString());
			//cell.setCellValue((value != null && !"".equals(value))?"'" + value:value);
		} else {
			cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Integer.parseInt(value));
		}
	}
//	protected void generateCell(HSSFWorkbook book, HSSFRow row, short offset,String value, HSSFFont font, HSSFCellStyle style, short align,boolean isNumeric) {
//
//		style.setFont(font);
//		if (align != -1)
//			style.setAlignment(align); // 文字对齐方式
//		HSSFCell cell = row.createCell(offset);
//		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cell.setCellStyle(style);
//		if (!isNumeric) {
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//			//cell.setCellValue(value);
//			cell.setCellValue(new HSSFRichTextString(value).getString());
//			//cell.setCellValue((value != null && !"".equals(value))?"'" + value:value);
//		} else {
//			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//			cell.setCellValue(Integer.parseInt(value));
//		}
//	}
	protected XSSFSheet generateHeaderRows2007title(XSSFWorkbook book, String sheetName,List titles, boolean gridsPrinted) {
		
		int rowNum = 0;
		XSSFSheet sheet = book.createSheet("sheet");
		sheet.setPrintGridlines(true);
		XSSFPrintSetup print = sheet.getPrintSetup();
		print.setLandscape(gridsPrinted);
		XSSFRow row = sheet.createRow(rowNum++);
		// 创建标题字体
		XSSFFont font0 = book.createFont();
		font0.setFontName("宋体");
		font0.setFontHeight((short) 400);
		// 创建栏目字体
		XSSFFont font1 = book.createFont(); // 设置字体
		font1.setFontName("宋体");
		font1.setFontHeight((short) 400);
		// 创建内容字体
		XSSFFont font2 = book.createFont(); // 设置字体
		font2.setFontName("宋体");
		font2.setFontHeight((short) 200);
		// 总标题风格
		XSSFCellStyle titlestyle = book.createCellStyle();
		// 创建标题栏style
		XSSFCellStyle headerstyle = book.createCellStyle();
		generateCell2007(book, row, (short) 0, sheetName, (short) 400,XSSFCellStyle.ALIGN_CENTER, font0, titlestyle);
		
		
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), (short)0, row.getRowNum(), (short)(titles.size()-1)));
		XSSFRow header = sheet.createRow(rowNum++);
		short offset = 0;
		for (int i = 0; i < titles.size(); i++) {
			String title = (String)titles.get(i);
			generateHeader2007(book, header, offset++, title, headerstyle);
		}
		return sheet;
	}
	
	protected XSSFSheet generateHeaderRows2007(XSSFWorkbook book, String sheetName, boolean gridsPrinted) {
		
		XSSFSheet sheet = book.createSheet("sheet");
		sheet.setPrintGridlines(true);
		XSSFPrintSetup print = sheet.getPrintSetup();
		print.setLandscape(gridsPrinted);
		XSSFRow row = sheet.createRow(rowNum++);
		// 创建标题字体
		XSSFFont font0 = book.createFont();
		font0.setFontName("宋体");
		font0.setFontHeight((short) 400);
		// 创建栏目字体
		XSSFFont font1 = book.createFont(); // 设置字体
		font1.setFontName("宋体");
		font1.setFontHeight((short) 400);
		// 创建内容字体
		XSSFFont font2 = book.createFont(); // 设置字体
		font2.setFontName("宋体");
		font2.setFontHeight((short) 200);
		// 总标题风格
		XSSFCellStyle titlestyle = book.createCellStyle();
		// 创建标题栏style
		XSSFCellStyle headerstyle = book.createCellStyle();
		generateCell2007(book, row, (short) 0, sheetName, (short) 400,XSSFCellStyle.ALIGN_CENTER, font0, titlestyle);
		//sheet.addMergedRegion(CellRangeAddress a)
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), (short)0, row.getRowNum(), colspan - 1));
		return sheet;
	}
	
	protected XSSFSheet generateHeaderTitle(XSSFSheet sheet, List<String> titles, List<String> titlesize, boolean gridsPrinted) {
		XSSFRow header = sheet.createRow(rowNum++);
		XSSFWorkbook book = sheet.getWorkbook();
		// 创建标题栏style
		XSSFCellStyle headerstyle = book.createCellStyle();
		short offset = 0;
		if (titlesize == null || titlesize.size() == 0) {
			for (int i = 0; i < titles.size(); i++) {
				String title = (String)titles.get(i);
				generateHeader2007(book, header, offset++, title, headerstyle);
			}
		} else {
			for (int i = 0; i < titles.size(); i++) {
				String title = (String)titles.get(i);
				generateHeader2007(book, header, offset, title, headerstyle);
				short temp = Short.parseShort(titlesize.get(i));
				sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), offset, offset + temp - 1));
				offset += temp;
			}
		}
		return sheet;
	}
	
	protected XSSFSheet generateHeaderRows2007(XSSFWorkbook book, String sheetName,List titles, boolean gridsPrinted) {
		
		int rowNum = 0;
		XSSFSheet sheet = book.createSheet("sheet");
		sheet.setPrintGridlines(true);
		XSSFPrintSetup print = sheet.getPrintSetup();
		print.setLandscape(gridsPrinted);
		XSSFRow row = sheet.createRow(rowNum++);
		// 创建标题字体
		XSSFFont font0 = book.createFont();
		font0.setFontName("宋体");
		font0.setFontHeight((short) 400);
		// 创建栏目字体
		XSSFFont font1 = book.createFont(); // 设置字体
		font1.setFontName("宋体");
		font1.setFontHeight((short) 400);
		// 创建内容字体
		XSSFFont font2 = book.createFont(); // 设置字体
		font2.setFontName("宋体");
		font2.setFontHeight((short) 200);
		// 总标题风格
		XSSFCellStyle titlestyle = book.createCellStyle();
		// 创建标题栏style
		XSSFCellStyle headerstyle = book.createCellStyle();
		generateCell2007(book, row, (short) 0, sheetName, (short) 400,XSSFCellStyle.ALIGN_CENTER, font0, titlestyle);
		//sheet.addMergedRegion(CellRangeAddress a)
		//sheet.addMergedRegion(new Region(row.getRowNum(), (short) 0, row.getRowNum(), (short) (titles.size() - 1)));
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), (short)0, row.getRowNum(), (short)(titles.size()-1)));
		XSSFRow header = sheet.createRow(rowNum++);
		short offset = 0;
		for (int i = 0; i < titles.size(); i++) {
			String title = (String)titles.get(i);
			generateHeader2007(book, header, offset++, title, headerstyle);
			sheet.setColumnWidth(i, title.length()*512);
		}
		return sheet;
	}


	/* protected HSSFSheet generateHeaderRows(HSSFWorkbook book, String sheetName,List titles, boolean gridsPrinted) {
		
		int rowNum = 0;
		HSSFSheet sheet = book.createSheet("sheet");
		sheet.setGridsPrinted(true);
		HSSFPrintSetup print = sheet.getPrintSetup();
		print.setLandscape(gridsPrinted);
		HSSFRow row = sheet.createRow(rowNum++);
		// 创建标题字体
		HSSFFont font0 = book.createFont();
		font0.setFontName("宋体");
		font0.setFontHeight((short) 400);
		// 创建栏目字体
		HSSFFont font1 = book.createFont(); // 设置字体
		font1.setFontName("宋体");
		font1.setFontHeight((short) 400);
		// 创建内容字体
		HSSFFont font2 = book.createFont(); // 设置字体
		font2.setFontName("宋体");
		font2.setFontHeight((short) 200);
		// 总标题风格
		HSSFCellStyle titlestyle = book.createCellStyle();
		// 创建标题栏style
		HSSFCellStyle headerstyle = book.createCellStyle();
		generateCell(book, row, (short) 0, sheetName, (short) 400,HSSFCellStyle.ALIGN_CENTER, font0, titlestyle);
		//sheet.addMergedRegion(CellRangeAddress a)
		sheet.addMergedRegion(new Region(row.getRowNum(), (short) 0, row.getRowNum(), (short) (titles.size() - 1)));
		HSSFRow header = sheet.createRow(rowNum++);
		short offset = 0;
		for (int i = 0; i < titles.size(); i++) {
			String title = (String)titles.get(i);
			generateHeader(book, header, offset++, title, headerstyle);
		}
		return sheet;
	}*/

	
	public void generateHeader2007(XSSFWorkbook book, XSSFRow row, short offset,String value, XSSFCellStyle style) {

		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setLocked(true);
		style.setAlignment(HorizontalAlignment.CENTER);
		//style.setWrapText(true);
		XSSFCell cell = row.createCell(offset);
		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(style);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(new XSSFRichTextString(value).getString());
	}
	
	/* public void generateHeader(HSSFWorkbook book, HSSFRow row, short offset,String value, HSSFCellStyle style) {

		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setLocked(true);
		//style.setWrapText(true);
		HSSFCell cell = row.createCell(offset);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(style);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		//cell.setCellValue(value);
		cell.setCellValue(new HSSFRichTextString(value).getString());
	}*/

	/**
	 * 把0.999999变成99.9%
	 * 
	 * @param d
	 * @return
	 */
	protected String formatPercent(Double d) {
		if(d==null){
			return "0.0%";
		}
		return formatPercent(d.doubleValue());
	}
	
	protected String formatPercent(double d) {
		return ((int) (d * 1000)) / 10.0 + "%";
	}
}
