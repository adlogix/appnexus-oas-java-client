package eu.adlogix.appnexus.oas.client.domain;

import static org.testng.AssertJUnit.assertEquals;

import java.util.Arrays;

import org.testng.annotations.Test;

public class StatefulDomainManagerTest {

	@Test
	public final void getModifiedObject_ObjectModified_ReturnObjectWithModifiedAttributes() {

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("insertion_order_1");
		insertionOrder.setDescription("test");
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_1", "campaign_2" }));
		InsertionOrder updatedInsertionOrder =new StatefulDomainManager().getModifiedObject(insertionOrder);
		assertEquals("insertion_order_1", updatedInsertionOrder.getId());
		assertEquals("test", updatedInsertionOrder.getDescription());
		assertEquals(2, updatedInsertionOrder.getCampaignIds().size());
	}

	@Test
	public final void getModifiedObject_ObjectResetAndModified_ReturnObjectWithOnlyModifiedAttributes() {

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("insertion_order_1");
		insertionOrder.setDescription("test");
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_1", "campaign_2" }));
		insertionOrder.resetModifiedAttributes();
		insertionOrder.setBookedClicks(1000l);
		InsertionOrder updatedInsertionOrder = new StatefulDomainManager().getModifiedObject(insertionOrder);
		assertEquals("insertion_order_1", updatedInsertionOrder.getId());
		assertEquals(null, updatedInsertionOrder.getDescription());
		assertEquals(null, updatedInsertionOrder.getCampaignIds());
		assertEquals(1000l, updatedInsertionOrder.getBookedClicks().longValue());
	}

	@Test
	public final void getModifiedObject_ObjectNotModified_ReturnEmptyObject() {

		InsertionOrder insertionOrder = new InsertionOrder();
		InsertionOrder updatedInsertionOrder = new StatefulDomainManager().getModifiedObject(insertionOrder);
		assertEquals(null, updatedInsertionOrder.getId());
		assertEquals(null, updatedInsertionOrder.getDescription());
		assertEquals(null, updatedInsertionOrder.getCampaignIds());

	}

}
