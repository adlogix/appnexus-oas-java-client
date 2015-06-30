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
		setModifiedFlag("code");
	}

	public void setCode(TargetingCode code) {
		this.code = code;
		setModifiedFlag("code");
	}

	public void setValues(List<String> values) {
		this.values = values;
		setModifiedFlag("values");
	}

	/**
	 * Resets the modified flags.The {@link Targeting} will be considered as an
	 * unmodified {@link Targeting} after calling this method.
	 */
	public void resetModifiedFlags() {
		super.resetModifiedFlags();
	}

	/**
	 * Returns a new {@link Targeting} object with only the modified attribute
	 * values.
	 */
	public Targeting getTargetingWithModifiedAttributes() {
		return super.getObjectWithModifiedAttributes();
	}

	public boolean isSupportingExcludeFlag() {
		return code.isSupportingExcludeFlagForCampaigns();
	}
}
