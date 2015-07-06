package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MobileCampaignTargeting extends AbstractCampaignTargeting {

	public MobileCampaignTargeting(TargetingCode code) {
		super(code);
	}

	@Override
	protected List<TargetingCode> getValidTargetingCodes() {
		return TargetingCode.getCodesForGroup(TargetGroup.MOBILE);
	}

}
