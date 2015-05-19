package com.zte.im.protocol;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zte.im.util.Constant;

public class ResponseContactsMain {
	
    private Res res;
	private List<?> added;
	private List<?> changed;
	private List<?> deleted;
	private Long newVersion;
	public Map<String, Object> initResultMap() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("res", res);
		return result;
	}

	public ResponseContactsMain() {
		res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("Operation is successful");
	}

	public Res getRes() {
		return res;
	}

	public void setRes(Res res) {
		this.res = res;
	}

    public List<?> getChanged() {
        return changed;
    }

    public void setChanged(List<?> changed) {
        this.changed = changed;
    }

    public List<?> getAdded() {
        return added;
    }

    public void setAdded(List<?> added) {
        this.added = added;
    }

    public List<?> getDeleted() {
        return deleted;
    }

    public void setDeleted(List<?> deleted) {
        this.deleted = deleted;
    }

    public Long getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(Long newVersion) {
        this.newVersion = newVersion;
    }
}
