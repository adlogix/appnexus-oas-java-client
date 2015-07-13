package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

/**
 * "Campaign Status" values which can be assigned to a {@link Campaign} in
 * Campaign update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum CampaignStatus {

	WORK_IN_PROGRESS("W"), LIVE("L"), WAITING_FOR_APPROVAL("P"), ORDERED("O"), COMPLETED("C"), RESERVED("R"), REJECTED(
			"J"), CANCELLED("X"), SUSPENDED("S"), ARCHIVED("A"), RLC_DECLINE("D"), TEMPLATE("T");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link CampaignStatus} object which corresponds to the given
	 * OAS code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link CampaignStatus} object
	 */
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