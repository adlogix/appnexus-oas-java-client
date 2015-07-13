package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

/**
 * "Hour Of Day" values which can be assigned to a {@link Campaign} in Campaign
 * creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum HourOfDay {

	ZERO("00"),
	ONE("01"),
	TWO("02"),
	THREE("03"),
	FOUR("04"),
	FIVE("05"),
	SIX("06"),
	SEVEN("07"),
	EIGHT("08"),
	NINE("09"),
	TEN("10"),
	ELEVEN("11"),
	TWELVE("12"),
	THIRTEEN("13"),
	FOURTEEN("14"),
	FIFTEEN("15"),
	SIXTEEN("16"),
	SEVENTEEN("17"),
	EIGHTEEN("18"),
	NINETEEN("19"),
	TWENTY("20"),
	TWENTY_ONE("21"),
	TWENTY_TWO("22"),
	TWENTY_THREE("23");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link HourOfDay} object which corresponds to the given OAS
	 * code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link HourOfDay} object
	 */
	public static HourOfDay fromString(String code) {
		if (code == null)
			return null;

		for (HourOfDay hourOfDay : HourOfDay.values()) {
			if (code.equalsIgnoreCase(hourOfDay.code)) {
				return hourOfDay;
			}
		}
		throw new IllegalArgumentException("Invalid HourOfDay Code:" + code);
	}
}