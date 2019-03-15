package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.IBaseDao;
import dao.impl.BaseDaoImpl;
import model.Project;
import service.ProjectService;
import util.CommonUtil;

/**
 * 对论文表进行增删改
 */
@WebServlet("/servlet/ProjectServlet")
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 根据不同的value值来分发到不同的操作
		String value = request.getParameter("value");
		IBaseDao baseDao = new BaseDaoImpl();
		CommonUtil commondao = new CommonUtil();
		ProjectService Projectservice = new ProjectService();
		String Psn = request.getParameter("Psn"); // 获取要更新的各字段值
		String Pleader = request.getParameter("Pleader"); // 项目第一责任人
		String Pname = request.getParameter("Pname");
		String Pmember = request.getParameter("Pmember");
		String Pgrad = request.getParameter("Pgrad");
		String Pkind = request.getParameter("Pkind");
		int Pmoney = 0;
		if(request.getParameter("Pmoney")!=null && !request.getParameter("Pmoney").equals("")) {
			Pmoney = Integer.parseInt(request.getParameter("Pmoney"));
		}
		String Pstatime = request.getParameter("Pstatime"); // 获取要更新的各字段值
		String Pcondition = request.getParameter("Pcondition"); // 项目第一责任人
		String Pendtime = request.getParameter("Pendtime");
		String Premarks = request.getParameter("Premarks");
		String Paccessory = request.getParameter("Paccessory");
		//审核意见
		String message = request.getParameter("message");
		System.out.println("message = " + message);
		if (value.equals("1")) { // 删除对应的项目信息
			try {
				int result = Projectservice.delProject(Psn);
				PrintWriter out = response.getWriter();
				out.flush();
				if (result > 0) {
					out.print("<script>alert('删除成功！')</script>");
				} else {
					out.print("<script>alert('删除失败！')</script>");
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				PrintWriter out = response.getWriter();
				out.flush();
				out.print("<script>alert('系统错误！')");
			}
		} else if (value.equals("2")) { // 修改对应的项目信息
			int result;
			Date pstatime = commondao.stringToDate(Pstatime); // 将 string转化为date
			Date pendtime = commondao.stringToDate(Pendtime); // 将 string转化为date信息·
			Project Project = new Project(Psn, Pleader, Pname, Pmember, Pgrad, Pkind, Pmoney,pstatime,Pcondition,pendtime,Premarks,Paccessory); // 构造要传入insert和update中的Project对象
			PrintWriter out = response.getWriter(); // 获取response对象，通过getWriter方法获取PrintWriter对象
			out.flush(); // 清空缓存
			try {
				result = Projectservice.alterProject(Project);
				if (result == 1) {
					out.print("<script>alert('修改成功！')</script>");
				} else if(result == -1) {
					out.print("<script>alert('该项目号已存在！')</script>");
				}else{
					out.print("<script>alert('系统错误！')</script>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (value.equals("3")) {
			int result;
			Date pstatime = commondao.stringToDate(Pstatime); // 将 string转化为date
			Date pendtime = commondao.stringToDate(Pendtime); // 将 string转化为date
			Project Project = new Project(Psn, Pname, Pleader, Pmember, Pgrad, Pkind, Pmoney,pstatime,Pcondition,pendtime,Premarks); // 构造要传入insert和update中的Project对象
			PrintWriter out = response.getWriter(); // 通过servlet的doget方法获取response对象，通过getWriter方法获取PrintWriter对象
			out.flush(); // 清空缓存
			try {
				result = Projectservice.insertProject(Project);
				if (result == 1) {
					out.print("<script>alert('添加成功！')</script>");
				} else if(result == -1) {
					out.print("<script>alert('该项目号已存在！')</script>");
				}else{
					out.print("<script>alert('系统错误！')</script>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}else if(value.equals("4")) {			//审核通过
			Projectservice.projectAudit(Psn, "1",message);
		}else if(value.equals("5")) {			//审核不通过
			Projectservice.projectAudit(Psn, "2",message);
		}
		try {
			baseDao.closeCon(); //关闭资源
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
