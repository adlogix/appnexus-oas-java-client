package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;

/**
 * "Payment Method" values which can be assigned to a {@link Campaign} in
 * Campaign creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum PaymentMethod {

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
		if (code == null)
			return null;

		for (PaymentMethod paymentMethod : PaymentMethod.values()) {
			if (code.equalsIgnoreCase(paymentMethod.code)) {
				return paymentMethod;
			}
		}
		throw new IllegalArgumentException("Invalid PaymentMethod Code:" + code);
	}

}
