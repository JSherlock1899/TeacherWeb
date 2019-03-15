package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Poi.CreateTemplate;
import util.CommonUtil;

/**
 * Servlet implementation class ImportExcel
 */
@WebServlet("/servlet/DownloadTemplate")
public class DownloadTemplate extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//根据不同的count值来分发到不同的操作
		String count = request.getParameter("count");
		CommonUtil commondao = new CommonUtil();
		System.out.println(count);
		CreateTemplate template = new CreateTemplate();
		if(count.equals("4")) {							//分别读取对应的xml文件
			template.createExcel(response,"patent");
		}else if(count.equals("3")) {
			template.createExcel(response,"honor");
		}
		else if(count.equals("2")) {
			template.createExcel(response,"paper");
		}
		else if(count.equals("1")) {
			template.createExcel(response,"project");
		}
		else if(count.equals("5")) {
			template.createExcel(response,"teacher");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
