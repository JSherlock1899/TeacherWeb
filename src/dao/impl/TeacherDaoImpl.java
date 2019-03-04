package dao.impl;

import java.sql.PreparedStatement;

import util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ITeacherDao;
import model.Patent;
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
	
	
	//查询教师的项目
	@Override
	public ResultSet getProject(String Tsn,int currentPage,int pageSize) throws SQLException {
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		String sql = "select * from (select COUNT(*)OVER() AS totalRecordRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,Tname ,"
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
	@Override
	public ResultSet getPaper(String Tsn,int currentPage,int pageSize) throws SQLException {
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		String sql = "select * from (select COUNT(*)OVER() AS totalRecordRecord,Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks"
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
	@Override
	public ResultSet getHonor(String Tsn,int currentPage,int pageSize) throws SQLException {
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		String sql = "select * from (select COUNT(*)OVER() AS totalRecordRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks"
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
	@Override
	public ResultSet getPatent(String Tsn,int currentPage,int pageSize) throws SQLException {
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		String sql = "select * from (select COUNT(*)OVER() AS totalRecord,Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Paudit,message,"
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
	@Override
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
	
	//按院、系、人名来查找教师
	@Override
	public ResultSet selectTeacher(String college, String sdept, String Tname,int m,int n) throws SQLException {
		//未设置任何条件
		String sql1 = "select * from (select Tname,Tsex,Ttel,Tmail,Tsn,Cname,Dname,TID,COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Tsn desc) as r from "
				+ "Teacher t  left join College c on t.Csn = c.Csn left join  Sdept s on t.Dsn = s.Dsn) as pp where pp.r between ? and ?";
		//设置了要查询的学院
		String sql2 = "select * from (select Tname,Tsex,Ttel,Tmail,Tsn,Cname,Dname,TID,COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Tsn desc) as r from "
				+ "Teacher t  left join College c on t.Csn = c.Csn left join  Sdept s on t.Dsn = s.Dsn where Cname = ?) as pp where pp.r between ? and ?";
		//设置了要查询的教师名
		String sql3 = "select * from (select Tname,Tsex,Ttel,Tmail,Tsn,Cname,Dname,TID,COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Tsn desc) as r from "
				+ "Teacher t  left join College c on t.Csn = c.Csn left join  Sdept s on t.Dsn = s.Dsn where Tname = ?) as pp where pp.r between ? and ?";
		//设置了要查询的教师名和学院名
		String sql4 = "select * from (select Tname,Tsex,Ttel,Tmail,Tsn,Cname,Dname,TID,COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Tsn desc) as r from "
				+ "Teacher t  left join College c on t.Csn = c.Csn left join  Sdept s on t.Dsn = s.Dsn where Tname = ? and Cname = ?) as pp where pp.r between ? and ?";
		//设置了要查询的学院名和系名
		String sql5 = "select * from (select Tname,Tsex,Ttel,Tmail,Tsn,Cname,Dname,TID,COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Tsn desc) as r from "
				+ "Teacher t  left join College c on t.Csn = c.Csn left join Sdept s on t.Dsn = s.Dsn where Tname = ? and Cname = ?) "
				+ "as pp where pp.r between ? and ?";
		//设置了要查询的学院名和系名和教师名
		//这里只需查询系名和教师名即可达到所需效果
		String sql6 = "select * from (select Tname,Tsex,Ttel,Tmail,Tsn,TID,Cname,Dname,COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Tsn desc) as r from "
				+ "Teacher t  left join College c on t.Csn = c.Csn left join Sdept s on t.Dsn = s.Dsn where Tname = ? and Dname = ?) "
				+ "as pp where pp.r between ? and ?";
		System.out.println("college = " + college);
		System.out.println("sdept = " + sdept);
		System.out.println("Tname = " + Tname);
		System.out.println("m = " + m);
		System.out.println("n = " + n);
		stmt = dbUtil.getConnection().prepareStatement(sql1);
		stmt.setInt(1, m);
		stmt.setInt(2, n);
		return stmt.executeQuery();
	}
	//将得到的教师信息结果集转化为集合
	@Override
	public List<Teacher> getDataList(ResultSet rs) {
		List<Teacher> datalist = new ArrayList<Teacher>();
		try {
			while(rs.next()) {
				datalist.add(new Teacher(rs.getString("Tsn"),rs.getString("Tname"), rs.getString("Tsex"),rs.getString("Ttel"),
						rs.getString("Tmail"),rs.getString("Cname"),rs.getString("Dname"),rs.getInt("totalRecord"),rs.getString("TID")));
				System.out.println(rs.getString("Tsn"));
			}
			return datalist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datalist;
	}

}
