package eu.adlogix.appnexus.oas.client.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import eu.adlogix.appnexus.oas.client.domain.CampaignDeliveryByPageAndPosition.Row;

class CampaignDeliveryByPageAndPositionIndex {

	private Map<String, Map<String, List<Row>>> rowsIndexedByPageAndPosition = Maps.newHashMap();

	void addRow(final Row row) {

		// Get Positions map for a single Page
		final Map<String, List<Row>> rowsByPosition = rowsIndexedByPageAndPosition.containsKey(row.getPageUrl()) ? rowsIndexedByPageAndPosition.get(row.getPageUrl())
				: new HashMap<String, List<Row>>();

		// Get Rows for a Page and position
		final List<Row> rows = rowsByPosition.containsKey(row.getPositionName()) ? rowsByPosition.get(row.getPositionName())
				: new ArrayList<CampaignDeliveryByPageAndPosition.Row>();

		rows.add(row);

		rowsByPosition.put(row.getPositionName(), rows);
		rowsIndexedByPageAndPosition.put(row.getPageUrl(), rowsByPosition);
	}

	Collection<String> getAllPages() {
		return rowsIndexedByPageAndPosition.keySet();
	}

	Collection<String> getAllPositionsOfPage(final String pageUrl) {
		return rowsIndexedByPageAndPosition.containsKey(pageUrl) ? rowsIndexedByPageAndPosition.get(pageUrl).keySet()
				: null;
	}

	List<Row> getRowsByPageAndPosition(final String pageUrl, final String positionName) {
		return rowsIndexedByPageAndPosition.containsKey(pageUrl) ? getRowByPageAndPositionFromInnerMap(rowsIndexedByPageAndPosition.get(pageUrl), positionName)
				: null;
	}

	List<Row> getRowByPageAndPositionFromInnerMap(final Map<String, List<Row>> positionMap,
			final String positionName) {
		return positionMap.containsKey(positionName) ? positionMap.get(positionName) : null;
	}
}