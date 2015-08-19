package eu.adlogix.appnexus.oas.client.runner;

import java.util.Arrays;

import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;
import eu.adlogix.appnexus.oas.client.service.InsertionOrderService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class AddInsertionOrderRunner {

	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		InsertionOrderService service = factory.getInsertionOrderService();

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder_01");
		insertionOrder.setAdvertiserId("test_advertiser");
		insertionOrder.setDescription("test_description");
		insertionOrder.setBookedClicks(2500l);
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_01", "campaign_02" }));
		service.add(insertionOrder);
	}
}
