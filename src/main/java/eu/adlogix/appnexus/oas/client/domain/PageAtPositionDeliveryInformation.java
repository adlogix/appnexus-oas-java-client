package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;

/**
 * Consists Delivery details of the Network for a given time range by date
 */
@Data
public class PageAtPositionDeliveryInformation {

	/**
	 * Consists stats of a Position of a page for at a date
	 */
	@Data
	@AllArgsConstructor
	public static class Row {
		private final DateTime date;
		private final String pageUrl;
		private final String positionName;
		private final long impressions;
		private final long clicks;
	}

	private final List<Row> rows = Lists.newArrayList();

	public void addRow(Row row) {
		rows.add(row);
	}
}
