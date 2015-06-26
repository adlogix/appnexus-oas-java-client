package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;

@Getter

public class Advertiser extends StatefulDomain {

	private String id;
	private String organization;
	private BillingInformation billingInformation;
	private String internalQuickReport;
	private String externalQuickReport;

	public void setId(String id) {
		this.id = id;
		setModifiedFlag("id");
	}

	public void setOrganization(String organization) {
		this.organization = organization;
		setModifiedFlag("organization");
	}

	public void setBillingInformation(BillingInformation billingInformation) {
		this.billingInformation = billingInformation;
		setModifiedFlag("billingInformation");
	}

	public void setInternalQuickReport(String internalQuickReport) {
		this.internalQuickReport = internalQuickReport;
		setModifiedFlag("internalQuickReport");
	}

	public void setExternalQuickReport(String externalQuickReport) {
		this.externalQuickReport = externalQuickReport;
		setModifiedFlag("externalQuickReport");
	}

	/**
	 * Resets the modified flags.The {@link Advertiser} will be considered as an
	 * unmodified {@link Advertiser} after calling this method.
	 */
	public void resetModifiedFlags() {
		super.resetModifiedFlags();
		setModifiedFlag("id");
	}

	/**
	 * Returns a new {@link Advertiser} object with only the modified attribute
	 * values.
	 */
	public Advertiser getAdvertiserWithModifiedAttributes() {
		return super.getObjectWithModifiedAttributes();
	}

}
