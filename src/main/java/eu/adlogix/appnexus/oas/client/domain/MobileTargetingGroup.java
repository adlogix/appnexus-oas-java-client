package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MobileTargetingGroup extends StatefulDomain implements CampaignTarget {
	private Boolean excludeMobileDevice;
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
