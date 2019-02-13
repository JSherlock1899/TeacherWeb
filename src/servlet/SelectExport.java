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
import dao.BaseDao;
import model.ExcelPatent;
import model.Honor;
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
		BaseDao baseDao = new BaseDao();
		CommonUnit util = new CommonUnit();
		String[] ids = request.getParameterValues("select");
		if (count.equals("4")) { // 关于论文表的导出，count为传入用来判断的值
			ExportExcel<ExcelPatent> ex = new ExportExcel<ExcelPatent>();
			// 表头
			String[] headers = { "专利名", "第一作者", "授权号", "申请时间", "授权时间", "级别", "备注" };
			List<ExcelPatent> dataset = new ArrayList<ExcelPatent>();
			if (all == null) { // 判断是导出全部数据还是导出部分数据
				// 分别导出所选的数据
				for (int i = 0; i < ids.length; i++) {
					String sql = "select * from Patent p join Teacher t on p.Tsn = t.Tsn where Patsn = '" + ids[i]
							+ "'";
					try {
						ResultSet rs = baseDao.select(sql);
						while (rs.next()) {
							dataset.add(new ExcelPatent(rs.getString("Patname"), rs.getString("Tname"),
									rs.getDate("Patendate"), rs.getDate("Patapdate"), rs.getString("Patsn"),
									rs.getString("Patgrad"), rs.getString("Patremarks")));
						}
					} catch (NullPointerException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				String sql = "select * from Patent p join Teacher t on p.Tsn = t.Tsn";
				DbUtil dbutil = new DbUtil();
				try {
					ResultSet rs = dbutil.getResultSet(sql, null);
					while (rs.next()) {
						dataset.add(new ExcelPatent(rs.getString("Patname"), rs.getString("Tname"),
								rs.getDate("Patendate"), rs.getDate("Patapdate"), rs.getString("Patsn"),
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
				DbUtil dbutil = new DbUtil();
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
			} else if (count.equals("2")) { // 关于荣誉信息的导出
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
					DbUtil dbutil = new DbUtil();
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
			try {
				baseDao.closeCon();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
