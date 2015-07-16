package eu.adlogix.appnexus.oas.client.domain;

import eu.adlogix.appnexus.oas.client.domain.enums.TargetingCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Used when setting and reading {@link Campaign#getTargetings()} mapped with
 * the related {@link TargetingCode}
 * 
 * @see CampaignTargetValues#getValues()
 * @see TargetingCode
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CampaignExcludableTargetValues extends CampaignTargetValues {

	/**
	 * Excludable flag for Targeting
	 */
	private Boolean exclude;

	public void setExclude(Boolean exclude) {
		this.exclude = exclude;
		addModifiedAttribute("exclude");
	}

	public boolean isSupportingExcludeFlag() {
		return true;
	}
}
