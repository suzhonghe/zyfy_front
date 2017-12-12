package com.zhongyang.java.system.util;

import java.util.UUID;

public class GetUUID {
	
	public static String getUniqueKey(){
		return UUID.randomUUID().toString().toUpperCase();
	}

}
