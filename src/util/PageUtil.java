package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PageUtil {
	protected static DbUtil dbUtil = new DbUtil();
	private static PreparedStatement stmt = null;
	
	//查询总条数
	public static int gettotalcount(String sql) {
		int count = -1;
	try {
		stmt = dbUtil.getConnection().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return count;
	}
	
	
	//获得正确的页码
	public int[] getPage(int currentPage,int totalPage) {
		if(currentPage == 1 || currentPage == 2) {																//当前页为前两页
			int [] pageArr = {1,2,3,4,5};
			return pageArr;
 		}else if(totalPage == currentPage + 1) {
 			int [] pageArr = {currentPage - 3,currentPage - 2,currentPage - 1,currentPage ,currentPage + 1};	//当前页为倒数第二页
 			return pageArr;
 		}else if(totalPage == currentPage) {
 			int [] pageArr = {currentPage - 4,currentPage - 3,currentPage - 2,currentPage - 1,currentPage};		//当前页为倒数第一页
			return pageArr;
 		}else {																									//一般情况
 			int [] pageArr = {currentPage - 2,currentPage - 1,currentPage,currentPage + 1,currentPage + 2};
			return pageArr;
 		}
	}
	
	//获得总页数
	public int getTotalPage(int totalRecord,int pageSize) {
		int totalPage = totalRecord / pageSize;
		if(totalRecord % pageSize !=0){
			totalPage = totalPage + 1;
		}
//		if(totalRecord / pageSize == 0) {	//不足一页时
//			totalPage = 1 ;
//		}
		return totalPage;
	}
	public static void main(String[] args) {
		
	}

}
