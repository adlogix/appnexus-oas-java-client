package eu.adlogix.appnexus.oas.client.runner;

import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.service.AdvertiserService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class AddAdvertiserRunner {

	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		AdvertiserService service = factory.getAdvertiserService();

		Advertiser advertiser = new Advertiser();
		advertiser.setId("test_advertiser");
		advertiser.setOrganization("test");
		service.addAdvertiser(advertiser);
	}
}
