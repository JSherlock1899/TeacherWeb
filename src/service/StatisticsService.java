package service;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.IStatisticsDao;
import dao.impl.StatisticsDaoImpl;

public class StatisticsService {
	
	IStatisticsDao statisticDao = new StatisticsDaoImpl();
	public int createPie(String Cname,String option) {			//返回值为-1则系统错误
		return statisticDao.createPie(Cname, option);
	}
	
	public ResultSet getCollegeCount(String option) throws SQLException {
		return statisticDao.getCollegeCount(option);
	}

	public ResultSet getSdeptCount(String option, String collegevalue) throws SQLException {
		return statisticDao.getSdeptCount(option,collegevalue);
	};
	
	public ResultSet getProjectMoney()throws SQLException{
		return statisticDao.getProjectMoney();
	}
}
