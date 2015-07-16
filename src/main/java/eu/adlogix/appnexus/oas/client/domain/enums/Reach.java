package eu.adlogix.appnexus.oas.client.domain.enums;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import lombok.AllArgsConstructor;

/**
 * "Reach" values which can be assigned to a {@link Campaign} in Campaign
 * creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum Reach {

	OPEN("O"), FIXED("F"), DYNAMIC("D"), DYNAMIC_RECALCULATED_MONTHLY("M"), HOUSE("H");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link Reach} object which corresponds to the given OAS code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link Reach} object
	 */
	public static Reach fromString(String code) {
		if (code == null)
			return null;

		for (Reach reach : Reach.values()) {
			if (code.equalsIgnoreCase(reach.code)) {
				return reach;
			}
		}
		throw new IllegalArgumentException("Invalid Reach Code:" + code);
	}
}