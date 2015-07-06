package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.StatefulDomainManager;
import eu.adlogix.appnexus.oas.client.parser.XmlToCampaignParser;
import eu.adlogix.appnexus.oas.client.transform.CampaignCreateParameterMapTransformer;
import eu.adlogix.appnexus.oas.client.transform.CampaignUpdateParameterMapTransformer;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

public class CampaignService extends AbstractOasService {

	protected CampaignService(OasApiService apiService) {
		super(apiService);
	}

	private final XmlRequestGenerator readCampaignRequestGenerator = new XmlRequestGenerator("read-campaign");
	private final XmlRequestGenerator addCampaignRequestGenerator = new XmlRequestGenerator("add-campaign");
	private final XmlRequestGenerator updateCampaignRequestGenerator = new XmlRequestGenerator("update-campaign");

	final public Campaign getCampaignById(final String id) {

		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("campaignId", id);
			}
		};
		final ResponseParser responseParser = performRequest(readCampaignRequestGenerator, parameters);

		XmlToCampaignParser campaignParser = new XmlToCampaignParser(responseParser);
		Campaign campaign = campaignParser.parse();

		campaign.resetModifiedAttributes();

		return campaign;
	}

	public final void addCampaign(Campaign campaign) {

		checkNotEmpty(campaign.getId(), "campaignId");
		checkNotEmpty(campaign.getAdvertiserId(), "advertiserId");
		checkNotEmpty(campaign.getName(), "campaignName");
		checkNotEmpty(campaign.getAgencyId(), "agencyId");
		checkNotEmpty(campaign.getProductId(), "productId");
		checkNotNull(campaign.getPriorityLevel(), "priorityLevel");
		checkNotEmpty(campaign.getReach(), "reach");
		checkNotEmpty(campaign.getSmoothOrAsap(), "smoothOrAsap");
		checkNotEmpty(campaign.getCompletion(), "completion");
		checkNotEmpty(campaign.getPaymentMethod(), "paymentMethod");
		if (StringUtils.equalsIgnoreCase("CLT", campaign.getType())) {
			checkNotEmpty(campaign.getCreativeTargetId(), "creativeTargetId");
		}

		final Campaign campaignWithDefaults = setDefaultsForEmptyFields(campaign);

		CampaignCreateParameterMapTransformer parameterTransformer = new CampaignCreateParameterMapTransformer(campaignWithDefaults);

		performRequest(addCampaignRequestGenerator, parameterTransformer.transform());

		campaign.resetModifiedAttributes();
	}

	public final void updateCampaign(Campaign campaign) {

		checkNotEmpty(campaign.getId(), "campaignId");

		final Campaign modifiedCampaign = new StatefulDomainManager().getModifiedObject(campaign);

		CampaignUpdateParameterMapTransformer parameterTransformer = new CampaignUpdateParameterMapTransformer(modifiedCampaign);

		performRequest(updateCampaignRequestGenerator, parameterTransformer.transform());

		campaign.resetModifiedAttributes();

	}


	/**
	 * Sets default values for the empty fields of the {@link Campaign} object
	 * 
	 * @param campaign
	 * @return {@link Campaign} object with default values
	 */
	private Campaign setDefaultsForEmptyFields(Campaign campaign) {

		campaign.setInternalQuickReport(defaultIfEmpty(campaign.getInternalQuickReport(), "to-date"));
		campaign.setExternalQuickReport(defaultIfEmpty(campaign.getExternalQuickReport(), "short"));
		campaign.setIsYieldManaged(defaultIfEmpty(campaign.getIsYieldManaged(), "N"));
		campaign.setBillTo(defaultIfEmpty(campaign.getBillTo(), "G"));
		if (campaign.hasTargeting() && campaign.getExcludeTargets() == null) {
			campaign.setExcludeTargets(new Boolean(false));
		}
		return campaign;
	}


}
