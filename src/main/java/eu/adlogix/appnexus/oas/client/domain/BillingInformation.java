package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;

/**
 * "Billing Information" which can be assigned to an {@link Advertiser} in
 * Advertiser creation, update and retrieval.
 */
@Getter
public class BillingInformation extends StatefulDomain {

	private String country;
	private String method;

	public void setCountry(String country) {
		this.country = country;
		addModifiedAttribute("country");
	}

	public void setMethod(String method) {
		this.method = method;
		addModifiedAttribute("method");
	}

}
