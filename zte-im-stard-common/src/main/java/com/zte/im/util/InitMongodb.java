package com.zte.im.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.zte.im.mongodb.MongoDBSupport;

public class InitMongodb {

	public static void main(String[] args) {

	}

	public void createUser() {
		MongoDBSupport mongodb = MongoDBSupport.getInstance();
		DBCollection dbCollection = mongodb.db
				.getCollection(MongoDBSupport._users);
		BasicDBObject obj = new BasicDBObject("uid", 1);
		dbCollection.ensureIndex(obj, "uid", true);
	}

}
