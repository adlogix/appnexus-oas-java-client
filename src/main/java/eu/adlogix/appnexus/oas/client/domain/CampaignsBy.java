package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CampaignsBy {

	ADVERTISER("A"), AGENCY("G");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

	public static CampaignsBy fromString(String code) {
		if (code == null)
			return null;

		for (CampaignsBy campaignsBy : CampaignsBy.values()) {
			if (code.equalsIgnoreCase(campaignsBy.code)) {
				return campaignsBy;
			}
		}
		throw new IllegalArgumentException("Invalid CampaignsBy Code:" + code);
	}

}
