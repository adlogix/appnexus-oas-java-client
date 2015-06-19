package eu.adlogix.appnexus.oas.client.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import org.testng.collections.Maps;

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
	private final Map<String, Map<String, List<Row>>> rowsByPageAndPosition = Maps.newHashMap();

	public void addRow(Row row) {
		rows.add(row);
		addRowToMap(row);
	}

	private void addRowToMap(final Row row) {
		// Get Positions map for a single Page
		final Map<String, List<Row>> rowsByPosition = rowsByPageAndPosition.containsKey(row.pageUrl) ? rowsByPageAndPosition.get(row.pageUrl)
				: new HashMap<String, List<Row>>();

		// Get Rows for a Page and position
		final List<Row> rows = rowsByPosition.containsKey(row.positionName) ? rowsByPosition.get(row.positionName)
				: new ArrayList<CampaignDeliveryByPageAndPosition.Row>();

		rows.add(row);

		rowsByPosition.put(row.positionName, rows);
		rowsByPageAndPosition.put(row.pageUrl, rowsByPosition);
	}

	public Collection<String> getAllPages() {
		return rowsByPageAndPosition.keySet();
	}

	public Collection<String> getAllPositionsOfPage(final String pageUrl) {
		return rowsByPageAndPosition.containsKey(pageUrl) ? rowsByPageAndPosition.get(pageUrl).keySet() : null;
	}

	public List<Row> getRowsByPageAndPosition(final String pageUrl, final String positionName) {
		return rowsByPageAndPosition.containsKey(pageUrl) ? getRowByPageAndPositionFromInnerMap(rowsByPageAndPosition.get(pageUrl), positionName)
				: null;
	}

	private List<Row> getRowByPageAndPositionFromInnerMap(final Map<String, List<Row>> positionMap,
			final String positionName) {
		return positionMap.containsKey(positionName) ? positionMap.get(positionName) : null;
	}
}
