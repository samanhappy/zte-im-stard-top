package com.zte.util.system;

import javax.servlet.http.HttpSession;

/**
 * 用户登录票据
 * 
 * @author jichen
 * 
 */
public class Ticket {

	private static ThreadLocal<Ticket> ticketLocal = new ThreadLocal<Ticket>();

	private HttpSession session;

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * 获取当前线程的Ticket对象
	 * 
	 * @return 登录信息对象
	 */
	public static Ticket getTicket() {
		return ticketLocal.get();
	}

	/**
	 * 初始化
	 * 
	 * @param session
	 *            session
	 */
	public static void setTicket(HttpSession session) {
		Ticket ticket = new Ticket();
		ticket.setSession(session);
		ticketLocal.set(ticket);
	}

	/**
	 * 登录用户账号
	 * 
	 * @return 用户账号
	 */
	public String getUserId() {
		return (String) session.getAttribute("userid");
	}

	/**
	 * 是否已登录
	 * 
	 * @return true / false
	 */
	public boolean isLogin() {
		return getUserId() != null;
	}
}
