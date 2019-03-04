package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ITeacherDao;
import dao.impl.TeacherDaoImpl;
import model.Teacher;

public class TeacherService {
		
		ITeacherDao teacherDao = new TeacherDaoImpl();
		//验证教师登录
		public int TeacherLogin(Teacher teacher) throws SQLException{
			return teacherDao.TeacherLogin(teacher);
		}
		
		
		// 查询教师号
		public String getTsn(String Tname) throws SQLException{
			return teacherDao.getTsn(Tname);
		}
		
		//查询教师名
		public String getTname(String Tsn)throws SQLException{
			return teacherDao.getTname(Tsn);
		}
		
		//查询教师的项目
		public ResultSet getProject(String Tsn,int currentPage,int pageSize)throws SQLException{
			return teacherDao.getProject(Tsn,currentPage,pageSize);
		}
		
		//查询教师的论文
		public ResultSet getPaper(String Tsn,int currentPage,int pageSize)throws SQLException{
			return teacherDao.getPaper(Tsn,currentPage,pageSize);
		}
			
		//查询教师的荣誉
		public ResultSet getHonor(String Tsn,int currentPage,int pageSize)throws SQLException{
			return teacherDao.getHonor(Tsn,currentPage,pageSize);
		}
		
		//查询教师的专利
		public ResultSet getPatent(String Tsn,int currentPage,int pageSize)throws SQLException{
			return teacherDao.getPatent(Tsn,currentPage,pageSize);
		}
		
		//按院、系、人名来查找教师
		public ResultSet selectTeacher(String college, String sdept, String Tname,int m,int n) throws SQLException {
			return teacherDao.selectTeacher(college, sdept, Tname, m, n);
		}
		
		//将得到的教师信息结果集转化为集合
		public List<Teacher> getDataList(ResultSet rs) {
			return teacherDao.getDataList(rs);
		}
}
