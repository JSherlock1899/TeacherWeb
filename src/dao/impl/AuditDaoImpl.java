package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.IAuditDao;
import dao.IHonorDao;
import dao.IPaperDao;
import dao.IPatentDao;
import dao.IProjectDao;
import model.Honor;
import model.Paper;
import model.Patent;
import model.Project;
import util.DbUtil;

public class AuditDaoImpl implements IAuditDao {

	protected DbUtil dbUtil = new DbUtil();

	// 在数据库中，grade为0代表未审核。grade为1代表审核通过，grade为2代表审核未通过
	@Override
	// 获取该学院所有未审核的项目
	public List<Project> getAllUnauditedProject(int auditNum, String college, int currentPage, int pageSize) {
		// 当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		// 当前页的最后一条记录
		int n = currentPage * pageSize;
		String sql = "select * from (select Psn,Pname,Pleader,Pmember,Pgrad,Pkind,Pmoney,Pstatime,Pcondition,Pendtime,Premarks,message,Paudit,"
				+ "COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Psn desc) as r from Project p join Teacher t on p.Tsn = t.Tsn "
				+ "join College c on t.Csn = c.Csn where Paudit = ? and c.Cname = ? ) as pp where pp.r between ? and ?";
		List params = Arrays.asList(auditNum, college, m, n);
		try {
			ResultSet rs = dbUtil.getResultSet(sql, params);
			IProjectDao projectDao = new ProjectDaoImpl();
			return projectDao.getDataList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	// 获取该学院所有未审核的论文
		public List<Paper> getAllUnauditedPaper(int auditNum, String college, int currentPage, int pageSize) {
			// 当前页的第一条记录
			int m = (currentPage - 1) * pageSize + 1;
			// 当前页的最后一条记录
			int n = currentPage * pageSize;
			String sql = "select * from (select Pasearchnum,Paname,Pawriter,Papublish,Padate,Pagrad,Paremarks,message,Paudit,"
					+ "COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Pasearchnum desc) as r from Paper p join Teacher t on p.Tsn = t.Tsn "
					+ "join College c on t.Csn = c.Csn where Paudit = ? and c.Cname = ? ) as pp where pp.r between ? and ?";
			List params = Arrays.asList(auditNum, college, m, n);
			try {
				ResultSet rs = dbUtil.getResultSet(sql, params);
				IPaperDao paperDao = new PaperDaoImpl();
				return paperDao.getDataList(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		// 获取该学院所有未审核的荣誉
		public List<Honor> getAllUnauditedHonor(int auditNum, String college, int currentPage, int pageSize) {
			// 当前页的第一条记录
			int m = (currentPage - 1) * pageSize + 1;
			// 当前页的最后一条记录
			int n = currentPage * pageSize;
			String sql = "select * from (select Hsn,Hname,Hwinner,Hdate,Hcompany,Hgrad,Hreward,Hremarks,Haccessory,message,Haudit,"
					+ "COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Hsn desc) as r from Honor h join Teacher t on h.Tsn = t.Tsn "
					+ "join College c on t.Csn = c.Csn where Haudit = ? and c.Cname = ? ) as pp where pp.r between ? and ?";
			List params = Arrays.asList(auditNum, college, m, n);
			try {
				ResultSet rs = dbUtil.getResultSet(sql, params);
				IHonorDao honorDao = new HonorDaoImpl();
				return honorDao.getDataList(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		// 获取该学院所有未审核的专利
		public List<Patent> getAllUnauditedPatent(int auditNum, String college, int currentPage, int pageSize) {
			// 当前页的第一条记录
			int m = (currentPage - 1) * pageSize + 1;
			// 当前页的最后一条记录
			int n = currentPage * pageSize;
			String sql = "select * from (select Patname,Patnum,Patsn,Patapdate,Patendate,Patgrad,Patremarks,Paccessory,Tname,message,Paudit,"
					+ "COUNT(*)OVER() AS totalRecord,ROW_NUMBER() over (order by Patsn desc) as r from Patent p join Teacher t on p.Tsn = t.Tsn "
					+ "join College c on t.Csn = c.Csn where Paudit = ? and c.Cname = ? ) as pp where pp.r between ? and ?";
			List params = Arrays.asList(auditNum, college, m, n);
			try {
				ResultSet rs = dbUtil.getResultSet(sql, params);
				IPatentDao patentDao = new PatentDaoImpl();
				return patentDao.getDataList(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}

}
