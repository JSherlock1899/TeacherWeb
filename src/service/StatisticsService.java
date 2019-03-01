package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.IStatisticsDao;
import dao.impl.StatisticsDaoImpl;

public class StatisticsService {
	
	IStatisticsDao statisticDao = new StatisticsDaoImpl();
	
	
	public ResultSet getCollegeCount(String option) throws SQLException { //获取各学院的各项目的数目
		return statisticDao.getCollegeCount(option);
	}

	public ResultSet getSdeptCount(String option, String collegevalue) throws SQLException { //获取各专业的各项目的数目
		return statisticDao.getSdeptCount(option,collegevalue);
	};
	
	public ResultSet getProjectMoney()throws SQLException{  //获取各学院的项目经费之和
		return statisticDao.getProjectMoney();
	}
	
	public ResultSet getSdeptProjectMoney(String collegevalue)throws SQLException{
		return statisticDao.getSdeptProjectMoney(collegevalue);
	}
	
	//获取近5年各学院项目数量
	public List getRecentYearsCount(String option) throws SQLException {
		return statisticDao.getRecentYearsCount(option);
	}
	
	//获取各专业的各项目的数目
	public List getRecentYearsSdeptCount(String option, String collegevalue) throws SQLException {
		return statisticDao.getRecentYearsSdeptCount(option, collegevalue);
	}
}
