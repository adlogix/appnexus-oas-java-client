package eu.adlogix.appnexus.oas.client.service;

import eu.adlogix.appnexus.oas.client.domain.Campaign;

/**
 * Service which provides functions for {@link Campaign} related operations
 * 
 */
public interface CampaignService {

	/**
	 * Retrieves an existing {@link Campaign} by the given campaign id
	 * 
	 * @param id
	 *            OAS campaign Id
	 * 
	 * @return {@link Campaign}
	 * 
	 */
	public Campaign getById(final String id);

	/**
	 * Adds a new {@link Campaign}
	 * 
	 * @param campaign
	 *            {@link Campaign}
	 * @return
	 */

	public void add(Campaign campaign);

	/**
	 * Updates an existing {@link Campaign}
	 * 
	 * @param campaign
	 *            {@link Campaign}
	 * @return
	 */
	public void update(Campaign campaign);


}
