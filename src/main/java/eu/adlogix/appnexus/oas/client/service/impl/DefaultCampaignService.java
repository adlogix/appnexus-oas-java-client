package eu.adlogix.appnexus.oas.client.service.impl;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

import java.util.HashMap;
import java.util.Map;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.StatefulDomainManager;
import eu.adlogix.appnexus.oas.client.domain.enums.BillTo;
import eu.adlogix.appnexus.oas.client.domain.enums.CampaignType;
import eu.adlogix.appnexus.oas.client.parser.XmlToCampaignParser;
import eu.adlogix.appnexus.oas.client.service.AbstractOasService;
import eu.adlogix.appnexus.oas.client.service.CampaignService;
import eu.adlogix.appnexus.oas.client.service.OasApiService;
import eu.adlogix.appnexus.oas.client.transform.CampaignCreateParameterMapTransformer;
import eu.adlogix.appnexus.oas.client.transform.CampaignParameterMapTransformer;
import eu.adlogix.appnexus.oas.client.transform.CampaignUpdateParameterMapTransformer;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Default implementation of {@link CampaignService} which provides functions
 * for Campaign related operations
 * 
 */
public class DefaultCampaignService extends AbstractOasService implements CampaignService {

	private final XmlRequestGenerator readCampaignRequestGenerator = new XmlRequestGenerator("read-campaign");
	private final XmlRequestGenerator addCampaignRequestGenerator = new XmlRequestGenerator("add-campaign");
	private final XmlRequestGenerator updateCampaignRequestGenerator = new XmlRequestGenerator("update-campaign");

	public DefaultCampaignService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Retrieves an existing {@link Campaign} by the given campaign id
	 * 
	 * @param id
	 *            OAS campaign Id
	 * 
	 * @return {@link Campaign}
	 * 
	 */
	final public Campaign getById(final String id) {

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

	/**
	 * Adds a new {@link Campaign}
	 * 
	 * @param campaign
	 *            {@link Campaign}
	 * @return
	 */

	public final void add(Campaign campaign) {

		checkNotEmpty(campaign.getId(), "campaignId");
		checkNotEmpty(campaign.getAdvertiserId(), "advertiserId");
		checkNotEmpty(campaign.getName(), "campaignName");
		checkNotEmpty(campaign.getAgencyId(), "agencyId");
		checkNotEmpty(campaign.getProductId(), "productId");
		checkNotNull(campaign.getPriorityLevel(), "priorityLevel");
		checkNotNull(campaign.getReach(), "reach");
		checkNotNull(campaign.getSmoothOrAsap(), "smoothOrAsap");
		checkNotNull(campaign.getCompletion(), "completion");
		checkNotNull(campaign.getPaymentMethod(), "paymentMethod");
		if (campaign.getType() != null && campaign.getType() == CampaignType.CLT) {
			checkNotEmpty(campaign.getCreativeTargetId(), "creativeTargetId");
		}

		final Campaign campaignWithDefaults = setDefaultsForEmptyFields(campaign);

		CampaignParameterMapTransformer parameterTransformer = new CampaignCreateParameterMapTransformer(campaignWithDefaults);

		performRequest(addCampaignRequestGenerator, parameterTransformer.transform());

		campaign.resetModifiedAttributes();
	}

	/**
	 * Updates an existing {@link Campaign}
	 * 
	 * @param campaign
	 *            {@link Campaign}
	 * @return
	 */
	public final void update(Campaign campaign) {

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
		campaign.setIsYieldManaged(campaign.getIsYieldManaged() == null ? false : campaign.getIsYieldManaged());
		campaign.setBillTo(campaign.getBillTo() == null ? BillTo.AGENCY : campaign.getBillTo());
		if (campaign.hasTargeting() && campaign.getExcludeTargets() == null) {
			campaign.setExcludeTargets(new Boolean(false));
		}
		return campaign;
	}

}
