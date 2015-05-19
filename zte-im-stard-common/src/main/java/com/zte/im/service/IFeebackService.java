package com.zte.im.service;

import com.zte.databinder.FeedbackBinder;
import com.zte.im.bean.Feedback;
import com.zte.im.mybatis.bean.page.FeedbackPageModel;

public interface IFeebackService {

	public FeedbackPageModel list(FeedbackBinder binder);

	public Feedback getFeedback(Long id);

}
