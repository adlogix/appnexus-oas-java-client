package eu.adlogix.appnexus.oas.client.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

@Getter
public class Campaign extends StatefulDomain {

	private String id;
	private String type;
	private String insertionOrderId;
	private String advertiserId;
	private String creativeTargetId;
	private String name;
	private String agencyId;
	private String description;
	private String campaignManager;
	private String productId;
	private String status;
	private List<String> campaignGroupIds;
	private List<String> competitiveCategroryIds;
	private List<String> externalUserIds;
	private String internalQuickReport;
	private String externalQuickReport;
	private Long impressions;
	private Long clicks;
	private Long uniques;
	private String weight;
	private String priorityLevel;
	private String completion;
	private LocalDate startDate;
	private LocalTime startTime;
	private LocalDate endDate;
	private LocalTime endTime;
	private String reach;
	private Long dailyImps;
	private Long dailyClicks;
	private Long dailyUniques;
	private String smoothOrAsap;
	private Long impressionsOverrun;
	private List<String> companionPositions;
	private String strictCompanions;
	private Long primaryImpsPerVisitor;
	private Long primaryClicksPerVisitor;
	private Long primaryFrequencyScope;
	private Long secondaryImpsPerVisitor;
	private Long secondaryFrequencyScope;
	private List<String> hourOfDay;
	private List<String> dayOfWeek;
	private String userTimeZone;
	private List<String> sectionIds;
	private List<String> pageUrls;

	private Boolean excludeTargets;
	private List<Targeting> commonTargeting;
	private RdbTargeting rdbTargeting;
	private SegmentTargeting segmentTargeting;

	private List<String> excludedSiteIds;
	private List<String> excludedPageUrls;
	private Double cpm;
	private Double cpc;
	private Double cpa;
	private Double flatRate;
	private Double tax;
	private Double agencyCommission;
	private String paymentMethod;
	private String isYieldManaged;
	private String billTo;
	private String currency;

	public void setId(String id) {
		this.id = id;
		setModifiedFlag("id");
	}

	public void setType(String type) {
		this.type = type;
		setModifiedFlag("type");
	}

	public void setInsertionOrderId(String insertionOrderId) {
		this.insertionOrderId = insertionOrderId;
		setModifiedFlag("insertionOrderId");
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
		setModifiedFlag("advertiserId");
	}

	public void setCreativeTargetId(String creativeTargetId) {
		this.creativeTargetId = creativeTargetId;
		setModifiedFlag("creativeTargetId");
	}

	public void setName(String name) {
		this.name = name;
		setModifiedFlag("name");
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
		setModifiedFlag("agencyId");
	}

	public void setDescription(String description) {
		this.description = description;
		setModifiedFlag("description");
	}

	public void setCampaignManager(String campaignManager) {
		this.campaignManager = campaignManager;
		setModifiedFlag("campaignManager");
	}

	public void setProductId(String productId) {
		this.productId = productId;
		setModifiedFlag("productId");
	}

	public void setStatus(String status) {
		this.status = status;
		setModifiedFlag("status");
	}

	public void setCampaignGroupIds(List<String> campaignGroupIds) {
		this.campaignGroupIds = campaignGroupIds;
		setModifiedFlag("campaignGroupIds");
	}

	public void setCompetitiveCategroryIds(List<String> competitiveCategroryIds) {
		this.competitiveCategroryIds = competitiveCategroryIds;
		setModifiedFlag("competitiveCategroryIds");
	}

	public void setExternalUserIds(List<String> externalUserIds) {
		this.externalUserIds = externalUserIds;
		setModifiedFlag("externalUserIds");
	}

	public void setInternalQuickReport(String internalQuickReport) {
		this.internalQuickReport = internalQuickReport;
		setModifiedFlag("internalQuickReport");
	}

	public void setExternalQuickReport(String externalQuickReport) {
		this.externalQuickReport = externalQuickReport;
		setModifiedFlag("externalQuickReport");
	}

	public void setImpressions(Long impressions) {
		this.impressions = impressions;
		setModifiedFlag("impressions");
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
		setModifiedFlag("clicks");
	}

	public void setUniques(Long uniques) {
		this.uniques = uniques;
		setModifiedFlag("uniques");
	}

