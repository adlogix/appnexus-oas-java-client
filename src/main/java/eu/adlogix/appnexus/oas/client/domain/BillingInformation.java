package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * "Billing Information" which can be assigned to an {@link Advertiser} in
 * Advertiser creation, update and retrieval.
 */
@Getter
@Setter
public class BillingInformation extends StatefulDomain {

	private String country;
	private String method;
}
