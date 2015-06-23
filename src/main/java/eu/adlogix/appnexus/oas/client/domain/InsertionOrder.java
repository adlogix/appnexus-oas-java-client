package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.Getter;

@Getter
public class InsertionOrder extends StatefulDomain {

	private String id;
	private String description;
	private String campaignsBy;
	private String advertiserId;
	private String agencyId;
	private String status;
	private List<String> campaignIds;
	private String internalQuickReport;
	private String externalQuickReport;
	private Long bookedClicks;
	private Long bookedImpressions;

	public void setId(String id) {
		this.id = id;
		setModifiedFlag("id");
	}

	public void setDescription(String description) {
		this.description = description;
		setModifiedFlag("description");
	}

	public void setCampaignsBy(String campaignsBy) {
		this.campaignsBy = campaignsBy;
		setModifiedFlag("campaignsBy");
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
		setModifiedFlag("advertiserId");
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
		setModifiedFlag("agencyId");
	}

	public void setStatus(String status) {
		this.status = status;
		setModifiedFlag("status");
	}

	public void setCampaignIds(List<String> campaignIds) {
		this.campaignIds = campaignIds;
		setModifiedFlag("campaignIds");
	}

	public void setInternalQuickReport(String internalQuickReport) {
		this.internalQuickReport = internalQuickReport;
		setModifiedFlag("internalQuickReport");
	}

	public void setExternalQuickReport(String externalQuickReport) {
		this.externalQuickReport = externalQuickReport;
		setModifiedFlag("externalQuickReport");
	}

	public void setBookedClicks(Long bookedClicks) {
		this.bookedClicks = bookedClicks;
		setModifiedFlag("bookedClicks");
	}

	public void setBookedImpressions(Long bookedImpressions) {
		this.bookedImpressions = bookedImpressions;
		setModifiedFlag("bookedImpressions");
	}


	/**
	 * Resets the modified flags.The {@link InsertionOrder} will be considered
	 * as an unmodified {@link InsertionOrder} after calling this method.
	 */
	public void resetModifiedFlags() {
		super.resetModifiedFlags();
	}

	/**
	 * Returns a new {@link InsertionOrder} object with only the modified
	 * attribute values.
	 */
	public InsertionOrder getInsertionOrderWithModifiedAttributes() {
		return super.getObjectWithModifiedAttributes();
	}

}
