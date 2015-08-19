package eu.adlogix.appnexus.oas.client.runner;

import java.util.Arrays;

import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;
import eu.adlogix.appnexus.oas.client.service.InsertionOrderService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class UpdateInsertionOrderRunner {

	public static void main(String[] args) {
		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		InsertionOrderService service = factory.getInsertionOrderService();

		InsertionOrder insertionOrder = service.getById("test_insertionorder_01");

		insertionOrder.setBookedImpressions(5000l);
		insertionOrder.setBookedClicks(2500l);
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "test_campaign_055" }));

		service.update(insertionOrder);
	}
}
