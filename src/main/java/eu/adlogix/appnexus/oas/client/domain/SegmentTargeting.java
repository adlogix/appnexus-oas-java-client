package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.Getter;

@Getter
public class SegmentTargeting extends StatefulDomain {

	private Boolean exclude;
	private List<String> values;
	private String segmentClusterMatch;

	public void setExclude(Boolean exclude) {
		this.exclude = exclude;
		setModifiedFlag("exclude");
	}

	public void setValues(List<String> values) {
		this.values = values;
		setModifiedFlag("values");
	}

	public void setSegmentClusterMatch(String segmentClusterMatch) {
		this.segmentClusterMatch = segmentClusterMatch;
		setModifiedFlag("segmentClusterMatch");
	}

	/**
	 * Resets the modified flags.The {@link SegmentTargeting} will be considered
	 * as an unmodified {@link SegmentTargeting} after calling this method.
	 */
	public void resetModifiedFlags() {
		super.resetModifiedFlags();
	}

	/**
	 * Returns a new {@link SegmentTargeting} object with only the modified
	 * attribute values.
	 */
	public SegmentTargeting getSegmentTargetingWithModifiedAttributes() {
		return super.getObjectWithModifiedAttributes();
	}

}
