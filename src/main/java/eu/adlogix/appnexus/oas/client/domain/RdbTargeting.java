package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;

@Getter
public class RdbTargeting extends StatefulDomain {

	private Integer ageFrom;
	private Integer ageTo;
	private Boolean ageExclude;

	private String gender;
	private Boolean genderExclude;

	private Long incomeFrom;
	private Long incomeTo;
	private Boolean incomeExclude;

	private String subscriberCode;
	private Boolean subscriberCodeExclude;

	private String preferenceFlags;
	private Boolean preferenceFlagsExclude;

	public void setAgeFrom(Integer ageFrom) {
		this.ageFrom = ageFrom;
		setModifiedFlag("ageFrom");
	}

	public void setAgeTo(Integer ageTo) {
		this.ageTo = ageTo;
		setModifiedFlag("ageTo");
	}

	public void setAgeExclude(Boolean ageExclude) {
		this.ageExclude = ageExclude;
		setModifiedFlag("ageExclude");
	}

	public void setGender(String gender) {
		this.gender = gender;
		setModifiedFlag("gender");
	}

	public void setGenderExclude(Boolean genderExclude) {
		this.genderExclude = genderExclude;
		setModifiedFlag("genderExclude");
	}

	public void setIncomeFrom(Long incomeFrom) {
		this.incomeFrom = incomeFrom;
		setModifiedFlag("incomeFrom");
	}

	public void setIncomeTo(Long incomeTo) {
		this.incomeTo = incomeTo;
		setModifiedFlag("incomeTo");
	}

	public void setIncomeExclude(Boolean incomeExclude) {
		this.incomeExclude = incomeExclude;
		setModifiedFlag("incomeExclude");
	}

	public void setSubscriberCode(String subscriberCode) {
		this.subscriberCode = subscriberCode;
		setModifiedFlag("subscriberCode");
	}

	public void setSubscriberCodeExclude(Boolean subscriberCodeExclude) {
		this.subscriberCodeExclude = subscriberCodeExclude;
		setModifiedFlag("subscriberCodeExclude");
	}

	public void setPreferenceFlags(String preferenceFlags) {
		this.preferenceFlags = preferenceFlags;
		setModifiedFlag("preferenceFlags");
	}

	public void setPreferenceFlagsExclude(Boolean preferenceFlagsExclude) {
		this.preferenceFlagsExclude = preferenceFlagsExclude;
		setModifiedFlag("preferenceFlagsExclude");
	}

	/**
	 * Resets the modified flags.The {@link RdbTargeting} will be considered as
	 * an unmodified {@link RdbTargeting} after calling this method.
	 */
	public void resetModifiedFlags() {
		super.resetModifiedFlags();
	}

	/**
	 * Returns a new {@link RdbTargeting} object with only the modified
	 * attribute values.
	 */
	public RdbTargeting getRdbargetingWithModifiedAttributes() {
		return super.getObjectWithModifiedAttributes();
	}
}
