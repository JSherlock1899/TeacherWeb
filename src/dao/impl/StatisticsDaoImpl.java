package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.IBaseDao;
import dao.IStatisticsDao;
import util.CommonUtil;
import util.DbUtil;

public class StatisticsDaoImpl implements IStatisticsDao {

	protected DbUtil dbUtil = new DbUtil();
	private PreparedStatement stmt = null;
	TeacherDaoImpl teacherdao = new TeacherDaoImpl();
	IBaseDao baseDao = new BaseDaoImpl();
	CommonUtil commonUtil = new CommonUtil();
	
	//获取各学院的各项目的数目
	@Override
	public ResultSet getCollegeCount(String option,String starttime,String endtime) throws SQLException {
		if(option!=null) {
			if(option.equals("Project")) {
				if(starttime == null && endtime == null) {
					String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Project p "
							+ "join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn) b on a.Cname = b.Cname group by a.Cname";
					stmt = dbUtil.getConnection().prepareStatement(sql);
					ResultSet rs = stmt.executeQuery();
					return rs;
				}else {
					String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Project p "
							+ "join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn where Pstatime > ? and Pstatime < ?) b on a.Cname = b.Cname group by a.Cname"; 
					stmt = dbUtil.getConnection().prepareStatement(sql);
					stmt.setString(1, starttime);
					stmt.setString(2, endtime);
					ResultSet rs = stmt.executeQuery();
					return rs;
				}
			}else if(option.equals("Paper")) {
				if(starttime == null && endtime == null) {
					String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Paper p "
							+ "join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn) b on a.Cname = b.Cname group by a.Cname";
					stmt = dbUtil.getConnection().prepareStatement(sql);
					ResultSet rs = stmt.executeQuery();
					return rs;
				}else {
					String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Paper p "
							+ "join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn where Padate > ? and Padate < ?) b on a.Cname = b.Cname group by a.Cname"; 
					stmt = dbUtil.getConnection().prepareStatement(sql);
					stmt.setString(1, starttime);
					stmt.setString(2, endtime);
					ResultSet rs = stmt.executeQuery();
					return rs;
				}
			}else if(option.equals("Honor")) {
				if(starttime == null && endtime == null) {
					String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Honor h "
							+ "join Teacher t on h.Tsn = t.Tsn join College c on t.Csn = c.Csn) b on a.Cname = b.Cname group by a.Cname";
					stmt = dbUtil.getConnection().prepareStatement(sql);
					ResultSet rs = stmt.executeQuery();
					return rs;
				}else {
					String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Honor h "
							+ "join Teacher t on h.Tsn = t.Tsn join College c on t.Csn = c.Csn where Hdate > ? and Hdate < ?) b on a.Cname = b.Cname group by a.Cname"; 
					stmt = dbUtil.getConnection().prepareStatement(sql);
					stmt.setString(1, starttime);
					stmt.setString(2, endtime);
					ResultSet rs = stmt.executeQuery();
					return rs;
				}
			}else if(option.equals("Patent")) {
				if(starttime == null && endtime == null) {
					String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Patent p "
							+ "join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn) b on a.Cname = b.Cname group by a.Cname"; 
					stmt = dbUtil.getConnection().prepareStatement(sql);
					ResultSet rs = stmt.executeQuery();
					return rs;
				}else {
					String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Patent p "
							+ "join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn where Patendate > ? and Patendate < ?) b on a.Cname = b.Cname group by a.Cname"; 
					stmt = dbUtil.getConnection().prepareStatement(sql);
					stmt.setString(1, starttime);
					stmt.setString(2, endtime);
					ResultSet rs = stmt.executeQuery();
					return rs;
				}
			}
		}
		return null;
	}
	
	
	//获取各专业的各项目的数目
	@Override
	public ResultSet getSdeptCount(String option, String collegevalue) throws SQLException {
		if(option!=null) {
			if(option.equals("Project")) {
				String sql = "select a.Dname,COUNT(b.Dname) as count from (select Dname from Sdept s  join College c on c.Csn = s.Csn "
						+ "where Cname = ?) as a  left join (select Dname from Project p join Teacher t on p.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ?) as b on a.Dname = b.Dname group by a.Dname ";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				stmt.setString(1, collegevalue);
				stmt.setString(2, collegevalue);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}else if(option.equals("Paper")) {
				String sql = "select a.Dname,COUNT(b.Dname) as count from (select Dname from Sdept s  join College c on c.Csn = s.Csn "
						+ "where Cname = ?) as a  left join (select Dname from Paper p join Teacher t on p.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ?) as b on a.Dname = b.Dname group by a.Dname ";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				stmt.setString(1, collegevalue);
				stmt.setString(2, collegevalue);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}else if(option.equals("Honor")) {
				String sql = "select a.Dname,COUNT(b.Dname) as count from (select Dname from Sdept s  join College c on c.Csn = s.Csn "
						+ "where Cname = ?) as a  left join (select Dname from Honor h join Teacher t on h.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ?) as b on a.Dname = b.Dname group by a.Dname ";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				stmt.setString(1, collegevalue);
				stmt.setString(2, collegevalue);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}else if(option.equals("Patent")) {
				String sql = "select a.Dname,COUNT(b.Dname) as count from (select Dname from Sdept s  join College c on c.Csn = s.Csn "
						+ "where Cname = ?) as a  left join (select Dname from Patent p join Teacher t on p.Tsn = t.Tsn join College c "
						+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ?) as b on a.Dname = b.Dname group by a.Dname "; 
				stmt = dbUtil.getConnection().prepareStatement(sql);
				stmt.setString(1, collegevalue);
				stmt.setString(2, collegevalue);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}
		}
		return null;
	}
	
	//获取各学院的项目经费之和
	@Override
	public ResultSet getProjectMoney(String starttime,String endtime) throws SQLException {
		if(starttime == null && endtime == null) {
			String sql = "select SUM(b.Pmoney) as Pmoney,a.Cname from (select Cname from College) a left join "
					+ "(select Cname,Pmoney from Project p join Teacher t on p.Tsn = t.Tsn join College c on c.Csn = t.Csn) b on a.Cname = b.Cname "
					+ "group by a.Cname";
			stmt = dbUtil.getConnection().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			return rs;
		}else {
			String sql = "select SUM(b.Pmoney) as Pmoney,a.Cname from (select Cname from College) a left join "
					+ "(select Cname,Pmoney from Project p join Teacher t on p.Tsn = t.Tsn join College c on c.Csn = t.Csn where Pstatime > ? "
					+ "and Pstatime < ?) b on a.Cname = b.Cname "
					+ "group by a.Cname";
			stmt = dbUtil.getConnection().prepareStatement(sql);
			stmt.setString(1, starttime);
			stmt.setString(2, endtime);
			ResultSet rs = stmt.executeQuery();
			return rs;
		}
	}
	
	
	//获取各专业的项目经费之和
	@Override
	public ResultSet getSdeptProjectMoney(String collegevalue)throws SQLException{
		String sql = "select SUM(b.Pmoney) as Pmoney,a.Dname from (select Dname from Sdept s join College c on s.Csn = c.Csn where Cname = ?)"
				+ " a left join (select Dname,Pmoney from Project p join Teacher t on p.Tsn = t.Tsn join College c on c.Csn = t.Csn "
				+ "join Sdept s on s.Dsn = t.Dsn where Cname = ?) b on a.Dname = b.Dname group by a.Dname";
		stmt = dbUtil.getConnection().prepareStatement(sql);
		stmt.setString(1, collegevalue);
		stmt.setString(2, collegevalue);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}

	//获取近5年各学院项目数量
	@Override
	public List getRecentYearsCount(String option) throws SQLException {
		if(option!=null) {
			String[] yearsArr = {"2015","2016","2017","2018","2019","2020"}; 
			ArrayList list = new ArrayList();
			if(option.equals("Project")) {
				String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Project p "
						+ "join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn where Pstatime >? and "
						+ "Pstatime < ?) b on a.Cname = b.Cname group by a.Cname";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				for(int i=0;i<5;i++) {
					stmt.setString(1, yearsArr[i]);
					stmt.setString(2, yearsArr[i+1]);
					ResultSet rs = stmt.executeQuery();
					Map<String,Integer> map = commonUtil.countRsToMap(rs);
					list.add(map);
				}
				return list;
			}else if(option.equals("Paper")) {
				String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Paper p "
						+ "join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn where Padate > ? and Padate < ?) b on a.Cname = b.Cname group by a.Cname";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				for(int i=0;i<5;i++) {
					stmt.setString(1, yearsArr[i]);
					stmt.setString(2, yearsArr[i+1]);
					ResultSet rs = stmt.executeQuery();
					Map<String,Integer> map = commonUtil.countRsToMap(rs);
					list.add(map);
				}
				return list;
			}else if(option.equals("Honor")) {
				String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Honor h "
						+ "join Teacher t on h.Tsn = t.Tsn join College c on t.Csn = c.Csn where Hdate > ? and Hdate < ?) b on a.Cname = b.Cname group by a.Cname";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				for(int i=0;i<5;i++) {
					stmt.setString(1, yearsArr[i]);
					stmt.setString(2, yearsArr[i+1]);
					ResultSet rs = stmt.executeQuery();
					Map<String,Integer> map = commonUtil.countRsToMap(rs);
					list.add(map);
				}
				return list;
			}else if(option.equals("Patent")) {
				String sql = "select a.Cname,COUNT(b.Cname) as count from (select Cname from College) a left join (select Cname from Patent p "
						+ "join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn where Patendate > ? and Patendate < ?) b on a.Cname = b.Cname group by a.Cname"; 
				stmt = dbUtil.getConnection().prepareStatement(sql);
				for(int i=0;i<5;i++) {
					stmt.setString(1, yearsArr[i]);
					stmt.setString(2, yearsArr[i+1]);
					ResultSet rs = stmt.executeQuery();
					Map<String,Integer> map = commonUtil.countRsToMap(rs);
					list.add(map);
				}
				return list;
			}
		}
		return null;
	}
	
	//获取各专业的各项目的数目
		@Override
		public List getRecentYearsSdeptCount(String option, String collegevalue) throws SQLException {
			if(option!=null) {
				String[] yearsArr = {"2015","2016","2017","2018","2019","2020"}; 
				ArrayList list = new ArrayList();
				if(option.equals("Project")) {
					String sql = "select a.Dname,COUNT(b.Dname) as count from (select Dname from Sdept s  join College c on c.Csn = s.Csn "
							+ "where Cname = ?) as a  left join (select Dname from Project p join Teacher t on p.Tsn = t.Tsn join College c "
							+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Pstatime >? and Pstatime < ?) as b on a.Dname = b.Dname group by a.Dname ";
					stmt = dbUtil.getConnection().prepareStatement(sql);
					for(int i=0;i<5;i++) {
						stmt.setString(1, collegevalue);
						stmt.setString(2, collegevalue);
						stmt.setString(3, yearsArr[i]);
						stmt.setString(4, yearsArr[i+1]);
						ResultSet rs = stmt.executeQuery();
						Map<String,Integer> map = commonUtil.collegeCountRsToMap(rs);
						list.add(map);
					}
					return list;
				}else if(option.equals("Paper")) {
					String sql = "select a.Dname,COUNT(b.Dname) as count from (select Dname from Sdept s  join College c on c.Csn = s.Csn "
							+ "where Cname = ?) as a  left join (select Dname from Paper p join Teacher t on p.Tsn = t.Tsn join College c "
							+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Padate > ? and Padate < ?) as b on a.Dname = b.Dname group by a.Dname ";
					stmt = dbUtil.getConnection().prepareStatement(sql);
					for(int i=0;i<5;i++) {
						stmt.setString(1, collegevalue);
						stmt.setString(2, collegevalue);
						stmt.setString(3, yearsArr[i]);
						stmt.setString(4, yearsArr[i+1]);
						ResultSet rs = stmt.executeQuery();
						Map<String,Integer> map = commonUtil.collegeCountRsToMap(rs);
						list.add(map);
					}
					return list;
				}else if(option.equals("Honor")) {
					String sql = "select a.Dname,COUNT(b.Dname) as count from (select Dname from Sdept s  join College c on c.Csn = s.Csn "
							+ "where Cname = ?) as a  left join (select Dname from Honor h join Teacher t on h.Tsn = t.Tsn join College c "
							+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Hdate > ? and Hdate < ?) as b on a.Dname = b.Dname group by a.Dname ";
					stmt = dbUtil.getConnection().prepareStatement(sql);
					for(int i=0;i<5;i++) {
						stmt.setString(1, collegevalue);
						stmt.setString(2, collegevalue);
						stmt.setString(3, yearsArr[i]);
						stmt.setString(4, yearsArr[i+1]);
						ResultSet rs = stmt.executeQuery();
						Map<String,Integer> map = commonUtil.collegeCountRsToMap(rs);
						list.add(map);
					}
					return list;
				}else if(option.equals("Patent")) {
					String sql = "select a.Dname,COUNT(b.Dname) as count from (select Dname from Sdept s  join College c on c.Csn = s.Csn "
							+ "where Cname = ?) as a  left join (select Dname from Patent p join Teacher t on p.Tsn = t.Tsn join College c "
							+ "on t.Csn = c.Csn join Sdept s on t.Dsn = s.Dsn where Cname = ? and Patendate > ? and Patendate < ?) as b on a.Dname = b.Dname group by a.Dname "; 
					stmt = dbUtil.getConnection().prepareStatement(sql);
					for(int i=0;i<5;i++) {
						stmt.setString(1, collegevalue);
						stmt.setString(2, collegevalue);
						stmt.setString(3, yearsArr[i]);
						stmt.setString(4, yearsArr[i+1]);
						ResultSet rs = stmt.executeQuery();
						Map<String,Integer> map = commonUtil.collegeCountRsToMap(rs);
						list.add(map);
					}
					return list;
				}
			}
			return null;
		}
}
