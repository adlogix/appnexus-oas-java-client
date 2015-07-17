package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.Getter;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * How {@link Boolean}s are represented in OAS XML requests and responses
 */
public enum XmlBoolean implements ToStringReturnsEnumCode {
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
		return EnumUtils.fromString(param, values(), XmlBoolean.class.getSimpleName());
	}

	public Boolean toBoolean() {
		return this.value;
	}
}
