package com.zte.im.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/zteim", asyncSupported = false, loadOnStartup = 1)
public class BaseInterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		req.setCharacterEncoding("gbk");
		String resultJson = null;
		BaseInteExecute base = null;
		String method = req.getParameter("method");
		logger.info("request method:" + method);
		if (method != null && !"".equalsIgnoreCase(method)) {
			String med = Start.executes.get(method);
			if (med != null && !"".equalsIgnoreCase(med)) {
				try {
					base = (BaseInteExecute) Class.forName(med).newInstance();
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (ClassNotFoundException e) {
				}
			}
			// base = (BaseInteExecute)Start.getFactory().getBean(method);
			if (base != null) {
				Context utilBean = new Context();
				utilBean.setRequest(req);
				utilBean.setMethod(method);
				logger.info("request json:" + utilBean.getJsonRequest());
				resultJson = base.doProcess(utilBean);
				logger.info("result json:" + resultJson);
			} else {
				resultJson = BaseInteExecute.getErrorRes("server medthod err.");
			}
		} else {
			resultJson = BaseInteExecute.getErrorRes("method is null");
		}
		if (resultJson == null || "".equalsIgnoreCase(resultJson)) {
			resultJson = BaseInteExecute.getErrorRes("server medthod err.");
		}
		ResponseSender.sendResponse(response, resultJson.getBytes("GBK"), null,
				null);
	}

	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		doGet(arg0, arg1);
	}

}
