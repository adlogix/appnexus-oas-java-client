package eu.adlogix.appnexus.oas.client.utils;

import java.util.Arrays;

import eu.adlogix.appnexus.oas.client.domain.enums.ToStringReturnsEnumCode;

public class EnumUtils {
	public static <T extends ToStringReturnsEnumCode> T fromString(String code, T[] allowedValues, String enumName) {
		if (code == null)
			return null;

		for (T t : allowedValues) {
			if (code.equalsIgnoreCase(t.toString())) {
				return t;
			}
		}
		throw new IllegalArgumentException("Invalid " + enumName + " Code '" + code + "'. Valid values are "
				+ Arrays.toString(allowedValues));
	}
}
