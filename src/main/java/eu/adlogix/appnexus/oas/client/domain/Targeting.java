package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Targeting extends StatefulDomain {

	private TargetingCode code;
	private List<String> values;

	public Targeting(TargetingCode code) {
		this.code = code;
		addModifiedAttribute("code");
	}

	public void setCode(TargetingCode code) {
		this.code = code;
		addModifiedAttribute("code");
	}

	public void setValues(List<String> values) {
		this.values = values;
		addModifiedAttribute("values");
	}

	public boolean isSupportingExcludeFlag() {
		return code.isSupportingExcludeFlagForCampaigns();
	}
}
