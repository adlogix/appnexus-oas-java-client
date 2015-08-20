package eu.adlogix.appnexus.oas.client.service.impl;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

import java.util.Map;

import eu.adlogix.appnexus.oas.client.domain.Creative;
import eu.adlogix.appnexus.oas.client.service.AbstractOasService;
import eu.adlogix.appnexus.oas.client.service.CreativeService;
import eu.adlogix.appnexus.oas.client.service.OasApiService;
import eu.adlogix.appnexus.oas.client.transform.CreativeCreateParameterMapTransformer;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Default implementation of {@link CreativeService} which provides functions
 * for {@link Creative}s related operations
 */
public class DefaultCreativeService extends AbstractOasService implements CreativeService {

	private final XmlRequestGenerator addCreativeRequestGenerator = new XmlRequestGenerator("add-creative");

	public DefaultCreativeService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Adds a Creative to OAS
	 * 
	 * @param creative
	 *            {@link Creative} objects with values properly populated
	 */
	public void add(final Creative creative) {
		
		checkNotNull(creative, "creative");
		checkNotEmpty(creative.getId(), "creative ID");
		checkNotEmpty(creative.getName(), "creative Name");
		checkNotEmpty(creative.getCampaignId(), "Campaign ID of the creative");
		checkNotEmpty(creative.getClickUrl(), "Click URL of the creative");
		checkNotEmpty(creative.getPositionNames(), "Positions of the creative");
		checkNotNull(creative.getDisplay(), "Display parameter of the creative");
		checkNotNull(creative.getDiscountImpressions(), "DiscountImpressions parameter of the creative");
		checkNotNull(creative.getExpireImmediately(), "ExpireImmediately parameter of the creative");
		checkNotNull(creative.getNoCache(), "NoCache parameter of the creative");
		
		final Map<String, Object> requestParameters = new CreativeCreateParameterMapTransformer(creative).transform();

		performRequest(addCreativeRequestGenerator, requestParameters);
	}
}
