package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.IBaseDao;
import dao.ITeacherDao;
import dao.impl.BaseDaoImpl;
import dao.impl.TeacherDaoImpl;
import model.Honor;
import model.Pager;
import model.Paper;
import model.Patent;
import model.Project;
import model.Teacher;
import service.HonorService;
import service.PaperService;
import service.PatentService;
import service.ProjectService;
import service.TeacherService;
import util.PageUtil;

/**
 * 处理分页请求
 * 对分页进行预处理
 */
@WebServlet("/servlet/PageServlet")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 根据不同的option值来分发到不同的jsp
		String option = request.getParameter("option");
		//来判断是否是显示详细信息
		String count = request.getParameter("count");
		//用来确定是datalist中的第几个项目
		String order = request.getParameter("order");
		String collegevalue = request.getParameter("collegevalue");// select下拉框中的学院
		String college = request.getParameter("college");// session中的学院
		String teacher = request.getParameter("teacher");
		if (college != null) { // 判断是否是院管理员
			collegevalue = college;
		}
		String sdeptValue = request.getParameter("sdeptValue");// 所属专业
		String starttime = request.getParameter("starttime");// 起止时间
		String endtime = request.getParameter("endtime");
		String selectByNameVal = request.getParameter("selectByNameVal");// 搜索人名字
		String currentPageString = request.getParameter("currentPage");// 当前页页码
		String pageSizeString = request.getParameter("pageSizeSelect");// 当前页数据条数
		int currentPage;
		int pageSize;
		PageUtil pageutil = new PageUtil();
		// 第一次进入时分别对当前页面与当前一页显示多少条记录进行初始化
		if (currentPageString == null || currentPageString.equals("undefined")) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		if (pageSizeString == null || currentPageString.equals("undefined")) {
			pageSize = 5;// 默认每页显示5条数据
		} else {
			pageSize = Integer.parseInt(request.getParameter("pageSizeSelect"));
		}
		IBaseDao baseDao = new BaseDaoImpl();
		ITeacherDao teacherDao = new TeacherDaoImpl();
		//当前页的第一条记录
		int m = (currentPage - 1) * pageSize + 1;
		//当前页的最后一条记录
		int n = currentPage * pageSize;
		if(teacher!=null) {
			if (teacher.equals("admin")) {// 判断是否是管理员
				//管理员部分
				if (option.equals("Patent")) {
					PatentService patentService = new PatentService();
					ResultSet patentRs = patentService.selectCollegePatent(collegevalue, sdeptValue, starttime, endtime,
							selectByNameVal, m, n);
					List<Patent> datalist = patentService.getDataList(patentRs); //获取查询得到的结果集
					if (datalist.size() > 0) {
						int totalRecord = datalist.get(0).getTotalRecord();// 获取数据总条数
						int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
						@SuppressWarnings("unchecked")
						Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
						int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
						request.setAttribute("pager", pager);
						request.setAttribute("currentPage", currentPage);
						request.setAttribute("pageArr", pageArr);
						request.setAttribute("college", collegevalue);
						request.setAttribute("sdept", sdeptValue);
						request.setAttribute("starttime", starttime);
						request.setAttribute("endtime", endtime);
						request.setAttribute("Tname", selectByNameVal);
						request.getRequestDispatcher("/School/Patent/SchoolPatentSelect.jsp").forward(request, response);
					}
				} else if (option.equals("Honor")) {
					HonorService honorservice = new HonorService();
					ResultSet honorrs = honorservice.selectCollegeHonor(collegevalue, sdeptValue, starttime,
							selectByNameVal, currentPage, pageSize);
					List<Honor> datalist = honorservice.getDataList(honorrs);
					if (datalist.size() > 0) {
						int totalRecord = datalist.get(0).getTotalRecord();// 获取数据总条数
						int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
						@SuppressWarnings("unchecked")
						Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
						int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
						request.setAttribute("pager", pager);
						request.setAttribute("currentPage", currentPage);
						request.setAttribute("pageArr", pageArr);
						request.setAttribute("college", collegevalue);
						request.setAttribute("sdept", sdeptValue);
						request.setAttribute("starttime", starttime);
						request.setAttribute("Tname", selectByNameVal);
						request.getRequestDispatcher("/School/Honor/SchoolHonorSelect.jsp").forward(request, response);
					}
				} else if (option.equals("Project")) {
					ProjectService projectService = new ProjectService();
					ResultSet projectRs;
					try {
						projectRs = projectService.selectCollegeProject(collegevalue, sdeptValue, starttime, endtime,
								selectByNameVal, m, n);
						List<Project> datalist = projectService.getDataList(projectRs);
						if (datalist.size() > 0) {
							int totalRecord = datalist.get(0).getTotalRecord();// 获取数据总条数
							int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
							@SuppressWarnings("unchecked")
							Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
							int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
							request.setAttribute("pager", pager);
							request.setAttribute("currentPage", currentPage);
							request.setAttribute("pageArr", pageArr);
							request.setAttribute("college", collegevalue);
							request.setAttribute("sdept", sdeptValue);
							request.setAttribute("starttime", starttime);
							request.setAttribute("endtime", endtime);
							request.setAttribute("Tname", selectByNameVal);
							request.getRequestDispatcher("/School/Project/SchoolProjectSelect.jsp").forward(request,
									response);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (option.equals("Paper")) {
					PaperService paperService = new PaperService();
					ResultSet paperRs = paperService.selectCollegePaper(collegevalue, sdeptValue, starttime,
							selectByNameVal, currentPage, pageSize);
					List<Paper> datalist = paperService.getDataList(paperRs);
					if (datalist.size() > 0) {
						int totalRecord = datalist.get(0).getTotalRecord();// 获取数据总条数
						int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
						@SuppressWarnings("unchecked")
						Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
						int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
						request.setAttribute("pager", pager);
						request.setAttribute("currentPage", currentPage);
						request.setAttribute("pageArr", pageArr);
						request.setAttribute("college", collegevalue);
						request.setAttribute("sdept", sdeptValue);
						request.setAttribute("starttime", starttime);
						request.setAttribute("Tname", selectByNameVal);
						request.getRequestDispatcher("/School/Paper/SchoolPaperSelect.jsp").forward(request, response);

					}
				}else if (option.equals("Teacher")) {
					TeacherService teacherService = new TeacherService();
					ResultSet teacherrRs;
					try {
						teacherrRs = teacherService.selectTeacher(college, sdeptValue, selectByNameVal, m, n);
						List<Teacher> datalist = teacherService.getDataList(teacherrRs);
						if (datalist.size() > 0) {
							int totalRecord = datalist.get(0).getTotalRecord();// 获取数据总条数
							int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
							@SuppressWarnings("unchecked")
							Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
							int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
							request.setAttribute("pager", pager);
							request.setAttribute("currentPage", currentPage);
							request.setAttribute("pageArr", pageArr);
							request.setAttribute("college", collegevalue);
							request.setAttribute("sdept", sdeptValue);
							request.setAttribute("Tname", selectByNameVal);
							request.getRequestDispatcher("/School/Teacher/SchoolTeacherSelect.jsp").forward(request, response);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				// 教师部分
				String Tsn = (String) request.getSession().getAttribute("Tsn");
				if (option.equals("Patent")) {
					PatentService patentService = new PatentService();
					ResultSet patentRs;
					try {
						patentRs = teacherDao.getPatent(Tsn,currentPage,pageSize);
						List<Patent> datalist = patentService.getDataList(patentRs);
						if (datalist.size() > 0) {
							int totalRecord = datalist.get(0).getTotalRecord();// 获取数据总条数
							int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
							@SuppressWarnings("unchecked")
							Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
							int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
							request.setAttribute("pager", pager);
							request.setAttribute("currentPage", currentPage);
							request.setAttribute("pageArr", pageArr);
							if(count.equals("1")) {
								if(order!=null) {
									int number = Integer.parseInt(order);
									//获取要获取详细信息的那个patent对象
									Patent patent = datalist.get(number);
									//将patent对象转化为List
									datalist = patentService.getlist(patent);
									//覆盖原先的pager
									pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
									request.setAttribute("pager", pager);
								}	
								request.getRequestDispatcher("/Teacher/Patent/TeacherDetailPatent.jsp").forward(request, response);
							}else {
								request.getRequestDispatcher("/Teacher/Patent/TeacherPatent.jsp").forward(request, response);
							}
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else if (option.equals("Honor")) {
					HonorService honorservice = new HonorService();
					ResultSet honorrs;
					try {
						honorrs = teacherDao.getHonor(Tsn,currentPage,pageSize);
						List<Honor> datalist = honorservice.getDataList(honorrs);
						if (datalist.size() > 0) {
							int totalRecord = datalist.get(0).getTotalRecord();// 获取数据总条数
							int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
							@SuppressWarnings("unchecked")
							Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
							int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
							request.setAttribute("pager", pager);
							request.setAttribute("currentPage", currentPage);
							request.setAttribute("pageArr", pageArr);
							request.getRequestDispatcher("/Teacher/Honor/TeacherHonor.jsp").forward(request, response);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (option.equals("Project")) {
					ProjectService projectService = new ProjectService();
					ResultSet projectRs;
					try {
						projectRs = teacherDao.getProject(Tsn,currentPage,pageSize);
						List<Project> datalist = projectService.getDataList(projectRs);
						if (datalist.size() > 0) {
							int totalRecord = datalist.get(0).getTotalRecord();// 获取数据总条数
							int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
							@SuppressWarnings("unchecked")
							Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
							int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
							request.setAttribute("pager", pager);
							request.setAttribute("currentPage", currentPage);
							request.setAttribute("pageArr", pageArr);
							request.getRequestDispatcher("/Teacher/Project/TeacherProject.jsp").forward(request, response);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else if (option.equals("Paper")) {
					PaperService paperService = new PaperService();
					ResultSet paperRs;
					try {
						paperRs = teacherDao.getPaper(Tsn,currentPage,pageSize);
						List<Paper> datalist = paperService.getDataList(paperRs);
						if (datalist.size() > 0) {
							int totalRecord = datalist.get(0).getTotalRecord();// 获取数据总条数
							int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
							@SuppressWarnings("unchecked")
							Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
							int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
							request.setAttribute("pager", pager);
							request.setAttribute("currentPage", currentPage);
							request.setAttribute("pageArr", pageArr);
							request.getRequestDispatcher("/Teacher/Paper/TeacherPaper.jsp").forward(request, response);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
