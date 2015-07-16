package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.Getter;

import org.apache.axis.utils.StringUtils;

/**
 * How {@link Boolean}s are represented in OAS XML requests and responses
 */
public enum XmlBoolean {
	Y(true), N(false);

	@Getter
	private final boolean value;

	private XmlBoolean(Boolean value) {
		this.value = value;
	}

	public static XmlBoolean fromBoolean(final Boolean param) {
		return param != null ? (param.booleanValue() ? Y : N) : null;
	}

	public static XmlBoolean fromString(final String param) {
		return !StringUtils.isEmpty(param) ? (param.equalsIgnoreCase("Y") ? Y : N) : null;
	}

	public Boolean toBoolean() {
		return this.value;
	}
}
