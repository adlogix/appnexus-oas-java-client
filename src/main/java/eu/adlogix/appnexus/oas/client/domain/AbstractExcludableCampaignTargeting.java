package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public abstract class AbstractExcludableCampaignTargeting extends AbstractCampaignTargeting {

	/**
	 * Excludable flag for Targeting
	 */
	private Boolean exclude;

	public AbstractExcludableCampaignTargeting(TargetingCode code) {
		super(code);
	}

	public void setExclude(Boolean exclude) {
		this.exclude = exclude;
		addModifiedAttribute("exclude");
	}

}
