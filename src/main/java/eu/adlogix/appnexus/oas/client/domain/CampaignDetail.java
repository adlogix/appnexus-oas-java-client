package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;

/**
 * Contains Campaign Detail Report containing many values including daily
 * Delivery of the Campaign for a given time range
 */
@Data
public class CampaignDetail {

	/**
	 * Campaign Detail Report row containing daily Delivery of the Campaign
	 */
	@AllArgsConstructor
	@Data
	public static class CampaignDetailDeliveryHistoryRow {
		private final DateTime date;
		private final long impressions;
		private final long clicks;
	}

	private final List<CampaignDetailDeliveryHistoryRow> deliveryHistoryRows = Lists.newArrayList();

	public void addCampaignDetailDeliveryHistoryRow(CampaignDetailDeliveryHistoryRow row) {
		deliveryHistoryRows.add(row);
	}
}
