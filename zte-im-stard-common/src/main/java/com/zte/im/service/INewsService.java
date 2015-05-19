package com.zte.im.service;

import java.util.List;

import com.zte.im.bean.News;

public interface INewsService {

	public void saveNewsObject(News news);

	public List<News> queryNewsByParams(int days, String title, String flag);

	public void delNewsObject(String id);

	public News getNewsById(String id);

	public void updateNewsObject(News news);
}
