package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Poi.ReadExcel;
import service.ExcelService;

@WebServlet("/servlet/ReadExcelServlet")
public class ReadExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				// 根据不同的count值来分发到不同的操作
				String count = request.getParameter("count");
				// 获取要读取的excel路径
				ExcelService excelService = new ExcelService();
				String realPath = request.getParameter("realPath");		//excel所处的目录
				String fileName = request.getParameter("fileName");
				String path = realPath + "\\" + fileName;			
				List CellValues = null;
				try {
					//获取excel文件各单元格的值
					CellValues = ReadExcel.getValue(path);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				PrintWriter out = response.getWriter();// 通过servlet的doget方法获取response对象，通过getWriter方法获取PrintWriter对象
				out.flush();// 清空缓存
				if(count.equals("1")) {
					int result = excelService.insertProjectValues(CellValues);
					if (result == 0) {
						out.print("<script>alert('数据导入成功！')</script>");
					} else {
						out.print("<script>alert('第" + result + "条数据导入出错！')</script>");
					}
				}else if(count.equals("2")) {
					int result = excelService.insertPaperValues(CellValues);
					if (result == 0) {
						out.print("<script>alert('数据导入成功！')</script>");
					} else {
						out.print("<script>alert('第" + result + "条数据导入出错！')</script>");
					}
				}else if(count.equals("3")) {
					int result = excelService.insertHonorValues(CellValues);
					if (result == 0) {
						out.print("<script>alert('数据导入成功！')</script>");
					} else {
						out.print("<script>alert('第" + result + "条数据导入出错！')</script>");
					}
				}else if(count.equals("4")) {
					int result = excelService.insertPatentValues(CellValues);
					if (result == 0) {
						out.print("<script>alert('数据导入成功！')</script>");
					} else {
						out.print("<script>alert('第" + result + "条数据导入出错！')</script>");
					}
				}else if(count.equals("5")) {
					int result = excelService.insertTeacherValues(CellValues);
					if (result == 0) {
						out.print("<script>alert('数据导入成功！')</script>");
					} else {
						out.print("<script>alert('第" + result + "条数据导入出错！')</script>");
					}
				}else {
					out.print("<script>alert('系统错误！')</script>");
				}
				out.close();
	}

}
