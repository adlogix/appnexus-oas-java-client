package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

public class GeneralCampaignTargeting extends AbstractExcludableCampaignTargeting {

	public GeneralCampaignTargeting() {
		super();
	}

	public GeneralCampaignTargeting(TargetingCode code) {
		super(code);
	}

	@Override
	protected List<TargetingCode> getValidTargetingCodes() {
		return TargetingCode.getCodesForGroup(TargetGroup.GENERAL);
	}

}
