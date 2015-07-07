package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SegmentType {

	ANY("A"), ALL("L");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

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
