package eu.adlogix.appnexus.oas.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;

import eu.adlogix.appnexus.oas.client.domain.XmlBoolean;

public class ParserUtil {

	public static Double createDouble(String string) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return Double.valueOf(string);
	}

	public static Long createLong(String string) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return Long.valueOf(string);
	}

	public static Integer createInteger(String string) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return Integer.valueOf(string);
	}

	public static DateTime createDateTime(String string, DateTimeFormatter dateFormatter) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return dateFormatter.parseDateTime(string);
	}

	public static LocalDate createLocalDate(String string, DateTimeFormatter dateFormatter) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return dateFormatter.parseLocalDate(string);
	}

	public static LocalTime createLocalTime(String string, DateTimeFormatter timeFormatter) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return timeFormatter.parseLocalTime(string);
	}

	public static Boolean createBooleanFromXmlString(String string) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return XmlBoolean.fromString(string).toBoolean();
	}

}
