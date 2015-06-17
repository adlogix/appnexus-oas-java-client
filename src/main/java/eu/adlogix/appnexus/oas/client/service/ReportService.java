package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.util.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.util.ValidatorUtils.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.collections.Lists;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.domain.CampaignDetail;
import eu.adlogix.appnexus.oas.client.domain.CampaignDetailDeliveryHistoryRow;
import eu.adlogix.appnexus.oas.client.domain.PageAtPositionDeliveryInformationRow;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

public class ReportService extends AbstractXaxisService {

	protected ReportService(Properties credentials, XaxisApiService apiService, CertificateManager certificateManager) {
		super(credentials, apiService, certificateManager);
	}

	protected ReportService(Properties credentials) {
		super(credentials);
	}

	protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(OAS_DATE_FORMAT);
	private static final String CUSTOMREPORT_TAG = "CustomReport";

	private final XmlRequestGenerator inventoryReportRequestGenerator = new XmlRequestGenerator("inventory-report");
	private final XmlRequestGenerator campaignDetailDeliveryGenerator = new XmlRequestGenerator("read-campaign-live-delivery-report");

	// private final XmlRequestGenerator trafficReportsRequestGenerator = new
	// XmlRequestGenerator("read-campaigndelivery-positionatpage");

	public List<PageAtPositionDeliveryInformationRow> getPageAtPositionDeliveryInformation(final DateTime startDate, final DateTime endDate) {

		checkNotNull(startDate, "startDate");
		checkNotNull(endDate, "endDate");

		final List<PageAtPositionDeliveryInformationRow> histoStats = new ArrayList<PageAtPositionDeliveryInformationRow>();

		@SuppressWarnings("serial")
		final HashMap<String, Object> datesParams = new HashMap<String, Object>() {
			{
				put("startDate", DATE_FORMATTER.print(startDate));
				put("endDate", DATE_FORMATTER.print(endDate));
			}
		};

		ResponseElementHandler inventoryReportResponseElementHandler = new ResponseElementHandler() {
			public final void processElement(final ResponseElement element) {
				final DateTime date = DATE_FORMATTER.parseDateTime(element.getChild("Date"));
				final String pageUrl = element.getChild("Page");
				final String positionName = element.getChild("Position");
				final long impressions = Long.parseLong(element.getChild("Impressions"));
				final long clicks = Long.parseLong(element.getChild("Clicks"));
				histoStats.add(new PageAtPositionDeliveryInformationRow(date, pageUrl, positionName, impressions, clicks));
			}
		};

		performPagedRequest(inventoryReportRequestGenerator, datesParams, CUSTOMREPORT_TAG, "//row", inventoryReportResponseElementHandler);

		return histoStats;
	}

	public CampaignDetail getCampaignDetail(final String oasCampaignId,
			final DateTime startDate, final DateTime endDate) {

		checkNotEmpty(oasCampaignId, "oasCampaignId");
		checkNotNull(startDate, "startDate");
		checkNotNull(endDate, "endDate");

		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("campaignId", oasCampaignId);
				put("startDate", DATE_FORMATTER.print(startDate));
				put("endDate", DATE_FORMATTER.print(endDate));
			}
		};

		final String request = campaignDetailDeliveryGenerator.generateRequest(parameters);

		final String response = performRequest(request);
		final ResponseParser parser = new ResponseParser(response);

		final List<CampaignDetailDeliveryHistoryRow> deliveryHistoryRows = Lists.newArrayList();

		final String deliveryHistory = "//reportTable[@name='CampaignDetailDeliveryHistory']/row";
		parser.forEachElement(deliveryHistory, new ResponseElementHandler() {

			public final void processElement(final ResponseElement element) {
				final CampaignDetailDeliveryHistoryRow row = new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime(element.getChild("Date")), Long.parseLong(element.getChild("Impressions")), Long.parseLong(element.getChild("Clickthrus")));
				deliveryHistoryRows.add(row);
			}
		});
		return new CampaignDetail(deliveryHistoryRows);
	}
}
