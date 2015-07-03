package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ExcludableTargeting extends Targeting {

	private Boolean exclude;

	public ExcludableTargeting(TargetingCode code) {
		super(code);

		if (!code.isSupportingExcludeFlagForCampaigns()) {
			throw new IllegalArgumentException("ExcludableTargeting can only be created by for TargetingCodes which supports exclude flag. Passed TargetingCode: "
					+ code);
		}
	}

	public void setExclude(Boolean exclude) {
		this.exclude = exclude;
		addModifiedAttribute("exclude");
	}

}
