package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

/**
 * Domain class which represents an OAS Insertion Order. This can be used in
 * Insertion Order creation, update and retrieval operations.
 */
@Getter
public class InsertionOrder extends StatefulDomainWithId {

	private String description;
	private CampaignsBy campaignsBy;
	private String advertiserId;
	private String agencyId;
	private InsertionOrderStatus status;
	private List<String> campaignIds;
	private String internalQuickReport;
	private String externalQuickReport;
	private Long bookedClicks;
	private Long bookedImpressions;

	public void setDescription(String description) {
		this.description = description;
		addModifiedAttribute("description");
	}

	public void setCampaignsBy(CampaignsBy campaignsBy) {
		this.campaignsBy = campaignsBy;
		addModifiedAttribute("campaignsBy");
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
		addModifiedAttribute("advertiserId");
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
		addModifiedAttribute("agencyId");
	}

	public void setStatus(InsertionOrderStatus status) {
		this.status = status;
		addModifiedAttribute("status");
	}

	public void setCampaignIds(List<String> campaignIds) {
		this.campaignIds = Collections.unmodifiableList(campaignIds);
		addModifiedAttribute("campaignIds");
	}

	public void setInternalQuickReport(String internalQuickReport) {
		this.internalQuickReport = internalQuickReport;
		addModifiedAttribute("internalQuickReport");
	}

	public void setExternalQuickReport(String externalQuickReport) {
		this.externalQuickReport = externalQuickReport;
		addModifiedAttribute("externalQuickReport");
	}

	public void setBookedClicks(Long bookedClicks) {
		this.bookedClicks = bookedClicks;
		addModifiedAttribute("bookedClicks");
	}

	public void setBookedImpressions(Long bookedImpressions) {
		this.bookedImpressions = bookedImpressions;
		addModifiedAttribute("bookedImpressions");
	}

}
