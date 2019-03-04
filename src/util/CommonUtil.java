package util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class CommonUtil {
	
	//将字符串型的数据转化为日期型
	public Date stringToDate(String str) {		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		// String转Date
		try {
			date = format.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	//util.Date转化为sql.Date
	public java.sql.Date utilToSql(Date utildate) {			
		java.sql.Date sqlDate=new java.sql.Date(utildate.getTime());
		return sqlDate;
		
	}
	
	//util.Date转化为sql.Date
		public java.util.Date sqlToUtil(java.sql.Date sqldate) {			
			java.util.Date utilDate=new java.util.Date(sqldate.getTime());
			return utilDate;
			
		}
	
	public void download(String path, HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	//处理分页数据的问题
	public static String disposePageValue(String value) { 
		if(value!=null) {
			if(value.equals("null")) {
				return null;
			}
		}else {
			return null;
		}
		return value;
	}
	
	
	//处理分页数据的问题
	public static String disposeSdeptValue(String sdept) {	
		if(sdept!=null) {
			if(sdept.equals("请选择所在专业") ) {
				return "";
			}
		}
		return sdept;
	}

	
	//将结果集转化成map
	public Map<String,Integer> countRsToMap(ResultSet rs) throws SQLException{
		Map<String,Integer> map = new HashMap<String,Integer>();
		while(rs.next()) {
			map.put(rs.getString("Cname"), rs.getInt("count"));
		}
		return map;
	}
	
	public Map<String,Integer> collegeCountRsToMap(ResultSet rs) throws SQLException{
		Map<String,Integer> map = new HashMap<String,Integer>();
		while(rs.next()) {
			map.put(rs.getString("Dname"), rs.getInt("count"));
		}
		return map;
	}
	
	//各院的经费统计
	public Map<String,Integer> moneyRsToMap(ResultSet rs) throws SQLException{
		Map<String,Integer> map = new HashMap<String,Integer>();
		while(rs.next()) {
			map.put(rs.getString("Cname"), rs.getInt("Pmoney"));
		}
		return map;
	}
	
	//各专业的经费统计
		public Map<String,Integer> countMoneyRsToMap(ResultSet rs) throws SQLException{
			Map<String,Integer> map = new HashMap<String,Integer>();
			while(rs.next()) {
				map.put(rs.getString("Dname"), rs.getInt("Pmoney"));
			}
			return map;
		}
	
	public String disposeAuditValue(String audit) {
		if(audit!=null) {
			if(audit.equals("0")) {
				return "未审核";
			}else if(audit.equals("1")) {
				return "审核通过";
			}else if(audit.equals("2")) {
				return "审核未通过";
			}
		}
		return "系统出错！";
	}
	
	public String disposeMessageValue(String message) {
		if(message == null) {
			return "无";
		}
		return message;
	}
	public static void main(String[] args) {
	
	}





	
}
