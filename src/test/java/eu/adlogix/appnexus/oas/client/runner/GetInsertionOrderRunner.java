package eu.adlogix.appnexus.oas.client.runner;

import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;
import eu.adlogix.appnexus.oas.client.service.InsertionOrderService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class GetInsertionOrderRunner {

	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		InsertionOrderService service = factory.getInsertionOrderService();

		InsertionOrder insertionOrder = service.getInsertionOrderById("test_insertionorder_01");
		System.out.println("Id:" + insertionOrder.getId());
		System.out.println("Description:" + insertionOrder.getDescription());
	}
}
