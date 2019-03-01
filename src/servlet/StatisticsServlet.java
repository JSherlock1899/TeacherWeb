package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
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

	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 根据不同的option值来分发到不同的操作
		String option = request.getParameter("option");
		String grade = request.getParameter("grade");
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
		//院管理员操作
		if (!collegevalue.equals("null")) {
			try {
				//获取统计结果的结果集
				rs = statisticService.getSdeptCount(option, collegevalue);
				//将结果集转化为Hashmap
				map = util.collegeCountRsToMap(rs);
				//将map转化为json，以传给前端
				JSONObject json = new JSONObject(map);
				request.setAttribute("json", json);
				//获取各专业近5年项目总数
				List countList = statisticService.getRecentYearsSdeptCount(option, collegevalue);
				JSONArray jsonList = new JSONArray(countList);
				request.setAttribute("jsonList", jsonList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				rs = statisticService.getCollegeCount(option);
				map = util.countRsToMap(rs);
				JSONObject json = new JSONObject(map);
				request.setAttribute("json", json);
				List countList = statisticService.getRecentYearsCount(option);
				JSONArray jsonList = new JSONArray(countList);
				request.setAttribute("jsonList", jsonList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//传递用户权限等级
		request.setAttribute("grade", grade);
		
			//判断是院管理员还是校管理员
			if(!collegevalue.equals("null") && option.equals("Project")) {
				//项目经费
				//院管理员
				try {
					//获取各专业经费的结果集
					rs = statisticService.getSdeptProjectMoney(collegevalue);
					//获取到专业名以及对应的经费存入map中
					map = util.countMoneyRsToMap(rs);      
					JSONObject json = new JSONObject(map);
					request.setAttribute("Pmoney", json);
				} catch (SQLException e) {
					e.printStackTrace();
				}					
			}else {
				//项目经费
				//校管理员
				try {
					rs = statisticService.getProjectMoney();
					map = util.moneyRsToMap(rs);					
					JSONObject json = new JSONObject(map);					
					request.setAttribute("Pmoney", json);
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			}
			
	
		//分发到不同页面
		if (option != null) {
			if (option.equals("Project")) {
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
