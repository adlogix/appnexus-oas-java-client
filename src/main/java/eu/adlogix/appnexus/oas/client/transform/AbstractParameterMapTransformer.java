package eu.adlogix.appnexus.oas.client.transform;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;

import eu.adlogix.appnexus.oas.client.domain.enums.XmlBoolean;

import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.DATE_FORMATTER;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.TIME_FORMATTER;

public abstract class AbstractParameterMapTransformer implements ParameterMapTransformer {

	protected void checkValueAndPutParam(String paramName, String paramValue, Map<String, Object> result) {
		if (paramValue != null)
			result.put(paramName, paramValue);
	}

	protected void checkValueAndPutParam(String paramName, Integer paramValue, Map<String, Object> result) {
		if (paramValue != null)
			result.put(paramName, paramValue);
	}

	protected void checkValueAndPutParam(String paramName, Boolean paramValue, Map<String, Object> result) {
		if (paramValue != null)
			result.put(paramName, XmlBoolean.fromBoolean(paramValue).toString());
	}

	protected void checkValueAndPutParam(String paramName, LocalDate paramValue, Map<String, Object> result) {
		if (paramValue != null)
			result.put(paramName, DATE_FORMATTER.print(paramValue));
	}

	protected void checkValueAndPutParam(String paramName, LocalTime paramValue, Map<String, Object> result) {
		if (paramValue != null)
			result.put(paramName, TIME_FORMATTER.print(paramValue));
	}

	protected void checkValueAndPutParam(String paramName, LocalTime paramValue, DateTimeFormatter timeFormatter,
			Map<String, Object> result) {
		if (paramValue != null)
			result.put(paramName, timeFormatter.print(paramValue));
	}

	protected void checkValueAndPutParam(String paramName, List<String> paramValue, Map<String, Object> result) {
		if (paramValue != null)
			result.put(paramName, paramValue);
	}

}
