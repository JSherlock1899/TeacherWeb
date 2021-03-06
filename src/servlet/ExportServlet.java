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
import dao.IHonorDao;
import dao.IPaperDao;
import dao.IPatentDao;
import dao.IProjectDao;
import dao.ITeacherDao;
import dao.impl.BaseDaoImpl;
import dao.impl.HonorDaoImpl;
import dao.impl.PaperDaoImpl;
import dao.impl.PatentDaoImpl;
import dao.impl.ProjectDaoImpl;
import dao.impl.TeacherDaoImpl;
import model.ExcelHonor;
import model.ExcelPaper;
import model.ExcelPatent;
import model.ExcelProject;
import model.ExcelTeacher;
import model.Honor;
import model.Paper;
import model.Patent;
import model.Project;
import model.Teacher;
import service.HonorService;
import service.PaperService;
import service.PatentService;
import service.ProjectService;
import service.TeacherService;
import util.CommonUtil;

@WebServlet("/servlet/ExportServlet")
public class ExportServlet extends HttpServlet {

	/**
	 * 用于各项目的excel导出
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String count = request.getParameter("count");
		String sdept = request.getParameter("sdept");
		String college = request.getParameter("college");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String Tname = request.getParameter("Tname");
		String totalRecord = request.getParameter("totalRecord");
		//数据总数
		int Record = Integer.parseInt(totalRecord);
		IBaseDao baseDao = new BaseDaoImpl();
		CommonUtil util = new CommonUtil();
		if (count.equals("4")) { // 关于专利表的导出，count为传入用来判断的值
			ExportExcel<ExcelPatent> ex = new ExportExcel<ExcelPatent>();
			// 表头
			String[] headers = { "专利名", "第一作者", "授权号", "申请时间", "授权时间", "级别", "备注" };
			List<ExcelPatent> dataset = new ArrayList<ExcelPatent>();
			PatentService patentService = new PatentService();
				// 分别导出所选的数据
			try {	
					ResultSet rs = patentService.selectCollegePatent(college, sdept, starttime, endtime,Tname, 1, Record);
					dataset = patentService.getExcelDataList(rs);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			OutputStream out = new FileOutputStream("D://专利信息表.xls");
			ex.exportExcel(headers, dataset, out); // 导出excel
			String path = "D://专利信息表.xls";
			util.download(path, response); // 下载弹出框
			out.close();
		} else if (count.equals("3")) { // 关于荣誉信息的导出
			// 表头
			String[] headers = { "编号", "名称", "获奖者", "时间", "颁奖单位", "级别", "奖金", "备注" };
			List<ExcelHonor> dataset = new ArrayList<ExcelHonor>();
			ExportExcel<ExcelHonor> ex = new ExportExcel<ExcelHonor>();
			HonorService honorService = new HonorService();
				// 分别导出所选的数据
			try {		
					ResultSet rs = honorService.selectCollegeHonor(college, sdept, starttime,Tname, 1, Record);
					dataset = honorService.getExcelDataList(rs);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			OutputStream out = new FileOutputStream("D://荣誉信息表.xls");
			ex.exportExcel(headers, dataset, out);
			String path = "D://荣誉信息表.xls";
			util.download(path, response); // 下载弹出框
			out.close();
		} else if (count.equals("2")) { // 关于论文信息的导出
			// 表头
			String[] headers = { "检索号", "论文名", "第一作者", "发表时间", "发表期刊", "级别", "备注" };
			List<ExcelPaper> dataset = new ArrayList<ExcelPaper>();
			ExportExcel<ExcelPaper> ex = new ExportExcel<ExcelPaper>();
			PaperService paperService = new PaperService();
				// 分别导出所选的数据
			try {		
					ResultSet rs = paperService.selectCollegePaper(college, sdept, starttime,Tname, 1, Record);
					dataset = paperService.getExcelDataList(rs);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			OutputStream out = new FileOutputStream("D://论文信息表.xls");
			ex.exportExcel(headers, dataset, out);
			String path = "D://论文信息表.xls";
			util.download(path, response); // 下载弹出框
			out.close();
		}else if (count.equals("1")) { // 关于项目信息的导出
				// 表头
				String[] headers = { "项目编号", "项目名称", "负责人", "级别", "类型", "科研状态", "成员", "经费", "立项时间", "结题时间", "备注" };
				List<ExcelProject> dataset = new ArrayList<ExcelProject>();
				ExportExcel<ExcelProject> ex = new ExportExcel<ExcelProject>();
				ProjectService projectService = new ProjectService();
					// 分别导出所选的数据
				try {		
						ResultSet rs = projectService.selectCollegeProject(college, sdept, starttime,endtime,Tname, 1, Record);
						dataset = projectService.getExcelDataList(rs);
					} catch (NullPointerException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				OutputStream out = new FileOutputStream("D://项目信息表.xls");
				ex.exportExcel(headers, dataset, out);
				String path = "D://项目信息表.xls";
				util.download(path, response); // 下载弹出框
				out.close();
			}else if(count.equals("5")) {
				// 表头
				String[] headers = { "教师号", "教师名",  "性别", "联系电话", "邮箱", "身份证号", "所属学院", "所属系"};
				List<ExcelTeacher> dataset = new ArrayList<ExcelTeacher>();
				ExportExcel<ExcelTeacher> ex = new ExportExcel<ExcelTeacher>();
				ITeacherDao dao = new TeacherDaoImpl();
				TeacherService teacherService = new TeacherService();
					// 分别导出所选的数据
				try {		
						ResultSet rs = teacherService.selectTeacher(college, sdept, starttime,1, Record);
						dataset = teacherService.getExcelDataList(rs);
					} catch (NullPointerException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				OutputStream out = new FileOutputStream("D://教师信息表.xls");
				ex.exportExcel(headers, dataset, out);
				String path = "D://教师信息表.xls";
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
