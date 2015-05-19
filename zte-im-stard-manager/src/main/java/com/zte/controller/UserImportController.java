package com.zte.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.bean.Composition;
import com.zte.im.bean.Firm;
import com.zte.im.bean.User;
import com.zte.im.bean.Users;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.mybatis.bean.UcGroupExample;
import com.zte.im.mybatis.bean.UcGroupMember;
import com.zte.im.mybatis.bean.UcGroupMemberExample;
import com.zte.im.mybatis.bean.UcMember;
import com.zte.im.mybatis.bean.UcMemberExample;
import com.zte.im.mybatis.bean.my.UcMemberMy;
import com.zte.im.mybatis.mapper.UcGroupMapper;
import com.zte.im.mybatis.mapper.UcGroupMemberMapper;
import com.zte.im.mybatis.mapper.UcMemberMapper;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.ICompositionService;
import com.zte.im.service.IFirmService;
import com.zte.im.service.impl.CompositionServiceImpl;
import com.zte.im.service.impl.FirmServiceImpl;
import com.zte.im.service.impl.VersionServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.EncryptionDecryption;
import com.zte.im.util.FastDSFUtil;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.IDSequence;
import com.zte.im.util.MyJSON;
import com.zte.im.util.SystemConfig;
import com.zte.im.util.UUIDGenerator;
import com.zte.service.IGroupMemberService;
import com.zte.service.IGroupService;
import com.zte.util.system.PinYinUtil;

@Controller
public class UserImportController extends ResBase {

	private static String FILE_BASE_PATH = System.getProperty("java.io.tmpdir");

	private static Pattern mobilePattern = Pattern.compile("^(\\+)?[0-9]*$");

	private static Pattern emailPattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

	@Autowired
	private IGroupMemberService gmService;

	@Autowired
	IGroupService groupService;

	@Autowired
	private UcMemberMapper memberMapper;

	@Autowired
	private UcGroupMapper groupMapper;

	@Autowired
	private UcGroupMemberMapper groupMemberMapper;

	private static Logger logger = LoggerFactory.getLogger(UserImportController.class);

