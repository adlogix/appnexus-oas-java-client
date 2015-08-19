package eu.adlogix.appnexus.oas.client.service;

import org.joda.time.DateTime;

import eu.adlogix.appnexus.oas.client.domain.CampaignDeliveryByPageAndPosition;
import eu.adlogix.appnexus.oas.client.domain.CampaignDetail;
import eu.adlogix.appnexus.oas.client.domain.PageAtPositionDeliveryInformation;

/**
 * Service to invoke regarding Reports including Inventory reports in different
 * levels
 */
public interface ReportService {

	/**
	 * Get Delivery details of the Network for a given time range by date
	 * 
	 * @param startDate
	 *            Start {@link DateTime} of time range
	 * @param endDate
	 *            End {@link DateTime} of time range
	 * @return {@link PageAtPositionDeliveryInformation}
	 * @see {@link PageAtPositionDeliveryInformation#getRows()}
	 */
	public PageAtPositionDeliveryInformation getPageAtPositionDeliveryInformation(final DateTime startDate,
			final DateTime endDate);

	/**
	 * Get Campaign Detail Report containing many values including daily
	 * Delivery of the Campaign for a given time range
	 * 
	 * @param campaignId
	 *            OAS Campaign ID
	 * @param startDate
	 *            Start {@link DateTime} of time range
	 * @param endDate
	 *            End {@link DateTime} of time range
	 * @return {@link CampaignDetail}
	 * @see {@link CampaignDetail#getDeliveryHistoryRows()}
	 */
	public CampaignDetail getCampaignDetail(final String campaignId, final DateTime startDate, final DateTime endDate);

	/**
	 * Get Campaign Delivery By Page and Position for a given day
	 * 
	 * @param campaignId
	 *            OAS Campaign ID
	 * @param date
	 *            {@link DateTime} which the statistics are retrieved
	 * @return {@link CampaignDeliveryByPageAndPosition}
	 * @see {@link CampaignDeliveryByPageAndPosition#getRows()}
	 * @see {@link CampaignDeliveryByPageAndPosition#getAllPages()}
	 * @see {@link CampaignDeliveryByPageAndPosition#getRowsByPageAndPosition(String, String)}
	 */
	public CampaignDeliveryByPageAndPosition getCampaignDeliveryByPageAndPosition(final String campaignId,
			final DateTime date);

	/**
	 * Get Campaign Delivery By Page and Position for a given time range
	 * 
	 * @param campaignId
	 *            OAS Campaign ID
	 * @param startDate
	 *            Start {@link DateTime} of time range
	 * @param endDate
	 *            End {@link DateTime} of time range
	 * @return {@link CampaignDeliveryByPageAndPosition}
	 * @see {@link CampaignDeliveryByPageAndPosition#getRows()}
	 * @see {@link CampaignDeliveryByPageAndPosition#getAllPages()}
	 * @see {@link CampaignDeliveryByPageAndPosition#getRowsByPageAndPosition(String, String)}
	 */
	public CampaignDeliveryByPageAndPosition getCampaignDeliveryByPageAndPosition(final String campaignId,
			final DateTime startDate, final DateTime endDate);

}
