package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CampaignStatus {

	WORK_IN_PROGRESS("W"), LIVE("L"), WAITING_FOR_APPROVAL("P"), ORDERED("O"), COMPLETED("C"), RESERVED("R"), REJECTED(
			"J"), CANCELLED("X"), SUSPENDED("S"), ARCHIVED("A"), RLC_DECLINE("D"), TEMPLATE("T");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

	public static CampaignStatus fromString(String code) {
		if (code == null)
			return null;

		for (CampaignStatus status : CampaignStatus.values()) {
			if (code.equalsIgnoreCase(status.code)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid CampaignStatus Code:" + code);
	}
}