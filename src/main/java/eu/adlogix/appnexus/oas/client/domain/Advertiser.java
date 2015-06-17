package eu.adlogix.appnexus.oas.client.domain;

import lombok.Data;

@Data
public class Advertiser {

	private String id;
	private String organization;
	private BillingInformation billingInformation;
	private String internalQuickReport;
	private String externalQuickReport;

	public Advertiser(String id, String organization) {
		this.id = id;
		this.organization = organization;
		this.billingInformation = new BillingInformation();
		billingInformation.setMethod("M");
		billingInformation.setCountry("US");

		this.internalQuickReport = "short";
		this.externalQuickReport = "to-date";

	}

	public Advertiser() {
		this(null, null);
	}

}
