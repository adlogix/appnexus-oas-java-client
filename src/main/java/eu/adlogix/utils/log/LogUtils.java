package eu.adlogix.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

	public static Logger getLogger(final Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	public static Logger getDetailedLogger(final String detailedLoggerName) {
		return LoggerFactory.getLogger("detailed." + detailedLoggerName);
	}
}
