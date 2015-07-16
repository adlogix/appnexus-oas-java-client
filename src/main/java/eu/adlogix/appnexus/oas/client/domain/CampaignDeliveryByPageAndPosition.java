package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import com.google.common.collect.Lists;

/**
 * Contains Campaign Delivery By Page and Position for a given time range
 */
public class CampaignDeliveryByPageAndPosition {

	/**
	 * Contains Delivery By Page and Position
	 */
	@AllArgsConstructor
	@Data
	public static class Row {
		private final String pageUrl;
		private final String positionName;
		private final long impressions;
		private final long clicks;
	}

	@Getter
	private final List<Row> rows = Lists.newArrayList();

	private CampaignDeliveryByPageAndPositionIndex index = new CampaignDeliveryByPageAndPositionIndex();

	public void addRow(Row row) {
		rows.add(row);
		index.addRow(row);
	}

	public Collection<String> getAllPages() {
		return index.getAllPages();
	}

	public Collection<String> getAllPositionsOfPage(final String pageUrl) {
		return index.getAllPositionsOfPage(pageUrl);
	}

	public List<Row> getRowsByPageAndPosition(final String pageUrl, final String positionName) {
		return index.getRowsByPageAndPosition(pageUrl, positionName);
	}


}
