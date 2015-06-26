package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.Data;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

@Data
public class Campaign {

	private String id;
	private String type;
	private String insertionOrderId;
	private String advertiserId;
	private String creativeTargetid;
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
	private List<String> zones;
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

	public boolean hasTargeting() {
		return (commonTargeting != null || zones != null || rdbTargeting != null || segmentTargeting != null || excludeTargets != null);
	}

	public boolean hasPrimaryFrequency() {
		return (primaryImpsPerVisitor != null || primaryClicksPerVisitor != null || primaryFrequencyScope != null);
	}

	public boolean hasSecondaryFrequency() {
		return (secondaryImpsPerVisitor != null || secondaryFrequencyScope != null);
	}
}
