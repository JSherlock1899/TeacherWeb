package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.BaseDao;
import dao.IAdminDao;
import model.Admin;
import util.DbUtil;

/**
 * 管理员数据库操作
 * @author Administrator
 */
public class AdminDaoImpl extends BaseDao implements IAdminDao{
	static Connection connection = null;
	static PreparedStatement stmt = null;
	
	public int AdminLogin(Admin admin) throws SQLException{	
		try {
			String sql = "select count(*) from Admin where Apsw=? and Aname=?";
			DbUtil dbutil = new DbUtil();
				stmt = dbutil.getConnection().prepareStatement(sql);
				stmt.setString(2, admin.getAname());
				stmt.setString(1, admin.getApsw());			
			ResultSet rs = stmt.executeQuery();
			int count = -1;
			if(rs.next()) {
				count = rs.getInt(1);
			}
			return count;	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbUtil.closeCon();  //关闭资源
		}
		return 0;
	}
	
	public int AdminJudge(String adminName)throws SQLException{
		try {
			int count = 0;//count为返回值，0为院管理员，1为校管理员,-1为程序异常
			String sql = "select * FROM Admin WHERE Aname = '" + adminName + "'";
			BaseDao baseDao = new BaseDao();
			ResultSet rs = baseDao.select(sql);
			if(rs.next()) {
				String a = "1";
				if(rs.getString("Agrad").equals(a)){		//检验管理员的grad是否为1
					count = 1;
				}
			}
			return count;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbUtil.closeCon();  //关闭资源
		}
		return -1;
	}
	
	}

