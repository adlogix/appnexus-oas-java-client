package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Completion" values which can be assigned to a {@link Campaign} in Campaign
 * creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum Completion implements ToStringReturnsEnumCode {

	SOONEST("S"), LATEST("L"), END_DATE("E"), IMPRESSION("I"), CLICK_THROUGH("C"),
	UNIQUE_VIEWERS("U");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

	public static Completion fromString(String code) {
		return EnumUtils.fromString(code, values(), Completion.class.getSimpleName());
	}

}
