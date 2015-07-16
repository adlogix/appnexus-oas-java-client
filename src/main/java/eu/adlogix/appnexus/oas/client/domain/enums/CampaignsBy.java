package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;

/**
 * "Campaigns By" values which can be assigned to a {@link Campaign} in Campaign
 * creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum CampaignsBy {

	ADVERTISER("A"), AGENCY("G");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link CampaignsBy} object which corresponds to the given OAS
	 * code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link CampaignsBy} object
	 */
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
