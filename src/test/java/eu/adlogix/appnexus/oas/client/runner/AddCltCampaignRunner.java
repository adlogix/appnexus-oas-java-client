package eu.adlogix.appnexus.oas.client.runner;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.CampaignType;
import eu.adlogix.appnexus.oas.client.domain.Completion;
import eu.adlogix.appnexus.oas.client.domain.PaymentMethod;
import eu.adlogix.appnexus.oas.client.domain.Reach;
import eu.adlogix.appnexus.oas.client.domain.SmoothAsap;
import eu.adlogix.appnexus.oas.client.service.CampaignService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class AddCltCampaignRunner {
	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		CampaignService service = factory.getCampaignService();

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_2_clt");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setType(CampaignType.CLT);
		campaign.setCreativeTargetId("test_clt_gunith_1");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		service.addCampaign(campaign);
	}
}
