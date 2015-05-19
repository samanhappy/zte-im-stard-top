package com.zte.im.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

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
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zte.im.bean.ImImage;
import com.zte.im.bean.ResponseObject;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.protocol.Res;
import com.zte.im.util.Constant;
import com.zte.im.util.FastDSFUtil;
import com.zte.im.util.PathUtil;
import com.zte.im.util.SystemConfig;

@WebServlet(name = "UploadHeadImgServlet", urlPatterns = "/uploadHeadImg", asyncSupported = false)
@WebInitParam(name = "default_charset", value = "utf-8")
public class UploadHeadImgServlet extends HttpServlet {
	
	private static Logger logger = LoggerFactory.getLogger(UploadHeadImgServlet.class);
	
	private static final long serialVersionUID = 1L;
	MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
	private static final String _KEY = "JiangJun";
	FastDSFUtil fastDSFUtil = new FastDSFUtil();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 头像上传
		 */
		String resultJson;
		ImImage imImage = null;
		long uid = 0;
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
							/**
							 * 1.先保存原图到本地； 2.压缩原图到本地； 3.原图和缩略图存储到fastdfs
							 * 4.删除本地原图和缩略图
							 */
							String source_path = PathUtil.getPath(0) + fileName;
							logger.info(source_path);
							File file = new File(source_path);
							item.write(file);

							if (type != null) {
								String source_dsf_path = fastDSFUtil.saveFile(
										_KEY, new FileInputStream(new File(
												source_path)), type);
								if (source_dsf_path != null) {
									imImage = new ImImage();
									imImage.setImgUrl(source_dsf_path);
									imImage.setHost(SystemConfig.fdfs_http_host);
								}
								logger.info(source_dsf_path);
								removeFile(source_path);
							}
						} else {
							message = "上传图片为空";
						}
					}
					if (item.getFieldName().equalsIgnoreCase("userId")) {
						String userId = item.getString("utf-8");
						uid = Long.parseLong(userId);
					}
				}

			} catch (Exception e) {
				logger.error("", e);
				message = "图片处理异常";
			}
		} else {
			message = "上传文件为空";
		}

		if (imImage == null) {
			Res r = new Res();
			r.setReCode(Constant.ERROR_CODE);
			r.setResMessage(message);
			resultJson = JSON.toJSONString(r);
		} else {
			if (uid != 0 && imImage.getImgUrl() != null
					&& !"".equals(imImage.getImgUrl())) {
				long time = new Date().getTime();
				updateIcon4UserDate(uid, imImage.getImgUrl(), time);
				updateIcon4GroupDate(uid, imImage.getImgUrl(), time);
				updateIcon4ComPosit(uid, imImage.getImgUrl(), time);
			}
			ResponseObject responseObject = new ResponseObject();
			responseObject.setObj(imImage);
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

	private void updateIcon4UserDate(long uid, String icon_path, long time) {
		logger.info("" + time);
		BasicDBObject query = new BasicDBObject();
		query.put("uid", uid);
		BasicDBObject param = new BasicDBObject();
		param.put("$set", new BasicDBObject("hp", icon_path));
		mongoDBSupport.updateCollection("user_data", query, param);

		DBObject object = mongoDBSupport.queryOneByParam("user_data", query,
				null);
		if (object != null) {
			BasicDBList list = (BasicDBList) object.get("groupids");
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					BasicDBObject param2 = new BasicDBObject();
					param2.put("$set", new BasicDBObject("groupids." + i
							+ ".ver", time));
					mongoDBSupport.updateCollection("user_data", query, param2);
				}
			}
		}
	}

	private void updateIcon4GroupDate(long uid, String icon_path, long time) {
		BasicDBObject query = new BasicDBObject();
		query.put("userlist.uid", uid);
		BasicDBObject param = new BasicDBObject();
		param.put("$set", new BasicDBObject("ver", new Date().getTime())
				.append("userlist.$.hp", icon_path));
		mongoDBSupport.updateCollection("group_data", query, param);
	}

	private void updateIcon4ComPosit(long uid, String icon_path, long time) {
		BasicDBObject query = new BasicDBObject();
		query.put("users.uid", uid);
		BasicDBObject param = new BasicDBObject();
		param.put("$set", new BasicDBObject("users.$.hp", icon_path)); // 修改指定内嵌文档的数据
		mongoDBSupport.updateCollection("com_posit", query, param);
	}

	private void removeFile(String path) {
		File file = new File(path);
		file.delete();
	}
}
