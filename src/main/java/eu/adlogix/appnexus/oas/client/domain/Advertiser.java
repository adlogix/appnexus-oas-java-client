package eu.adlogix.appnexus.oas.client.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Advertiser {

	private String id;
	private String organization;
	private BillingInformation billingInformation;
	private String internalQuickReport;
	private String externalQuickReport;

	public Advertiser(String id, String organization) {
		this.id = id;
		this.organization = organization;
	}

}