	public void setWeight(String weight) {
		this.weight = weight;
		setModifiedFlag("weight");
	}

	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
		setModifiedFlag("priorityLevel");
	}

	public void setCompletion(String completion) {
		this.completion = completion;
		setModifiedFlag("completion");
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
		setModifiedFlag("startDate");
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
		setModifiedFlag("startTime");
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
		setModifiedFlag("endDate");
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
		setModifiedFlag("endTime");
	}

	public void setReach(String reach) {
		this.reach = reach;
		setModifiedFlag("reach");
	}

	public void setDailyImps(Long dailyImps) {
		this.dailyImps = dailyImps;
		setModifiedFlag("dailyImps");
	}

	public void setDailyClicks(Long dailyClicks) {
		this.dailyClicks = dailyClicks;
		setModifiedFlag("dailyClicks");
	}

	public void setDailyUniques(Long dailyUniques) {
		this.dailyUniques = dailyUniques;
		setModifiedFlag("dailyUniques");
	}

	public void setSmoothOrAsap(String smoothOrAsap) {
		this.smoothOrAsap = smoothOrAsap;
		setModifiedFlag("smoothOrAsap");
	}

	public void setImpressionsOverrun(Long impressionsOverrun) {
		this.impressionsOverrun = impressionsOverrun;
		setModifiedFlag("impressionsOverrun");
	}

	public void setCompanionPositions(List<String> companionPositions) {
		this.companionPositions = companionPositions;
		setModifiedFlag("companionPositions");
	}

	public void setStrictCompanions(String strictCompanions) {
		this.strictCompanions = strictCompanions;
		setModifiedFlag("strictCompanions");
	}

	public void setPrimaryImpsPerVisitor(Long primaryImpsPerVisitor) {
		this.primaryImpsPerVisitor = primaryImpsPerVisitor;
		setModifiedFlag("primaryImpsPerVisitor");
	}

	public void setPrimaryClicksPerVisitor(Long primaryClicksPerVisitor) {
		this.primaryClicksPerVisitor = primaryClicksPerVisitor;
		setModifiedFlag("primaryClicksPerVisitor");
	}

	public void setPrimaryFrequencyScope(Long primaryFrequencyScope) {
		this.primaryFrequencyScope = primaryFrequencyScope;
		setModifiedFlag("primaryFrequencyScope");
	}

	public void setSecondaryImpsPerVisitor(Long secondaryImpsPerVisitor) {
		this.secondaryImpsPerVisitor = secondaryImpsPerVisitor;
		setModifiedFlag("secondaryImpsPerVisitor");
	}

	public void setSecondaryFrequencyScope(Long secondaryFrequencyScope) {
		this.secondaryFrequencyScope = secondaryFrequencyScope;
		setModifiedFlag("secondaryFrequencyScope");
	}

	public void setHourOfDay(List<String> hourOfDay) {
		this.hourOfDay = hourOfDay;
		setModifiedFlag("hourOfDay");
	}

	public void setDayOfWeek(List<String> dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
		setModifiedFlag("dayOfWeek");
	}

	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
		setModifiedFlag("userTimeZone");
	}

	public void setSectionIds(List<String> sectionIds) {
		this.sectionIds = sectionIds;
		setModifiedFlag("sectionIds");
	}

	public void setPageUrls(List<String> pageUrls) {
		this.pageUrls = pageUrls;
		setModifiedFlag("pageUrls");
	}

	public void setExcludeTargets(Boolean excludeTargets) {
		this.excludeTargets = excludeTargets;
		setModifiedFlag("excludeTargets");
	}

	public void setCommonTargeting(List<Targeting> commonTargeting) {
		this.commonTargeting = commonTargeting;
		setModifiedFlag("commonTargeting");
	}

	public void setRdbTargeting(RdbTargeting rdbTargeting) {
		this.rdbTargeting = rdbTargeting;
		setModifiedFlag("rdbTargeting");
	}

	public void setSegmentTargeting(SegmentTargeting segmentTargeting) {
		this.segmentTargeting = segmentTargeting;
		setModifiedFlag("segmentTargeting");
	}

	public void setExcludedSiteIds(List<String> excludedSiteIds) {
		this.excludedSiteIds = excludedSiteIds;
		setModifiedFlag("excludedSiteIds");
	}

	public void setExcludedPageUrls(List<String> excludedPageUrls) {
		this.excludedPageUrls = excludedPageUrls;
		setModifiedFlag("excludedPageUrls");
	}

	public void setCpm(Double cpm) {
		this.cpm = cpm;
		setModifiedFlag("cpm");
	}

	public void setCpc(Double cpc) {
		this.cpc = cpc;
		setModifiedFlag("cpc");
	}

	public void setCpa(Double cpa) {
		this.cpa = cpa;
		setModifiedFlag("cpa");
	}

	public void setFlatRate(Double flatRate) {
		this.flatRate = flatRate;
		setModifiedFlag("flatRate");
	}

	public void setTax(Double tax) {
		this.tax = tax;
		setModifiedFlag("tax");
	}

	public void setAgencyCommission(Double agencyCommission) {
		this.agencyCommission = agencyCommission;
		setModifiedFlag("agencyCommission");
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
		setModifiedFlag("paymentMethod");
	}

	public void setIsYieldManaged(String isYieldManaged) {
		this.isYieldManaged = isYieldManaged;
		setModifiedFlag("isYieldManaged");
	}

	public void setBillTo(String billTo) {
		this.billTo = billTo;
		setModifiedFlag("billTo");
	}

	public void setCurrency(String currency) {
		this.currency = currency;
		setModifiedFlag("currency");
	}

	/**
	 * Returns a new {@link Campaign} object with only the modified attribute
	 * values.
	 */
	public Campaign getCampaignWithModifiedAttributes() {
		Campaign modifiedCampaign = super.getObjectWithModifiedAttributes();

		if (isModified("segmentTargeting")) {
			modifiedCampaign.setSegmentTargeting(segmentTargeting.getSegmentTargetingWithModifiedAttributes());
		}

		if (isModified("commonTargeting")) {
			List<Targeting> modifiedTageting = new ArrayList<Targeting>();
			for (Targeting targeting : commonTargeting) {
				modifiedTageting.add(targeting.getTargetingWithModifiedAttributes());
			}
			modifiedCampaign.setCommonTargeting(modifiedTageting);
		}

		if (isModified("rdbTargeting")) {
			modifiedCampaign.setRdbTargeting(rdbTargeting.getRdbargetingWithModifiedAttributes());
		}
		return modifiedCampaign;
	}

	/**
	 * Resets the modified flags.The {@link Campaign} will be considered as an
	 * unmodified {@link Campaign} after calling this method.
	 */
	public void resetModifiedFlags() {
		super.resetModifiedFlags();
		if (isModified("segmentTargeting")) {
			segmentTargeting.resetModifiedFlags();
		}
		if (isModified("commonTargeting")) {
			for (Targeting targeting : commonTargeting) {
				targeting.resetModifiedFlags();
			}
		}
		if (isModified("rdbTargeting")) {
			rdbTargeting.resetModifiedFlags();
		}
		setModifiedFlag("id");
	}

	public boolean hasTargeting() {
		return (commonTargeting != null || rdbTargeting != null || segmentTargeting != null || excludeTargets != null);
	}

	public boolean hasPrimaryFrequency() {
		return (primaryImpsPerVisitor != null || primaryClicksPerVisitor != null || primaryFrequencyScope != null);
	}

	public boolean hasSecondaryFrequency() {
		return (secondaryImpsPerVisitor != null || secondaryFrequencyScope != null);
	}

	public boolean hasSchedule() {
		return (impressions != null || clicks != null || uniques != null || weight != null || priorityLevel != null
				|| completion != null || startDate != null || endDate != null || reach != null || dailyImps != null
				|| dailyClicks != null || dailyUniques != null || smoothOrAsap != null || impressionsOverrun != null
				|| strictCompanions != null || hasSecondaryFrequency() || hourOfDay != null || dayOfWeek != null
				|| userTimeZone != null || startTime != null || endTime != null || companionPositions != null
				|| hasPrimaryFrequency() || sectionIds != null);
	}

	public boolean hasExclude() {
		return excludedPageUrls != null || excludedSiteIds != null;
	}

	public boolean hasBilling() {
		return (cpm != null || cpc != null || cpa != null || flatRate != null || tax != null
				|| agencyCommission != null || paymentMethod != null || isYieldManaged != null || billTo != null || currency != null);
	}
}
