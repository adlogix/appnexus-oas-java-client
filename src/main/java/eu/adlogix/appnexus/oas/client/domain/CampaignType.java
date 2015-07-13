package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

/**
 * "Campaign Type" values which can be assigned to a {@link Campaign} in
 * Campaign creation and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum CampaignType {

	CLT("CLT"), REGULAR("RM");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link CampaignType} object which corresponds to the given OAS
	 * code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link CampaignType} object
	 */
	public static CampaignType fromString(String code) {
		if (code == null)
			return null;

		for (CampaignType campaignType : CampaignType.values()) {
			if (code.equalsIgnoreCase(campaignType.code)) {
				return campaignType;
			}
		}
		throw new IllegalArgumentException("Invalid CampaignType Code:" + code);
	}

}
