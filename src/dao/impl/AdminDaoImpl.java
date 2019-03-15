package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.IAdminDao;
import dao.IBaseDao;
import model.Admin;
import util.DbUtil;

/**
 * 管理员数据库操作
 * @author Administrator
 */
public class AdminDaoImpl extends BaseDaoImpl implements IAdminDao{
	static Connection connection = null;
	static PreparedStatement stmt = null;
	DbUtil util = new DbUtil(); 
	
	@Override
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
	
	@Override
	public int AdminJudge(String adminName)throws SQLException{
		try {
			int count = 0;//count为返回值，0为院管理员，1为校管理员,-1为程序异常
			String sql = "select * FROM Admin WHERE Aname = ?";
			PreparedStatement stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, adminName);
			ResultSet rs = stmt.executeQuery();
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

	@Override
	public String getAdminCname(String Aname) throws SQLException {
		String sql = "select Cname from Admin a join College c on a.Csn = c.Csn where Aname = ?";
		PreparedStatement stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1, Aname);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			String Cname = rs.getString("Cname"); 
			return Cname;
		}
		return null;
	}
	
	
	//验证密码是否正确
	@Override
	public boolean verifyPassword(String Aname, String oldPassword) throws SQLException {
		String sql = "select Apsw from Admin where Aname = ?";
		System.out.println(Aname);
		PreparedStatement stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1, Aname);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			String Apsw = rs.getString("Apsw"); 
			if(Apsw.trim().equals(oldPassword)) 
				return true;				
			else
				return false;
		}
		return false;
	}
	
	//修改密码
	@Override
	public int alterPassword(String Aname, String newPassword) throws SQLException {
		String sql = "update Admin set Apsw = ? where Aname = ?";
		PreparedStatement stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1, newPassword);
		stmt.setString(2, Aname);
		int result = stmt.executeUpdate();
		return result;
	}
	}

