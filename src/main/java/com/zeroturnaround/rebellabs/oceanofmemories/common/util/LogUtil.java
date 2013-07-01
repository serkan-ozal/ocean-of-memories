package com.zeroturnaround.rebellabs.oceanofmemories.common.util;

import org.apache.log4j.Logger;

public class LogUtil {

    private LogUtil() {

    }

	public static Logger getLogger() {
		return Logger.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

}
