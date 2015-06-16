package eu.adlogix.appnexus.oas.client.domain;

import org.joda.time.DateTime;

public class PageAtPositionDeliveryInformationRow {

	private final DateTime date;
	private final String pageUrl;
	private final String positionName;
	private final long impressions;
	private final long clicks;

	public PageAtPositionDeliveryInformationRow(final DateTime date, final String pageUrl, final String positionName,
			final long impressions, final long clicks) {
		this.date = date;
		this.pageUrl = pageUrl;
		this.positionName = positionName;
		this.impressions = impressions;
		this.clicks = clicks;
	}

	public DateTime getDate() {
		return date;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public String getPositionName() {
		return positionName;
	}

	public long getImpressions() {
		return impressions;
	}

	public long getClicks() {
		return clicks;
	}
}
