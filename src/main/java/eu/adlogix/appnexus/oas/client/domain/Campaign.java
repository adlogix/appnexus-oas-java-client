package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.List;

import lombok.Getter;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

@Getter
public class Campaign extends StatefulDomainWithId {

	private static final String ATTRNAME_RDBTARGETING = "rdbTargeting";
	private static final String ATTRNAME_SEGMENTTARGETING = "segmentTargeting";
	private static final String ATTRNAME_TARGETING = "targeting";

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
	private Long weight;
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
	private Boolean excludeMobileDevice;
	private List<Targeting> targeting;
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

	public void setType(String type) {
		this.type = type;
		addModifiedAttribute("type");
	}

	public void setInsertionOrderId(String insertionOrderId) {
		this.insertionOrderId = insertionOrderId;
		addModifiedAttribute("insertionOrderId");
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
		addModifiedAttribute("advertiserId");
	}

	public void setCreativeTargetId(String creativeTargetId) {
		this.creativeTargetId = creativeTargetId;
		addModifiedAttribute("creativeTargetId");
	}

	public void setName(String name) {
		this.name = name;
		addModifiedAttribute("name");
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
		addModifiedAttribute("agencyId");
	}

	public void setDescription(String description) {
		this.description = description;
		addModifiedAttribute("description");
	}

	public void setCampaignManager(String campaignManager) {
		this.campaignManager = campaignManager;
		addModifiedAttribute("campaignManager");
	}

	public void setProductId(String productId) {
		this.productId = productId;
		addModifiedAttribute("productId");
	}

	public void setStatus(String status) {
		this.status = status;
		addModifiedAttribute("status");
	}

	public void setCampaignGroupIds(List<String> campaignGroupIds) {
		this.campaignGroupIds = Collections.unmodifiableList(campaignGroupIds);
		addModifiedAttribute("campaignGroupIds");
	}

	public void setCompetitiveCategroryIds(List<String> competitiveCategroryIds) {
		this.competitiveCategroryIds = Collections.unmodifiableList(competitiveCategroryIds);
		addModifiedAttribute("competitiveCategroryIds");
	}

	public void setExternalUserIds(List<String> externalUserIds) {
		this.externalUserIds = Collections.unmodifiableList(externalUserIds);
		addModifiedAttribute("externalUserIds");
	}

	public void setInternalQuickReport(String internalQuickReport) {
		this.internalQuickReport = internalQuickReport;
		addModifiedAttribute("internalQuickReport");
	}

	public void setExternalQuickReport(String externalQuickReport) {
		this.externalQuickReport = externalQuickReport;
		addModifiedAttribute("externalQuickReport");
	}

	public void setImpressions(Long impressions) {
		this.impressions = impressions;
		addModifiedAttribute("impressions");
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
		addModifiedAttribute("clicks");
	}

	public void setUniques(Long uniques) {
		this.uniques = uniques;
		addModifiedAttribute("uniques");
	}

