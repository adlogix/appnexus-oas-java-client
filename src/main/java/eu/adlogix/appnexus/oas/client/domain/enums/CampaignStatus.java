package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Campaign Status" values which can be assigned to a {@link Campaign} in
 * Campaign update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum CampaignStatus implements ToStringReturnsEnumCode {

	WORK_IN_PROGRESS("W"), LIVE("L"), WAITING_FOR_APPROVAL("P"), ORDERED("O"), COMPLETED("C"), RESERVED("R"), REJECTED(
			"J"), CANCELLED("X"), SUSPENDED("S"), ARCHIVED("A"), RLC_DECLINE("D"), TEMPLATE("T");

	@Getter
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
		return EnumUtils.fromString(code, values(), CampaignStatus.class.getSimpleName());
	}
}