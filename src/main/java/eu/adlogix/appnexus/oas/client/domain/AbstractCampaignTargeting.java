package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public abstract class AbstractCampaignTargeting extends StatefulDomain implements CampaignTarget {

	/**
	 * Relevant {@link TargetingCode}
	 */
	private TargetingCode code;

	/**
	 * Set Targeting values of a {@link Campaign} for a particular
	 * {@link TargetingCode}. Should contain permittable values for this
	 * {@link TargetingCode}
	 */
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

	/**
	 * Set Targeting values of a {@link Campaign} for a particular
	 * {@link TargetingCode}
	 * 
	 * @param values
	 *            The permittable values for this {@link TargetingCode}
	 */
	public void setValues(List<String> values) {
		this.values = Collections.unmodifiableList(values);
		addModifiedAttribute("values");
	}

	/**
	 * If exclude flag is enabled for this Targetting
	 * 
	 * @return value based on
	 *         {@link TargetingCode#isSupportingExcludeFlagForCampaigns()}
	 */
	public boolean isSupportingExcludeFlag() {
		return code.isSupportingExcludeFlagForCampaigns();
	}

	@Override
	public TargetGroup getGroup() {
		return code.getGroup();
	}
}
