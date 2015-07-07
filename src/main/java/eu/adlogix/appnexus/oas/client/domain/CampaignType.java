package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CampaignType {

	CLT("CLT"), REGULAR("RM");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

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
