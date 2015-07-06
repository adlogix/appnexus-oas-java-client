package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public abstract class AbstractCampaignTargeting extends StatefulDomain implements CampaignTarget {

	private TargetingCode code;
	private List<String> values;

	public AbstractCampaignTargeting(TargetingCode code) {
		validateTargetingCode(code);
		this.code = code;
		addModifiedAttribute("code");
	}

	/**
	 * Hook for subclasses to override to validate the {@link AbstractCampaignTargeting} against
	 * the {@link TargetingCode} when setting the code
	 * 
	 * @param code
	 *            The {@link TargetingCode} of the {@link AbstractCampaignTargeting}
	 */
	protected void validateTargetingCode(TargetingCode code) {
		if (!getValidTargetingCodes().contains(code)) {
			throw new IllegalArgumentException("Targeting of Code " + code
					+ " cannot be added to this list as only the following list is supported: "
					+ getValidTargetingCodes());
		}
	}

	protected abstract List<TargetingCode> getValidTargetingCodes();

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

	@Override
	public TargetGroup getGroup() {
		return code.getGroup();
	}
}
