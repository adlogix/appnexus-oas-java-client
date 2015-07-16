package eu.adlogix.appnexus.oas.client.runner;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Maps;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.CampaignTargetValues;
import eu.adlogix.appnexus.oas.client.domain.MobileTargetings;
import eu.adlogix.appnexus.oas.client.domain.enums.TargetingCode;
import eu.adlogix.appnexus.oas.client.service.CampaignService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class UpdateCampaignRunner {
	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		CampaignService service = factory.getCampaignService();

		Campaign campaign = service.getCampaignById("test_campaign_gunith_2_clt");

		// campaign.setImpressions(12500l);
		// campaign.setAdvertiserId("TestAdvertiser");
		// campaign.setName("test adlogix");
		// campaign.setAgencyId("agency1");
		// campaign.setDescription("Description Updated");
		// campaign.setProductId("TestProduct");
		// campaign.setCampaignManager("TestCampaignManager");
		// campaign.setInternalQuickReport("short");
		// campaign.setExternalQuickReport("to-date");
		// campaign.setStartTime(new LocalTime(20, 30));
		// campaign.setEndTime(new LocalTime(23, 30));
		// campaign.setCompanionPositions(Arrays.asList(new String[] { "B/T"
		// }));
		// campaign.setPrimaryImpsPerVisitor(10l);
		// campaign.setPrimaryFrequencyScope(1l);
		// campaign.setPrimaryClicksPerVisitor(10l);
		// campaign.setCpa(120.0);
		// campaign.setFlatRate(10.0);
		// campaign.setTax(50.0);
		// campaign.setAgencyCommission(0.25);
		// campaign.setPaymentMethod("B");
		// campaign.setIsYieldManaged("Y");
		// campaign.setBillTo("A");
		// campaign.setCurrency("EUR");

		// RdbTargeting rdbTargeting = new RdbTargeting();
		// rdbTargeting.setAgeExclude(true);
		// rdbTargeting.setAgeFrom(13);
		// rdbTargeting.setAgeTo(30);
		// rdbTargeting.setGenderExclude(true);
		// rdbTargeting.setGender("M");
		// rdbTargeting.setIncomeExclude(true);
		// rdbTargeting.setIncomeFrom(13l);
		// rdbTargeting.setIncomeTo(30l);
		// rdbTargeting.setSubscriberCodeExclude(true);
		// rdbTargeting.setSubscriberCode("TEST");
		// rdbTargeting.setPreferenceFlagsExclude(true);
		// rdbTargeting.setPreferenceFlags("012345678911");
		// campaign.setRdbTargeting(rdbTargeting);

		// campaign.setPageUrls(Lists.newArrayList("www.spon.de/sport/wm-spezial/center",
		// "www.testfz.com", "www.mkarlov.com/sports"));
		// campaign.setPageUrls(Arrays.asList(new String[0]));
		// campaign.setSectionIds(Lists.newArrayList("383section", "AASec",
		// "Adulte"));
		// campaign.setSectionIds(Arrays.asList(new String[0]));
//		campaign.setExcludedSiteIds(Lists.newArrayList("mkarlov.com", "apiSite2"));
//		campaign.setExcludedPageUrls(Lists.newArrayList("www.spon.de/sport/wm-spezial/center", "www.testfz.com", "www.mkarlov.com/sports"));
		// campaign.setExcludedSiteIds(Lists.newArrayList(Arrays.asList(new
		// String[0])));
		// campaign.setExcludedPageUrls(Lists.newArrayList(Arrays.asList(new
		// String[0])));
		
		// campaign.setCampaignGroupIds(Arrays.asList(new String[] {
		// "0521_AGEN313394_Campaig_010313_12948_152",
		// "055CASHME388747_Campaig_010313_12947_152" }));
		//campaign.setExternalUserIds(Lists.newArrayList("AAExt", "alaExt"));
		// campaign.setExternalUserIds(Arrays.asList(new String[0]));

		CampaignTargetValues deviceGroupTargeting = new CampaignTargetValues();
		deviceGroupTargeting.setValues(Arrays.asList(new String[] { "427", "429" }));
		Map<TargetingCode, CampaignTargetValues> mobileTargetings = Maps.newHashMap();
		mobileTargetings.put(TargetingCode.DEVICE_GROUP, deviceGroupTargeting);
		MobileTargetings mobileTargeting = new MobileTargetings();
		mobileTargeting.setExcludeMobileDevice(false);
		mobileTargeting.setTargetings(mobileTargetings);
		campaign.setMobileTargeting(mobileTargeting);

		service.updateCampaign(campaign);

		service.getCampaignById("test_campaign_gunith_2_clt");

	}
}
