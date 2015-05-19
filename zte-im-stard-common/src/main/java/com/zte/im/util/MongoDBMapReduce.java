package com.zte.im.util;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand.OutputType;
import com.mongodb.MapReduceOutput;

/**
 * 查询用户修改信息mapreduce.
 * @author root
 *
 */
public class MongoDBMapReduce {

    @SuppressWarnings("rawtypes")
    public static List<List> mapRequce(DBCollection collection, BasicDBObject query) throws Exception {

        //构建map函数
        String map = "function() {" +
                "var userListVer = this.userListVer;" +
                "    emit(" + 
                "            {" + 
                "                uid:this.uid, " + 
                "                oper:this.oper, " + 
                "            }," + 
                "            this " + 
                "        ); " + 
                "     " + 
                "}";
        
        //构建reduce函数
        String reduce = "function(key, values) {"
                + "var max = 0;"
                + "var maxItem = null;"
                + "for(var i =0, max = 0; i < values.length; i++ ) {"
                + "if(values[i].userListVer > max) {"
                + "max = values[i].userListVer;"
                + "maxItem = values[i];"
                + "}"
                + "}"
                + "return maxItem;"
                + "};";
        
        MapReduceOutput output = collection.mapReduce(map, reduce, null, OutputType.INLINE, query);
        List<List> record = new ArrayList<List>();
        Iterable<DBObject> iter = output.results();
        List<Long> add = new ArrayList<Long>();
        List<Long> chang = new ArrayList<Long>();
        List<Long> delete = new ArrayList<Long>();
        List<Long> userListVer = new ArrayList<Long>();
        userListVer.add(1L);
        for(DBObject obj : iter) {
            //获取最新时间戳
            BasicDBObject timeobject = (BasicDBObject) obj.get("value");
            if(userListVer.get(0) < timeobject.getLong("userListVer")) {
                userListVer.set(0, timeobject.getLong("userListVer"));
            }
            DBObject object = (DBObject) obj.get("_id");
            if(object.get("oper").equals("added")) {
                add.add((Long)object.get("uid"));
            } else if(object.get("oper").equals("changed")) {
                chang.add((Long)object.get("uid"));
            } else {
                delete.add((Long)object.get("uid"));
            }
        }
        //移除新增、更新、删除中共同的用户uid    
        for(int i = 0; i < delete.size(); i++) {
            for(int j = 0; j < chang.size(); j++) {
                if(delete.get(i).equals(chang.get(j))) {
                    chang.remove(j);
                }
            }
         }
        //移除新增、更新、删除中共同的用户uid  
        for(int i = 0; i < delete.size(); i++) {
            for(int j = 0; j < add.size(); j++) {
                if(delete.get(i).equals(add.get(j))) {
                    add.remove(j);
                }
            }
        }
        //移除新增、更新、删除中共同的用户uid  
        for(int i = 0; i < chang.size(); i++) {
            for(int j = 0; j < add.size(); j++) {
                if(chang.get(i).equals(add.get(j))) {
                    add.remove(j);
                }
            }
        }
        record.add(add);
        record.add(chang);
        record.add(delete);
        record.add(userListVer);
        return record;
    }
}
