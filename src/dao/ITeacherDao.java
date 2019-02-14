package dao;

import java.sql.SQLException;

import model.Teacher;

public interface ITeacherDao {
	
	
	//验证教师登录
	public int TeacherLogin(Teacher teacher) throws SQLException;
	
	
	// 查询教师号
	public String getTsn(String Tname) throws SQLException;
	
	//查询教师名
	public String getTname(String Tsn)throws SQLException;
}
