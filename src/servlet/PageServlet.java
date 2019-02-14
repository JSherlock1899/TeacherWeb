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
import dao.impl.BaseDaoImpl;
import model.Honor;
import model.Pager;
import model.Paper;
import model.Patent;
import model.Project;
import service.HonorService;
import service.PaperService;
import service.PatentService;
import service.ProjectService;
import util.PageUtil;

/**
 * 处理分页请求
 */
@WebServlet("/servlet/PageServlet")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				// 根据不同的option值来分发到不同的jsp
				String option = request.getParameter("option");
				String collegevalue = request.getParameter("collegevalue");//所属学院
				String college = request.getParameter("college");//所属学院
				if(college!=null) {				//判断是否是院管理员
					collegevalue = college;
				}
				String sdeptValue = request.getParameter("sdeptValue");//所属专业
				String starttime = request.getParameter("starttime");//起止时间
				String endtime = request.getParameter("endtime");
				String selectByNameVal = request.getParameter("selectByNameVal");//搜索人名字		
				String currentPageString = request.getParameter("currentPage");//当前页页码
				String pageSizeString = request.getParameter("pageSizeSelect");//当前页数据条数
				int currentPage;
				int pageSize;
				PageUtil pageutil = new PageUtil();
				//第一次进入时分别对当前页面与当前一页显示多少条记录进行初始化
				if(currentPageString == null || currentPageString.equals("undefined")){
					currentPage = 1;
				}else{
					currentPage = Integer.parseInt(request.getParameter("currentPage"));
				}
				if(pageSizeString == null || currentPageString.equals("undefined")){
					pageSize = 5;//默认每页显示5条数据
				}else{
					pageSize = Integer.parseInt(request.getParameter("pageSizeSelect"));
				}
				IBaseDao baseDao = new BaseDaoImpl();
				if(option.equals("Patent")) {
					PatentService patentService = new PatentService();
					ResultSet patentRs = patentService.selectCollegePatent(collegevalue, sdeptValue, starttime, endtime, selectByNameVal, currentPage, pageSize);
					List<Patent> datalist = patentService.getDataList(patentRs);
					if(datalist.size()>0) {
						int totalRecord = datalist.get(0).getTotalRecord();//获取数据总条数
						int totalPage = pageutil.getTotalPage(totalRecord, pageSize);//获取总页数
						@SuppressWarnings("unchecked")
						Pager pager = new Pager(pageSize,currentPage,totalRecord,totalPage,datalist);
						int[] pageArr = pageutil.getPage(currentPage,totalPage); //获取正确的页码
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
				}else if(option.equals("Honor")) {
					HonorService honorservice = new HonorService();
					ResultSet honorrs = honorservice.selectCollegeHonor(collegevalue, sdeptValue, starttime, selectByNameVal, currentPage, pageSize);
					List<Honor> datalist = honorservice.getDataList(honorrs);
					if(datalist.size()>0) {
						int totalRecord = datalist.get(0).getTotalRecord();//获取数据总条数
						int totalPage = pageutil.getTotalPage(totalRecord, pageSize);//获取总页数
						@SuppressWarnings("unchecked")
						Pager pager = new Pager(pageSize,currentPage,totalRecord,totalPage,datalist);
						int[] pageArr = pageutil.getPage(currentPage,totalPage); //获取正确的页码
						request.setAttribute("pager", pager);
						request.setAttribute("currentPage", currentPage);
						request.setAttribute("pageArr", pageArr);
						request.setAttribute("college", collegevalue);
						request.setAttribute("sdept", sdeptValue);
						request.setAttribute("starttime", starttime);
						request.setAttribute("Tname", selectByNameVal);
						request.getRequestDispatcher("/School/Honor/SchoolHonorSelect.jsp").forward(request, response);
					}
				}else if(option.equals("Project")) {
					ProjectService projectService = new ProjectService();
					ResultSet projectRs;
					try {
						projectRs = projectService.selectCollegeProject(collegevalue, sdeptValue,starttime, endtime, selectByNameVal, currentPage, pageSize);
						List<Project> datalist = projectService.getDataList(projectRs);
						if(datalist.size()>0) {
							int totalRecord = datalist.get(0).getTotalRecord();//获取数据总条数
							int totalPage = pageutil.getTotalPage(totalRecord, pageSize);//获取总页数
							@SuppressWarnings("unchecked")
							Pager pager = new Pager(pageSize,currentPage,totalRecord,totalPage,datalist);
							int[] pageArr = pageutil.getPage(currentPage,totalPage); //获取正确的页码
							request.setAttribute("pager", pager);
							request.setAttribute("currentPage", currentPage);
							request.setAttribute("pageArr", pageArr);
							request.setAttribute("college", collegevalue);
							request.setAttribute("sdept", sdeptValue);
							request.setAttribute("endtime", endtime);
							request.setAttribute("Tname", selectByNameVal);
							request.getRequestDispatcher("/School/Project/SchoolProjectSelect.jsp").forward(request, response);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if(option.equals("Paper")) {
					PaperService paperService = new PaperService();
					ResultSet paperRs = paperService.selectCollegePaper(collegevalue, sdeptValue, starttime, selectByNameVal, currentPage, pageSize);
					List<Paper> datalist = paperService.getDataList(paperRs);
					if(datalist.size()>0) {
						int totalRecord = datalist.get(0).getTotalRecord();//获取数据总条数
						int totalPage = pageutil.getTotalPage(totalRecord, pageSize);//获取总页数
						@SuppressWarnings("unchecked")
						Pager pager = new Pager(pageSize,currentPage,totalRecord,totalPage,datalist);
						int[] pageArr = pageutil.getPage(currentPage,totalPage); //获取正确的页码
						request.setAttribute("pager", pager);
						request.setAttribute("currentPage", currentPage);
						request.setAttribute("pageArr", pageArr);
						request.setAttribute("college", collegevalue);
						request.setAttribute("sdept", sdeptValue);
						request.setAttribute("starttime", starttime);
						request.setAttribute("Tname", selectByNameVal);
						request.getRequestDispatcher("/School/Paper/SchoolPaperSelect.jsp").forward(request, response);
					
					}
				}
			}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
