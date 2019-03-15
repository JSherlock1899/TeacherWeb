package dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;

import util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.IBaseDao;
import dao.ITeacherDao;
import model.ExcelTeacher;
import model.Patent;
import model.Teacher;
/**
 * 教师数据库操作
 *
 */
public class TeacherDaoImpl extends BaseDaoImpl implements ITeacherDao{
	private PreparedStatement stmt = null;
	IBaseDao baseDao = new BaseDaoImpl();
	CommonUtil util = new CommonUtil();
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
		String sql = "select * from (select COUNT(*)OVER() AS totalRecord,Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,"
				+ "Pendtime,Premarks,Tname,Paudit,message,Paccessory,"
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
		String sql = "select * from (select COUNT(*)OVER() AS totalRecord,Pasearchnum,PTsn,Pawriter,Papublish,Padate,Pagrad,Paremarks,Pdisvol,Paudit,"
				+ "message,Paccessory,"
				+ "ROW_NUMBER() over (order by Pasearchnum) as r from Paper p left join Teacher t on p.Tsn = t.Tsn where p.Tsn = ?) "
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
		String sql = "select * from (select COUNT(*)OVER() AS totalRecord,Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Haccessory,Haudit,message,"
				+ "ROW_NUMBER() over (order by Hsn) as r from Honor h left join Teacher t on h.Tsn = t.Tsn where h.Tsn = ?) "
				+ "as  pp where pp.r between ? and ?";
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Tsn);
			stmt.setInt(2,m);
			stmt.setInt(3,n);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
	
	
	//查询教师的教师
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
		public String getTeacherCollege(String Cname)throws SQLException{
			String sql = "select Csn from College  where Cname = ?";
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1,Cname);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				String Csn = rs.getString("Csn");
				return Csn;
			}
			return null;
		}
	
	
	//获取教师所属学院
	@Override
	public String getTeacherSdept(String Dname)throws SQLException{
		String sql = "select Dsn from Sdept where Dname = ?";
		stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1,Dname);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			String Dsn = rs.getString("Dsn");
			return Dsn;
		}
		return null;
	}
	
	//按院、系、人名来查找教师
	@Override
	public ResultSet selectTeacher(String college, String sdept, String Tname,int m,int n) throws SQLException {
		college = CommonUtil.disposePageValue(college);
		sdept = CommonUtil.disposePageValue(sdept);		//处理sdept的值问题(第二次点击时)
		sdept = CommonUtil.disposeSdeptValue(sdept);
		Tname = CommonUtil.disposePageValue(Tname);
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
				+ "Teacher t  left join College c on t.Csn = c.Csn left join Sdept s on t.Dsn = s.Dsn where Cname = ? and Dname = ?) "
				+ "as pp where pp.r between ? and ?";
		//设置了要查询的学院名和系名和教师名
		//这里只需查询系名和教师名即可达到所需效果
		String sql6 = "select * from (select Tname,Tsex,Ttel,Tmail,Tsn,TID,Cname,Dname,COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Tsn desc) as r from "
				+ "Teacher t  left join College c on t.Csn = c.Csn left join Sdept s on t.Dsn = s.Dsn where Tname = ? and Dname = ?) "
				+ "as pp where pp.r between ? and ?";
		try {
			if(Tname == null || Tname.equals("")) {		//判断是否进行了精确查询
				if(college ==  null && sdept == null || college.equals("null") && sdept == null || college.equals("") && sdept.equals("") ) {							//此时刚跳转到该jsp,页面刚刷新，所有参数均为null，输出所有教师信息
					List params = Arrays.asList(m,n);
					return dbUtil.getResultSet(sql1, params);
				}else if(!college.equals("") && sdept == null|| !college.equals("") && sdept.equals("")) {	//只选了学院
					List params = Arrays.asList(college,m,n);
					return dbUtil.getResultSet(sql2, params);
				}else if(!college.equals("") && !sdept.equals("")) {			//选了学院和专业
					List params = Arrays.asList(college,sdept,m,n);
					return dbUtil.getResultSet(sql5, params);
				}
			}else {
				if(college.equals("") && sdept == null|| college.equals("") && sdept.equals("")) {							//选了教师名				
					List params = Arrays.asList(Tname,m,n);
					return dbUtil.getResultSet(sql3, params);
				}else if(!college.equals("") && sdept.equals("")) {	//只选了学院和教师名
					List params = Arrays.asList(Tname,college,m,n);
					return dbUtil.getResultSet(sql4, params);
				}else if(!college.equals("") && !sdept.equals("")) {			//选了学院和专业和教师名
					List params = Arrays.asList(Tname,sdept,m,n);
					return dbUtil.getResultSet(sql6, params);
				}
				}
			} catch (NullPointerException | SQLException e) {
				e.printStackTrace();
			}finally {
				baseDao.closeCon();
			}
		return null;
			
	}
	//将得到的教师信息结果集转化为集合
	@Override
	public List<Teacher> getDataList(ResultSet rs) {
		List<Teacher> datalist = new ArrayList<Teacher>();
		try {
			while(rs.next()) {
				datalist.add(new Teacher(rs.getString("Tsn"),rs.getString("Tname"), rs.getString("Tsex"),rs.getString("Ttel"),
						rs.getString("Tmail"),rs.getString("Cname"),rs.getString("Dname"),rs.getInt("totalRecord"),rs.getString("TID")));
			}
			return datalist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datalist;
	}
	
	
	//获取导出excel的集合
	@Override
	public List<ExcelTeacher> getExcelDataList(ResultSet rs) throws SQLException {
		List<ExcelTeacher> datalist = new ArrayList<ExcelTeacher>();
		try {
			while(rs.next()) {
				datalist.add(new ExcelTeacher(rs.getString("Tsn"),rs.getString("Tname"), rs.getString("Tsex"),rs.getString("Ttel"),
						rs.getString("Tmail"), rs.getString("TID"),rs.getString("Cname"),rs.getString("Dname")));
			}
			return datalist;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datalist;
	}
	
	
	//删除教师信息
	@Override
	public int delTeacher(String Tsn) throws SQLException {
		String sql = "delete from Teacher where Tsn = ?";		
		try {
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, Tsn);
			int result = stmt.executeUpdate();
			if(result == 1) {	//当删除成功时返回1，删除失败时返回0
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return 0;
	}
	
	//根据学院名查询学院号
	public String[] getCsnDsn(String Dname) throws SQLException {
		String sql = "select c.Csn,Dsn from College c join Sdept s on c.Csn = s.Csn where s.Dname = ?";
		stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1, Dname);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			String Csn = rs.getString("Csn");
			String Dsn = rs.getString("Dsn");
			return new String[] {Csn,Dsn};
		}
		return null;
		
	}
	//修改教师信息
	@Override
	public int alterTeacher(Teacher teacher) throws SQLException {
		String Cname = teacher.getCname();			//获取教师对应的学院
		String Dname = teacher.getDname();			//获取教师对应的专业
		String Csn = getCsnDsn(Dname)[0];
		String Dsn = getCsnDsn(Dname)[1];
		String sql = "update Teacher set Tname = ?,Tsex = ?,Ttel = ? ,Tmail = ?,TID = ?,Csn = ?,Dsn = ? "
				+ "where Tsn = ?";
		List params = Arrays.asList(teacher.getTname(),teacher.getTsex(),teacher.getTtel(),teacher.getTmail(),
				teacher.getTID(),Csn,Dsn,teacher.getTsn());
		return dbUtil.getUpdateResult(sql, params);
	}
	
	
	//新建教师信息
	@Override 
	public int insertTeacher(Teacher teacher) throws SQLException {
		String Cname = teacher.getCname();			//获取教师对应的学院
		String Dname = teacher.getDname();			//获取教师对应的专业
		String Csn = getCsnDsn(Dname)[0];
		String Dsn = getCsnDsn(Dname)[1];
		String sql = "insert into Teacher (Tsn,Tname,Tsex,Ttel,Tmail,TID,Csn,Dsn) Values(?,?,?,?,?,?,?,?)";
		List params = Arrays.asList(teacher.getTsn(),teacher.getTname(),teacher.getTsex(),teacher.getTtel(),teacher.getTmail(),
				teacher.getTID(),Csn,Dsn);
		return dbUtil.getUpdateResult(sql, params);
	}
	
	
	//验证密码是否正确
	@Override
	public boolean verifyPassword(String Tsn, String oldPassword) throws SQLException {
		String sql = "select Tpsw from Teacher where Tsn = ?";
		PreparedStatement stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1, Tsn);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			String Tpsw = rs.getString("Tpsw"); 
			if(Tpsw.trim().equals(oldPassword)) 
				return true;				
			else
				return false;
		}
		return false;
	}
		
	//修改密码
	@Override
	public int alterPassword(String Tsn, String newPassword) throws SQLException {
		String sql = "update Teacher set Tpsw = ? where Tsn = ?";
		PreparedStatement stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1, newPassword);
		stmt.setString(2, Tsn);
		int result = stmt.executeUpdate();
		return result;
	}
}
