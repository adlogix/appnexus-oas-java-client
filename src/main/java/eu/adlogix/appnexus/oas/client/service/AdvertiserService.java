package eu.adlogix.appnexus.oas.client.service;

import java.util.List;

import eu.adlogix.appnexus.oas.client.domain.Advertiser;

/**
 * Service which provides functions for {@link Advertiser} related operations
 * 
 */

public interface AdvertiserService {

	/**
	 * Adds a new {@link Advertiser}
	 * 
	 * @param advertiser
	 *            {@link Advertiser}
	 * @return
	 */
	public void add(Advertiser advertiser);

	/**
	 * Updates an existing {@link Advertiser}
	 * 
	 * @param advertiser
	 *            {@link Advertiser}
	 * @return
	 */
	public void update(final Advertiser advertiser);

	/**
	 * Retrieves a list of existing {@link Advertiser}s
	 * 
	 * @return {@link List} of {@link Advertiser}s
	 */
	public List<Advertiser> getAll();

	/**
	 * Retrieves an existing {@link Advertiser} by the given advertiser id
	 * 
	 * @param id
	 *            OAS advertiser Id
	 * 
	 * @return {@link Advertiser}
	 * 
	 */
	public Advertiser getById(final String id);

}
