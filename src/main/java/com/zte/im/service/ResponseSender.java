package com.zte.im.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseSender {

	private static final Logger logger = LoggerFactory
			.getLogger(ResponseSender.class);

	private static final String default_charset = "GBK";

	private static final String default_ContentType = "text/plain;charset=";

	public static void sendResponse(HttpServletResponse response, byte[] res,
			String charset, String contentType) {
		ServletOutputStream o = null;
		if (charset != null && !"".equalsIgnoreCase(charset)) {
		} else {
			charset = default_charset;
		}
		if (contentType != null && !"".equalsIgnoreCase(contentType)) {
		} else {
			contentType = default_ContentType;
		}
		response.setCharacterEncoding(charset);
		response.setContentType(contentType + charset);
		response.setHeader("Connection", "keep-alive");
		// logger.info("response :" + new String(res));
		try {
			o = response.getOutputStream();
			o.write(res);
		} catch (UnsupportedEncodingException e) {
			logger.error("response err.", e);
		} catch (IOException e) {
			logger.error("response err.", e);
		} finally {
			try {
				if (o != null) {
					o.flush();
					o.close();
				}
			} catch (IOException e) {
				logger.error("response err...response:[" + res + "]", e);
			}
		}
	}
}
