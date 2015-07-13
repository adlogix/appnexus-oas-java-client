package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

/**
 * "Smooth or ASAP" values which can be assigned to {@link Campaign} in Campaign
 * creation, update and retrieval operations. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum SmoothAsap {

	SMOOTH("S"), ASAP("A");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link SmoothAsap} object which corresponds to the given OAS
	 * code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link SmoothAsap} object
	 */
	public static SmoothAsap fromString(String code) {
		if (code == null)
			return null;

		for (SmoothAsap smoothAsap : SmoothAsap.values()) {
			if (code.equalsIgnoreCase(smoothAsap.code)) {
				return smoothAsap;
			}
		}
		throw new IllegalArgumentException("Invalid SmoothOrAsap Code:" + code);
	}

}
