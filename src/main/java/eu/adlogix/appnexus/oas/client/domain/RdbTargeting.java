package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;

/**
 * Domain class which represents OAS RDB Targeting. This can be used in Campaign
 * creation, update and retrieval operations.
 */
@Getter
public class RdbTargeting extends StatefulDomain implements CampaignTarget {

	private Integer ageFrom;
	private Integer ageTo;
	private Boolean ageExclude;

	private Gender gender;
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
		addModifiedAttribute("ageFrom");
	}

	public void setAgeTo(Integer ageTo) {
		this.ageTo = ageTo;
		addModifiedAttribute("ageTo");
	}

	public void setAgeExclude(Boolean ageExclude) {
		this.ageExclude = ageExclude;
		addModifiedAttribute("ageExclude");
	}

	public void setGender(Gender gender) {
		this.gender = gender;
		addModifiedAttribute("gender");
	}

	public void setGenderExclude(Boolean genderExclude) {
		this.genderExclude = genderExclude;
		addModifiedAttribute("genderExclude");
	}

	public void setIncomeFrom(Long incomeFrom) {
		this.incomeFrom = incomeFrom;
		addModifiedAttribute("incomeFrom");
	}

	public void setIncomeTo(Long incomeTo) {
		this.incomeTo = incomeTo;
		addModifiedAttribute("incomeTo");
	}

	public void setIncomeExclude(Boolean incomeExclude) {
		this.incomeExclude = incomeExclude;
		addModifiedAttribute("incomeExclude");
	}

	public void setSubscriberCode(String subscriberCode) {
		this.subscriberCode = subscriberCode;
		addModifiedAttribute("subscriberCode");
	}

	public void setSubscriberCodeExclude(Boolean subscriberCodeExclude) {
		this.subscriberCodeExclude = subscriberCodeExclude;
		addModifiedAttribute("subscriberCodeExclude");
	}

	public void setPreferenceFlags(String preferenceFlags) {
		this.preferenceFlags = preferenceFlags;
		addModifiedAttribute("preferenceFlags");
	}

	public void setPreferenceFlagsExclude(Boolean preferenceFlagsExclude) {
		this.preferenceFlagsExclude = preferenceFlagsExclude;
		addModifiedAttribute("preferenceFlagsExclude");
	}

	@Override
	public TargetGroup getGroup() {
		return TargetGroup.RDB;
	}

}
