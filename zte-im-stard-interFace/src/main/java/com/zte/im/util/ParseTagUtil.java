package com.zte.im.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTagUtil {

	// 在原始文本中提取@用户名
	public static List<Long> parseMentionUserId(String s) {
		// 提取mUserid
		Pattern pmentionid = Pattern.compile("mentionUserId=[\\S&&[^\u0020]]+");
		Matcher mmentionid = pmentionid.matcher(s);
		List<Long> mUserIdList = new ArrayList<Long>();
		while (mmentionid.find()) {
			s = s.replace(mmentionid.group() + "\u0020", "");
			String content = mmentionid.group();
			mUserIdList.add(Long.parseLong(content.split("=")[1]));
		}

		return mUserIdList;
	}

	// 在原始文本中提取#话题#
	public static List<Long> parseTopicId(String s) {
		// 提取poundid
		Pattern ppoundid = Pattern.compile("poundid=[\\S&&[^\u0020]]+");
		Matcher mpoundid = ppoundid.matcher(s);
		List<Long> topicIdList = new ArrayList<Long>();
		while (mpoundid.find()) {
			s = s.replace(mpoundid.group() + "\u0020", "");
			String content = mpoundid.group();
			topicIdList.add(Long.parseLong(content.split("=")[1]));
		}
		return topicIdList;
	}

	// 在原始文本中过滤@用户名和#话题#
	public static String filterContent(String s) {
		// 剔除@人员标识
		Pattern pmentionid = Pattern.compile("mentionUserId=[\\S&&[^\u0020]]+");
		Matcher mmentionid = pmentionid.matcher(s);
		while (mmentionid.find()) {
			s = s.replace("\u0020" + mmentionid.group() + "\u0020", "");
		}
		// 剔除话题标识
		Pattern ppoundid = Pattern.compile("poundid=[\\S&&[^\u0020]]+");
		Matcher mpoundid = ppoundid.matcher(s);
		while (mpoundid.find()) {
			s = s.replace("\u0020" + mpoundid.group() + "\u0020", "");
		}
		return s;
	}

	public static void main(String[] args) {
		String html = "@@f2234@自定义Html标签\u0020mentionUserId=11\u0020@自定义标签\u0020mentionUserId=12\u0020微风徐来，水波不兴,#柳宗元#\u0020poundid=13\u0020河南永州f003";
		//List<Long> list = ParseTagUtil.parseMentionUserId(html);
		
		String fstr = ParseTagUtil.filterExpression(html);
		System.out.println("=============" + fstr);

	}

	// 在原始文本中过滤f开头后3位为数字内容
	public static String filterExpression(String s) {
		if(s!=null){
			s=s.replace("@@", "");
			// 剔除@人员标识
			Pattern pmentionid = Pattern.compile("f\\d{3}+");
			Matcher mpoundid = pmentionid.matcher(s); 
			while (mpoundid.find()) {
				s = s.replace(mpoundid.group() , ""); 
			} 
			return s; 
		}else{
			return "";
		}
	}
}
