package util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetEncodingFilter implements Filter{

	private static String encoding; // 定义变量接收初始化的值
	 
	// 初始化
	public void init(FilterConfig config) throws ServletException {
		// 接收web.xml配置文件中的初始参数
		encoding = config.getInitParameter("CharsetEncoding");		
	}
 
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 设置字符编码链锁
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		//解决中文乱码
		response.setContentType("text/html;charset=" + encoding);
		chain.doFilter(request, response);
	}

}
