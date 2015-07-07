package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InsertionOrderStatus {

	APPROVED("A"), PENDING("P");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

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
