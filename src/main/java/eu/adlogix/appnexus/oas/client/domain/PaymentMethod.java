package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentMethod {

	CASH("C"), BARTER("B"), IN_HOUSE("I"), INTERNAL_TRANSFER("T");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

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
