package eu.adlogix.appnexus.oas.client.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.RdbTargeting;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.domain.Targeting;

@AllArgsConstructor
public class CampaignUpdateParameterMapTransformer extends AbstractParameterMapTransformer {

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
		parameters.put("competitiveCategoryIds", campaign.getCompetitiveCategroryIds());
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
			checkValueAndPutParam("campaignEndDate", campaign.getEndDate(), parameters);
			parameters.put("campaignReach", campaign.getReach());
			parameters.put("dailyCampaignImpressions", campaign.getDailyImps());
			parameters.put("dailyCampaignClicks", campaign.getDailyClicks());
			parameters.put("dailyCampaignUniques", campaign.getDailyUniques());
			parameters.put("campaignDailyDeliveryDate", campaign.getSmoothOrAsap());
			parameters.put("impressionsOverrun", campaign.getImpressionsOverrun());
			parameters.put("strictCompanions", campaign.getStrictCompanions());

			if (campaign.hasSecondaryFrequency()) {
				parameters.put("secondaryFrequency", "secondaryFrequency");
				parameters.put("secondaryImpressionsPerVisitor", campaign.getSecondaryImpsPerVisitor());
				parameters.put("secondaryFrequencyScope", campaign.getSecondaryFrequencyScope());
			}
			parameters.put("hourOfDay", campaign.getHourOfDay());
			parameters.put("dayOfWeek", campaign.getDayOfWeek());
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
		parameters.put("zone", campaign.getZones());
		return parameters;
	}

	final Map<String, Object> getCommonTargetingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		if (!CollectionUtils.isEmpty(campaign.getCommonTargeting())) {

			for (Targeting targeting : campaign.getCommonTargeting()) {
				String targetingType = targeting.getTargetingType().toString().toLowerCase();
				checkValueAndPutParam(targetingType + "Exclude", targeting.getExculde(), parameters);

				final List<String> values = targeting.getValues();
				checkValueAndPutParam(targetingType, values, parameters);
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
		if (segmentTargeting != null && segmentTargeting.getSegmentClusterMatch() != null
				&& segmentTargeting.getValues() != null) {
			parameters.put("segmentTargeting", "segmentTargeting");
			parameters.put("segmentType", segmentTargeting.getSegmentClusterMatch());
			parameters.put("segmentcluster", segmentTargeting.getValues());
			checkValueAndPutParam("segmentclusterExclude", segmentTargeting.getExculde(), parameters);

		}
		return parameters;
	}

	final Map<String, Object> getBillingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		if (campaign.hasBilling()) {
			parameters.put("hasBilling", campaign.hasBilling());
			parameters.put("cpm", campaign.getCpm());
			parameters.put("cpc", campaign.getCpc());
		}
		return parameters;
	}

}
