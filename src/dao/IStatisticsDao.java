package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IStatisticsDao {
		
	//获取各学院的各项目的数目
	ResultSet getCollegeCount(String option,String starttime,String endtime) throws SQLException;

	//获取各专业的各项目的数目
	public ResultSet getSdeptCount(String option, String collegevalue)throws SQLException;
	
	//获取各学院的项目经费之和
	public ResultSet getProjectMoney()throws SQLException;
	
	//获取各专业的项目经费之和
	public ResultSet getSdeptProjectMoney(String collegevalue)throws SQLException;
	
	//获取近5年各学院项目数量
	public List getRecentYearsCount(String option)throws SQLException;
	
	//获取近5年各专业项目数量
	public List getRecentYearsSdeptCount(String option, String collegevalue) throws SQLException;
		
}
