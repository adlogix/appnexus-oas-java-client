package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Used when setting and reading {@link Campaign#getZoneTargeting()} and
 * {@link MobileTargetings} attributes mapped with the related
 * {@link TargetingCode}
 * 
 * @see TargetingCode
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CampaignTargetValues extends StatefulDomain {

	/**
	 * Set Targeting values of a {@link Campaign} for a particular
	 * {@link TargetingCode}. Should contain permittable values for this
	 * {@link TargetingCode}
	 */
	private List<String> values;

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

	public boolean isSupportingExcludeFlag() {
		return false;
	}

}
