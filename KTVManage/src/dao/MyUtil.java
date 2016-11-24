package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import Imp.RoomImp;
import entity.ShowRoomTakenInfo;

public class MyUtil {
	private static RoomImp ri=new RoomImp();;
	static int i;
	private static List<ShowRoomTakenInfo> list=new ArrayList<>();;
    public  static HSSFWorkbook exportForxls(){

//创建工作簿
		  HSSFWorkbook wb=new HSSFWorkbook();
		  
		
// 创建表 
		HSSFSheet sheet=wb.createSheet("已拿房间情况");
		sheet.setColumnWidth(2, 25 * 256);
		sheet.setColumnWidth(3, 25 * 256);
		HSSFRow row1=sheet.createRow(0);
		row1.createCell(0).setCellValue("房号");
		row1.createCell(1).setCellValue("房间类型");
		row1.createCell(2).setCellValue("开始时间");
		row1.createCell(3).setCellValue("结束时间");
        
		list=ri.getRoomTakenInfo();
		for(i=0;i<list.size();i++){
			
			HSSFRow row=sheet.createRow(i+1);
			ShowRoomTakenInfo showRoomTakenInfo=list.get(i);
			HSSFCell cell=row.createCell(0);
			cell.setCellValue(showRoomTakenInfo.getId());
			cell=row.createCell(1);
			cell.setCellValue(showRoomTakenInfo.getTypeName());
			cell=row.createCell(2);
			cell.setCellValue(showRoomTakenInfo.getStartTime());
			cell=row.createCell(3);
			cell.setCellValue(showRoomTakenInfo.getEndTime());
		}
	   
		return wb;
	    	
		
	}

}
