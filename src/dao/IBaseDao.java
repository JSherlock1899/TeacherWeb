package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IBaseDao {
	
	/**
	 * 数据库操作工具类
	 * @throws SQLException 
	 */
	
	//关闭资源
	public void closeCon() throws SQLException;
		
	
	
	
	 // 查询数据函数
	public ResultSet select(String sql)throws SQLException, NullPointerException, ClassNotFoundException;
	
	
	 // 更新数据函数
	public int update(String sql)throws SQLException, NullPointerException, ClassNotFoundException;
	   
}
