package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Poi.ExportExcel;
import dao.IBaseDao;
import dao.ITeacherDao;
import dao.impl.BaseDaoImpl;
import dao.impl.TeacherDaoImpl;
import model.ExcelPatent;
import model.Honor;
import model.Paper;
import model.Patent;
import model.Project;
import util.CommonUnit;
import util.DbUtil;

@WebServlet("/servlet/SelectExport")
public class SelectExport extends HttpServlet {

	/**
	 * 用于各项目的excel导出
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String count = request.getParameter("count");
		String all = request.getParameter("all");
		IBaseDao baseDao = new BaseDaoImpl();
		ITeacherDao teacherDao = new TeacherDaoImpl();
		DbUtil dbutil = new DbUtil();
		CommonUnit util = new CommonUnit();
		String[] ids = request.getParameterValues("select");
		if (count.equals("4")) { // 关于专利表的导出，count为传入用来判断的值
			ExportExcel<Patent> ex = new ExportExcel<Patent>();
			// 表头
			String[] headers = { "专利名", "第一作者", "授权号", "申请时间", "授权时间", "级别", "备注" };
			List<Patent> dataset = new ArrayList<Patent>();
			if (all == null) { // 判断是导出全部数据还是导出部分数据
				// 分别导出所选的数据
				for (int i = 0; i < ids.length; i++) {
					String sql = "select * from Patent p join Teacher t on p.Tsn = t.Tsn where Patsn = '" + ids[i]
							+ "'";
					try {
						ResultSet rs = baseDao.select(sql);
						while (rs.next()) {
							String Pleader = teacherDao.getTname(rs.getString("Tsn"));
							dataset.add(new Patent(rs.getString("Patname"),Pleader , rs.getString("Patsn"),
									rs.getDate("Patendate"), rs.getDate("Patapdate"),
									rs.getString("Patgrad"), rs.getString("Patremarks")));
						}
					} catch (NullPointerException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				String sql = "select * from Patent p join Teacher t on p.Tsn = t.Tsn";
				try {
					ResultSet rs = dbutil.getResultSet(sql, null);
					while (rs.next()) {
						String Pleader = teacherDao.getTname(rs.getString("Tsn"));
						dataset.add(new Patent(rs.getString("Patname"),Pleader , rs.getString("Patsn"),
								rs.getDate("Patendate"), rs.getDate("Patapdate"),
								rs.getString("Patgrad"), rs.getString("Patremarks")));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			OutputStream out = new FileOutputStream("D://专利信息表.xls");
			ex.exportExcel(headers, dataset, out); // 导出excel
			String path = "D://专利信息表.xls";
			util.download(path, response); // 下载弹出框
			out.close();
		} else if (count.equals("3")) { // 关于荣誉信息的导出
			// 表头
			String[] headers = { "编号", "名称", "获奖者", "时间", "颁奖单位", "级别", "奖金", "备注" };
			List<Honor> dataset = new ArrayList<Honor>();
			ExportExcel<Honor> ex = new ExportExcel<Honor>();
			if (all == null) {
				// 分别导出所选的数据
				for (int i = 0; i < ids.length; i++) {
					String sql = "select * from Honor h join Teacher t on h.Tsn = t.Tsn where Hsn = '" + ids[i] + "'";
					try {
						ResultSet rs = baseDao.select(sql);
						while (rs.next()) {
							dataset.add(new Honor(rs.getString("Hsn"), rs.getString("Hname"), rs.getString("Hwinner"),
									rs.getDate("Hdate"), rs.getString("Hcompany"), rs.getString("Hgrad"),
									rs.getString("Hreward"), rs.getString("Hremarks")));
						}
					} catch (NullPointerException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				String sql = "select * from Honor h join Teacher t on h.Tsn = t.Tsn";
				try {
					ResultSet rs = baseDao.select(sql);
					while (rs.next()) {
						dataset.add(new Honor(rs.getString("Hsn"), rs.getString("Hname"), rs.getString("Hwinner"),
								rs.getDate("Hdate"), rs.getString("Hcompany"), rs.getString("Hgrad"),
								rs.getString("Hreward"), rs.getString("Hremarks")));
					}
				} catch (NullPointerException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
			OutputStream out = new FileOutputStream("D://荣誉信息表.xls");
			ex.exportExcel(headers, dataset, out);
			String path = "D://荣誉信息表.xls";
			util.download(path, response); // 下载弹出框
			out.close();
		} else if (count.equals("2")) { // 关于论文信息的导出
			// 表头
			String[] headers = { "检索号", "论文名", "第一作者", "发表期刊", "发表时间", "级别", "备注" };
			List<Paper> dataset = new ArrayList<Paper>();
			ExportExcel<Paper> ex = new ExportExcel<Paper>();
			// 判断是否是导出全部数据
			if (all == null) {
				// 分别导出所选的数据
				for (int i = 0; i < ids.length; i++) {
					String sql = "select * from Paper p join Teacher t on p.Tsn = t.Tsn where Pasearchnum = '" + ids[i] + "'";
					try {
						ResultSet rs = baseDao.select(sql);
						while (rs.next()) {
							dataset.add(new Paper(rs.getString("Pasearchnum"),rs.getString("Paname"), rs.getString("Pawriter"),rs.getString("Papublish"),
									rs.getString("Pagrad"),rs.getDate("Padate"),rs.getString("Paremarks")));
						}
					} catch (NullPointerException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			}else {
					String sql = "select * from Paper p join Teacher t on p.Tsn = t.Tsn";
					try {
						ResultSet rs = baseDao.select(sql);
					while (rs.next()) {
							dataset.add(new Paper(rs.getString("Pasearchnum"),rs.getString("Paname"), rs.getString("Pawriter"),rs.getString("Papublish"),
									rs.getString("Pagrad"),rs.getDate("Padate"),rs.getString("Paremarks")));
						}
					} catch (NullPointerException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			OutputStream out = new FileOutputStream("D://论文信息表.xls");
			ex.exportExcel(headers, dataset, out);
			String path = "D://论文信息表.xls";
			util.download(path, response); // 下载弹出框
			out.close();
		}else if (count.equals("1")) { // 关于项目信息的导出
				// 表头
				String[] headers = { "项目编号", "项目名称", "负责人", "级别", "类型", "科研状态", "成员", "经费", "立项时间", "结题时间", "备注" };
				List<Project> dataset = new ArrayList<Project>();
				ExportExcel<Project> ex = new ExportExcel<Project>();
				// 判断是否是导出全部数据
				if (all == null) {
		
					// 分别导出所选的数据
					for (int i = 0; i < ids.length; i++) {
						String sql = "select * from Project p join Teacher t on p.Tsn = t.Tsn where Psn = '" + ids[i] + "'";
						try {
							ResultSet rs = baseDao.select(sql);
							while (rs.next()) {
								dataset.add(new Project(rs.getString("Psn"),rs.getString("Pname"), rs.getString("Pleader"),rs.getString("Pmember"),
										rs.getString("Pgrad"),rs.getString("Pkind"),rs.getInt("Pmoney"),rs.getDate("Pstatime"),rs.getString("Pcondition"),
										rs.getDate("Pendtime"),rs.getString("Premarks")));
							}
						} catch (NullPointerException | ClassNotFoundException | SQLException e) {
							e.printStackTrace();
						}
					}
				} else {
					String sql = "select * from Project p join Teacher t on p.Tsn = t.Tsn";
					try {
						ResultSet rs = baseDao.select(sql);
						while (rs.next()) {
							dataset.add(new Project(rs.getString("Psn"),rs.getString("Pname"), rs.getString("Pleader"),rs.getString("Pmember"),
									rs.getString("Pgrad"),rs.getString("Pkind"),rs.getInt("Pmoney"),rs.getDate("Pstatime"),rs.getString("Pcondition"),
									rs.getDate("Pendtime"),rs.getString("Premarks")));
						}
					} catch (NullPointerException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
				OutputStream out = new FileOutputStream("D://项目信息表.xls");
				ex.exportExcel(headers, dataset, out);
				String path = "D://项目信息表.xls";
				util.download(path, response); // 下载弹出框
				out.close();
			}
			try {
				baseDao.closeCon();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
