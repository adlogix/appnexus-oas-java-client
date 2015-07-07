package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Used when setting and reading {@link Campaign#getMobileTargeting()}.
 * {@link TargetingCode}s related to {@link #setTargetings(List)} type is any
 * code with {@link TargetGroup#MOBILE}
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MobileTargetingGroup extends StatefulDomain implements CampaignTarget {
	/**
	 * Excludable flag for Targeting
	 */
	private Boolean excludeMobileDevice;

	/**
	 * Valid {@link MobileCampaignTargeting}s. Make sure {@link TargetingCode}s
	 * related to MobileCampaignTargetings types is any code with
	 * {@link TargetGroup#MOBILE}
	 */
	private List<MobileCampaignTargeting> targetings;

	@Override
	public TargetGroup getGroup() {
		return TargetGroup.MOBILE;
	}

	public void setExcludeMobileDevice(Boolean excludeMobileDevice) {
		this.excludeMobileDevice = excludeMobileDevice;
		addModifiedAttribute("excludeMobileDevice");
	}

	public void setTargetings(List<MobileCampaignTargeting> targetings) {
		this.targetings = Collections.unmodifiableList(targetings);
		addModifiedAttribute("targetings");
	}

}
