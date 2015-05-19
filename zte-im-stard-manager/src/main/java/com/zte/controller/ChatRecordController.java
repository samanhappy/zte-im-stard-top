package com.zte.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.databinder.ChatRecordBinder;
import com.zte.im.bean.ChatRecord;
import com.zte.im.mybatis.bean.page.ChatRecordPageModel;
import com.zte.im.protocol.Page;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.util.Constant;
import com.zte.im.util.FastDSFUtil;
import com.zte.im.util.SystemConfig;
import com.zte.service.IChatRecordService;

@Controller
public class ChatRecordController extends ResBase {

	@Autowired
	IChatRecordService recordService;

	private final static Logger logger = LoggerFactory.getLogger(ChatRecordController.class);

	private static String FILE_BASE_PATH = System.getProperty("java.io.tmpdir");

	@RequestMapping(value = "listChat")
	@ResponseBody
	public String list(ChatRecordBinder binder) {
		ResponseMain main = new ResponseMain();
		ChatRecordPageModel fpm = recordService.list(binder);
		if (fpm != null && fpm.getRecordList() != null) {
			for (ChatRecord obj : fpm.getRecordList()) {
				if (obj.getPayload() != null) {
					try {
						obj.setPayload(Base64.encodeBase64String(obj.getPayload().getBytes("utf-8")));
					} catch (UnsupportedEncodingException e) {
						logger.error("", e);
					}
				}
				if (obj.getContent() != null) {
					try {
						obj.setContent(Base64.encodeBase64String(obj.getContent().getBytes("utf-8")));
					} catch (UnsupportedEncodingException e) {
						logger.error("", e);
					}
				}
			}
		}
		main.setItem(fpm.getRecordList());
		Page page = new Page();
		page.setCurrentPage(fpm.getCurrentPage());
		page.setPages(fpm.getTotalPage());
		page.setPernum(fpm.getPageSize());
		page.setStart(fpm.getFirst());
		page.setEnd(fpm.getLast());
		page.setTotal(fpm.getTotalRecord());
		main.setPage(page);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "exportChat")
	@ResponseBody
	public String exportChat(@RequestParam("ids") String ids, HttpServletRequest request) {

		String tenantId = (String) request.getSession().getAttribute(Constant.TENANT_ID);

		String fileUrl = exportChatData(tenantId, ids);
		if (fileUrl == null) {
			return getErrorRes("导出聊天记录出错");
		}

		ResponseMain main = new ResponseMain();
		main.setFileUrl(fileUrl);
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}


	private String exportChatData(String tenantId, String ids) {

		Workbook wb;
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File(this.getClass().getResource("/").getPath()
					+ "chat template.xlsx")));
			Sheet sheet = wb.getSheet("Sheet1");

			int startRow = 1;// 从第二行开始写入数据

			String fileName = "chat_record_";
			List<ChatRecord> recordList = recordService.listByIds(ids);

			if (recordList != null) {
				for (ChatRecord chat : recordList) {
					int startCell = 0; // 从第一个单元格开始写入
					Row row = sheet.createRow((short) startRow++);
					row.createCell(startCell++).setCellValue(chat.getContentType());
					row.createCell(startCell++).setCellValue(chat.getType());
					row.createCell(startCell++).setCellValue(chat.getSender());
					row.createCell(startCell++).setCellValue(chat.getReceiver());
					row.createCell(startCell++).setCellValue(chat.getTime());
					row.createCell(startCell++).setCellValue(chat.getContent());
					row.createCell(startCell++).setCellValue("");
				}
			}

			// 保存到本地
			String localFilePath = FILE_BASE_PATH + System.currentTimeMillis() + ".xlsx";
			FileOutputStream fileOut = new FileOutputStream(new File(localFilePath));
			wb.write(fileOut);
			fileOut.close();

			// 保存到fastdfs
			String httpFilePath = new FastDSFUtil().saveFile("sam", new FileInputStream(localFilePath), "xlsx");
			return SystemConfig.fdfs_http_host + httpFilePath + "?attname=" + URLEncoder.encode(fileName, "utf-8")
					+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xlsx";
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

}
