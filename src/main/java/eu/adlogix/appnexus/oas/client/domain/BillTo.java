package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BillTo {

	ADVERTISER("A"), AGENCY("G");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

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