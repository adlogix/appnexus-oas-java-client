package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class SegmentTargeting extends StatefulDomain implements CampaignTarget {

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

	@Override
	public TargetGroup getGroup() {
		return TargetGroup.SEGMENT;
	}
}
