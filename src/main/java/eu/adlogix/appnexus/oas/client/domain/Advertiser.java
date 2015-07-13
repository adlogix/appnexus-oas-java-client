package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;

/**
 * Domain class which represents an OAS Advertiser. This can be used in
 * Advertiser creation, update and retrieval operations.
 */
@Getter
public class Advertiser extends StatefulDomainWithId {

	private String organization;
	private BillingInformation billingInformation;
	private String internalQuickReport;
	private String externalQuickReport;

	public void setOrganization(String organization) {
		this.organization = organization;
		addModifiedAttribute("organization");
	}

	public void setBillingInformation(BillingInformation billingInformation) {
		this.billingInformation = billingInformation;
		addModifiedAttribute("billingInformation");
	}

	public void setInternalQuickReport(String internalQuickReport) {
		this.internalQuickReport = internalQuickReport;
		addModifiedAttribute("internalQuickReport");
	}

	public void setExternalQuickReport(String externalQuickReport) {
		this.externalQuickReport = externalQuickReport;
		addModifiedAttribute("externalQuickReport");
	}
}
