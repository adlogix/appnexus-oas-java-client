package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class MobileTargetings extends StatefulDomain {
	/**
	 * Excludable flag for Targeting
	 */
	private Boolean excludeMobileDevice;

	/**
	 * Valid {@link MobileCampaignTargeting}s. Make sure {@link TargetingCode}s
	 * related to MobileCampaignTargetings types is any code with
	 * {@link TargetGroup#MOBILE}
	 */
	private Map<TargetingCode, CampaignTargetValues> targetings;

	public void setExcludeMobileDevice(Boolean excludeMobileDevice) {
		this.excludeMobileDevice = excludeMobileDevice;
		addModifiedAttribute("excludeMobileDevice");
	}

	public void setTargetings(Map<TargetingCode, CampaignTargetValues> targetings) {
		this.targetings = Collections.unmodifiableMap(targetings);
		addModifiedAttribute("targetings");
	}

	public void setDeviceGroupTargeting(CampaignTargetValues target) {
		addTargeting(TargetingCode.DEVICE_GROUP, target);
	}

	public CampaignTargetValues getDeviceGroupTargeting() {
		return targetings.get(TargetingCode.DEVICE_GROUP);
	}

	private void addTargeting(TargetingCode code, CampaignTargetValues target) {

		final Map<TargetingCode, CampaignTargetValues> targetings = this.targetings != null ? new HashMap<TargetingCode, CampaignTargetValues>(this.targetings)
				: new HashMap<TargetingCode, CampaignTargetValues>();

		targetings.put(code, target);
		setTargetings(targetings);
	}
}
