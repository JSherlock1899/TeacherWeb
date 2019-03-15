package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AdminService;
import service.TeacherService;

/**
 * 修改密码
 */
@WebServlet("/servlet/PasswordServlet")
public class PasswordServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取新旧密码
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		//获取用户身份
		String identity = req.getParameter("identity");
		PrintWriter out = resp.getWriter();
		out.flush();
		if(identity.equals("admin")) {
			AdminService service = new AdminService();
			//获取管理员名
			String Aname = req.getParameter("Aname");
			boolean vaild = false;
			try {
				//判断原密码是否正确
				vaild = service.verifyPassword(Aname, oldPassword);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//修改密码
			if(vaild) {
				try {
					int result = service.alterPassword(Aname, newPassword);
					if(result == 1) {
						out.print("<script>alert('修改密码成功！');</script>");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				out.print("<script>alert('原密码错误！');</script>");
			}
		}else if(identity.equals("teacher")) {
			TeacherService service = new TeacherService();
			//获取教师号
			String Tsn = req.getParameter("Tsn");
			boolean vaild = false;
			try {
				//判断原密码是否正确
				vaild = service.verifyPassword(Tsn, oldPassword);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//修改密码
			if(vaild) {
				try {
					int result = service.alterPassword(Tsn, newPassword);
					if(result == 1) {
						out.print("<script>alert('修改密码成功！');</script>");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				out.print("<script>alert('原密码错误！');</script>");
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
