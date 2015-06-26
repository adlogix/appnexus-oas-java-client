package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.Data;

@Data
public class SegmentTargeting {

	private Boolean exculde;
	private List<String> values;
	private String segmentClusterMatch;

}