	@RequestMapping(value = "importUser", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String importUser(@RequestParam("fileUrl") String fileUrl, HttpServletRequest request) {
		if (fileUrl == null) {
			return getErrorRes("fileUrl can not be null");
		}

		String error = importUserData(fileUrl, (String) request.getSession().getAttribute(Constant.TENANT_ID));
		if (error != null) {
			return getErrorRes(error);
		}

		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

	@RequestMapping(value = "exportUser")
	@ResponseBody
	public String exportUser(@RequestParam("groupId") String groupId, @RequestParam("uids") String uids,
			HttpServletRequest request) {

		String tenantId = (String) request.getSession().getAttribute(Constant.TENANT_ID);

		String fileUrl = exportUserData(tenantId, groupId, uids);
		if (fileUrl == null) {
			return getErrorRes("导出用户出错");
		}

		ResponseMain main = new ResponseMain();
		main.setFileUrl(fileUrl);
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

	public String exportUserData(String tenantId, String groupId, String uids) {
		// 检查企业ID
		IFirmService firmService = FirmServiceImpl.getInstance();
		Firm firm = firmService.getFirm(tenantId);
		if (firm == null || firm.getGid() == null) {
			return null;
		}

		Workbook wb;
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File(this.getClass().getResource("/").getPath()
					+ "user template.xlsx")));
			Sheet sheet = wb.getSheet("Sheet1");

			int startRow = 1;// 从第二行开始写入数据

			List<User> users = null;
			BasicDBObject param = new BasicDBObject();// 条件
			param.put("gid", firm.getGid());
			String fileName = firm.getName() + "_";
			if (uids != null && !uids.equals("")) {
				param.put("id", new BasicDBObject("$in", Arrays.asList(uids.split(","))));
				fileName = "template_";
			} else if (groupId != null) {

				List<Long> cidList = new ArrayList<Long>();
				UcGroup group = groupMapper.selectByPrimaryKey(groupId);
				if (group != null && group.getFullId() != null) {
					fileName = group.getName() + "_";
					UcGroupExample example = new UcGroupExample();
					example.createCriteria().andFullIdLike(group.getFullId() + "%");
					List<UcGroup> groupList = groupMapper.selectByExample(example);
					if (groupList != null && groupList.size() > 0) {
						for (UcGroup ug : groupList) {
							ICompositionService comService = CompositionServiceImpl.getInstance();
							Composition com = comService.getComBySqlId(ug.getId());
							if (com != null) {
								cidList.add(com.getCid());
							}
						}

					}
				}

				if (cidList.size() > 0) {
					param.put("cid", new BasicDBObject("$in", cidList));
				}
			}
			logger.info(param.toString());
			DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(Constant.USER_DATA, param, null);
			if (cur != null && cur.size() > 0) {
				Type type = new TypeToken<List<User>>() {
				}.getType();
				users = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
			}

			if (users != null) {
				for (User u : users) {
					int startCell = 0; // 从第一个单元格开始写入
					Row row = sheet.createRow((short) startRow++);
					row.createCell(startCell++).setCellValue(u.getName());
					row.createCell(startCell++).setCellValue(u.getJid());
					row.createCell(startCell++).setCellValue(u.getCname());
					row.createCell(startCell++).setCellValue(u.getEmail());
					row.createCell(startCell++).setCellValue(u.getMob());
					row.createCell(startCell++).setCellValue(u.getPost());
					row.createCell(startCell++).setCellValue(u.getTelephone());
					row.createCell(startCell++).setCellValue(u.getFax());
					row.createCell(startCell++).setCellValue(u.getRemark());
					row.createCell(startCell++).setCellValue(u.getAutograph());
					row.createCell(startCell++).setCellValue(u.getExtNumber());
					row.createCell(startCell++).setCellValue(u.getViopId());
					row.createCell(startCell++).setCellValue(u.getViopPwd());
					row.createCell(startCell++).setCellValue(u.getViopSid());
					row.createCell(startCell++).setCellValue(u.getViopSidPwd());
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

	public String importUserData(String fileUrl, String tenantId) {

		Set<String> errors = new LinkedHashSet<String>();

		// 检查企业ID
		IFirmService firmService = FirmServiceImpl.getInstance();
		Firm firm = firmService.getFirm(tenantId);
		if (firm == null || firm.getGid() == null) {
			errors.add("企业ID不正确");
		}

		try {
			String[][] result;
			InputStream in = new URL(fileUrl).openStream();
			result = getData(in, 1);
			int rowLength = result.length;
			List<User> mongoUserList = new ArrayList<User>();
			List<UcMemberMy> postgreUserList = new ArrayList<UcMemberMy>();
			ICompositionService comService = CompositionServiceImpl.getInstance();
			Set<String> jidSet = new HashSet<String>();
			for (int i = 0; i < rowLength; i++) {
				User u = new User();
				u.setId(UUIDGenerator.randomUUID());
				try {
					EncryptionDecryption enc = new EncryptionDecryption();
					String pwd = enc.encrypt(Constant.DEFAULT_PWD);
					u.setPwd(pwd);
				} catch (Exception e) {
					logger.error("", e);
				}

				int j = 0;
				u.setName(j < result[i].length ? result[i][j++].trim() : "");
				u.setJid(j < result[i].length ? result[i][j++].trim() : "");
				u.setCname(j < result[i].length ? result[i][j++].trim() : "");
				u.setEmail(j < result[i].length ? result[i][j++].trim() : "");
				u.setMob(j < result[i].length ? result[i][j++].trim() : "");
				u.setPost(j < result[i].length ? result[i][j++].trim() : "");
				u.setTelephone(j < result[i].length ? result[i][j++].trim() : "");
				u.setFax(j < result[i].length ? result[i][j++].trim() : "");
				u.setRemark(j < result[i].length ? result[i][j++].trim() : "");
				u.setAutograph(j < result[i].length ? result[i][j++].trim() : "");
				u.setExtNumber(j < result[i].length ? result[i][j++].trim() : "");
				u.setViopId(j < result[i].length ? result[i][j++].trim() : "");
				u.setViopPwd(j < result[i].length ? result[i][j++].trim() : "");
				u.setViopSid(j < result[i].length ? result[i][j++].trim() : "");
				u.setViopSidPwd(j < result[i].length ? result[i][j++].trim() : "");
				u.setGid(firm.getGid());

				
				// 检查姓名
				if (StringUtils.isEmpty(u.getName())) {
					errors.add("第" + (i + 1) + "行的姓名为空");
				} 
				
				// 检查工号
				if (StringUtils.isEmpty(u.getJid())) {
					errors.add("第" + (i + 1) + "行的工号为空");
				} else if (jidSet.contains(u.getJid())) {
					errors.add("第" + (i + 1) + "行的工号" + u.getJid() + "重复");
				} else {
					jidSet.add(u.getJid());
				}
				
				// 检查手机号
				if (StringUtils.isEmpty(u.getMob())) {
					errors.add("第" + (i + 1) + "行的手机为空");
				} else if (!mobilePattern.matcher(u.getMob()).matches()) {
					errors.add("第" + (i + 1) + "行的手机" + u.getMob() + "格式不正确");
				}

				// 检查邮箱
				if (StringUtils.isEmpty(u.getEmail())) {
					errors.add("第" + (i + 1) + "行的邮箱为空");
				} else if (!emailPattern.matcher(u.getEmail()).matches()) {
					errors.add("第" + (i + 1) + "行的邮箱" + u.getEmail() + "格式不正确");
				}

				if (firm != null && firm.getGid() != null) {
					// 统一使用自动生成的UID
					u.setUid(IDSequence.getGroupId());
				}

				UcMemberMy ucMember = new UcMemberMy();
				
				// 检查部门
				if (StringUtils.isEmpty(u.getCname())) {
					errors.add("第" + (i + 1) + "行的部门为空");
				} else {
					/* 自动生成部门 */
					List<UcGroup> deptList = gmService.getGroupByName(tenantId, u.getCname(), Constant.GROUP_DEPT);
					if (deptList == null || deptList.size() == 0) {
						UcGroup deptGroup = new UcGroup();
						deptGroup.setId(UUIDGenerator.randomUUID());
						deptGroup.setName(u.getCname());
						deptGroup.setPid(tenantId);
						deptGroup.setTenantId(tenantId);
						deptGroup.setSequ(1000L); // 默认靠后排序
						deptGroup.setTypeId(Constant.GROUP_DEPT);
						deptGroup.setCreator("userimport");
						deptGroup.setFullId("." + deptGroup.getId());
						deptGroup.setPinyin(PinYinUtil.getT9PinyinStr(deptGroup.getName()));
						groupService.addGroup(deptGroup);
					}

					// 检查部门名称
					Composition composition = comService.getComposition(firm.getGid(), u.getCname());
					UcGroup dept = gmService.getGroupByTenantId(tenantId, Constant.GROUP_DEPT, u.getCname());
					if (composition == null || composition.getCid() == null) {
						errors.add("第" + (i + 1) + "行的部门" + u.getCname() + "不存在");
					} else if (dept == null) {
						errors.add("第" + (i + 1) + "行的数据库部门" + u.getCname() + "不存在");
					} else {
						u.setCid(composition.getCid());
						// 保存部门ID
						u.setDeptId(dept.getId());
						ucMember.setDeptId(dept.getId());
					}
				}

				// 检查职位
				if (StringUtils.isEmpty(u.getPost())) {
					errors.add("第" + (i + 1) + "行的职位为空");
				} else {
					/* 自动生成职级 */
					List<UcGroup> postList = gmService.getGroupByName(tenantId, u.getPost(), Constant.GROUP_POSITION);
					UcGroup pos = null;
					if (postList == null || postList.size() == 0) {
						pos = new UcGroup();
						pos.setId(UUIDGenerator.randomUUID());
						pos.setName(u.getPost());
						pos.setPid("");
						pos.setTenantId(tenantId);
						pos.setSequ(0L);
						pos.setTypeId(Constant.GROUP_POSITION);
						pos.setCreator("userimport");
						pos.setPinyin(PinYinUtil.getT9PinyinStr(u.getPost()));
						groupMapper.insertSelective(pos);
					} else {
						pos = postList.get(0);
					}
					// 保存职位数据
					ucMember.setRoleId(pos.getId());
				}

				postgreUserList.add(ucMember);
				mongoUserList.add(u);
			}

			if (errors.size() > 0) {
				StringBuffer sb = new StringBuffer();
				Iterator<String> it = errors.iterator();
				while (it.hasNext()) {
					sb.append(it.next());
					if (it.hasNext()) {
						sb.append("<br/>");
					}
				}

				return sb.toString();
			}

			// 保存用户数据
			for (int i = 0; i < mongoUserList.size(); i++) {
				User user = mongoUserList.get(i);
				UcMemberMy memberMy = postgreUserList.get(i);

				/*
				 * 保存到PostgreSQL数据库
				 */
				// 根据工号判断用户是否存在
				boolean memberExists = false;
				UcMember member = new UcMember();
				UcMemberExample ue = new UcMemberExample();
				ue.createCriteria().andUidEqualTo(user.getJid());
				List<UcMember> memberList = memberMapper.selectByExample(ue);
				if (memberList != null && memberList.size() > 0) {
					member = memberList.get(0);
					user.setId(member.getId());
					memberExists = true;
				}

				getUcMemberFromUser(member, user, tenantId);

				UcGroupMember deptGroupMember = new UcGroupMember();
				deptGroupMember.setId(UUIDGenerator.randomUUID());
				deptGroupMember.setTenantId(tenantId);
				deptGroupMember.setMemberId(member.getId());
				deptGroupMember.setGroupId(memberMy.getDeptId());

				UcGroupMember posGroupMember = new UcGroupMember();
				posGroupMember.setId(UUIDGenerator.randomUUID());
				posGroupMember.setTenantId(tenantId);
				posGroupMember.setMemberId(member.getId());
				posGroupMember.setGroupId(memberMy.getRoleId());

				if (memberExists) {
					if (memberMapper.updateByPrimaryKey(member) == 1) {
						logger.info("save member success for:" + member.getCn());
					} else {
						return "更新用户数据失败";
					}

					// 删除已经存在的用户关系
					UcGroupMemberExample deleteDGMExample = new UcGroupMemberExample();
					deleteDGMExample.createCriteria().andTenantIdEqualTo(tenantId).andMemberIdEqualTo(member.getId());
					if (groupMemberMapper.deleteByExample(deleteDGMExample) > 0) {
						logger.info("remove old member relation success for:" + member.getCn());
					} else {
						return "更新用户关系数据失败";
					}
				} else {
					if (memberMapper.insert(member) == 1) {
						logger.info("save member success for:" + member.getCn());
					} else {
						return "保存用户数据失败";
					}
				}

				if (groupMemberMapper.insert(deptGroupMember) == 1) {
					logger.info("save dept group member success for:" + member.getCn());
				} else {
					return "保存用户部门关系失败";
				}
				if (groupMemberMapper.insert(posGroupMember) == 1) {
					logger.info("save pos group member success for:" + member.getCn());
				} else {
					return "保护用户职位失败";
				}

				DBObject userDBObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(user));
				logger.info(userDBObject.toString());

				// 根据工号判断用户是否存在
				BasicDBObject jidQuery = new BasicDBObject("jid", user.getJid());
				if (MongoDBSupport.getInstance().getCount(Constant.USER_DATA, jidQuery) > 0) {
					// 更新
					MongoDBSupport.getInstance().findAndModifyCollection(Constant.USER_DATA, jidQuery, userDBObject);

					// 删除已经存在的用户
					BasicDBObject query = new BasicDBObject();
					query.put("gid", user.getGid());
					// 因为可能存在所属部门变更，所以此处不根据cid查询
					// query.put("cid", user.getCid());

					BasicDBObject param = new BasicDBObject();
					Users users = new Users();
					User u = new User();
					u.setJid(user.getJid());
					users.setUsers(u);
					DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(users));
					param.put("$pull", dbObject); // 从指定文档删除一个内嵌文档
					logger.info(query.toString());
					logger.info(param.toString());
					MongoDBSupport.getInstance().updateCollection(Constant.COM_POSIT, query, param);

				} else {
					MongoDBSupport.getInstance().save(userDBObject, Constant.USER_DATA);
				}

				BasicDBObject query = new BasicDBObject();
				query.put("gid", user.getGid());
				query.put("cid", user.getCid());
				BasicDBObject param = new BasicDBObject();
				Users users = new Users();
				users.setUsers(user);
				DBObject usersDBObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(users));
				param.put("$push", usersDBObject); // 为指定文档添加一个内嵌文档
				MongoDBSupport.getInstance().updateCollection(Constant.COM_POSIT, query, param);

				// 更新用户信息列表版本
				VersionServiceImpl.getInstance().incUserListVer();
			}

		} catch (Exception e) {
			logger.error("", e);
			return e.getMessage();
		}
		return null;
	}

	private void getUcMemberFromUser(UcMember member, User user, String tenantId) {
		if (member.getId() == null) {
			member.setId(user.getId());
		}
		member.setBirthday(null);
		member.setCn(user.getName());
		member.setPinyin(PinYinUtil.getPinyinStr(user.getName())); // 处理汉字拼音
		member.setT9Pinyin(PinYinUtil.getT9PinyinStr(user.getName()));
		member.setCreateTime(new Date());
		member.setCreator("");// 创建者
		member.setEditable("true");
		member.setDeletable("true");
		member.setMail(user.getEmail());
		member.setMobile(user.getMob());
		member.setNickname(user.getNickname());

		// 保存到postgres中不加密
		try {
			member.setPassword(new EncryptionDecryption().decrypt(user.getPwd()));
		} catch (Exception e) {
			logger.error("", e);
		}
		member.setPhoto(null);
		member.setPhotoSign(user.getAutograph());
		member.setPostaladdress(null);
		member.setQq(null);
		member.setSequ(0L);
		member.setSex(user.getSex());
		member.setShortcode(null);
		member.setState("true");
		member.setTenantId(tenantId);
		member.setUid(user.getJid());
		member.setUsable("true");
		member.setUserType("user");
		member.setWwwhomepage(null);
		member.setHometelephone(user.getTelephone());
		member.setFax(user.getFax());
		member.setInfo(user.getRemark());
	}

	public String[][] getData(InputStream file, int ignoreRows) throws FileNotFoundException, IOException {

		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(in);
		XSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet st = wb.getSheetAt(sheetIndex);
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				XSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y" : "N");
							break;
						default:
							value = "";
						}
					}

					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}

	public String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

}
