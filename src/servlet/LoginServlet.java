package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.IAdminDao;
import dao.ITeacherDao;
import dao.impl.AdminDaoImpl;
import dao.impl.TeacherDaoImpl;
import model.Admin;
import model.Teacher;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	/**
	 * 判断登录者身份并检验用户名和密码是否正确
	 */
	private static final long serialVersionUID = -5870852067427524781L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		out.flush();
		int type = 1;
		switch (type) { // 先判断是否是管理员若不是则判断是否是老师若还不是再输出提醒
		case 1: {
			IAdminDao adminDao = new AdminDaoImpl();
			Admin admin = new Admin(name, password);
			try {
				int count;
				if (admin != null) {

					count = adminDao.AdminLogin(admin);
					if (count == 1) {// 1为登录成功，0为登录失败，-1为系统异常
						request.getSession().setAttribute("user", admin.getAname());	//获取用户的姓名和所属院系，存入session中
						
						
						
						int result = adminDao.AdminJudge(name);// result为管理员身份判断返回值，0为院管理员，1为校管理员,-1为程序异常
						String Cname = adminDao.getAdminCname(name);
						if (result == 0) {
							// 跳转到院管理员页面
							request.getSession().setAttribute("grade", 2); //设置权限等级
							request.getSession().setAttribute("Cname", Cname);						
							out.print("<script>window.location='School/SchoolAdmin.jsp'</script>");
							return; 
						} else if (result == 1) {
							// 跳转到校管理员页面
							request.getSession().setAttribute("grade", 1);
							out.print("<script>window.location='School/SchoolAdmin.jsp'</script>"); 
							return;
						} else {
							 // 跳转到系统错误的页面
							out.print("<script>alert('系统错误！');window.location='error.jsp'</script>");
							return;
						}
					} else if (count == 0) {	
						// 因为还要判断教师表中是否有符合的用户所以不用进行操作
					} else {
						response.sendRedirect("error.jsp");
						
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			;

		}
		case 2: {

			ITeacherDao teacherDao = new TeacherDaoImpl();
			Teacher teacher = new Teacher(name, password);
			try {
				int count = teacherDao.TeacherLogin(teacher);
				if (count == 1) {
					request.getSession().setAttribute("user", teacher.getTname());
					request.getSession().setAttribute("grade", 3);
					request.getSession().setAttribute("Tsn", request.getParameter("username"));
					out.print("<script>window.location='Teacher/Teacher.jsp'</script>");					
					return;
				} else if (count == 0) {
					out.print("<script>alert('用户名或密码错误！');window.location='login.jsp'</script>");
					return;

				} else {
					out.print("<script>alert('系统错误！');window.location='error.jsp'</script>");
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		default:
			break;
		}
		// response.getWriter().write(loginStatus);

	}

}