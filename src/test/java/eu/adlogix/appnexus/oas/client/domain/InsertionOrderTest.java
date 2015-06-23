package eu.adlogix.appnexus.oas.client.domain;

import static org.testng.AssertJUnit.assertEquals;

import java.util.Arrays;

import org.testng.annotations.Test;

public class InsertionOrderTest {

	@Test
	public final void getInsertionOrderWithModifiedAttributes_ObjectModified_ReturnIOWithModifiedAttributes() {

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("insertion_order_1");
		insertionOrder.setDescription("test");
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_1", "campaign_2" }));
		InsertionOrder updatedInsertionOrder = insertionOrder.getInsertionOrderWithModifiedAttributes();
		assertEquals("insertion_order_1", updatedInsertionOrder.getId());
		assertEquals("test", updatedInsertionOrder.getDescription());
		assertEquals(2, updatedInsertionOrder.getCampaignIds().size());
	}

	@Test
	public final void getInsertionOrderWithModifiedAttributes_ObjectResetAndModified_ReturnIOWithOnlyModifiedAttributes() {

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("insertion_order_1");
		insertionOrder.setDescription("test");
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_1", "campaign_2" }));
		insertionOrder.resetModifiedFlags();
		insertionOrder.setBookedClicks(1000l);
		InsertionOrder updatedInsertionOrder = insertionOrder.getInsertionOrderWithModifiedAttributes();
		assertEquals(null, updatedInsertionOrder.getId());
		assertEquals(null, updatedInsertionOrder.getDescription());
		assertEquals(null, updatedInsertionOrder.getCampaignIds());
		assertEquals(1000l, updatedInsertionOrder.getBookedClicks().longValue());
	}

	@Test
	public final void getInsertionOrderWithModifiedAttributes_ObjectNotModified_ReturnEmptyInsertionOrder() {

		InsertionOrder insertionOrder = new InsertionOrder();
		InsertionOrder updatedInsertionOrder = insertionOrder.getInsertionOrderWithModifiedAttributes();
		assertEquals(null, updatedInsertionOrder.getId());
		assertEquals(null, updatedInsertionOrder.getDescription());
		assertEquals(null, updatedInsertionOrder.getCampaignIds());

	}

}
