package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

/**
 * "Segment Type" values which can be assigned to {@link SegmentTargeting} in
 * Campaign creation, update and retrieval operations. The OAS code is
 * accessible via {@link #toString()}
 */
@AllArgsConstructor
public enum SegmentType {

	ANY("A"), ALL("L");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link SegmentType} object which corresponds to the given OAS
	 * code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link SegmentType} object
	 */
	public static SegmentType fromString(String code) {
		if (code == null)
			return null;

		for (SegmentType segmentType : SegmentType.values()) {
			if (code.equalsIgnoreCase(segmentType.code)) {
				return segmentType;
			}
		}
		throw new IllegalArgumentException("Invalid SegmentType Code:" + code);
	}

}

