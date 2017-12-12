package com.zhongyang.java.task;
import org.springframework.stereotype.Component;

import com.zhongyang.java.system.util.UtilBiz;

@Component("utilBizSessionTask")
public class SessionTask extends UtilBiz{

	public void removeSessionTask() {
		super.removeAllZsession();
	}
}
