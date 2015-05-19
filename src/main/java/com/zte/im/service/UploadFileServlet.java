package com.zte.im.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.zte.im.bean.ImFile;
import com.zte.im.bean.ResponseObject;
import com.zte.im.protocol.Res;
import com.zte.im.util.Constant;
import com.zte.im.util.FileSaveHandler;

@WebServlet(name = "UploadFile", urlPatterns = "/uploadFile", asyncSupported = false)
@WebInitParam(name = "default_charset", value = "utf-8")
public class UploadFileServlet extends HttpServlet {
	
	private static Logger logger = LoggerFactory.getLogger(UploadFileServlet.class);
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 接收到客户端的数据之后，先把原图保存（使用线程池异步处理，保存好了以后向hashmap中存入原图的url），再生成一张缩略图，得到缩略图之后，根据id查询内存中的原图的url，生成json返回给客户端
		// 向库中存入文件信息，后期清理接口可以根据库中的创建时间判断文件是否删除,fastdfs有删除方法

		String resultJson;
		List<ImFile> files = new ArrayList<ImFile>();
		String message = "";
		logger.info("===============上传文件===============");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 构造一个文件上传处理对象
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			Iterator<FileItem> items;
			try {
				items = upload.parseRequest(request).iterator();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();
					if (!item.isFormField()) {

						// 上传文件
						String type = null;
						String name = null;
						String fileName = item.getName();
						if (null != fileName && !"".equals(fileName)) {
							if (fileName.indexOf(".") > -1) {
								int spilitIndex = fileName.lastIndexOf(".");
								name = fileName.substring(0, spilitIndex);
								type = fileName.substring(spilitIndex + 1);
							}
							fileName = name.hashCode() + ""
									+ new Date().getTime() + "." + type;
							files.add(new FileSaveHandler().save(item,
									fileName, type));
						} else {
							message = "上传图片为空";
						}
					}
				}

			} catch (Exception e) {
				logger.error("", e);
				message = "图片处理异常";
			}
		} else {
			message = "上传文件为空";
		}

		if (files.size() == 0) {
			Res r = new Res();
			r.setReCode(Constant.ERROR_CODE);
			r.setResMessage(message);
			resultJson = JSON.toJSONString(r);
		} else {
			ResponseObject responseObject = new ResponseObject();
			responseObject.setItem(files);
			resultJson = JSON.toJSONStringWithDateFormat(responseObject,
					"yyyy-MM-dd HH:mm:ss");
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
