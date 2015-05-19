package com.zte.im.service;

import java.util.List;

import com.mongodb.DBObject;

public interface IWorkImgService {

	public List<DBObject> listWorkImg();
	
	public void saveWorkImg(String imgList);

}
