package dao.impl;

import java.sql.PreparedStatement;

import util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.ITeacherDao;
import model.Teacher;
/**
 * 教师数据库操作
 *
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
	
	
	//查询教师的项目
	public ResultSet getProject(String Tsn,int currentPage,int pageSize) throws SQLException {
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		String sql = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname ,"
				+ "ROW_NUMBER() over (order by Psn) as r from Project p left join Teacher t on p.Tsn = t.Tsn where p.Tsn = ?) "
				+ "as  pp where pp.r between ? and ?";
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Tsn);
			stmt.setInt(2,m);
			stmt.setInt(3,n);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
	
	
	//查询教师的论文
	public ResultSet getPaper(String Tsn,int currentPage,int pageSize) throws SQLException {
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		String sql = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks"
				+ ",ROW_NUMBER() over (order by Pasearchnum) as r from Paper p left join Teacher t on p.Tsn = t.Tsn where p.Tsn = ?) "
				+ "as  pp where pp.r between ? and ?";
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Tsn);
			stmt.setInt(2,m);
			stmt.setInt(3,n);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
	
	
	//查询教师的荣誉
	public ResultSet getHonor(String Tsn,int currentPage,int pageSize) throws SQLException {
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		String sql = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks"
				+ ",ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn where h.Tsn = ?) "
				+ "as  pp where pp.r between ? and ?";
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Tsn);
			stmt.setInt(2,m);
			stmt.setInt(3,n);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
	
	
	//查询教师的专利
	public ResultSet getPatent(String Tsn,int currentPage,int pageSize) throws SQLException {
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		String sql = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,"
				+ "Tname,ROW_NUMBER() over (order by Patnum) as r from Patent p left join Teacher t on p.Tsn = t.Tsn where p.Tsn = ?) "
				+ "as  pp where pp.r between ? and ?";
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Tsn);
			stmt.setInt(2,m);
			stmt.setInt(3,n);
			ResultSet rs = stmt.executeQuery();
			return rs;
	}
	
	
	//获取教师所属学院
		public String getTeacherCollege(String Tsn)throws SQLException{
			String sql = "select Cname from college c join Teacher t on c.Csn = t.Csn where t.Tsn = ?";
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Tsn);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				String teacherCollege = rs.getString("Cname");
				return teacherCollege;
			}
			return null;
		}

}
