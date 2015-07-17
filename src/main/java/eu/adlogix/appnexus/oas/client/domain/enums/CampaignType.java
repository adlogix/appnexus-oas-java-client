package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Campaign Type" values which can be assigned to a {@link Campaign} in
 * Campaign creation and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum CampaignType implements ToStringReturnsEnumCode {

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
		return EnumUtils.fromString(code, values(), CampaignType.class.getSimpleName());
	}
}
