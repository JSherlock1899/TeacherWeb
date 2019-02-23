package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import service.StatisticsService;
import util.CommonUtil;

/**
 * 数据统计
 */
@WebServlet("/servlet/StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 根据不同的option值来分发到不同的操作
		String option = request.getParameter("option");
		String collegevalue = request.getParameter("collegevalue");// select下拉框中的学院
		String college = request.getParameter("college");// session中的学院
		if (college != null) { // 判断是否是院管理员
			collegevalue = college;
		}
		StatisticsService statisticService = new StatisticsService();
		CommonUtil util = new CommonUtil();
		ResultSet rs; // 初始化存放学院名和count的结果集
		Map<String, Integer> map = new HashMap<String, Integer>();
//		System.out.println(collegevalue);
		if (!collegevalue.equals("null")) {
			try {
				rs = statisticService.getSdeptCount(option, collegevalue);
				map = util.countRsToMap(rs);
				JSONObject json = new JSONObject(map);
				request.setAttribute("json", json);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				rs = statisticService.getCollegeCount(option);
				map = util.countRsToMap(rs);
				JSONObject json = new JSONObject(map);
				request.setAttribute("json", json);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (option != null) {
			if (option.equals("Project")) {
				try {
					rs = statisticService.getProjectMoney();
					map = util.moneyRsToMap(rs);
					JSONObject json = new JSONObject(map);
					request.setAttribute("Pmoney", json);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher("/School/Project/SchoolProjectStatistics.jsp").forward(request, response);
			} else if (option.equals("Paper")) {
				request.getRequestDispatcher("/School/Paper/SchoolPaperStatistics.jsp").forward(request, response);
			} else if (option.equals("Honor")) {
				request.getRequestDispatcher("/School/Honor/SchoolHonorStatistics.jsp").forward(request, response);
			} else if (option.equals("Patent")) {
				request.getRequestDispatcher("/School/Patent/SchoolPatentStatistics.jsp").forward(request, response);
			}
		}
	}
}
