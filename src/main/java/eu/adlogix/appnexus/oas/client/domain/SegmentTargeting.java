package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class SegmentTargeting extends StatefulDomain {

	private Boolean exclude;
	private List<String> values;
	private String segmentClusterMatch;

	public void setExclude(Boolean exclude) {
		this.exclude = exclude;
		addModifiedAttribute("exclude");
	}

	public void setValues(List<String> values) {
		this.values = Collections.unmodifiableList(values);
		addModifiedAttribute("values");
	}

	public void setSegmentClusterMatch(String segmentClusterMatch) {
		this.segmentClusterMatch = segmentClusterMatch;
		addModifiedAttribute("segmentClusterMatch");
	}

}
