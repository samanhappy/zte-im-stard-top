package com.zte.im.util;

import com.zte.im.mybatis.bean.UcMember;

public class ImDataCompare {
    
    
    public static boolean result = true;
    /**
     * 比对MDM用户数据、Mongodb用户数是否一致.
     * @param member  MDM用户数据
     * @param user    Mongodb用户数据
     * @return        返回比对结果
     */
    public static boolean compare(UcMember member1, UcMember member2) {
        
        if(member1.getCn() == null && member2.getCn() == null) {
            result = true;
        } else if(!(member1.getCn().equals(member2.getCn()))) {
            return false;
        };
        
        if(member1.getPinyin() == null && member2.getPinyin() == null) {
            result = true;
        } else if(!(member1.getPinyin().equals(member2.getPinyin()))) {
            return false;
        };
        
        if(member1.getT9Pinyin() == null && member2.getT9Pinyin() == null) {
            result = true;
        } else if(!member1.getT9Pinyin().equals(member2.getT9Pinyin())) {
            return false;
        };
        
        if(member1.getCreator() == null && member2.getCreator() == null) {
            result = true;
        } else if(!(member1.getCreator().equals(member2.getCreator()))) {
            return false;
        };
        if(member1.getEditable() == null && member2.getEditable() == null) {
            result = true;
        } else if(!(member1.getEditable().equals(member2.getEditable()))) {
            return false;
        };
        if(member1.getDeletable() == null && member2.getDeletable() == null) {
            result = true;
        } else if(!(member1.getDeletable().equals(member2.getDeletable()))) {
            return false;
        };
        if(member1.getMail() == null && member2.getMail() == null) {
            result = true;
        } else if(!(member1.getMail().equals(member2.getMail()))) {
            return false;
        };
        
        if(member1.getMobile() == null && member2.getMobile() == null) {
            result = true;
        } else if(!(member1.getMobile().equals(member2.getMobile()))) {
            return false;
        };
        
        if(member1.getNickname() == null && member2.getNickname() == null) {
            result = true;
        } else if(!(member1.getNickname().equals(member2.getNickname()))) {
            return false;
        };
        
        if(member1.getPassword() == null && member2.getPassword() == null) {
            result = true;
        } else if(!(member1.getPassword().equals(member2.getPassword()))) {
            return false;
        };
        
        if(member1.getPhoto() == null && member2.getPhoto() == null) {
            result = true;
        } else if(!(member1.getPhoto().equals(member2.getPhoto()))) {
            return false;
        };
        
        if(member1.getPhotoSign() == null && member2.getPhotoSign() == null) {
            result = true;
        } else if(!(member1.getPhotoSign().equals(member2.getPhotoSign()))) {
            return false;
        };
        
        if(member1.getPostaladdress() == null && member2.getPostaladdress() == null) {
            result = true;
        } else if(!(member1.getPostaladdress().equals(member2.getPostaladdress()))) {
            return false;
        }; 
        
        if(member1.getQq() == null && member2.getQq() == null) {
            result = true;
        } else if(!(member1.getQq().equals(member2.getQq()))) {
            return false;
        };
        
        if(member1.getSequ() == null && member2.getSequ() == null) {
            result = true;
        } else if(!(member1.getSequ().equals(member2.getSequ()))) {
            return false;
        };
        
        if(member1.getSex() == null && member2.getSex() == null) {
            result = true;
        } else if(!(member1.getSex().equals(member2.getSex()))) {
            return false;
        };
        
        if(member1.getShortcode() == null && member2.getShortcode() == null) {
            result = true;
        } else if(member1.getShortcode().equals(member2.getShortcode())) {
            return false;
        };
        
        if(member1.getState() == null && member2.getState() == null) {
            result = true;
        } else if(!(member1.getState().equals(member2.getState()))) {
            return false;
        };
        
        if(member1.getTenantId() == null && member2.getTenantId() == null) {
            result = true;
        } else if(!(member1.getTenantId().equals(member2.getTenantId()))) {
            return false;
        };
        
        if(member1.getUid() == null && member2.getUid() == null) {
            result = true;
        } else if(!(member1.getUid().equals(member2.getUid()))) {
            return false;
        };
        
        if(member1.getUsable() == null && member2.getUsable() == null) {
            result = true;
        } else if(!(member1.getUsable().equals(member2.getUsable()))) {
            return false;
        };
        
        if(member1.getUserType() == null && member2.getUserType() == null) {
            result = true;
        } else if(!(member1.getUserType().equals(member2.getUserType()))) {
            return false;
        };
        
        if(member1.getWwwhomepage() == null && member2.getWwwhomepage() == null) {
            result  = true;
        } else if(!(member1.getWwwhomepage().equals(member2.getWwwhomepage()))) {
            return false;
        };
        
        if(member1.getHometelephone() == null && member2.getHometelephone() == null) {
            result = true;
        } else if(!(member1.getHometelephone().equals(member2.getHometelephone()))) {
            return false;
        };
        
        if(member1.getFax() == null && member2.getFax() == null) {
            result = true;
        } else if(!(member1.getFax().equals(member2.getFax()))) {
            return false;
        };
        
        if(member1.getInfo() == null && member2.getInfo() == null) {
            result = true;
        } else if(!(member1.getInfo().equals(member2.getInfo()))) {
            return false;
        };
        
        if(member1.getL() == null && member2.getL() == null) {
            result = true;
        } else if(!(member1.getL().equals(member2.getL()))) {
            return false;
        };
        return result;
    }
}
