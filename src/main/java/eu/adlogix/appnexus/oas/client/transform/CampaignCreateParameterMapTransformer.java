package eu.adlogix.appnexus.oas.client.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

import org.apache.commons.collections4.MapUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.CampaignExcludableTargetValues;
import eu.adlogix.appnexus.oas.client.domain.CampaignTargetValues;
import eu.adlogix.appnexus.oas.client.domain.MobileTargetings;
import eu.adlogix.appnexus.oas.client.domain.RdbTargeting;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.domain.enums.TargetingCode;
import eu.adlogix.appnexus.oas.client.exceptions.OasClientSideException;
import eu.adlogix.appnexus.oas.client.utils.log.LogUtils;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

@AllArgsConstructor
public class CampaignCreateParameterMapTransformer extends AbstractParameterMapTransformer {

	private static final DateTimeFormatter startTimeFormatter = DateTimeFormat.forPattern("HH:00");
	private static final DateTimeFormatter endTimeFormatter = DateTimeFormat.forPattern("HH:59");

	private static final Logger logger = LogUtils.getLogger(CampaignCreateParameterMapTransformer.class);

	private Campaign campaign;

	@Override
	public Map<String, Object> transform() {

		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				putAll(getOverviewParameters(campaign));
				putAll(getScheduleParameters(campaign));
				put("pageUrls", campaign.getPageUrls());
				putAll(getTargetingParameters(campaign));
				put("excludedSiteIds", campaign.getExcludedSiteIds());
				put("excludedPageIds", campaign.getExcludedPageUrls());
				putAll(getBillingParameters(campaign));
			}
		};

		return parameters;
	}

	final Map<String, Object> getOverviewParameters(Campaign campaign) {

		if (campaign.getStatus() != null)
			logger.warn("Status cannot be added in addCampaign. Campaign ID:" + campaign.getId() + " Status:"
					+ campaign.getStatus());

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("campaignId", campaign.getId());
		parameters.put("type", campaign.getType());
		parameters.put("advertiserId", campaign.getAdvertiserId());
		parameters.put("campaignName", campaign.getName());
		parameters.put("creativeTargetId", campaign.getCreativeTargetId());
		parameters.put("agencyId", campaign.getAgencyId());
		parameters.put("campaignDescription", campaign.getDescription());
		parameters.put("campaignManagerId", campaign.getCampaignManager());
		parameters.put("productId", campaign.getProductId());
		parameters.put("campaignGroupIds", campaign.getCampaignGroupIds());
		parameters.put("competitiveCategoryIds", campaign.getCompetitiveCategroryIds());
		parameters.put("externalUserIds", campaign.getExternalUserIds());
		parameters.put("internalQuickReport", campaign.getInternalQuickReport());
		parameters.put("externalQuickReport", campaign.getExternalQuickReport());
		return parameters;
	}

	final Map<String, Object> getScheduleParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
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
		checkValueAndPutParam("strictCompanions", campaign.getStrictCompanions(), parameters);

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
		parameters.put("hourOfDay", campaign.getHourOfDay());
		parameters.put("dayOfWeek", campaign.getDayOfWeek());
		parameters.put("sectionIds", campaign.getSectionIds());
		checkValueAndPutParam("userTimeZone", campaign.getUserTimeZone(), parameters);
		return parameters;

	}

	final Map<String, Object> getTargetingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		if (campaign.hasTargeting()) {
			parameters.put("target", "target");
		}
		checkValueAndPutParam("excludeTargets", campaign.getExcludeTargets(), parameters);

		parameters.putAll(getTargetingGeneralParameters(campaign));
		parameters.putAll(getTargetingZoneParameters(campaign));
		parameters.putAll(getMobileTargetingParameters(campaign));
		parameters.putAll(getRdbTargetingParameters(campaign));
		parameters.putAll(getSegmentTargetingParameters(campaign));
		return parameters;
	}

	private Map<String, Object> getMobileTargetingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		MobileTargetings mobileTargeting = campaign.getMobileTargeting();

		if (mobileTargeting != null) {
			checkValueAndPutParam("mobileDeviceExclude", mobileTargeting.getExcludeMobileDevice(), parameters);
			addTargetingsToParameters(mobileTargeting.getTargetings(), parameters);
		}
		return parameters;
	}

	private Map<String, Object> getTargetingZoneParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		addTargetingToParameters(TargetingCode.ZONE, campaign.getZoneTargeting(), parameters);
		return parameters;
	}

	final Map<String, Object> getTargetingGeneralParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		final Map<TargetingCode, CampaignExcludableTargetValues> targetings = campaign.getTargetings();
		addTargetingsToParameters(targetings, parameters);
		return parameters;
	}

	private void addTargetingsToParameters(final Map<TargetingCode, ? extends CampaignTargetValues> targetings,
			final Map<String, Object> parameters) {

		if (MapUtils.isNotEmpty(targetings)) {
			for (Map.Entry<TargetingCode, ? extends CampaignTargetValues> targeting : targetings.entrySet()) {
				addTargetingToParameters(targeting.getKey(), targeting.getValue(), parameters);
			}
		}
	}

	private void addTargetingToParameters(TargetingCode code, CampaignTargetValues targetingValues,
			final Map<String, Object> parameters) {

		if (code != null && targetingValues != null) {

			checkNotNull(code, "Targeting code of targeting object having values " + targetingValues.getValues());

			final String targetingType = code.getCodeForCampaigns().toString().toLowerCase();

			if (code.isSupportingExcludeFlagForCampaigns() != targetingValues.isSupportingExcludeFlag()) {
				throw new OasClientSideException("Targeting code " + code.name()
						+ " expects an Exclude flag but TargetingValues object doesn't support an exclude flag");
			}

			if (targetingValues.isSupportingExcludeFlag()) {
				checkValueAndPutParam(targetingType + "Exclude", ((CampaignExcludableTargetValues) targetingValues).getExclude(), parameters);
			}

			final List<String> values = targetingValues.getValues();
			checkValueAndPutParam(targetingType, values, parameters);
		}
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
		if (segmentTargeting != null && segmentTargeting.getSegmentType() != null
				&& segmentTargeting.getValues() != null) {
			parameters.put("segmentTargeting", "segmentTargeting");
			parameters.put("segmentType", segmentTargeting.getSegmentType());
			parameters.put("segmentcluster", segmentTargeting.getValues());
			checkValueAndPutParam("segmentclusterExclude", segmentTargeting.getExclude(), parameters);

		}
		return parameters;
	}

	final Map<String, Object> getBillingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cpm", campaign.getCpm());
		parameters.put("cpc", campaign.getCpc());
		parameters.put("cpa", campaign.getCpa());
		parameters.put("flatRate", campaign.getFlatRate());
		parameters.put("tax", campaign.getTax());
		parameters.put("agencyCommission", campaign.getAgencyCommission());
		parameters.put("paymentMethod", campaign.getPaymentMethod());
		checkValueAndPutParam("isYieldManaged", campaign.getIsYieldManaged(), parameters);
		parameters.put("billTo", campaign.getBillTo());
		parameters.put("currency", campaign.getCurrency());
		return parameters;
	}

}
