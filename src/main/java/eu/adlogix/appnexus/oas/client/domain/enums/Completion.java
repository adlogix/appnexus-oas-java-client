package eu.adlogix.appnexus.oas.client.domain.enums;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import lombok.AllArgsConstructor;

/**
 * "Completion" values which can be assigned to a {@link Campaign} in Campaign
 * creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum Completion {

	SOONEST("S"), LATEST("L"), END_DATE("E"), IMPRESSION("I"), CLICK_THROUGH("C"),
	UNIQUE_VIEWERS("U");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

	public static Completion fromString(String code) {
		if (code == null)
			return null;

		for (Completion completion : Completion.values()) {
			if (code.equalsIgnoreCase(completion.code)) {
				return completion;
			}
		}
		throw new IllegalArgumentException("Invalid Completion Code:" + code);
	}

}
