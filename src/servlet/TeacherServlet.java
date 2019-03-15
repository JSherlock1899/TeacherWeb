package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ITeacherDao;
import dao.impl.TeacherDaoImpl;
import model.Teacher;
import service.TeacherService;

/**
 * 教师相关操作
 */
@WebServlet("/servlet/TeacherServlet")
public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String value = request.getParameter("value");
		String Tsn = request.getParameter("Tsn");
		String Tname = request.getParameter("Tname");
		String Tsex = request.getParameter("Tsex");
		String Ttel = request.getParameter("Ttel");
		String Tmail = request.getParameter("Tmail");
		String Cname = request.getParameter("Cname");
		String Sdept = request.getParameter("Sdept");
		String TID = request.getParameter("TID");
//		System.out.println("Tsn = " + Tsn);
//		System.out.println("Tname = " + Tname);
//		System.out.println("Tsex = " + Tsex);
//		System.out.println("Ttel = " + Ttel);
//		System.out.println("Tsn = " + Tsn);
//		System.out.println("Tmail = " + Tmail);
//		System.out.println("Cname = " + Cname);
//		System.out.println("Sdept = " + Sdept);
		TeacherService service = new TeacherService();
		PrintWriter out = response.getWriter();
		out.flush();
		if(value!=null) {
			if(value.equals("1")) {				//删除教师信息
				try {
					int result = service.delTeacher(Tsn);
					if (result > 0) {
						out.print("<script>alert('删除成功！')</script>");
					} else {
						out.print("<script>alert('删除失败！')</script>");
					}
				} catch (SQLException e) {
					out.print("<script>alert('系统错误！')</script>");
					e.printStackTrace();
				}
			}else if(value.equals("2")) {
				Teacher teacher = new Teacher(Tsn,Tname,Tsex,Ttel,Tmail,Cname,Sdept,TID);
				int result;
				try {
					result = service.alterTeacher(teacher);
					if (result > 0) {
						out.print("<script>alert('修改成功！')</script>");
					} else {
						out.print("<script>alert('修改失败！')</script>");
					}
				} catch (SQLException e) {
					out.print("<script>alert('输入信息有误！')</script>");
					e.printStackTrace();
				}
			}else if(value.equals("3")) {
				Teacher teacher = new Teacher(Tsn,Tname,Tsex,Ttel,Tmail,Cname,Sdept,TID);
				int result;
				try {
					result = service.insertTeacher(teacher);
					if (result > 0) {
						out.print("<script>alert('新建成功！')</script>");
					} else {
						out.print("<script>alert('新建失败！')</script>");
					}
				} catch (SQLException e) {
					out.print("<script>alert('新建信息有误！')</script>");
					e.printStackTrace();
				}
			}
		}
	
	}

}
