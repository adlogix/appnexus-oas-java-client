package eu.adlogix.appnexus.oas.client.runner;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.service.CampaignService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class UpdateCampaignRunner {
	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		CampaignService service = factory.getCampaignService();

		Campaign campaign = service.getCampaignById("test");
		System.out.println("imps before update:" + campaign.getImpressions());

		campaign.setImpressions(12500l);
		service.updateCampaign(campaign);

		Campaign updatedCampaign = service.getCampaignById("test");
		System.out.println("imps after update:" + updatedCampaign.getImpressions());

	}
}
