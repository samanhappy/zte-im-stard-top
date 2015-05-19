package com.zte.weibo.service;

import java.util.List;

import com.zte.im.bean.TComment;
import com.zte.im.util.Page;

public interface CommentService {

	/**
	 * 删除指定的评论
	 * @author syq	2015年1月23日
	 * @param commentId
	 * @throws Exception
	 */
	public void delByCommentId(Long commentId) throws Exception;
	
	
	public List<TComment> getListByTwitterId(Long twitterId,Page page) throws Exception;
}
