package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	@Override
	public int createPie(String Cname, String option) { // 返回值为-1则系统错误
		String sql;
		if (option != null) {
			if (option.equals("Project")) {
				sql = "select COUNT(*) from Project p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn where c.Cname = ?";
				int result = -1;
				try {
					stmt = dbUtil.getConnection().prepareStatement(sql);
					stmt.setString(1, Cname);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						result = rs.getInt(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return result;
			} else if (option.equals("Paper")) {
				sql = "select COUNT(*) from Paper p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn where c.Cname = ?";
				int result = -1;
				try {
					stmt = dbUtil.getConnection().prepareStatement(sql);
					stmt.setString(1, Cname);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						result = rs.getInt(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return result;
			} else if (option.equals("Honor")) {
				sql = "select COUNT(*) from Honor h join Teacher t on h.Tsn = t.Tsn join College c on t.Csn = c.Csn where c.Cname = ?";
				int result = -1;
				try {
					stmt = dbUtil.getConnection().prepareStatement(sql);
					stmt.setString(1, Cname);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						result = rs.getInt(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return result;
			} else if (option.equals("Patent")) {
				sql = "select COUNT(*) from  Patent p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn where c.Cname = ?";
				int result = -1;
				try {
					stmt = dbUtil.getConnection().prepareStatement(sql);
					stmt.setString(1, Cname);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						result = rs.getInt(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return result;
			}

		}
		return -1;

	}
	
	//获取各学院的各项目的数目
	public ResultSet getCollegeCount(String option) throws SQLException {
		if(option!=null) {
			if(option.equals("Project")) {
				String sql = "select c.Cname,COUNT(*) as count from Project p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn group by c.Cname";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}else if(option.equals("Paper")) {
				String sql = "select c.Cname,COUNT(*) as count from Paper p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn group by c.Cname";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}else if(option.equals("Honor")) {
				String sql = "select c.Cname,COUNT(*) as count from Honor h join Teacher t on h.Tsn = t.Tsn join College c on t.Csn = c.Csn group by c.Cname";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}else if(option.equals("Patent")) {
				String sql = "select c.Cname,COUNT(*) as count from Patent p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn group by c.Cname"; 
				stmt = dbUtil.getConnection().prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}
		}
		return null;
	}
	
	
	//获取各专业的各项目的数目
	public ResultSet getSdeptCount(String option, String collegevalue) throws SQLException {
		if(option!=null) {
			if(option.equals("Project")) {
				String sql = "select Dname,COUNT(*) as count from Project p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn join Sdept s on c.Csn = s.Csn where c.Cname = ? group by s.Dname ";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				stmt.setString(1, collegevalue);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}else if(option.equals("Paper")) {
				String sql = "select Dname,COUNT(*) as count from Paper p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn join Sdept s on c.Csn = s.Csn where c.Cname = ? group by s.Dname ";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				stmt.setString(1, collegevalue);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}else if(option.equals("Honor")) {
				String sql = "select Dname,COUNT(*) as count from Honor h join Teacher t on h.Tsn = t.Tsn join College c on t.Csn = c.Csn  join Sdept s on c.Csn = s.Csn where c.Cname = ? group by s.Dname ";
				stmt = dbUtil.getConnection().prepareStatement(sql);
				stmt.setString(1, collegevalue);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}else if(option.equals("Patent")) {
				String sql = "select Dname,COUNT(*) as count from Patent p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn join Sdept s on c.Csn = s.Csn where c.Cname = ? group by s.Dname "; 
				stmt = dbUtil.getConnection().prepareStatement(sql);
				stmt.setString(1, collegevalue);
				ResultSet rs = stmt.executeQuery();
				return rs;
			}
		}
		return null;
	}
	
	//获取各学院的项目之和
	public ResultSet getProjectMoney() throws SQLException {
		String sql = "select SUM(Pmoney) as Pmoney,Cname from Project p join Teacher t on p.Tsn = t.Tsn join College c on t.Csn = c.Csn group by Cname";
		stmt = dbUtil.getConnection().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
}
