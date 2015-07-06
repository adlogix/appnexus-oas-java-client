package eu.adlogix.appnexus.oas.client.domain;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class AbstractCampaignTargetingTest {
	@Test
	public void validateTargetingCode_MobileTargetTypeForMobileCampaignTargeting_Success() {
		MobileCampaignTargeting deviceGroupTargeting = new MobileCampaignTargeting(TargetingCode.DEVICE_GROUP);
		deviceGroupTargeting.setValues(Arrays.asList(new String[] { "427", "429" }));
		MobileTargetingGroup mobileTargeting = new MobileTargetingGroup();
		mobileTargeting.setTargetings(Lists.newArrayList(deviceGroupTargeting));
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Targeting of Code BrowserV cannot be added to this list as only the following list is supported: \\[DeviceGroup\\]")
	public void validateTargetingCode_GeneralTargetTypeForMobileCampaignTargeting_Success() {
		MobileCampaignTargeting browserVersionTargeting = new MobileCampaignTargeting(TargetingCode.BROWSER_VERSIONS);
		browserVersionTargeting.setValues(Arrays.asList("firefox19", "opera12"));
		MobileTargetingGroup mobileTargeting = new MobileTargetingGroup();
		mobileTargeting.setTargetings(Lists.newArrayList(browserVersionTargeting));
	}
}
