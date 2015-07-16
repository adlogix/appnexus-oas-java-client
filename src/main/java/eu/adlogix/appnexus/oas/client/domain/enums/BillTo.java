package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;

/**
 * "Bill To" values which can be assigned to a {@link Campaign} in Campaign
 * creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum BillTo {

	ADVERTISER("A"), AGENCY("G");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}


	/**
	 * Returns a {@link BillTo} object which corresponds to the given OAS code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link BillTo} object
	 */
	public static BillTo fromString(String code) {
		if (code == null)
			return null;

		for (BillTo billTo : BillTo.values()) {
			if (code.equalsIgnoreCase(billTo.code)) {
				return billTo;
			}
		}
		throw new IllegalArgumentException("Invalid BillTo Code:" + code);
	}
}