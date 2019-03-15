package dao.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import dao.IBaseDao;
import dao.IExcelDao;
import util.CommonUtil;
import util.DbUtil;

public class ExcelDaoImpl extends BaseDaoImpl implements IExcelDao {
	private PreparedStatement stmt = null;
	DbUtil dbutil = new DbUtil();
	IBaseDao baseDao = new BaseDaoImpl();
	CommonUtil unit = new CommonUtil();
	TeacherDaoImpl teacherdao = new TeacherDaoImpl();
	
	@Override
	public int insertPatentValues(List datalist) {
		int times = 0; // 用来记录导入数据的条数
		try {
			String sql = "insert into Patent (Patname,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Tsn,Paudit) Values(?,?,?,?,?,?,?,0)";
			stmt = dbutil.getConnection().prepareStatement(sql);
			int count = datalist.size();
			List list = dbutil.disposeNullCell(datalist);
			for (int i = 0; i < count / 7; i++) {
				// j为专利信息表的列数
				String Tsn = teacherdao.getTsn((String) list.get(i * 7 + 2));// 教师名在excel中的第二列
				// 获取专利申请日期，将util.Date转化为sql.Date
				Date Patapdate = (Date) list.get(3 + i * 7);
				java.sql.Date sqlDate1 = new java.sql.Date(Patapdate.getTime());
				// 获取专利授权日期，将util.Date转化为sql.Date
				Date Patendate = (Date) list.get(4 + i * 7);
				java.sql.Date sqlDate2 = new java.sql.Date(Patendate.getTime());	
				List params = Arrays.asList(list.get(0 + i*7),list.get(1 + i*7),sqlDate1,sqlDate2,list.get(5 + i*7),list.get(6 + i*7),Tsn);
				dbutil.getUpdateResult(sql, params);
				times++;
			}
			if (times == count / 7) {
				return 0;
			}
		} catch (SQLServerException e) {	
			e.printStackTrace();
			return times + 1;
		}catch(ClassCastException e) {
			e.printStackTrace();
			return times + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return times;
	}
	
	@Override
	public int insertHonorValues(List datalist) {
		int times = 0; // 用来记录导入数据的条数
		try {
			String sql = "insert into Honor (Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Tsn,Haudit) Values(?,?,?,?,?,?,?,?,?,0)";
			int count = datalist.size();
			List list = dbutil.disposeNullCell(datalist);
			for (int i = 0; i < count / 8; i++) {
				// j为荣誉信息表的列数
				String Tsn = teacherdao.getTsn((String) list.get(i * 8 + 2));// 荣誉号在excel中的第一列
				// 获取获奖日期，将util.Date转化为sql.Date
				Date Hdate = (Date) list.get(3 + i * 8);
				java.sql.Date sqlDate1 = new java.sql.Date(Hdate.getTime());
				List params = Arrays.asList(list.get(i*8),list.get(1 + i*8),list.get(2 + i*8),sqlDate1,list.get(4 + i*8),list.get(5 + i*8),list.get(6 + i*8),list.get(7 + i*8),Tsn);
				dbutil.getUpdateResult(sql, params);
				times++;
			}
			//导入数据正确
			if (times == count / 7) {
				return 0;
			}
		}  catch (SQLServerException e) {	
			e.printStackTrace();
			return times + 1;
		}catch(ClassCastException e) {
			e.printStackTrace();
			return times + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return times;
	}

	@Override
	public int insertProjectValues(List datalist) {
		int times = 0; // 用来记录导入数据的条数
		try {
			String sql = "insert into Project (Psn,Pname,Pleader,Pgrad,Pkind,Pcondition,Pmember,Pmoney,Pstatime,Pendtime,Premarks,Tsn,Paudit) Values(?,?,?,?,?,?,?,?,?,?,?,?,0)";
			stmt = dbutil.getConnection().prepareStatement(sql);
			int count = datalist.size();
			List list = dbutil.disposeNullCell(datalist);
			for(Object a : list) {
				System.out.println(a);
			}
			for (int i = 0; i < count / 7; i++) {
				// j为专利信息表的列数
				String Tsn = teacherdao.getTsn((String) list.get(i * 7 + 2));// 教师名在excel中的第二列
				Date Pstatime = (Date) list.get(8 + i * 7);
				java.sql.Date sqlDate1 = new java.sql.Date(Pstatime.getTime());
				Date Pendtime = (Date) list.get(9 + i * 7);
				java.sql.Date sqlDate2 = new java.sql.Date(Pendtime.getTime());
				List params = Arrays.asList(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
						list.get(5), list.get(6),list.get(7),sqlDate1,sqlDate2, list.get(10),Tsn);
				dbutil.getUpdateResult(sql, params);
				times++;
			}
			//导入数据正确
			if (times == count / 7) {
				return 0;
			}
		}  catch (SQLServerException e) {	
			e.printStackTrace();
			return times + 1;
		}catch(ClassCastException e) {
			e.printStackTrace();
			return times + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return times;
	}


	@Override
	public int insertPaperValues(List datalist) {
		int times = 0; // 用来记录导入数据的条数
		try {
			String sql = "insert into paper (Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,Tsn,Paudit) Values(?,?,?,?,?,?,?,?,0)";
			int count = datalist.size();
			List list = dbutil.disposeNullCell(datalist);
			for (int i = 0; i < count / 7; i++) {
				// j为论文信息表的列数
				String Tsn = teacherdao.getTsn((String) list.get(i * 7 + 2));// 教师名在excel中的第三列
				Date Pstatime = (Date) list.get(4 + i * 7);
				java.sql.Date sqlDate1 = new java.sql.Date(Pstatime.getTime());
				List params = Arrays.asList(list.get(0), list.get(1), list.get(2), list.get(3), sqlDate1,
						list.get(5), list.get(6),Tsn);
				dbutil.getUpdateResult(sql, params);
				times++;
			}
			//导入数据正确
			if (times == count / 7) {
				return 0;
			}
		}  catch (SQLServerException e) {	
			e.printStackTrace();
			return times + 1;
		}catch(ClassCastException e) {
			e.printStackTrace();
			return times + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return times;
	}

	@Override
	public int insertTeacherValues(List datalist) {
		int times = 0; // 用来记录导入数据的条数
		try {
			String sql = "insert into Teacher (Tsn,Tname,Tsex,Ttel,Tmail,TID,Csn,Dsn) Values(?,?,?,?,?,?,?,?)";
			int count = datalist.size();
			List list = dbutil.disposeNullCell(datalist);
			for (int i = 0; i < count / 8; i++) {
				if(list.get(i*8) == null) {
					continue;
				}
				// j为教师信息表的列数
				String Cname = teacherdao.getTeacherCollege((String)list.get(i*8 + 6));
				String Dname = teacherdao.getTeacherSdept((String)list.get(i*8 + 7));
				List params = Arrays.asList(list.get(i*8), list.get(1 + i*8), list.get(2 + i*8), list.get(3 + i*8), list.get(4 + i*8),
						list.get(5 + i*8), Cname,Dname);
				dbutil.getUpdateResult(sql, params);
				times++;
			}
			//导入数据正确
			if (times == count / 8) {
				return 0;
			}
		}  catch (SQLServerException e) {	
			e.printStackTrace();
			return times + 1;
		}catch(ClassCastException e) {
			e.printStackTrace();
			return times + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return times;
	}
	
}
