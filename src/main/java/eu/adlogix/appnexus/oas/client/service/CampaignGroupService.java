package eu.adlogix.appnexus.oas.client.service;

import eu.adlogix.appnexus.oas.client.domain.CampaignGroup;

/**
 * Service for OAS {@link CampaignGroup} related activities
 */
public interface CampaignGroupService {

	/**
	 * Creates a Campaign Group if it doesn't exist
	 * 
	 * @param group
	 *            The campaignGroup to create on OAS
	 */
	public void add(final CampaignGroup group);

}
