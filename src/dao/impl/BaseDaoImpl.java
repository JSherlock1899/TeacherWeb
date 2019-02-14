package dao.impl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.IBaseDao;
import util.DbUtil;


public class BaseDaoImpl implements IBaseDao {
protected DbUtil dbUtil = new DbUtil();
private PreparedStatement stmt = null;

	/**
	 * 数据库操作工具类
	 * @throws SQLException 
	 */
	public void closeCon() throws SQLException{
		dbUtil.closeCon();
	}
	
	/**
	 * 查询数据函数
	 */
	public ResultSet select(String sql)throws SQLException, NullPointerException, ClassNotFoundException{
		ResultSet rs = null;
	    try {
	    	PreparedStatement stmt = dbUtil.getConnection().prepareStatement(sql);	
	    	rs = stmt.executeQuery();
	    }catch (Exception e) {
	    	e.printStackTrace();
	    	System.out.println("系统异常");
	    	return null;
	    }
	    	return rs;    
	}
	
	/**
	 *更新数据函数
	 */
	public int update(String sql)throws SQLException, NullPointerException, ClassNotFoundException{
	    try {
	    	PreparedStatement stmt = dbUtil.getConnection().prepareStatement(sql);
	    	int count = stmt.executeUpdate();
			return count;
	    }catch (Exception e) {
	    	e.printStackTrace();
	    	return -1;
	    }
	}
	
	
	
}
