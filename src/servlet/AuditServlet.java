package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Honor;
import model.Pager;
import model.Paper;
import model.Patent;
import model.Project;
import service.AuditService;
import service.HonorService;
import service.PatentService;
import util.PageUtil;

/**
 * 分发到各个审核页面
 * 对分页进行预处理
 */
@WebServlet("/servlet/AuditServlet")
public class AuditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String option = request.getParameter("option");
		String college = request.getParameter("college");//管理员所在学院
		request.setAttribute("college", college);
		//来判断是否是显示详细信息
		String count = request.getParameter("count");
		//用来确定是datalist中的第几个项目
		String order = request.getParameter("order");
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
		if(option!=null) {
			AuditService auditService = new AuditService();
			PrintWriter out = response.getWriter();
			if(option.equals("Project")) {
				//在数据库中，grade为0代表未审核。grade为1代表审核通过，grade为2代表审核未通过
				List<Project> datalist = auditService.getAllUnauditedProject(0, college, currentPage, pageSize);
				if (datalist.size() > 0) {
					int totalRecord = datalist.get(0).getTotalRecord(); // 获取数据总条数
					int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
					@SuppressWarnings("unchecked")
					Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
					int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
					request.setAttribute("pager", pager);
					request.setAttribute("currentPage", currentPage);
					request.setAttribute("pageArr", pageArr);
					request.getRequestDispatcher("/School/Project/CollegeProjectAudit.jsp").forward(request,response);
				}
				out.println("<script>alert('已不存在未审核的项目')</script>");
			}else if(option.equals("Paper")) {
				List<Paper> datalist = auditService.getAllUnauditedPaper(0, college, currentPage, pageSize);
				if (datalist.size() > 0) {
					int totalRecord = datalist.get(0).getTotalRecord(); // 获取数据总条数
					int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
					@SuppressWarnings("unchecked")
					Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
					int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
					request.setAttribute("pager", pager);
					request.setAttribute("currentPage", currentPage);
					request.setAttribute("pageArr", pageArr);
					request.getRequestDispatcher("/School/Paper/CollegePaperAudit.jsp").forward(request,response);
				}
				out.println("<script>alert('已不存在未审核的论文')</script>");
			}else if(option.equals("Honor")) {
				List<Honor> datalist = auditService.getAllUnauditedHonor(0, college, currentPage, pageSize);
				if (datalist.size() > 0) {
					int totalRecord = datalist.get(0).getTotalRecord(); // 获取数据总条数
					int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
					@SuppressWarnings("unchecked")
					Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
					int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
					request.setAttribute("pager", pager);
					request.setAttribute("currentPage", currentPage);
					request.setAttribute("pageArr", pageArr);
					if(count!=null) {
						if(order!=null) {
							HonorService honorService = new HonorService();
							int number = Integer.parseInt(order);
							Honor honor = datalist.get(number);
							datalist = honorService.getlist(honor);
							//覆盖原先的pager
							pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
							request.setAttribute("pager", pager);
							request.getRequestDispatcher("/School/Patent/CollegeDetailPatentAudit.jsp").forward(request,response);
						}
					}else {
					request.getRequestDispatcher("/School/Honor/CollegeHonorAudit.jsp").forward(request,response);
					}
				}
				out.println("<script>alert('已不存在未审核的荣誉')</script>");
			}else if(option.equals("Patent")) {
				List<Patent> datalist = auditService.getAllUnauditedPatent(0, college, currentPage, pageSize);
				if (datalist.size() > 0) {
					int totalRecord = datalist.get(0).getTotalRecord(); // 获取数据总条数
					int totalPage = pageutil.getTotalPage(totalRecord, pageSize);// 获取总页数
					@SuppressWarnings("unchecked")
					Pager pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
					int[] pageArr = pageutil.getPage(currentPage, totalPage); // 获取正确的页码
					request.setAttribute("pager", pager);
					request.setAttribute("currentPage", currentPage);
					request.setAttribute("pageArr", pageArr);
					//根据是否是显示详细信息来分发到不同的页面
					if(count!=null) {
						if(order!=null) {
							PatentService patentService = new PatentService();
							int number = Integer.parseInt(order);
							//获取单个专利的详细信息
							Patent patent = datalist.get(number);
							datalist = patentService.getlist(patent);
							//覆盖原先的pager
							pager = new Pager(pageSize, currentPage, totalRecord, totalPage, datalist);
							request.setAttribute("pager", pager);
							request.getRequestDispatcher("/School/Patent/CollegeDetailPatentAudit.jsp").forward(request,response);
						}
					}else {
						request.getRequestDispatcher("/School/Patent/CollegePatentAudit.jsp").forward(request,response);
					}
					
				}
				out.println("<script>alert('已不存在未审核的专利')</script>");
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
