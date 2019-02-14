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
import model.Honor;
import service.HonorService;
import util.CommonUnit;

/**
 * 对荣誉表的增删改
 */
@WebServlet("/servlet/HonorServlet")
public class HonorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				// 根据不同的value值来分发到不同的操作
				String value = request.getParameter("value");
				IBaseDao baseDao = new BaseDaoImpl();
				HonorService honorservice = new HonorService();
				CommonUnit commondao = new CommonUnit();
				String Hsn = request.getParameter("Hsn"); // 获取要更新的各字段值
				String Hname = request.getParameter("Hname"); // 专利第一责任人
				String Hwinner = request.getParameter("Hwinner");
				String Hdate = request.getParameter("Hdate");
				String Hcompany = request.getParameter("Hcompany");
				String Hgrad = request.getParameter("Hgrad");
				String Hreward = request.getParameter("Hreward");
				String Hremarks = request.getParameter("Hremarks");
				if (value.equals("1")) { // 删除对应的专利信息
					try {
						int result = honorservice.delHonor(Hsn);
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
				} else if (value.equals("2")) { // 修改对应的专利信息
					int result;
					Date hdate = commondao.stringToDate(Hdate); // 将 string转化为date
					Honor honor = new Honor(Hsn,Hname,Hwinner,hdate,Hcompany,Hgrad,Hreward,Hremarks); // 构造要传入insert和update中的honor对象
					PrintWriter out = response.getWriter(); // 通过servlet的doget方法获取response对象，通过getWriter方法获取PrintWriter对象
					out.flush(); // 清空缓存
					try {
						result = honorservice.alterHonor(honor);
						if (result > 0) {
							out.print("<script>alert('操作成功！')");
						} else {
							out.print("<script>alert('操作失败！')");
						}
					} catch (SQLException e) {
						out.print("<script>alert('系统错误！')");
						e.printStackTrace();
					}
				} else if (value.equals("3")) {
					int result;
					Date hdate = commondao.stringToDate(Hdate); // 将 string转化为date
					Honor honor = new Honor(Hsn,Hname,Hwinner,hdate,Hcompany,Hgrad,Hreward,Hremarks); // 构造要传入insert和update中的honor对象
					PrintWriter out = response.getWriter(); // 通过servlet的doget方法获取response对象，通过getWriter方法获取PrintWriter对象
					out.flush(); // 清空缓存
					try {
						result = honorservice.insertHonor(honor);
						if (result > 0) {
							out.print("<script>alert('操作成功！')");
						} else {
							out.print("<script>alert('操作失败！')");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					baseDao.closeCon(); //关闭资源
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

}
