package eu.adlogix.appnexus.oas.client.runner;

import java.util.List;

import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.service.NetworkService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class GetAllPagesRunner {
	public static void main(String[] args) {
		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		NetworkService service = factory.getNetworkService();

		List<Page> list = service.getAllPagesWithoutSiteDetails();
		System.out.println(list);
	}
}
