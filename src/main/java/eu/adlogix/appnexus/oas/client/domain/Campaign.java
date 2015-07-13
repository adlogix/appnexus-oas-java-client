package eu.adlogix.appnexus.oas.client.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Domain class which represents an OAS Campaign. This can be used in Campaign
 * creation, update and retrieval operations.
 */
@Getter
public class Campaign extends StatefulDomainWithId {

	private static final String ATTRNAME_MOBILETARGETING = "mobileTargeting";
	private static final String ATTRNAME_RDBTARGETING = "rdbTargeting";
	private static final String ATTRNAME_SEGMENTTARGETING = "segmentTargeting";
	private static final String ATTRNAME_TARGETING = "targetings";

	private CampaignType type;
	private String insertionOrderId;
	private String advertiserId;
	private String creativeTargetId;
	private String name;
	private String agencyId;
	private String description;
	private String campaignManager;
	private String productId;
	private CampaignStatus status;
	private List<String> campaignGroupIds;
	private List<String> competitiveCategroryIds;
	private List<String> externalUserIds;
	private String internalQuickReport;
	private String externalQuickReport;
	private Long impressions;
	private Long clicks;
	private Long uniques;
	private Long weight;
	private Long priorityLevel;
	private Completion completion;
	private LocalDate startDate;
	private LocalTime startTime;
	private LocalDate endDate;
	private LocalTime endTime;
	private Reach reach;
	private Long dailyImps;
	private Long dailyClicks;
	private Long dailyUniques;
	private SmoothAsap smoothOrAsap;
	private Long impressionsOverrun;
	private List<String> companionPositions;
	private Boolean strictCompanions;
	private Long primaryImpsPerVisitor;
	private Long primaryClicksPerVisitor;
	private FrequencyScope primaryFrequencyScope;
	private Long secondaryImpsPerVisitor;
	private FrequencyScope secondaryFrequencyScope;
	private List<HourOfDay> hourOfDay;
	private List<DayOfWeek> dayOfWeek;
	private Boolean userTimeZone;
	private List<String> sectionIds;
	private List<String> pageUrls;

	private Boolean excludeTargets;

	private Map<TargetingCode, CampaignExcludableTargetValues> targetings;
	private CampaignTargetValues zoneTargeting;
	private MobileTargetings mobileTargeting;
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
	private PaymentMethod paymentMethod;
	private Boolean isYieldManaged;
	private BillTo billTo;
	private String currency;

