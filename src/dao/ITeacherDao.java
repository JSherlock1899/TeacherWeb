package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	
	//按院、系、人名来查找教师并进行分页
	public ResultSet selectTeacher(String college,String sdept,String Tname,int m,int n) throws SQLException;
	
	//将得到的教师信息结果集转化为集合
	public List<Teacher> getDataList(ResultSet rs);
	
	
}
