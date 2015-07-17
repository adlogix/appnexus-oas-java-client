package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Payment Method" values which can be assigned to a {@link Campaign} in
 * Campaign creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum PaymentMethod implements ToStringReturnsEnumCode {

	CASH("C"), BARTER("B"), IN_HOUSE("I"), INTERNAL_TRANSFER("T");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link PaymentMethod} object which corresponds to the given OAS
	 * code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link PaymentMethod} object
	 */
	public static PaymentMethod fromString(String code) {
		return EnumUtils.fromString(code, values(), PaymentMethod.class.getSimpleName());
	}

}
