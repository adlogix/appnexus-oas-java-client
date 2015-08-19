package eu.adlogix.appnexus.oas.client.runner;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.enums.Completion;
import eu.adlogix.appnexus.oas.client.domain.enums.PaymentMethod;
import eu.adlogix.appnexus.oas.client.domain.enums.Reach;
import eu.adlogix.appnexus.oas.client.domain.enums.SmoothAsap;
import eu.adlogix.appnexus.oas.client.service.CampaignService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class AddCampaignRunner {
	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		CampaignService service = factory.getCampaignService();

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_1");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		service.add(campaign);
	}
}
