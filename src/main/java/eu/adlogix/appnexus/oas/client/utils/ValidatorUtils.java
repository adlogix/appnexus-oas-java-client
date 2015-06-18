package eu.adlogix.appnexus.oas.client.utils;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class ValidatorUtils {
	public static void checkNotNull(Object obj, String variableName) {
		if (obj == null) {
			throw new RuntimeException(variableName + " shouldn't have a null value");
		}
	}

	public static void checkNotEmpty(String obj, String variableName) {
		if (StringUtils.isEmpty(obj)) {
			throw new RuntimeException(variableName + " shouldn't have a empty value");
		}
	}

	public static void checkNotEmpty(Collection<?> obj, String variableName) {
		if (CollectionUtils.isEmpty(obj)) {
			throw new RuntimeException(variableName + " shouldn't be empty");
		}
	}
}
