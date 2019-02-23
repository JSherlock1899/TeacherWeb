package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IStatisticsDao {
	
	public int createPie(String Cname,String option);
	
	//获取各学院的各项目的数目
	ResultSet getCollegeCount(String option) throws SQLException;

	//获取各专业的各项目的数目
	public ResultSet getSdeptCount(String option, String collegevalue)throws SQLException;
	
	//获取各学院的项目经费之和
	public ResultSet getProjectMoney()throws SQLException;

}
