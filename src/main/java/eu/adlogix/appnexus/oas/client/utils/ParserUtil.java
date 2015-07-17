package eu.adlogix.appnexus.oas.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.adlogix.appnexus.oas.client.domain.enums.XmlBoolean;

public class ParserUtil {

	public static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
	public static final String TIME_FORMAT_STRING = "HH:mm";

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT_STRING);
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern(TIME_FORMAT_STRING);

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

	public static DateTime createDateTime(String string) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return DATE_FORMATTER.parseDateTime(string);
	}

	public static LocalDate createLocalDate(String string) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return DATE_FORMATTER.parseLocalDate(string);
	}

	public static LocalTime createLocalTime(String string) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return TIME_FORMATTER.parseLocalTime(string);
	}

	public static Boolean createBooleanFromXmlString(String string) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		return XmlBoolean.fromString(string).toBoolean();
	}

}
