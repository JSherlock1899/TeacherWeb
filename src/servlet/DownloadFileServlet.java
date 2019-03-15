package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.HonorService;
import service.PaperService;
import service.PatentService;
import service.ProjectService;
import util.CommonUtil;

/**
 * 下载对应的附件
 */
@WebServlet("/servlet/DownloadFileServlet")
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//下载文件，需要设置消息头
//		response.addHeader("content-Type", "application/octet-stream");  //MIME类型：二进制文件（任意文件）
//		response.addHeader("content-Disposition","attachement;filename=");
		//获取主键
		String mainKey = request.getParameter("mainKey");
		//获取项目名
		String Proname = request.getParameter("Proname");
		String option = request.getParameter("option");
		String path = null;
		System.out.println("Proname = " + Proname);
		System.out.println("option = " + option);
		System.out.println("mainKey = " + mainKey);
		//根据不同的类别分别获取对应的附件路径
		if(option.equals("project")) {
			ProjectService service = new ProjectService();
			path = service.getAccessory(mainKey);
		}else if(option.equals("paper")) {
			PaperService service = new PaperService();
			path = service.getAccessory(mainKey);
		}else if(option.equals("honor")) {
			HonorService service = new HonorService();
			path = service.getAccessory(mainKey);
		}else if(option.equals("patent")) {
			PatentService service = new PatentService();
			path = service.getAccessory(mainKey);
		}
		
		if(path == null) {
			PrintWriter out = response.getWriter();
			out.flush();
			out.print("<script>alert('附件路径错误！')</script>");
		}
		CommonUtil util = new CommonUtil();
		ArrayList<File> fileList = new ArrayList<>();
        File [] tarFile = util.getFiles(fileList, path); //将多个文件放入File数组

        String zipName;
		try {
			zipName = doZIP("zipname",tarFile);
			//这里，我写了一个将多个文件打包为zip的方法doZip()，doZip()返回生成的zip路径

	        response.setCharacterEncoding("utf-8");//设置编码统一
	        response.setContentType("multipart/form-data");//设置multipart
	        //响应头部
	        response.setHeader("Content-disposition", "attachment;filename=order_" + Proname + ".zip");
	        InputStream inputStreamzip = new FileInputStream(new File(zipName));//通过zip路径实例化inputStream流

	        OutputStream os = response.getOutputStream();
	        byte[] b = new byte[2048];
	        int length;
	        while ((length = inputStreamzip.read(b)) > 0) {
	            os.write(b, 0, length);
	        }
	        os.close();
	        inputStreamzip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	 /*文件打包zip*/
    public String doZIP(String zipname, File [] files) throws Exception{
    //doZIP(命名的打包文件名，传递过来的File数组)
        byte[] buffer = new byte[1024];

        String strZipName = zipname;

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));

        for(int i=0;i<files.length;i++) {

            FileInputStream fis = new FileInputStream(files[i]);

            out.putNextEntry(new ZipEntry(files[i].getName()));

            int len;

            //读入需要下载的文件的内容，打包到zip文件

            while((len = fis.read(buffer))>0) {

                out.write(buffer,0,len);

            }

            out.closeEntry();

            fis.close();

        }

        out.close();

        return strZipName;
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
