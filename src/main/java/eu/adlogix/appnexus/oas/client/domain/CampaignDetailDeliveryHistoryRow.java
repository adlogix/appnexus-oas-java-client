package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.joda.time.DateTime;

@AllArgsConstructor
@Data
public class CampaignDetailDeliveryHistoryRow {
	private final DateTime date;
	private final long impressions;
	private final long clicks;
}
