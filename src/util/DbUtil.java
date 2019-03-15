package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
	建立连接，关闭资源
 */
public class DbUtil {
	private String dbUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=teacherdb";
	private String dbUser = "sa";
	private String dbPassword = "123456";
	private String jdbcName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public Connection connection = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	public Connection getConnection(){		//建立连接
		try {
			Class.forName(jdbcName);
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeCon() throws SQLException{		//关闭资源
		if(rs!=null) {
			rs.close();
		}
		if(stmt!=null) {
			stmt.close();
		}
		if(connection != null) {
			connection.close();
		}
			
	}
	
	
	//对于JDBC查询的封装，直接返回结果集
	//params：按顺序的预编译参数
	public  ResultSet getResultSet(String sql,List params) throws SQLException {  
		connection = getConnection();
		stmt = connection.prepareStatement(sql);
		if(params!=null) {									//判断是否有预编译参数要设置
			for(int i=0; i<params.size(); i++) {
				if(params.get(i) instanceof String) {
					stmt.setString(i+1, params.get(i).toString());
				}else if(params.get(i) instanceof Integer) {
					stmt.setInt(i+1, (int) params.get(i));
				}else if(params.get(i) instanceof Date) {
					stmt.setDate(i+1, (Date) params.get(i));
					System.out.println("date = " + (Date)params.get(i));
				}			
			}
		}
		return stmt.executeQuery();
	}
	
		//对于JDBC更新的封装，直接返回结果集
		//params：按顺序的预编译参数
		public  int getUpdateResult(String sql,List params) throws SQLException {  
			connection = getConnection();
			stmt = connection.prepareStatement(sql);
			if(params!=null) {									//判断是否有预编译参数要设置
				for(int i=0; i<params.size(); i++) {
					if(params.get(i) instanceof String) {
						stmt.setString(i+1, params.get(i).toString());
					}else if(params.get(i) instanceof Integer) {
						stmt.setInt(i+1, (int) params.get(i));
					}else if(params.get(i) instanceof Date) {
						stmt.setDate(i+1, (Date) params.get(i));
					}			
				}
			}
			int result = stmt.executeUpdate();
			return result;
		}
		
		
		//处理导入excel时的空单元格
		public List disposeNullCell(List list) {
			for(Object i : list) {
				if(i == null || "".equals(i)) {
					i = " ";
				}
			}
			return list;
		}
	
	public static void main(String[] args) throws SQLException {
		DbUtil dbUtil = new DbUtil();
		dbUtil.getConnection();
		
	}

}