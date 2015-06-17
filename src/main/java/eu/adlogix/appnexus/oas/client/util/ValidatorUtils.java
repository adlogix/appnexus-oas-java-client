package eu.adlogix.appnexus.oas.client.util;

import org.apache.commons.lang3.StringUtils;

public class ValidatorUtils {
	public static void checkNotNull(Object obj, String variableName) {
		if (obj == null) {
			throw new RuntimeException(variableName + " shouldn't have a null value");
		}
	}

	public static void checkEmpty(String obj, String variableName) {
		if (StringUtils.isEmpty(obj)) {
			throw new RuntimeException(variableName + " shouldn't have a empty value");
		}
	}
}
