package eu.adlogix.appnexus.oas.client.transform;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.utils.log.LogUtils;

public class CampaignCreateParameterMapTransformer extends CampaignParameterMapTransformer {

	private static final Logger logger = LogUtils.getLogger(CampaignCreateParameterMapTransformer.class);

	public CampaignCreateParameterMapTransformer(Campaign campaign) {
		super(campaign);
	}

	@Override
	protected Map<String, Object> getExcludeParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("excludedSiteIds", campaign.getExcludedSiteIds());
		parameters.put("excludedPageIds", campaign.getExcludedPageUrls());
		return parameters;
	}

	@Override
	protected Map<String, Object> getPageParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pageUrls", campaign.getPageUrls());
		return parameters;
	}

	@Override
	protected Map<String, Object> getOverviewParameters(Campaign campaign) {

		if (campaign.getStatus() != null)
			logger.warn("Status cannot be added in addCampaign. Campaign ID:" + campaign.getId() + " Status:"
					+ campaign.getStatus());

		final Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.putAll(getCommonOverviewParameters(campaign));

		parameters.put("type", campaign.getType());
		parameters.put("creativeTargetId", campaign.getCreativeTargetId());
		parameters.put("campaignGroupIds", campaign.getCampaignGroupIds());
		parameters.put("externalUserIds", campaign.getExternalUserIds());
		return parameters;
	}

	@Override
	protected Map<String, Object> getScheduleParameters(Campaign campaign) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.putAll(getCommonScheduleParameters(campaign));

		parameters.put("hourOfDay", campaign.getHourOfDay());
		parameters.put("dayOfWeek", campaign.getDayOfWeek());
		parameters.put("sectionIds", campaign.getSectionIds());

		return parameters;

	}

	@Override
	protected boolean isSegmentTargetingEmpty(SegmentTargeting segmentTargeting) {
		return segmentTargeting != null && segmentTargeting.getSegmentType() != null
				&& segmentTargeting.getValues() != null;
	}

	@Override
	protected Map<String, Object> getBillingParameters(Campaign campaign) {
		return getCommonBillingParameters(campaign);
	}

	@Override
	protected boolean injectNotNullFlagParams() {
		return false;
	}

}
