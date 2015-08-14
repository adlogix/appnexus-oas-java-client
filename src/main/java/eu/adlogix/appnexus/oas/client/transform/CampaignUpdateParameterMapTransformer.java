package eu.adlogix.appnexus.oas.client.transform;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.utils.log.LogUtils;

public class CampaignUpdateParameterMapTransformer extends CampaignParameterMapTransformer {

	private static final Logger logger = LogUtils.getLogger(CampaignUpdateParameterMapTransformer.class);

	public CampaignUpdateParameterMapTransformer(Campaign campaign) {
		super(campaign);
	}

	@Override
	protected Map<String, Object> getPageParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		if (campaign.getPageUrls() != null) {
			parameters.put("pageUrlsNotNull", true);
			parameters.put("pageUrls", campaign.getPageUrls());
		}
		return parameters;
	}

	@Override
	protected Map<String, Object> getExcludeParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		if (campaign.hasExclude()) {
			parameters.put("hasExclude", true);
			if (campaign.getExcludedSiteIds() != null) {
				parameters.put("excludedSiteIdsNotNull", true);
				parameters.put("excludedSiteIds", campaign.getExcludedSiteIds());
			}

			if (campaign.getExcludedPageUrls() != null) {
				parameters.put("excludedPageIdsNotNull", true);
				parameters.put("excludedPageIds", campaign.getExcludedPageUrls());
			}
		}
		return parameters;
	}

	@Override
	protected Map<String, Object> getOverviewParameters(Campaign campaign) {

		if (campaign.getType() != null)
			logger.warn("Type cannot be updated in updateCampaign. Campaign ID:" + campaign.getId() + " Type:"
					+ campaign.getType());

		if (StringUtils.isNotEmpty(campaign.getCreativeTargetId()))
			logger.warn("Creative Target ID cannot be updated in updateCampaign. Campaign ID:" + campaign.getId()
					+ " Creative Target ID:" + campaign.getCreativeTargetId());

		final Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.putAll(getCommonOverviewParameters(campaign));

		parameters.put("status", campaign.getStatus());

		if (campaign.getExternalUserIds() != null) {
			parameters.put("externalUserIdsNotNull", true);
			parameters.put("externalUserIds", campaign.getExternalUserIds());
		}
		if (campaign.getCampaignGroupIds() != null) {
			parameters.put("campaignGroupIdsNotNull", true);
			parameters.put("campaignGroupIds", campaign.getCampaignGroupIds());
		}
		return parameters;
	}

	@Override
	protected Map<String, Object> getScheduleParameters(Campaign campaign) {

		final Map<String, Object> parameters = new HashMap<String, Object>();
		if (campaign.hasSchedule()) {

			parameters.put("hasSchedule", campaign.hasSchedule());

			parameters.putAll(getCommonScheduleParameters(campaign));

			if (campaign.getHourOfDay() != null) {
				parameters.put("hourOfDayIsNotNull", true);
				parameters.put("hourOfDay", campaign.getHourOfDay());
			}

			if (campaign.getDayOfWeek() != null) {
				parameters.put("dayOfWeekIsNotNull", true);
				parameters.put("dayOfWeek", campaign.getDayOfWeek());
			}

			if (campaign.getSectionIds() != null) {
				parameters.put("sectionIdsNotNull", true);
				parameters.put("sectionIds", campaign.getSectionIds());
			}

		}
		return parameters;

	}

	@Override
	protected boolean isSegmentTargetingEmpty(SegmentTargeting segmentTargeting) {
		return segmentTargeting != null;
	}

	@Override
	protected Map<String, Object> getBillingParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		if (campaign.hasBilling()) {

			parameters.put("hasBilling", campaign.hasBilling());

			parameters.putAll(getCommonBillingParameters(campaign));
		}
		return parameters;
	}

	@Override
	protected boolean injectNotNullFlagParams() {
		return true;
	}

}
