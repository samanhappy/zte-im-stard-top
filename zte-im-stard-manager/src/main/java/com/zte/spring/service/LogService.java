package com.zte.spring.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.util.ParameterMap;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zte.databinder.ClientBinder;
import com.zte.databinder.DeptBinder;
import com.zte.databinder.GroupBinder;
import com.zte.databinder.NewsBinder;
import com.zte.databinder.TenantBinder;
import com.zte.databinder.UserBinder;
import com.zte.databinder.UserPermBinder;
import com.zte.im.bean.Customization;
import com.zte.im.bean.Param;
import com.zte.im.bean.Role;
import com.zte.im.bean.SysLog;
import com.zte.im.mybatis.bean.UserPermission;
import com.zte.im.mybatis.bean.my.UcTenantMy;
import com.zte.im.service.ISysLogService;
import com.zte.im.service.impl.SysLogServiceImpl;

@Component
@Aspect
public class LogService {

	private final static Logger logger = LoggerFactory.getLogger(LogService.class);

	public static Map<String, String> logMethodsMap = new HashMap<String, String>();

	public static Map<String, String> paramDescMap = new HashMap<String, String>();

	static {
		/*
		 * 需要记录日志的方法
		 */
		logMethodsMap.put("login", "登录");
		logMethodsMap.put("logout", "登出");
		logMethodsMap.put("editPass", "修改密码");

		logMethodsMap.put("addUser", "增加用户");
		logMethodsMap.put("removeUser", "删除用户");
		logMethodsMap.put("updateUser", "更新用户");
		logMethodsMap.put("importUser", "导入用户");
		logMethodsMap.put("exportUser", "导出用户");
		logMethodsMap.put("addGroup", "增加部门");
		logMethodsMap.put("editGroup", "修改部门");
		logMethodsMap.put("delGroup", "删除部门");
		logMethodsMap.put("addDept", "增加部门");
		logMethodsMap.put("updateDept", "修改部门");
		logMethodsMap.put("delDept", "删除部门");
		logMethodsMap.put("updateUserPerm", "设置用户权限");

		logMethodsMap.put("saveNews", "新建或编辑新闻");
		logMethodsMap.put("delNews", "删除新闻");

		logMethodsMap.put("saveOrUpdateRole", "新增或修改角色");
		logMethodsMap.put("removeRole", "删除角色");
		logMethodsMap.put("addClient", "新增客户端");
		logMethodsMap.put("editClient", "修改客户端");
		logMethodsMap.put("removeClient", "删除客户端");
		logMethodsMap.put("updateSysParam", "修改系统参数");
		logMethodsMap.put("updateContactParam", "修改通讯录参数");
		logMethodsMap.put("updateParam", "修改参数设置");
		logMethodsMap.put("saveOrUpdateParam", "新增或修改自定义参数");
		logMethodsMap.put("removeParam", "删除自定义参数");

		logMethodsMap.put("editTenant", "修改企业信息");
		logMethodsMap.put("updateTenant", "修改企业信息");
		logMethodsMap.put("updateTenantCustomization", "修改企业元素");

		/*
		 * 参数描述
		 */
		paramDescMap.put("name", "用户名");
		paramDescMap.put("password", "密码");
		paramDescMap.put("deptManage", "部门管理");
		paramDescMap.put("userManage", "员工管理");
		paramDescMap.put("userPrivManage", "员工权限设置");
		paramDescMap.put("userAbleManage", "员工启用、停用");
		paramDescMap.put("userImport", "导入");
		paramDescMap.put("userExport", "导出");
		paramDescMap.put("publishNews", "发布新闻");
		paramDescMap.put("editNews", "编辑新闻");
		paramDescMap.put("removeNews", "删除新闻");
		paramDescMap.put("updateCompInfo", "企业信息修改");
		paramDescMap.put("roleManage", "角色管理");
		paramDescMap.put("updateSystemParam", "系统参数修改");
		paramDescMap.put("updateContactParam", "通讯录参数修改");
		paramDescMap.put("defineParam", "通讯录自定义");
		paramDescMap.put("licenseManage", "License管理");
		paramDescMap.put("clientManage", "客户端管理");

		paramDescMap.put("0", "否");
		paramDescMap.put("1", "是");
		paramDescMap.put("LDAP", "LDAP");
		paramDescMap.put("DATABASE", "本地数据库");
		paramDescMap.put("THIRD", "三方数据库");
		paramDescMap.put("number", "数字");
		paramDescMap.put("date", "日期");
		paramDescMap.put("string", "字符串");

	}

