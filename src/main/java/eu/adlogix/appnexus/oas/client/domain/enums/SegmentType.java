package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Segment Type" values which can be assigned to {@link SegmentTargeting} in
 * Campaign creation, update and retrieval operations. The OAS code is
 * accessible via {@link #toString()}
 */
@AllArgsConstructor
public enum SegmentType implements ToStringReturnsEnumCode {

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
		return EnumUtils.fromString(code, values(), SegmentType.class.getSimpleName());
	}

}

