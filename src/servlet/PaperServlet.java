package servlet;

import java.awt.print.Paper;
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
import dao.IBaseDao;
import dao.IPaperDao;
import dao.impl.BaseDaoImpl;
import dao.impl.PaperDaoImpl;
import model.Patent;
import service.PaperService;
import util.CommonUtil;

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
		// 解决中文乱码
		response.setContentType("text/html;charset=utf-8");
		// 请求解决乱码
		request.setCharacterEncoding("utf-8");
		// 响应解决乱码
		response.setCharacterEncoding("utf-8");
		IBaseDao baseDao = new BaseDaoImpl();
		IPaperDao paperDao = new PaperDaoImpl();
		CommonUtil commondao = new CommonUtil();
		String Pasearchnum = request.getParameter("Pasearchnum"); // 获取要更新的各字段值
		String Paname = request.getParameter("Paname"); 
		String Pawriter = request.getParameter("Pawriter");
		String Papublish = request.getParameter("Papublish");
		String Pdisvol = request.getParameter("Pdisvol");		//期/卷/页
		String Padate = request.getParameter("Padate");
		String Pagrad = request.getParameter("Pagrad");
		String Paremarks = request.getParameter("Paremarks");
		//审核意见
		String message = request.getParameter("message");
		PaperService paperService = new PaperService();
		if (value.equals("1")) { // 删除对应的论文信息
			try {
				int result = paperService.delPaper(Pasearchnum);
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
		} else if (value.equals("2")) { // 修改对应的论文信息
			int result;
			Date padate = commondao.stringToDate(Padate); // 将 string转化为date
			model.Paper paper = new model.Paper(Pasearchnum, Paname, Pawriter, Papublish, Pagrad, padate, Paremarks,Pdisvol); // 构造要传入insert和update中的paper对象
			PrintWriter out = response.getWriter(); // 获取response对象，通过getWriter方法获取PrintWriter对象
			out.flush(); // 清空缓存
			try {
				result = paperService.alterPaper(paper);
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
			Date padate = commondao.stringToDate(Padate); // 将 string转化为date
			model.Paper paper = new model.Paper(Pasearchnum, Paname, Pawriter, Papublish, Pagrad, padate, Paremarks,Pdisvol);// 构造要传入insert和update中的patent对象
			PrintWriter out = response.getWriter(); // 通过servlet的doget方法获取response对象，通过getWriter方法获取PrintWriter对象
			out.flush(); // 清空缓存
			try {
				result = paperService.insertPaper(paper);
				if (result > 0) {
					out.print("<script>alert('操作成功！')");
				} else {
					out.print("<script>alert('操作失败！')");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else if(value.equals("4")) {
			paperService.paperAudit(Pasearchnum, "1",message);	//审核通过
		}else if(value.equals("5")) {
			paperService.paperAudit(Pasearchnum, "2",message);	//审核不通过
		}
		try {
			baseDao.closeCon(); //关闭资源
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
