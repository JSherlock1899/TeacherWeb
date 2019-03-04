package util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *防止从地址栏直接访问
 */
public class urlFilter implements Filter {

  
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
 
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession(true);
 
		String url = request.getRequestURI();//获取地址栏的url
		String uname= (String) session.getAttribute("user");//获取登录时存放的session
		if (uname== null && url.indexOf("/SchoolAdmin") !=-1  && url.indexOf("login.jsp") == -1) {
			String location = "/error.jsp";//定义当访客非法访问不被允许的地址时跳转的界面
			request.getRequestDispatcher(location).forward(request, response);//跳转至指定界面
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
		} else {
			chain.doFilter(request, response);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
