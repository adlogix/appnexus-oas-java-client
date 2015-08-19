package eu.adlogix.appnexus.oas.client.runner;

import java.util.List;

import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.service.AdvertiserService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class GetAllAdvertisersRunner {

	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		AdvertiserService service = factory.getAdvertiserService();

		List<Advertiser> advertisersList = service.getAll();
		for (Advertiser advertiser : advertisersList) {
			System.out.println("Id:" + advertiser.getId() + " Organization:" + advertiser.getOrganization());
		}
	}
}
