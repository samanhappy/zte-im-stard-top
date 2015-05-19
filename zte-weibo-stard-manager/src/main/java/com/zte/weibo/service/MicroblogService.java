package com.zte.weibo.service;

import java.util.List;

import com.zte.im.bean.TTwitter;
import com.zte.im.util.Page;
import com.zte.weibo.databinder.MicroblogBinder;

public interface MicroblogService {

	public String export(Long[] ids) throws Exception;
	public void delByIds(Long[] ids) throws Exception;
	public List<TTwitter> search(MicroblogBinder microblogBinder,Page page) throws Exception;
	
	/**
	 * 查询分享详情，包括分享的用户信息
	 * @author syq	2015年1月23日
	 * @param twitterId
	 * @return
	 * @throws Exception
	 */
	public TTwitter getDetailByTwitterId(Long twitterId) throws Exception;
	
}
