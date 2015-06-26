package eu.adlogix.appnexus.oas.client.service;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.adlogix.appnexus.oas.client.domain.CampaignDeliveryByPageAndPosition;
import eu.adlogix.appnexus.oas.client.domain.CampaignDetail;
import eu.adlogix.appnexus.oas.client.domain.CampaignDetail.CampaignDetailDeliveryHistoryRow;
import eu.adlogix.appnexus.oas.client.domain.PageAtPositionDeliveryInformation;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

/**
 * Service to invoke regarding Reports including Inventory reports in different
 * levels
 */
public class ReportService extends AbstractOasService {

	protected ReportService(OasApiService apiService) {
		super(apiService);
	}

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(OAS_DATE_FORMAT);
	private static final String CUSTOMREPORT_TAG = "CustomReport";

	private final XmlRequestGenerator inventoryReportRequestGenerator = new XmlRequestGenerator("inventory-report");
	private final XmlRequestGenerator campaignDetailDeliveryGenerator = new XmlRequestGenerator("read-campaign-live-delivery-report");
	private final XmlRequestGenerator trafficReportsRequestGenerator = new XmlRequestGenerator("read-campaigndelivery-positionatpage");

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
			final DateTime endDate) {

		checkNotNull(startDate, "startDate");
		checkNotNull(endDate, "endDate");

		@SuppressWarnings("serial")
		final HashMap<String, Object> datesParams = new HashMap<String, Object>() {
			{
				put("startDate", DATE_FORMATTER.print(startDate));
				put("endDate", DATE_FORMATTER.print(endDate));
			}
		};

		final PageAtPositionDeliveryInformation histoStats = new PageAtPositionDeliveryInformation();

		ResponseElementHandler inventoryReportResponseElementHandler = new ResponseElementHandler() {
			public final void processElement(final ResponseElement element) {
				final DateTime date = DATE_FORMATTER.parseDateTime(element.getChild("Date"));
				final String pageUrl = element.getChild("Page");
				final String positionName = element.getChild("Position");
				final long impressions = Long.parseLong(element.getChild("Impressions"));
				final long clicks = Long.parseLong(element.getChild("Clicks"));
				histoStats.addRow(new PageAtPositionDeliveryInformation.Row(date, pageUrl, positionName, impressions, clicks));
			}
		};

		performPagedRequest(inventoryReportRequestGenerator, datesParams, CUSTOMREPORT_TAG, "//row", inventoryReportResponseElementHandler);

		return histoStats;
	}

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
	public CampaignDetail getCampaignDetail(final String campaignId, final DateTime startDate, final DateTime endDate) {

		checkNotEmpty(campaignId, "oasCampaignId");
		checkNotNull(startDate, "startDate");
		checkNotNull(endDate, "endDate");

		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("campaignId", campaignId);
				put("startDate", DATE_FORMATTER.print(startDate));
				put("endDate", DATE_FORMATTER.print(endDate));
			}
		};

		final ResponseParser parser = performRequest(campaignDetailDeliveryGenerator, parameters);

		final CampaignDetail detail = new CampaignDetail();

		final String deliveryHistory = "//reportTable[@name='CampaignDetailDeliveryHistory']/row";
		parser.forEachElement(deliveryHistory, new ResponseElementHandler() {

			public final void processElement(final ResponseElement element) {
				final CampaignDetailDeliveryHistoryRow row = new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime(element.getChild("Date")), Long.parseLong(element.getChild("Impressions")), Long.parseLong(element.getChild("Clickthrus")));
				detail.addCampaignDetailDeliveryHistoryRow(row);
			}
		});
		return detail;
	}

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
			final DateTime date) {
		return getCampaignDeliveryByPageAndPosition(campaignId, date, date);
	}

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
			final DateTime startDate, final DateTime endDate) {

		checkNotEmpty(campaignId, "campaignId");
		checkNotNull(startDate, "startDate");
		checkNotNull(endDate, "endDate");

		@SuppressWarnings("serial")
		final HashMap<String, Object> requestParameters = new HashMap<String, Object>() {
			{
				put("startFetchDate", DATE_FORMATTER.print(startDate));
				put("endFetchDate", DATE_FORMATTER.print(endDate));
				put("campaignId", campaignId);
			}
		};

		final ResponseParser parser = performRequest(trafficReportsRequestGenerator, requestParameters, true);

		final CampaignDeliveryByPageAndPosition delivery = new CampaignDeliveryByPageAndPosition();

		parser.forEachElement("//reportTable[@name='Delivery.Campaign.Base.T280.01']/row", new ResponseElementHandler() {
			public final void processElement(final ResponseElement element) {
				final String pageUrl = element.getChild("Page");
				final String positionName = element.getChild("Position");
				final long impressions = Long.parseLong(element.getChild("Impressions"));
				final long clicks = Long.parseLong(element.getChild("Clicks"));

				CampaignDeliveryByPageAndPosition.Row deliveryByPageAndPositionRow = new CampaignDeliveryByPageAndPosition.Row(pageUrl, positionName, impressions, clicks);
				delivery.addRow(deliveryByPageAndPositionRow);
			}
		});

		return delivery;
	}
}
