package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Targeting extends StatefulDomain {

	private TargetingCode code;
	private List<String> values;

	public Targeting(TargetingCode code) {
		validateTargetingCode(code);
		this.code = code;
		addModifiedAttribute("code");
	}

	/**
	 * Hook for subclasses to override to validate the {@link Targeting} against
	 * the {@link TargetingCode} when setting the code
	 * 
	 * @param code
	 *            The {@link TargetingCode} of the {@link Targeting}
	 */
	protected void validateTargetingCode(TargetingCode code) {
	}

	public void setCode(TargetingCode code) {
		validateTargetingCode(code);
		this.code = code;
		addModifiedAttribute("code");
	}

	public void setValues(List<String> values) {
		this.values = Collections.unmodifiableList(values);
		addModifiedAttribute("values");
	}

	public boolean isSupportingExcludeFlag() {
		return code.isSupportingExcludeFlagForCampaigns();
	}
}
