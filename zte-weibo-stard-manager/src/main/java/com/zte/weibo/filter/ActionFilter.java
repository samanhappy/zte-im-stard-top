package com.zte.weibo.filter;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.zte.weibo.protocol.ResBase;

/**
 * 登录验证过滤器
 * 
 * @author jichen
 * 
 */
public class ActionFilter implements Filter {

	/**
	 * 默认构造器
	 */
	public ActionFilter() {
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
		
		// 排除登录请求、登出请求、获取企业元素
		if (StringUtils.equals(urlString, SystemPagePath.LOGINDO)
				|| StringUtils.equals(urlString, SystemPagePath.LOGOUTDO)
				|| StringUtils
						.equals(urlString, SystemPagePath.CustomizationDO)) {
			chain.doFilter(request, response);
			return;
		}
		Ticket.setTicket(req.getSession());
		// 如果没登录，则跳回登陆页
		if (!Ticket.getTicket().isLogin()) {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(ResBase.getErrorRes("User Not Login", "2"));
			out.flush();
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
