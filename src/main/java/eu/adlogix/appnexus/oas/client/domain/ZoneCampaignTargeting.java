package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

/**
 * Used when setting and reading {@link Campaign#getZoneTargeting()}.
 * {@link TargetingCode} related to this type is {@link TargetingCode#ZONE}
 * 
 * @see AbstractCampaignTargeting#getValues()
 */
public class ZoneCampaignTargeting extends AbstractCampaignTargeting {

	public ZoneCampaignTargeting() {
		super(TargetingCode.ZONE);
	}

	@Override
	protected List<TargetingCode> getValidTargetingCodes() {
		return TargetingCode.getCodesForGroup(TargetGroup.ZONE);
	}

}
