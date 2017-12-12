package com.zhongyang.java.system.util;

import java.util.Map;

/**
 * Created by Matthew on 2015/12/29.
 */
public abstract class UtilBiz {

	public void setZsession(String key,Object object) {
		ZSession zsession = ZSession.getzSession();
		zsession.getzSessionMap().put(key, object);
	}

	public Object getZsession(String key) {
		if (key != null) {
			ZSession zsession = ZSession.getzSession();
			Map<String, Object> sessionMap = (Map<String, Object>) zsession.getzSessionMap().get(key);
			zsession.getzSessionMap().remove(key);
			return sessionMap;
		}
		return null;
	}

	public Object getZsessionObject(String key) {
		if (key != null) {
			ZSession zsession = ZSession.getzSession();
			Map<String, Object> sessionMap = (Map<String, Object>) zsession.getzSessionMap();
			return sessionMap.get(key);
		}
		return null;
	}

	public boolean removeZsessionObject(String uuid) {
		if (uuid != null) {
			ZSession zsession = ZSession.getzSession();
			Map<String, Object> sessionMap = (Map<String, Object>) zsession.getzSessionMap();

			sessionMap.remove(uuid);
			return true;
		}
		return false;
	}
	
	public boolean removeAllZsession() {
		ZSession zsession = ZSession.getzSession();
		Map<String, Object> sessionMap = (Map<String, Object>) zsession.getzSessionMap();

		sessionMap.clear();
		return true;
	}
}
