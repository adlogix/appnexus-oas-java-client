package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

/**
 * "Frequency Scope" values which can be assigned to a {@link Campaign} in
 * Campaign creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum FrequencyScope {

	ZERO("0"), SESSION("1"), HOURLY("2"), DAILY("3"), WEEKLY("4"), MONTHLY("5"), LIFETIME("6");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link FrequencyScope} object which corresponds to the given
	 * OAS code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link FrequencyScope} object
	 */
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
