package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CampaignDetail {
	private final List<CampaignDetailDeliveryHistoryRow> deliveryHistoryRows;
}
