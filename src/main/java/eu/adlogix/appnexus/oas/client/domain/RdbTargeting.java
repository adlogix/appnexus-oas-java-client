package eu.adlogix.appnexus.oas.client.domain;

import lombok.Data;

@Data
public class RdbTargeting {

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

}
