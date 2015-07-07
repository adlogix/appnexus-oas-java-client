package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.NoArgsConstructor;

/**
 * Used when setting and reading {@link Campaign#getTargetings()}.
 * {@link TargetingCode}s related to this type is any code with
 * {@link TargetGroup#GENERAL}
 * 
 * @see AbstractCampaignTargeting#getValues()
 * @see AbstractCampaignTargeting#getCode()
 * @see TargetingCode
 */
@NoArgsConstructor
public class GeneralCampaignTargeting extends AbstractExcludableCampaignTargeting {

	public GeneralCampaignTargeting(TargetingCode code) {
		super(code);
	}

	@Override
	protected List<TargetingCode> getValidTargetingCodes() {
		return TargetingCode.getCodesForGroup(TargetGroup.GENERAL);
	}

}