	ISysLogService logService = SysLogServiceImpl.getInstance();

	@Pointcut("execution(* com.zte.controller..*.*(..))")
	public void methodCachePointcut() {
	}

	@Around("methodCachePointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {

		Object result = null;
		String methodName = point.getSignature().getName();

		// 登录要先执行
		if (methodName.equals("login")) {
			result = point.proceed();
		}

		if (logMethodsMap.containsKey(methodName)) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String loginName = (String) request.getSession().getAttribute("userid");
			String roleName = (String) request.getSession().getAttribute("uname");
			String clientIP = (String) request.getSession().getAttribute("clientIP");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar ca = Calendar.getInstance();
			String operDate = df.format(ca.getTime());
			StringBuffer operDetail = new StringBuffer();
			operDetail.append(roleName).append(loginName).append("(").append(clientIP).append(")");
			Object[] method_param = point.getArgs(); // 获取方法参数

			// 需要根据参数判断为新增还是更新的操作
			if (!methodName.equals("addUser") && !methodName.equals("saveNews")
					&& !methodName.equals("saveOrUpdateRole") && !methodName.equals("saveOrUpdateParam")
					&& !methodName.equals("updateParam")) {
				operDetail.append(logMethodsMap.get(methodName));
			}
			operDetail.append(getOperContent(method_param, methodName));
			SysLog log = new SysLog();
			log.setType("oper");
			log.setCreatime(operDate);
			log.setUname(loginName);
			log.setUid("");
			log.setOper(logMethodsMap.get(methodName));
			log.setContent(operDetail.toString());
			logger.info(log.getContent());
			logService.saveLog(log);
		}

		if (!methodName.equals("login")) {
			result = point.proceed();
		}

