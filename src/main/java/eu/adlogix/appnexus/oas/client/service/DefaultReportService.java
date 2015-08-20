package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.DATE_FORMATTER;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import eu.adlogix.appnexus.oas.client.domain.CampaignDeliveryByPageAndPosition;
import eu.adlogix.appnexus.oas.client.domain.CampaignDetail;
import eu.adlogix.appnexus.oas.client.domain.CampaignDetail.CampaignDetailDeliveryHistoryRow;
import eu.adlogix.appnexus.oas.client.domain.PageAtPositionDeliveryInformation;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Default implementation of {@link ReportService} which provides functions for
 * retrieving Delivery Reports
 */
public class DefaultReportService extends AbstractOasService implements ReportService {


	private static final String CUSTOMREPORT_TAG = "CustomReport";

	private final XmlRequestGenerator inventoryReportRequestGenerator = new XmlRequestGenerator("inventory-report");
	private final XmlRequestGenerator campaignDetailDeliveryGenerator = new XmlRequestGenerator("read-campaign-live-delivery-report");
	private final XmlRequestGenerator trafficReportsRequestGenerator = new XmlRequestGenerator("read-campaigndelivery-positionatpage");

	public DefaultReportService(OasApiService apiService) {
		super(apiService);
	}

	@Override
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

	@Override
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

	@Override
	public CampaignDeliveryByPageAndPosition getCampaignDeliveryByPageAndPosition(final String campaignId,
			final DateTime date) {
		return getCampaignDeliveryByPageAndPosition(campaignId, date, date);
	}

	@Override
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