	public void setType(CampaignType type) {
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

	public void setStatus(CampaignStatus status) {
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

	public void setPriorityLevel(Long priorityLevel) {
		this.priorityLevel = priorityLevel;
		addModifiedAttribute("priorityLevel");
	}

	public void setCompletion(Completion completion) {
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

	public void setReach(Reach reach) {
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

	public void setSmoothOrAsap(SmoothAsap smoothOrAsap) {
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

	public void setStrictCompanions(Boolean strictCompanions) {
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

	public void setPrimaryFrequencyScope(FrequencyScope primaryFrequencyScope) {
		this.primaryFrequencyScope = primaryFrequencyScope;
		addModifiedAttribute("primaryFrequencyScope");
	}

	public void setSecondaryImpsPerVisitor(Long secondaryImpsPerVisitor) {
		this.secondaryImpsPerVisitor = secondaryImpsPerVisitor;
		addModifiedAttribute("secondaryImpsPerVisitor");
	}

	public void setSecondaryFrequencyScope(FrequencyScope secondaryFrequencyScope) {
		this.secondaryFrequencyScope = secondaryFrequencyScope;
		addModifiedAttribute("secondaryFrequencyScope");
	}

	public void setHourOfDay(List<HourOfDay> hourOfDay) {
		this.hourOfDay = Collections.unmodifiableList(hourOfDay);
		addModifiedAttribute("hourOfDay");
	}

	public void setDayOfWeek(List<DayOfWeek> dayOfWeek) {
		this.dayOfWeek = Collections.unmodifiableList(dayOfWeek);
		addModifiedAttribute("dayOfWeek");
	}

	public void setUserTimeZone(Boolean userTimeZone) {
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

	public void setZoneTargeting(CampaignTargetValues zoneTargeting) {
		this.zoneTargeting = zoneTargeting;
		addModifiedAttribute("zoneTargeting");
	}

	public void setMobileTargeting(MobileTargetings mobileTargeting) {
		this.mobileTargeting = mobileTargeting;
		addModifiedAttribute(ATTRNAME_MOBILETARGETING);
	}

	public void setTopDomainTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.TOP_DOMAIN, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getTopDomainTargeting() {
		return targetings.get(TargetingCode.TOP_DOMAIN);
	}

	public void setBandwidthTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.BANDWIDTH, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getBandwidthTargeting() {
		return targetings.get(TargetingCode.BANDWIDTH);
	}

	public void setContinentTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.CONTINENT, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getContinentTargeting() {
		return targetings.get(TargetingCode.CONTINENT);
	}

	public void setCountryTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.COUNTRY, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getCountryTargeting() {
		return targetings.get(TargetingCode.COUNTRY);
	}

	public void setStateTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.STATE, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getStateTargeting() {
		return targetings.get(TargetingCode.STATE);
	}

	public void setMsaTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.MSA, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getMsaTargeting() {
		return targetings.get(TargetingCode.MSA);
	}

	public void setDmaTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.DMA, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getDmaTargeting() {
		return targetings.get(TargetingCode.DMA);
	}

	public void setOsTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.OS, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getOsTargeting() {
		return targetings.get(TargetingCode.OS);
	}

	public void setBrowserTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.BROWSER, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getBrowserTargeting() {
		return targetings.get(TargetingCode.BROWSER);
	}

	public void setBrowserVTargeting(CampaignExcludableTargetValues campaignExcludableTarget) {
		addTargeting(TargetingCode.BROWSER_VERSIONS, campaignExcludableTarget);
	}

	public CampaignExcludableTargetValues getBrowserVTargeting() {
		return targetings.get(TargetingCode.BROWSER_VERSIONS);
	}

	private void addTargeting(TargetingCode code, CampaignExcludableTargetValues campaignExcludableTarget) {

		final Map<TargetingCode, CampaignExcludableTargetValues> targetings = this.targetings != null ? new HashMap<TargetingCode, CampaignExcludableTargetValues>(this.targetings)
				: new HashMap<TargetingCode, CampaignExcludableTargetValues>();

		targetings.put(code, campaignExcludableTarget);
		setTargetings(targetings);
	}

	public void setTargetings(Map<TargetingCode, CampaignExcludableTargetValues> targetings) {
		this.targetings = Collections.unmodifiableMap(targetings);
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

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
		addModifiedAttribute("paymentMethod");
	}

	public void setIsYieldManaged(Boolean isYieldManaged) {
		this.isYieldManaged = isYieldManaged;
		addModifiedAttribute("isYieldManaged");
	}

	public void setBillTo(BillTo billTo) {
		this.billTo = billTo;
		addModifiedAttribute("billTo");
	}

	public void setCurrency(String currency) {
		this.currency = currency;
		addModifiedAttribute("currency");
	}



	/**
	 * Checks if the {@link Campaign} has Targeting
	 * 
	 * @return true if {@link Campaign} has Targeting, false if {@link Campaign}
	 *         doesn't have Targeting.
	 */
	public boolean hasTargeting() {
		return (targetings != null || rdbTargeting != null || segmentTargeting != null || excludeTargets != null
				|| mobileTargeting != null || zoneTargeting != null);
	}

	/**
	 * Checks if the {@link Campaign} has Primary Frequency
	 * 
	 * @return true if {@link Campaign} has Primary Frequency, false if
	 *         {@link Campaign} doesn't have Primary Frequency.
	 */
	public boolean hasPrimaryFrequency() {
		return (primaryImpsPerVisitor != null || primaryClicksPerVisitor != null || primaryFrequencyScope != null);
	}

	/**
	 * Checks if the {@link Campaign} has Secondary Frequency
	 * 
	 * @return true if {@link Campaign} has Secondary Frequency, false if
	 *         {@link Campaign} doesn't have Secondary Frequency.
	 */
	public boolean hasSecondaryFrequency() {
		return (secondaryImpsPerVisitor != null || secondaryFrequencyScope != null);
	}

	/**
	 * Checks if the {@link Campaign} has Schedule Section
	 * 
	 * @return true if {@link Campaign} has Schedule, false if {@link Campaign}
	 *         doesn't have Schedule.
	 */
	public boolean hasSchedule() {
		return (impressions != null || clicks != null || uniques != null || weight != null || priorityLevel != null
				|| completion != null || startDate != null || endDate != null || reach != null || dailyImps != null
				|| dailyClicks != null || dailyUniques != null || smoothOrAsap != null || impressionsOverrun != null
				|| strictCompanions != null || hasSecondaryFrequency() || hourOfDay != null || dayOfWeek != null
				|| userTimeZone != null || startTime != null || endTime != null || companionPositions != null
				|| hasPrimaryFrequency() || sectionIds != null);
	}

	/**
	 * Checks if the {@link Campaign} has Exclude Section
	 * 
	 * @return true if {@link Campaign} has Exclude, false if {@link Campaign}
	 *         doesn't have Exclude.
	 */
	public boolean hasExclude() {
		return excludedPageUrls != null || excludedSiteIds != null;
	}

	/**
	 * Checks if the {@link Campaign} has Billing Section
	 * 
	 * @return true if {@link Campaign} has Billing, false if {@link Campaign}
	 *         doesn't have Billing.
	 */
	public boolean hasBilling() {
		return (cpm != null || cpc != null || cpa != null || flatRate != null || tax != null
				|| agencyCommission != null || paymentMethod != null || isYieldManaged != null || billTo != null || currency != null);
	}

	/**
	 * Resets the modified attributes.The {@link Campaign} will be considered as
	 * an unmodified {@link Campaign} after calling this method.
	 */
	public void resetModifiedAttributes() {

		if (isModified(ATTRNAME_TARGETING)) {
			resetModifiedTargetingAttributes();
		}
		if (isModified(ATTRNAME_SEGMENTTARGETING) && segmentTargeting != null) {
			segmentTargeting.resetModifiedAttributes();
		}
		if (isModified(ATTRNAME_RDBTARGETING) && rdbTargeting != null) {
			rdbTargeting.resetModifiedAttributes();
		}
		if (isModified(ATTRNAME_MOBILETARGETING) && mobileTargeting != null) {
			mobileTargeting.resetModifiedAttributes();
		}
		super.resetModifiedAttributes();
	}

	private void resetModifiedTargetingAttributes() {
		for (Entry<TargetingCode, CampaignExcludableTargetValues> targetingValue : targetings.entrySet()) {
			if (targetingValue.getValue() != null) {
				targetingValue.getValue().resetModifiedAttributes();
			}
		}
	}
}
