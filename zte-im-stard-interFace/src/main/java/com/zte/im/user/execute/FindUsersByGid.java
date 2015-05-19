package com.zte.im.user.execute;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.DataVer;
import com.zte.im.bean.User;
import com.zte.im.protocol.ResponseListMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IUserService;
import com.zte.im.service.IVersionService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.service.impl.VersionServiceImpl;
import com.zte.im.util.Page;

/**
 * 根据企业id和页数获取用户列表信息，userListVer最新时不返回数据
 * 
 * @author Administrator
 * 
 */
public class FindUsersByGid extends AbstractValidatoExecute {

	private final static Logger logger = LoggerFactory
			.getLogger(FindUsersByGid.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Long gid = json.getLong("gid");
		List<User> list = null;
		Integer cpage = json.getInteger("page");
		Page page = new Page(20, cpage);
		IUserService service = UserServiceImpl.getInstance();
		Long count = service.getUserCountbyGid(gid);
		if (count > 0) {
			Long uid = super.getContext().getUser().getUid();
			User user = service.getUserbyid(uid);
			list = service.getUserbyGid(gid, page, user);
		}
		User user = super.getContext().getUser();
		if (cpage == 1) {
			// 记录用户日志
			try {
				StringBuilder oper = new StringBuilder();
				oper.append("用户");
				oper.append(user.getName());
				oper.append("同步企业员工通讯录数据成功");
				TwitterServiceImpl.getInstance().saveUserLog(user, "用户更新通讯录",
						oper.toString());
			} catch (Exception e) {
			}
		}
		ResponseListMain main = new ResponseListMain();
		main.setItem(list);
		main.setMaxcount(count);
		IVersionService vService = VersionServiceImpl.getInstance();
		DataVer dataVer = vService.getDataVer();
		if (user != null && user.getGroupVer() != null) {
			dataVer.setGroupVer(user.getGroupVer());
		} else {
			dataVer.setGroupVer(0L);
		}
		main.setDataVer(dataVer);
		resJson = JSON.toJSONString(main);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("page", 26);
		json.put("gid", 1);
		json.put("token", "6027360_SWTXVI6X");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new FindUsersByGid();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("findUserByGid");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
