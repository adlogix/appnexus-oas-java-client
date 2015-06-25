package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;

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
}
