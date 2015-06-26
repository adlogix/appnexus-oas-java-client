package eu.adlogix.appnexus.oas.client.runner;

import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.service.AdvertiserService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class UpdateAdvertiserRunner {

	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		AdvertiserService service = factory.getAdvertiserService();

		Advertiser advertiser = service.getAdvertiserById("test_advertiser");
		advertiser.setOrganization("Adlogix");
		service.updateAdvertiser(advertiser);
	}
}
