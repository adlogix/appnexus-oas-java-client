package eu.adlogix.appnexus.oas.client.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.domain.PageAtPositionDeliveryInformationRow;
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

	public List<PageAtPositionDeliveryInformationRow> getPageAtPositionDeliveryInformation(final DateTime startDate, final DateTime endDate) {

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

}
