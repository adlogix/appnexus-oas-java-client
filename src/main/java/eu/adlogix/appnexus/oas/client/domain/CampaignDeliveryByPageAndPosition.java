package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CampaignDeliveryByPageAndPosition {

	private final String pageUrl;
	private final String positionName;
	private final long impressions;
	private final long clicks;

}
