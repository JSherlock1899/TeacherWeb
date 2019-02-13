package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BaseDao;
import dao.impl.PatentDaoImpl;
import model.Patent;
import util.CommonUnit;

/**
 * 对论文表进行增删改
 */
@WebServlet("/servlet/PaperServlet")
public class PaperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 根据不同的value值来分发到不同的操作
		String value = request.getParameter("value");
		System.out.println(value);
		// 解决中文乱码
		response.setContentType("text/html;charset=utf-8");
		// 请求解决乱码
		request.setCharacterEncoding("utf-8");
		// 响应解决乱码
		response.setCharacterEncoding("utf-8");
		BaseDao baseDao = new BaseDao();
		PatentDaoImpl patentdao = new PatentDaoImpl();
		CommonUnit commondao = new CommonUnit();
		String Patname = request.getParameter("Patname"); // 获取要更新的各字段值
		String Pleader = request.getParameter("Pleader"); // 专利第一责任人
		String Patsn = request.getParameter("Patsn");
		String Patapdate = request.getParameter("Patapdate");
		String Patemdate = request.getParameter("Patemdate");
		String Patgrad = request.getParameter("Patgrad");
		String Patremarks = request.getParameter("Patremarks");
		if (value.equals("1")) { // 删除对应的专利信息
			try {
				int result = patentdao.delPatent(Patsn);
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
			Date patapdate = commondao.stringToDate(Patapdate); // 将 string转化为date
			Date patemdate = commondao.stringToDate(Patemdate); // 将 string转化为date信息·
			Patent patent = new Patent(Patname, Pleader, Patsn, patapdate, patemdate, Patgrad, Patremarks); // 构造要传入insert和update中的patent对象
			PrintWriter out = response.getWriter(); // 获取response对象，通过getWriter方法获取PrintWriter对象
			out.flush(); // 清空缓存
			try {
				result = patentdao.alterPatent(patent);
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
			Date patapdate = commondao.stringToDate(Patapdate); // 将 string转化为date
			Date patemdate = commondao.stringToDate(Patemdate); // 将 string转化为date
			Patent patent = new Patent(Patname, Pleader, Patsn, patapdate, patemdate, Patgrad, Patremarks); // 构造要传入insert和update中的patent对象
			PrintWriter out = response.getWriter(); // 通过servlet的doget方法获取response对象，通过getWriter方法获取PrintWriter对象
			out.flush(); // 清空缓存
			result = patentdao.insertPatent(patent);
			if (result > 0) {
				out.print("<script>alert('操作成功！')");
			} else {
				out.print("<script>alert('操作失败！')");
			}
		}
		try {
			baseDao.closeCon(); //关闭资源
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