	public void setWeight(Long weight) {
		this.weight = weight;
		addModifiedAttribute("weight");
	}

	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
		addModifiedAttribute("priorityLevel");
	}

	public void setCompletion(String completion) {
		this.completion = completion;
		addModifiedAttribute("completion");
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
		addModifiedAttribute("startDate");
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
		addModifiedAttribute("startTime");
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
		addModifiedAttribute("endDate");
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
		addModifiedAttribute("endTime");
	}

	public void setReach(String reach) {
		this.reach = reach;
		addModifiedAttribute("reach");
	}

	public void setDailyImps(Long dailyImps) {
		this.dailyImps = dailyImps;
		addModifiedAttribute("dailyImps");
	}

	public void setDailyClicks(Long dailyClicks) {
		this.dailyClicks = dailyClicks;
		addModifiedAttribute("dailyClicks");
	}

	public void setDailyUniques(Long dailyUniques) {
		this.dailyUniques = dailyUniques;
		addModifiedAttribute("dailyUniques");
	}

	public void setSmoothOrAsap(String smoothOrAsap) {
		this.smoothOrAsap = smoothOrAsap;
		addModifiedAttribute("smoothOrAsap");
	}

	public void setImpressionsOverrun(Long impressionsOverrun) {
		this.impressionsOverrun = impressionsOverrun;
		addModifiedAttribute("impressionsOverrun");
	}

	public void setCompanionPositions(List<String> companionPositions) {
		this.companionPositions = Collections.unmodifiableList(companionPositions);
		addModifiedAttribute("companionPositions");
	}

	public void setStrictCompanions(String strictCompanions) {
		this.strictCompanions = strictCompanions;
		addModifiedAttribute("strictCompanions");
	}

	public void setPrimaryImpsPerVisitor(Long primaryImpsPerVisitor) {
		this.primaryImpsPerVisitor = primaryImpsPerVisitor;
		addModifiedAttribute("primaryImpsPerVisitor");
	}

	public void setPrimaryClicksPerVisitor(Long primaryClicksPerVisitor) {
		this.primaryClicksPerVisitor = primaryClicksPerVisitor;
		addModifiedAttribute("primaryClicksPerVisitor");
	}

	public void setPrimaryFrequencyScope(Long primaryFrequencyScope) {
		this.primaryFrequencyScope = primaryFrequencyScope;
		addModifiedAttribute("primaryFrequencyScope");
	}

	public void setSecondaryImpsPerVisitor(Long secondaryImpsPerVisitor) {
		this.secondaryImpsPerVisitor = secondaryImpsPerVisitor;
		addModifiedAttribute("secondaryImpsPerVisitor");
	}

	public void setSecondaryFrequencyScope(Long secondaryFrequencyScope) {
		this.secondaryFrequencyScope = secondaryFrequencyScope;
		addModifiedAttribute("secondaryFrequencyScope");
	}

	public void setHourOfDay(List<String> hourOfDay) {
		this.hourOfDay = Collections.unmodifiableList(hourOfDay);
		addModifiedAttribute("hourOfDay");
	}

	public void setDayOfWeek(List<String> dayOfWeek) {
		this.dayOfWeek = Collections.unmodifiableList(dayOfWeek);
		addModifiedAttribute("dayOfWeek");
	}

	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
		addModifiedAttribute("userTimeZone");
	}

	public void setSectionIds(List<String> sectionIds) {
		this.sectionIds = Collections.unmodifiableList(sectionIds);
		addModifiedAttribute("sectionIds");
	}

	public void setPageUrls(List<String> pageUrls) {
		this.pageUrls = Collections.unmodifiableList(pageUrls);
		addModifiedAttribute("pageUrls");
	}

	public void setExcludeTargets(Boolean excludeTargets) {
		this.excludeTargets = excludeTargets;
		addModifiedAttribute("excludeTargets");
	}

	public void setExcludeMobileDevice(Boolean excludeMobileDevice) {
		this.excludeMobileDevice = excludeMobileDevice;
		addModifiedAttribute("excludeMobileDevice");
	}

	public void setTargeting(List<Targeting> targeting) {
		this.targeting = Collections.unmodifiableList(targeting);
		addModifiedAttribute(ATTRNAME_TARGETING);
	}

	public void setRdbTargeting(RdbTargeting rdbTargeting) {
		this.rdbTargeting = rdbTargeting;
		addModifiedAttribute(ATTRNAME_RDBTARGETING);
	}

	public void setSegmentTargeting(SegmentTargeting segmentTargeting) {
		this.segmentTargeting = segmentTargeting;
		addModifiedAttribute(ATTRNAME_SEGMENTTARGETING);
	}

	public void setExcludedSiteIds(List<String> excludedSiteIds) {
		this.excludedSiteIds = Collections.unmodifiableList(excludedSiteIds);
		addModifiedAttribute("excludedSiteIds");
	}

	public void setExcludedPageUrls(List<String> excludedPageUrls) {
		this.excludedPageUrls = Collections.unmodifiableList(excludedPageUrls);
		addModifiedAttribute("excludedPageUrls");
	}

	public void setCpm(Double cpm) {
		this.cpm = cpm;
		addModifiedAttribute("cpm");
	}

	public void setCpc(Double cpc) {
		this.cpc = cpc;
		addModifiedAttribute("cpc");
	}

	public void setCpa(Double cpa) {
		this.cpa = cpa;
		addModifiedAttribute("cpa");
	}

	public void setFlatRate(Double flatRate) {
		this.flatRate = flatRate;
		addModifiedAttribute("flatRate");
	}

	public void setTax(Double tax) {
		this.tax = tax;
		addModifiedAttribute("tax");
	}

	public void setAgencyCommission(Double agencyCommission) {
		this.agencyCommission = agencyCommission;
		addModifiedAttribute("agencyCommission");
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
		addModifiedAttribute("paymentMethod");
	}

	public void setIsYieldManaged(String isYieldManaged) {
		this.isYieldManaged = isYieldManaged;
		addModifiedAttribute("isYieldManaged");
	}

	public void setBillTo(String billTo) {
		this.billTo = billTo;
		addModifiedAttribute("billTo");
	}

	public void setCurrency(String currency) {
		this.currency = currency;
		addModifiedAttribute("currency");
	}



	public boolean hasTargeting() {
		return (targeting != null || rdbTargeting != null || segmentTargeting != null || excludeTargets != null || excludeMobileDevice != null);
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

	/**
	 * Resets the modified attributes.The {@link Campaign} will be considered as
	 * an unmodified {@link Campaign} after calling this method.
	 */
	public void resetModifiedAttributes() {

		if (isModified(ATTRNAME_SEGMENTTARGETING)) {
			segmentTargeting.resetModifiedAttributes();
		}
		if (isModified(ATTRNAME_TARGETING)) {
			for (Targeting targetingValue : targeting) {
				targetingValue.resetModifiedAttributes();
			}
		}
		if (isModified(ATTRNAME_RDBTARGETING)) {
			rdbTargeting.resetModifiedAttributes();
		}
		super.resetModifiedAttributes();
	}
}
