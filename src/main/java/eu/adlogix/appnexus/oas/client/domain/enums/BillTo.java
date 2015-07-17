package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Bill To" values which can be assigned to a {@link Campaign} in Campaign
 * creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum BillTo implements ToStringReturnsEnumCode {

	ADVERTISER("A"), AGENCY("G");

	@Getter
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
		return EnumUtils.fromString(code, values(), BillTo.class.getSimpleName());
	}
}