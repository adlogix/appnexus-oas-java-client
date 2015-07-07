package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.NoArgsConstructor;

/**
 * Used when setting and reading {@link Campaign#getMobileTargeting()} these
 * will be injected as {@link MobileTargetingGroup#getTargetings()}.
 * {@link TargetingCode}s related to this type is any code with
 * {@link TargetGroup#MOBILE}
 * 
 * @see AbstractCampaignTargeting#getValues()
 * @see AbstractCampaignTargeting#getCode()
 * @see TargetingCode
 */
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
