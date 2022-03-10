package com.jointthinker.framework.common.logging;

import org.apache.log4j.Logger;

public class Log4jlogging {
	public static void debug(Object o){
		Logger.getLogger(Log4jlogging.class).debug(o);
	}
	
	public static void debug(Object o, Exception ex){
		Logger.getLogger(Log4jlogging.class).debug(o, ex);
	}
	
	public static void error(Object o, Exception ex){
		Logger.getLogger(Log4jlogging.class).error(o, ex);
	}
}
