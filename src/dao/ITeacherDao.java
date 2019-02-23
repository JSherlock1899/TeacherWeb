package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Teacher;

public interface ITeacherDao {
	
	
	//验证教师登录
	public int TeacherLogin(Teacher teacher) throws SQLException;
	
	
	// 查询教师号
	public String getTsn(String Tname) throws SQLException;
	
	//查询教师名
	public String getTname(String Tsn)throws SQLException;
	
	//查询教师的项目
	public ResultSet getProject(String Tsn,int currentPage,int pageSize)throws SQLException;
	
	//查询教师的论文
	public ResultSet getPaper(String Tsn,int currentPage,int pageSize)throws SQLException;
		
	//查询教师的荣誉
	public ResultSet getHonor(String Tsn,int currentPage,int pageSize)throws SQLException;
	
	//查询教师的专利
	public ResultSet getPatent(String Tsn,int currentPage,int pageSize)throws SQLException;
	
	//获取教师所属学院
	public String getTeacherCollege(String Tsn)throws SQLException;
}
