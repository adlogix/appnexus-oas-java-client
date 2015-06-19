package eu.adlogix.appnexus.oas.client.domain;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import static org.testng.Assert.assertEquals;

public class CampaignDeliveryByPageAndPositionTest {
	@Test
	public void mapMethods_MultiplePagesAndPositions_CorrectlyMapped() {
		CampaignDeliveryByPageAndPosition deliveryByPageAndPosition = new CampaignDeliveryByPageAndPosition();
		final CampaignDeliveryByPageAndPosition.Row rowPg1Pos1_1 = new CampaignDeliveryByPageAndPosition.Row("Page1", "Position1", 111, 11);
		final CampaignDeliveryByPageAndPosition.Row rowPg1Pos1_2 = new CampaignDeliveryByPageAndPosition.Row("Page1", "Position1", 112, 11);
		final CampaignDeliveryByPageAndPosition.Row rowPg1Pos2 = new CampaignDeliveryByPageAndPosition.Row("Page1", "Position2", 120, 12);
		final CampaignDeliveryByPageAndPosition.Row rowPg2Pos1 = new CampaignDeliveryByPageAndPosition.Row("Page2", "Position1", 210, 21);
		final CampaignDeliveryByPageAndPosition.Row rowPg2Pos2 = new CampaignDeliveryByPageAndPosition.Row("Page2", "Position2", 220, 22);

		deliveryByPageAndPosition.addRow(rowPg1Pos1_1);
		deliveryByPageAndPosition.addRow(rowPg1Pos1_2);
		deliveryByPageAndPosition.addRow(rowPg1Pos2);
		deliveryByPageAndPosition.addRow(rowPg2Pos1);
		deliveryByPageAndPosition.addRow(rowPg2Pos2);

		assertEquals(deliveryByPageAndPosition.getRows(), Lists.newArrayList(rowPg1Pos1_1, rowPg1Pos1_2, rowPg1Pos2, rowPg2Pos1, rowPg2Pos2));

		assertEquals(deliveryByPageAndPosition.getAllPages(), Lists.newArrayList("Page1", "Page2"));
		assertEquals(deliveryByPageAndPosition.getAllPositionsOfPage("Page1"), Sets.newHashSet("Position1", "Position2"));
		assertEquals(deliveryByPageAndPosition.getAllPositionsOfPage("Page2"), Sets.newHashSet("Position1", "Position2"));

		assertEquals(deliveryByPageAndPosition.getRowsByPageAndPosition("Page1", "Position1"), Lists.newArrayList(rowPg1Pos1_1, rowPg1Pos1_2));
		assertEquals(deliveryByPageAndPosition.getRowsByPageAndPosition("Page2", "Position2"), Lists.newArrayList(rowPg2Pos2));
	}
}
