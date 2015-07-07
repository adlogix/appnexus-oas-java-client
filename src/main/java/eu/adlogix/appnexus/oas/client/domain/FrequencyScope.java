package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FrequencyScope {

	ZERO("0"), SESSION("1"), HOURLY("2"), DAILY("3"), WEEKLY("4"), MONTHLY("5"), LIFETIME("6");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

	public static FrequencyScope fromString(String code) {
		if (code == null)
			return null;

		for (FrequencyScope frequencyScope : FrequencyScope.values()) {
			if (code.equalsIgnoreCase(frequencyScope.code)) {
				return frequencyScope;
			}
		}
		throw new IllegalArgumentException("Invalid FrequencyScope Code:" + code);
	}

}
