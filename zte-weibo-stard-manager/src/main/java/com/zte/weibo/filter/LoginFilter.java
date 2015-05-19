package com.zte.weibo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.zte.weibo.common.util.system.Ticket;
import com.zte.weibo.pagePath.SystemPagePath;

/**
 * 登录验证过滤器
 * 
 * @author jichen
 * 
 */
public class LoginFilter implements Filter {

	/**
	 * 默认构造器
	 */
	public LoginFilter() {
		super();
	}

	/**
	 * 验证是否登录过
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String urlString = req.getRequestURI();
		String context = req.getContextPath();
		// 获取去除项目名以后的请求url
		if (StringUtils.isNotEmpty(context)) {
			urlString = StringUtils.substring(urlString, context.length());
		}

		// 排除登录页面、忘记密码页面
		if (StringUtils.equals(urlString, SystemPagePath.LOGIN)
				|| StringUtils.equals(urlString, SystemPagePath.FORG_PASS)) {
			chain.doFilter(request, response);
			return;
		}
		Ticket.setTicket(req.getSession());
		// 如果没登录，则跳回登陆页
		if (!Ticket.getTicket().isLogin()) {
			request.getRequestDispatcher(SystemPagePath.LOGIN).forward(request,
					response);
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
