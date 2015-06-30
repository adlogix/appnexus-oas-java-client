package eu.adlogix.appnexus.oas.client.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.ExcludableTargeting;
import eu.adlogix.appnexus.oas.client.domain.RdbTargeting;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.domain.Targeting;

@AllArgsConstructor
public class CampaignUpdateParameterMapTransformer extends AbstractParameterMapTransformer {

	private static final DateTimeFormatter startTimeFormatter = DateTimeFormat.forPattern("HH:00");
	private static final DateTimeFormatter endTimeFormatter = DateTimeFormat.forPattern("HH:59");

	private Campaign campaign;

	@Override
	public Map<String, Object> transform() {

		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				putAll(getOverviewParameters(campaign));
				putAll(getScheduleParameters(campaign));
				putAll(getTargetingParameters(campaign));
				put("excludedSiteIds", campaign.getExcludedSiteIds());
				put("excludedPageIds", campaign.getExcludedPageUrls());
				putAll(getBillingParameters(campaign));
			}
		};

		return parameters;
	}

	final Map<String, Object> getOverviewParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("campaignId", campaign.getId());
		parameters.put("status", campaign.getStatus());
		parameters.put("advertiserId", campaign.getAdvertiserId());
		parameters.put("campaignName", campaign.getName());
		parameters.put("agencyId", campaign.getAgencyId());
		parameters.put("campaignDescription", campaign.getDescription());
		parameters.put("campaignManagerId", campaign.getCampaignManager());
		parameters.put("productId", campaign.getProductId());
		parameters.put("competitiveCategoryIds", campaign.getCompetitiveCategroryIds());
		parameters.put("internalQuickReport", campaign.getInternalQuickReport());
		parameters.put("externalQuickReport", campaign.getExternalQuickReport());
		return parameters;
	}

	final Map<String, Object> getScheduleParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		if (campaign.hasSchedule()) {
			parameters.put("hasSchedule", campaign.hasSchedule());
			parameters.put("totalCampaignImpressions", campaign.getImpressions());
			parameters.put("totalCampaignClicks", campaign.getClicks());
			parameters.put("totalCampaignUniques", campaign.getUniques());
			parameters.put("campaignWeight", campaign.getWeight());
			parameters.put("campaignPriority", campaign.getPriorityLevel());
			parameters.put("campaignCompletionStrategy", campaign.getCompletion());
			checkValueAndPutParam("campaignStartDate", campaign.getStartDate(), parameters);
			checkValueAndPutParam("campaignStartTime", campaign.getStartTime(), startTimeFormatter, parameters);
			checkValueAndPutParam("campaignEndDate", campaign.getEndDate(), parameters);
			checkValueAndPutParam("campaignEndTime", campaign.getEndTime(), endTimeFormatter, parameters);
			parameters.put("campaignReach", campaign.getReach());
			parameters.put("dailyCampaignImpressions", campaign.getDailyImps());
			parameters.put("dailyCampaignClicks", campaign.getDailyClicks());
			parameters.put("dailyCampaignUniques", campaign.getDailyUniques());
			parameters.put("campaignDailyDeliveryDate", campaign.getSmoothOrAsap());
			parameters.put("impressionsOverrun", campaign.getImpressionsOverrun());
			parameters.put("companionPositions", campaign.getCompanionPositions());
			parameters.put("strictCompanions", campaign.getStrictCompanions());

			if (campaign.hasPrimaryFrequency()) {
				parameters.put("primaryFrequency", "primaryFrequency");
				parameters.put("primaryImpressionsPerVisitor", campaign.getPrimaryImpsPerVisitor());
				parameters.put("primaryClicksPerVisitor", campaign.getPrimaryClicksPerVisitor());
				parameters.put("primaryFrequencyScope", campaign.getPrimaryFrequencyScope());
			}
			if (campaign.hasSecondaryFrequency()) {
				parameters.put("secondaryFrequency", "secondaryFrequency");
				parameters.put("secondaryImpressionsPerVisitor", campaign.getSecondaryImpsPerVisitor());
				parameters.put("secondaryFrequencyScope", campaign.getSecondaryFrequencyScope());
			}

			if (campaign.getHourOfDay() != null) {
				parameters.put("hourOfDayIsNotNull", true);
				parameters.put("hourOfDay", campaign.getHourOfDay());
			}

			if (campaign.getDayOfWeek() != null) {
				parameters.put("dayOfWeekIsNotNull", true);
				parameters.put("dayOfWeek", campaign.getDayOfWeek());
			}

			parameters.put("userTimeZone", campaign.getUserTimeZone());

		}
		return parameters;

	}

	final Map<String, Object> getTargetingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		if (campaign.hasTargeting()) {
			parameters.put("target", "target");
		}
		checkValueAndPutParam("excludeTargets", campaign.getExcludeTargets(), parameters);
		parameters.putAll(getCommonTargetingParameters(campaign));
		parameters.putAll(getRdbTargetingParameters(campaign));
		parameters.putAll(getSegmentTargetingParameters(campaign));

		return parameters;
	}

	final Map<String, Object> getCommonTargetingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		if (!CollectionUtils.isEmpty(campaign.getCommonTargeting())) {

			for (Targeting targeting : campaign.getCommonTargeting()) {
				final String targetingType = targeting.getCode().getCodeForCampaigns().toString().toLowerCase();
				
				if (targeting.isSupportingExcludeFlag()) {
					checkValueAndPutParam(targetingType + "Exclude", ((ExcludableTargeting) targeting).getExclude(), parameters);
				}

				final List<String> values = targeting.getValues();
				if (values != null) {
					parameters.put(targetingType + "IsNotNull", true);
					parameters.put(targetingType, values);
				}
			}
		}
		return parameters;
	}

	final Map<String, Object> getRdbTargetingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		RdbTargeting rdbTargeting = campaign.getRdbTargeting();

		if (rdbTargeting != null) {
			checkValueAndPutParam("ageExclude", rdbTargeting.getAgeExclude(), parameters);
			parameters.put("ageFrom", rdbTargeting.getAgeFrom());
			parameters.put("ageTo", rdbTargeting.getAgeTo());
			checkValueAndPutParam("genderExclude", rdbTargeting.getGenderExclude(), parameters);
			parameters.put("gender", rdbTargeting.getGender());
			parameters.put("incomeExclude", rdbTargeting.getIncomeExclude());
			checkValueAndPutParam("incomeExclude", rdbTargeting.getIncomeExclude(), parameters);
			parameters.put("incomeFrom", rdbTargeting.getIncomeFrom());
			parameters.put("incomeTo", rdbTargeting.getIncomeTo());
			parameters.put("subscriberExclude", rdbTargeting.getSubscriberCodeExclude());
			checkValueAndPutParam("subscriberExclude", rdbTargeting.getSubscriberCodeExclude(), parameters);
			parameters.put("subscriber", rdbTargeting.getSubscriberCode());
			checkValueAndPutParam("preferenceExclude", rdbTargeting.getPreferenceFlagsExclude(), parameters);
			parameters.put("preference", rdbTargeting.getPreferenceFlags());
		}
		return parameters;
	}

	final Map<String, Object> getSegmentTargetingParameters(Campaign campaign) {

		final Map<String, Object> parameters = new HashMap<String, Object>();

		SegmentTargeting segmentTargeting = campaign.getSegmentTargeting();
		if (segmentTargeting != null) {
			parameters.put("segmentTargeting", "segmentTargeting");
			parameters.put("segmentType", segmentTargeting.getSegmentClusterMatch());
			parameters.put("segmentcluster", segmentTargeting.getValues());
			checkValueAndPutParam("segmentclusterExclude", segmentTargeting.getExclude(), parameters);

		}
		return parameters;
	}

	final Map<String, Object> getBillingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		if (campaign.hasBilling()) {
			parameters.put("hasBilling", campaign.hasBilling());
			parameters.put("cpm", campaign.getCpm());
			parameters.put("cpc", campaign.getCpc());
			parameters.put("cpa", campaign.getCpa());
			parameters.put("flatRate", campaign.getFlatRate());
			parameters.put("tax", campaign.getTax());
			parameters.put("agencyCommission", campaign.getAgencyCommission());
			parameters.put("paymentMethod", campaign.getPaymentMethod());
			parameters.put("isYieldManaged", campaign.getIsYieldManaged());
			parameters.put("billTo", campaign.getBillTo());
			parameters.put("currency", campaign.getCurrency());
		}
		return parameters;
	}

}
