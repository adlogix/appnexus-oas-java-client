package eu.adlogix.appnexus.oas.client.runner;

import java.util.List;

import eu.adlogix.appnexus.oas.client.domain.TargetingCodeData;
import eu.adlogix.appnexus.oas.client.domain.enums.TargetingCode;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.service.TargetingService;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class GetTargetingCodeDataRunner {

	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		TargetingService service = factory.getTargetingService();

		List<TargetingCodeData> list = service.getTargetingCodeDataLists(TargetingCode.DEVICE_GROUP);
		System.out.println(list);
	}
}
