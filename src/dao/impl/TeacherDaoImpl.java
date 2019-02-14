package dao.impl;

import java.sql.PreparedStatement;

import util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.ITeacherDao;
import model.Teacher;
/**
 * 教师数据库操作
 * @author Administrator
 */
public class TeacherDaoImpl extends BaseDaoImpl implements ITeacherDao{
	private PreparedStatement stmt = null;
	@Override
	public int TeacherLogin(Teacher teacher) throws SQLException{	
		try {
			String sql = "select count(*) from Teacher where Tsn=? and Tpsw=?";
			DbUtil dbutil = new DbUtil();
			stmt = dbutil.getConnection().prepareStatement(sql);
			stmt.setString(1, teacher.getTpsw());
			stmt.setString(2, teacher.getTname());
			ResultSet rs = stmt.executeQuery();
			int count = -1;
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			return count;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/*
	 * 查询教师号
	 */
	@Override
	public String getTsn(String Tname) throws SQLException {
		String sql = "select Tsn from Teacher where Tname =?";
		stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1,Tname);
		ResultSet Trs = stmt.executeQuery();
		String Tsn = null;
		while(Trs.next()) {
			 Tsn = Trs.getString("Tsn");
			 return Tsn;
		}
		return Tsn;
		
	}

	@Override
	public String getTname(String Tsn) throws SQLException {
		String sql = "select Tname from Teacher where Tsn = ?";
		stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1,Tsn);
		ResultSet Trs = stmt.executeQuery();
		String Tname = null;
		while(Trs.next()) {
			Tname = Trs.getString("Tname");
			 return Tname;
		}
		return Tsn;
	}

}