		return result;
	}

	/**
	 * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
	 * 
	 * @param args
	 * @param mName
	 * @return
	 */
	public String getOperContent(Object[] args, String mName) {
		if (args == null || args.length == 0) {
			return null;
		}

		StringBuffer content = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			logger.info(arg.getClass().toString());
			if (arg instanceof String) {
				if (StringUtils.isNotBlank((String) arg)) {
					content.append(",参数").append(i + 1).append(":").append(arg);
				}
			} else if (arg instanceof DeptBinder) {
				return getContentByDeptBinder((DeptBinder) arg);
			} else if (arg instanceof Param) {
				return getContentByParam((Param) arg, mName);
			} else if (arg instanceof ClientBinder) {
				return getContentByClientBinder((ClientBinder) arg);
			} else if (arg instanceof Role) {
				return getContentByRole((Role) arg, mName);
			} else if (arg instanceof NewsBinder) {
				return getContentByNewsBinder((NewsBinder) arg, mName);
			} else if (arg instanceof UserPermBinder) {
				return getContentByUserPermBinder((UserPermBinder) arg);
			} else if (arg instanceof GroupBinder) {
				return getContentByGroupBinder((GroupBinder) arg);
			} else if (arg instanceof TenantBinder) {
				return getContentByTenantBinder((TenantBinder) arg);
			} else if (arg instanceof UcTenantMy) {
				return getContentByUcTenantMy((UcTenantMy) arg);
			} else if (arg instanceof Customization) {
				return getContentByCustomization((Customization) arg);
			} else if (arg instanceof UserBinder) {
				return getContentByUserBinder((UserBinder) arg);
			} else {
				return getContentByParameters(arg);
			}
		}
		return content.toString();
	}

	private String getContentByCustomization(Customization arg) {
		StringBuffer content = new StringBuffer();
		if (arg != null) {
			handleLogField(content, arg.getServerLogo(), ",服务端LOGO:");
			handleLogField(content, arg.getServerLoginLogo(), ",服务端登录LOGO:");
			handleLogField(content, arg.getServerManagerName(), ",服务端管理平台名称:");
			handleLogField(content, arg.getServerCopyright(), ",服务端版权信息:");

			handleLogField(content, arg.getClientLogo(), ",客户端LOGO:");
			handleLogField(content, arg.getClientName(), ",客户端名称:");
			handleLogField(content, arg.getClientCopyright(), ",客户端版权:");
			handleLogField(content, arg.getClientHomePage(), ",客户端官方网址:");
			handleLogField(content, arg.getClientContact(), ",客户端客服电话:");
		}
		return content.toString();
	}

	private String getContentByDeptBinder(DeptBinder arg) {
		StringBuffer content = new StringBuffer();
		if (arg != null) {
			handleLogField(content, arg.getGname(), ",部门名称:");
			handleLogField(content, arg.getDeptName(), ",上级部门:");
			handleLogField(content, arg.getDeptDesc(), ",部门描述:");
			handleLogField(content, arg.getSequ() != null ? arg.getSequ().toString() : null, ",排序号:");
		}
		return content.toString();
	}

	private String getContentByParam(Param arg, String method) {
		StringBuffer content = new StringBuffer();
		if (arg != null) {
			if (method.equals("saveOrUpdateParam")) {
				content.append(arg.getId() == null ? "新增参数," : "修改参数,");
				handleLogField(content, arg.getParamName(), "参数名称:");
				handleLogField(content, arg.getParamType(), ",参数类型:", true);
			} else if (arg.getId() != null) {
				if (arg.getId().equals("system")) {
					content.append("设置系统参数,");
					handleLogField(content, arg.getPwdExpire(), "密码定时过期:", true);
					handleLogField(content, arg.getPwdPeriod(), ",密码有效期:");
					handleLogField(content, arg.getPwdLength(), ",密码长度:", true);
					handleLogField(content, arg.getPwdMinLength(), ",密码最小长度:");
					handleLogField(content, arg.getPwdMaxLength(), ",密码最大长度:");

					handleLogField(content, arg.getPwdCheck(), ",密码强度:", true);
					handleLogField(content, arg.getPwdFirstCheck(), ",首次登录强制修改密码:", true);
					handleLogField(content, arg.getLoginCheck(), ",登录验校码支持:", true);
					handleLogField(content, arg.getIpCheck(), ",是否校验IP地址:", true);
					handleLogField(content, arg.getLoginAuthType(), ",登录认证方式:", true);
					handleLogField(content, arg.getLdapUrl(), ",LDAP服务器URL:");
					handleLogField(content, arg.getBaseDN(), ",baseDN:");
					handleLogField(content, arg.getDomain(), ",域名:");
				} else if (arg.getId().equals("contact")) {
					content.append("设置通讯录参数,");
					handleLogField(content, arg.getContactOrgName(), "通讯录单位名称:");
					handleLogField(content, arg.getSyncLDAP(), ",是否同步LDAP:", true);
					handleLogField(content, arg.getLdapUrl(), ",LDAP服务器URL:");
					handleLogField(content, arg.getContactBaseDN(), ",BaseDN:");
					handleLogField(content, arg.getProtectedPropNames(), ",成员属性访问保护:");
					handleLogField(content, arg.getEditablePropNames(), ",通讯录个人可设置的属性:");
				} else {
					content.append(",");
					handleLogField(content, arg.getParamName(), "参数名称:");
				}
			} else {
				logger.error("wrong param id:{}" + arg.getId());
			}
		}
		return content.toString();
	}

	private String getContentByClientBinder(ClientBinder arg) {
		StringBuffer content = new StringBuffer();
		if (arg != null) {
			content.append(",");
			handleLogField(content, arg.getCname(), "应用名称:");
			handleLogField(content, arg.getCtype(), ",应用类型:");
			handleLogField(content, arg.getVersion(), ",应用版本:");
		}
		return content.toString();
	}

	/**
	 * 除了Role参数得到操作日志.
	 * 
	 * @param arg
	 * @param mName
	 * @return
	 */
	private String getContentByRole(Role r, String method) {
		StringBuffer content = new StringBuffer();
		if (r != null) {
			if (method.equals("saveOrUpdateRole")) {
				content.append(StringUtils.isEmpty(r.getRoleId()) ? "新建角色," : "编辑角色,");
			} else {
				content.append(",");
			}
			handleLogField(content, r.getRoleName(), "角色名称:");
			handleLogField(content, r.getDesc(), ",角色描述:");
			handleLogField(content, r.getPrivileges(), ",角色设置:");

			if (r.getUserList() != null && r.getUserList().size() > 0) {
				content.append(",角色成员:");
				for (int i = 0; i < r.getUserList().size(); i++) {
					content.append(r.getUserList().get(i).getName());
					if (i < r.getUserList().size() - 1) {
						content.append("、");
					}
				}
			}
		}
		return content.toString();
	}

	private void handleLogField(StringBuffer content, String param, String fieldStart) {
		handleLogField(content, param, fieldStart, false);
	}

	/**
	 * 
	 * @param content
	 * @param param
	 * @param fieldStart
	 * @param useDesc
	 *            true使用特定的描述转换
	 */
	private void handleLogField(StringBuffer content, String param, String fieldStart, boolean useDesc) {
		if (useDesc) {
			content.append(param != null ? fieldStart
					+ (paramDescMap.containsKey(param) ? paramDescMap.get(param) : param) : "");
		} else {
			content.append(param != null ? fieldStart + param : "");
		}

	}

	private void handleLogField(StringBuffer content, List<String> params, String fieldStart) {
		if (params != null && params.size() > 0) {
			content.append(fieldStart);
			for (int i = 0; i < params.size(); i++) {
				String param = params.get(i);
				content.append(paramDescMap.containsKey(param) ? paramDescMap.get(param) : param);
				if (i < params.size() - 1) {
					content.append("、");
				}
			}
		}
	}

	/**
	 * 处理NewsBinder类型得到操作日志.
	 * 
	 * @param upb
	 * @return
	 */
	private String getContentByNewsBinder(NewsBinder nb, String method) {
		StringBuffer content = new StringBuffer();
		if (nb != null) {
			if (method.equals("saveNews")) {
				content.append(StringUtils.isEmpty(nb.getNews_id()) ? "新建新闻," : "编辑新闻,");
			} else {
				content.append(",");
			}
			content.append(nb.getNews_title() != null ? "标题:" + nb.getNews_title() : "");
			content.append(nb.getNews_con() != null ? ",内容:" + nb.getNews_con() : "");
		}
		return content.toString();
	}

	/**
	 * 处理UserPermBinder类型参数得到操作日志内容.
	 * 
	 * @param upb
	 * @return
	 */
	private String getContentByUserPermBinder(UserPermBinder upb) {
		StringBuffer content = new StringBuffer();
		if (upb != null) {
			content.append(",");
			content.append(upb.getUname() != null ? "用户名:" + upb.getUname() : "");
			if (upb.getPermList() != null && upb.getPermList().size() > 0) {
				Collections.sort(upb.getPermList());
				Integer permType = null;
				for (UserPermission up : upb.getPermList()) {
					if (permType == null || permType != up.getPermType()) {
						content.append(",").append(getPermTypeDesc(up.getPermType())).append(":")
								.append(up.getPermName());
						permType = up.getPermType();
					} else {
						content.append("、").append(up.getPermName());
					}
				}
			}
		}
		return content.toString();
	}

	/**
	 * 获取权限类型描述.
	 * 
	 * @param permType
	 * @return
	 */
	private String getPermTypeDesc(int permType) {
		if (permType == 1)
			return "可见职级";
		if (permType == 2)
			return "可见部门";
		if (permType == 3)
			return "可见人员";
		return "未知类型";
	}

	/**
	 * 处理GroupBinder类型参数得到操作日志.
	 * 
	 * @param gb
	 * @return
	 */
	private String getContentByGroupBinder(GroupBinder gb) {
		StringBuffer content = new StringBuffer();
		if (gb != null) {
			content.append(",");
			content.append(gb.getGname() != null ? "部门名称:" + gb.getGname() : "");
			content.append(gb.getGpname() != null ? ",上级部门:" + gb.getGpname() : "");
			content.append(gb.getGdesc() != null ? ",部门描述:" + gb.getGdesc() : "");
			content.append(gb.getSequ() != null ? ",排序号:" + gb.getSequ() : "");
		}
		return content.toString();
	}

	/**
	 * 处理UserBinder类型得到操作日志.
	 * 
	 * @param ub
	 * @return
	 */
	private String getContentByUserBinder(UserBinder ub) {
		StringBuffer content = new StringBuffer();
		if (content != null) {
			content.append(StringUtils.isEmpty(ub.getId()) ? "新增用户," : "更新用户,");
			content.append(ub.getUid() != null ? "工号:" + ub.getUid() : "");
			content.append(ub.getCn() != null ? ",姓名:" + ub.getCn() : "");
			content.append(ub.getDeptName() != null ? ",部门:" + ub.getDeptName() : "");
			content.append(ub.getSexDisplay() != null ? ",性别:" + ub.getSexDisplay() : "");
			content.append(ub.getPosDisplay() != null ? ",职级:" + ub.getPosDisplay() : "");
			content.append(ub.getBirthday() != null ? ",出生日期:" + ub.getBirthday() : "");
			content.append(ub.getMobile() != null ? ",手机:" + ub.getMobile() : "");
			content.append(ub.getMail() != null ? ",邮箱:" + ub.getMail() : "");
			content.append(ub.getL() != null ? ",登录账户:" + ub.getL() : "");
			content.append(ub.getPassword() != null ? ",密码:" + ub.getPassword() : "");
			content.append(ub.getPhotoUrl() != null ? ",头像:" + ub.getPhotoUrl() : "");
			content.append(ub.getWeixin() != null ? ",微信:" + ub.getWeixin() : "");
			content.append(ub.getPhotoSign() != null ? ",个性签名:" + ub.getPhotoSign() : "");
			content.append(ub.getWeibo() != null ? ",微博:" + ub.getWeibo() : "");
			content.append(ub.getHobby() != null ? ",爱好:" + ub.getHobby() : "");
			content.append(ub.getHometelephone() != null ? ",家庭电话:" + ub.getHometelephone() : "");
			content.append(ub.getQq() != null ? ",QQ:" + ub.getQq() : "");
			content.append(ub.getFax() != null ? ",传真电话:" + ub.getFax() : "");
			content.append(ub.getAddress() != null ? ",地址:" + ub.getAddress() : "");
			content.append(ub.getInfo() != null ? ",备注:" + ub.getInfo() : "");
		}
		return content.toString();
	}

	/**
	 * 处理TenantBinder类型参数得到操作日志.
	 * 
	 * @param tb
	 * @return
	 */
	private String getContentByTenantBinder(TenantBinder tb) {
		StringBuffer content = new StringBuffer();
		if (tb != null) {
			content.append(",");
			content.append(tb.getTname() != null ? "企业名称:" + tb.getTname() : "");
			content.append(tb.getTpid() != null ? ",企业号:" + tb.getTpid() : "");
			content.append(tb.getPhotoImg() != null ? ",企业Logo:" + tb.getPhotoImg() : "");
			content.append(tb.getTlinkman() != null ? ",企业联系人:" + tb.getTlinkman() : "");
			content.append(tb.getTmobile() != null ? ",联系电话:" + tb.getTmobile() : "");
			content.append(tb.getTmail() != null ? ",电子邮件:" + tb.getTmail() : "");
			content.append(tb.getTtel() != null ? ",固定电话:" + tb.getTtel() : "");
			content.append(tb.getTguimo() != null ? ",企业规模:" + tb.getTguimo() : "");
			content.append(tb.getTprov() != null ? ",所在地区:" + tb.getTprov() : "");
			content.append(tb.getTcity() != null ? tb.getTcity() : "");
			content.append(tb.getTaddress() != null ? ",通讯地址:" + tb.getTaddress() : "");

			content.append(tb.getOpass() != null ? ",旧密码:" + tb.getOpass() : "");
			content.append(tb.getPass() != null ? ",新密码:" + tb.getPass() : "");
		}
		return content.toString();
	}

	/**
	 * 处理UcTenantMy类型参数得到操作日志.
	 * 
	 * @param tb
	 * @return
	 */
	private String getContentByUcTenantMy(UcTenantMy tb) {
		StringBuffer content = new StringBuffer();
		if (tb != null) {
			content.append(",");
			content.append(tb.getName() != null ? "企业名称:" + tb.getName() : "");
			content.append(tb.getPlatformId() != null ? ",企业号:" + tb.getPlatformId() : "");
			content.append(tb.getPhotoImg() != null ? ",企业Logo:" + tb.getPhotoImg() : "");
			content.append(tb.getLinkman() != null ? ",企业联系人:" + tb.getLinkman() : "");
			content.append(tb.getMobile() != null ? ",联系电话:" + tb.getMobile() : "");
			content.append(tb.getMail() != null ? ",电子邮件:" + tb.getMail() : "");
			content.append(tb.getTel() != null ? ",固定电话:" + tb.getTel() : "");
			content.append(tb.getGuimo() != null ? ",企业规模:" + tb.getGuimo() : "");
			content.append(tb.getProv() != null ? ",所在地区:" + tb.getProv() : "");
			content.append(tb.getCity() != null ? tb.getCity() : "");
			content.append(tb.getAddress() != null ? ",通讯地址:" + tb.getAddress() : "");
		}
		return content.toString();
	}

	/**
	 * 处理参数获取操作日志.
	 * 
	 * @param info
	 * @return
	 */
	private String getContentByParameters(Object info) {
		StringBuffer content = new StringBuffer();
		// 获取对象的所有方法
		Method[] methods = info.getClass().getDeclaredMethods();
		// 遍历方法，判断get方法
		for (Method method : methods) {
			String methodName = method.getName();

			// 判断是不是get方法
			if (methodName.indexOf("get") == -1) {// 不是get方法
				continue;// 不处理
			}

			// 只记录参数
			if (methodName.equals("getParameterMap")) {
				Object rsValue = null;
				try {
					// 调用get方法，获取返回值
					rsValue = method.invoke(info);
					logger.info(rsValue.getClass() + "");
					if (rsValue instanceof ParameterMap) {
						@SuppressWarnings("unchecked")
						ParameterMap<String, String[]> map = (ParameterMap<String, String[]>) rsValue;
						if (map.size() > 0) {
							content.append(",");
						}
						int j = 0;
						for (Entry<String, String[]> entry : map.entrySet()) {
							String key = entry.getKey();
							if (paramDescMap.containsKey(key)) {

							}
							String[] vals = entry.getValue();
							content.append(paramDescMap.containsKey(key) ? paramDescMap.get(key) : key);
							content.append(":");
							if (vals.length > 1) {
								content.append("[");
							}
							for (int i = 0; i < vals.length; i++) {
								content.append(vals[i]);
								if (i < vals.length - 1) {
									content.append(",");
								}
							}
							if (vals.length > 1) {
								content.append("]");
							}
							if (j < map.size() - 1) {
								content.append(",");
							}
							j++;
						}
					}
				} catch (Exception e) {
					logger.error("", e);
					continue;
				}
			}
		}
		return content.toString();
	}

}
