package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

public class ZoneCampaignTargeting extends AbstractCampaignTargeting {

	public ZoneCampaignTargeting() {
		super();
	}

	public ZoneCampaignTargeting(TargetingCode code) {
		super(code);
	}

	@Override
	protected List<TargetingCode> getValidTargetingCodes() {
		return TargetingCode.getCodesForGroup(TargetGroup.ZONE);
	}

}
