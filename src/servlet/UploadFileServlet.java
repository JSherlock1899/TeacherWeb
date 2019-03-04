package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;

import dao.ITeacherDao;
import dao.impl.TeacherDaoImpl;
import service.HonorService;
import service.PaperService;
import service.PatentService;
import service.ProjectService;

/**
 * 上传文件
 */
@WebServlet("/servlet/UploadFileServlet")
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//tally用来判断用户是从哪个接口进入的
		String tally = request.getParameter("tally");
		// count用来分发到不同项目对应的servlet
		String count = request.getParameter("count");
		//获取对应的项目名称
		String Proname = request.getParameter("Proname");
		//获取对应项目所对应的主键
		String majorKey = request.getParameter("majorKey");
		String option = request.getParameter("option");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置上传文件时 用到的临时文件的大小DiskFileItemFactory
		factory.setSizeThreshold(10240);// 设置临时的缓冲文件大小为10
		// 控制上传单个文件的大小 20000KB ServletFileUpload
		upload.setSizeMax(20480000);// 字节B

		// 通过parseRequest解析form中的所有请求字段，并保存到 items集合中
		// 文件就保存在了items中
		PrintWriter out = response.getWriter();
		List<FileItem> items;
		try {
			items = upload.parseRequest(request);
			// 遍历items中的数据
			Iterator<FileItem> iter = items.iterator();
			//存放文件的主目录
			String path = "D:\\uploadFile"; 
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (!item.isFormField()) {
					String fileName = item.getName();
					//获取文件后缀名
					String ext = fileName.substring(fileName.indexOf(".") + 1);{
						//教师上传文件至对应的项目文件夹
						ITeacherDao teacherDao = new TeacherDaoImpl();
						String Tsn = (String) request.getSession().getAttribute("Tsn");
						String Tname = teacherDao.getTname(Tsn);
						String teacherCollege = teacherDao.getTeacherCollege(Tsn);
						path = path + "\\" + teacherCollege;
						File fileupload = new File(path);
						if (!fileupload.exists()) {
							fileupload.mkdir();				
						}
						path = path + "\\" + Tname;	
						
						fileupload = new File(path);
						if(!fileupload.exists()) {
							fileupload.mkdir();
						}
						path = path + "\\" + Proname;	
						fileupload = new File(path);
						if(!fileupload.exists()) {
							fileupload.mkdir();
						}
						File file = new File(path, fileName);
						item.write(file);// 上传
						
						//保存附件路径
						if(option!=null) {
							if(option.equals("project")) {
								ProjectService service = new ProjectService();
							}else if(option.equals("paper")) {
								PaperService service = new PaperService();
							}else if(option.equals("honor")) {
								HonorService service = new HonorService();
							}else if(option.equals("patent")) {
								PatentService service = new PatentService();
								service.saveFilePath(path, majorKey);
							}
						}
						
					}
					
					
					//返回给fileinput的json数据
					 response.setContentType("application/json;charset=utf-8");//指定返回的格式为JSON格式
					 response.setCharacterEncoding("UTF-8");//setContentType与setCharacterEncoding的顺序不能调换，否则还是无法解决中文乱码的问题
					 String StringjsonStr ="{\"id\":\"123\"}";
					 out =response.getWriter() ;
					 out.write(StringjsonStr);
					 out.close();

					//导入excel的接口
					if (tally != null && tally.equals("1")) {
						// 判断是不是excel文件如果是则导入数据
						if (ext.equals("xls") || ext.equals("xlsx")) {
							// realPath为excel所处的目录，fileNam	e为excel文件名
							// fileName为未转码的文件名，在当前servlet输出为乱码而fileName是已经转码了的文件名，而在将其转发到另一个servlet中又一次转码，反而导致了文件名的乱码
							String url = "ReadExcelServlet?fileName=" + fileName + "&realPath=" + path + "&count=" + count;
							response.sendRedirect(url);
						}else {
							out.print("<script>alert('请导入excel文件！')</script>");
							return;
						}
					}
					
					return;
				}
			}

		} catch (FileUploadBase.SizeLimitExceededException e) {// SizeLimitExceededException是FileUploadException的一个子类
			out.print("<script>alert('上传文件大小超过限制！最大20000KB！')</script>");
		} catch (FileUploadException e) {
			e.printStackTrace();

		} catch (Exception e) {// 解析请求
			e.printStackTrace();
		}

	}

}
