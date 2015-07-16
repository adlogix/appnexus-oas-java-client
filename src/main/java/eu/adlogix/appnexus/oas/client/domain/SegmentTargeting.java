package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

/**
 * Domain class which represents OAS Segment Targeting. This can be used in
 * Campaign creation, update and retrieval operations.
 */
@Getter
public class SegmentTargeting extends StatefulDomain {

	private Boolean exclude;
	private List<String> values;
	private SegmentType segmentType;

	public void setExclude(Boolean exclude) {
		this.exclude = exclude;
		addModifiedAttribute("exclude");
	}

	public void setValues(List<String> values) {
		this.values = Collections.unmodifiableList(values);
		addModifiedAttribute("values");
	}

	public void setSegmentType(SegmentType segmentType) {
		this.segmentType = segmentType;
		addModifiedAttribute("segmentType");
	}
}
