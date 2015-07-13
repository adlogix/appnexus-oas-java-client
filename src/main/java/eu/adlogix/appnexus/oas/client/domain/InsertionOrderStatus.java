package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

/**
 * "Insertion Order Status" values which can be assigned to a
 * {@link InsertionOrder} in Insertion Order creation, update and retrieval. The
 * OAS code is accessible via {@link #toString()}
 */
@AllArgsConstructor
public enum InsertionOrderStatus {

	APPROVED("A"), PENDING("P");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link InsertionOrderStatus} object which corresponds to the
	 * given OAS code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link InsertionOrderStatus} object
	 */
	public static InsertionOrderStatus fromString(String code) {
		if (code == null)
			return null;

		for (InsertionOrderStatus status : InsertionOrderStatus.values()) {
			if (code.equalsIgnoreCase(status.code)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid InsertionOrderStatus Code:" + code);
	}

}
