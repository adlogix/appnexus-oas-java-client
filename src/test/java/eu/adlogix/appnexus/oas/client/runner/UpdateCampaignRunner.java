package eu.adlogix.appnexus.oas.client.runner;

import java.util.Arrays;

import org.joda.time.LocalTime;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.service.CampaignService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class UpdateCampaignRunner {
	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		CampaignService service = factory.getCampaignService();

		Campaign campaign = service.getCampaignById("test_1");

		campaign.setImpressions(12500l);
		campaign.setAdvertiserId("UnitTestAdvertiser_OASUnitTestAdvertiser_EXT_ID");
		campaign.setName("test adlogix");
		campaign.setAgencyId("agency_01");
		campaign.setDescription("Description Updated");
		campaign.setProductId("default-product");
		campaign.setCampaignManager("test");
		campaign.setInternalQuickReport("short");
		campaign.setExternalQuickReport("to-date");
		campaign.setStartTime(new LocalTime(20, 30));
		campaign.setEndTime(new LocalTime(23, 30));
		campaign.setCompanionPositions(Arrays.asList(new String[] { "B/T" }));
		campaign.setPrimaryImpsPerVisitor(10l);
		campaign.setPrimaryFrequencyScope(1l);
		campaign.setPrimaryClicksPerVisitor(10l);
		campaign.setCpa(120.0);
		campaign.setFlatRate(10.0);
		campaign.setTax(50.0);
		campaign.setAgencyCommission(0.25);
		campaign.setPaymentMethod("B");
		campaign.setIsYieldManaged("Y");
		campaign.setBillTo("A");
		campaign.setCurrency("EUR");

		service.updateCampaign(campaign);

		Campaign updatedCampaign = service.getCampaignById("test");

	}
}
