package eu.adlogix.appnexus.oas.client.service;

import eu.adlogix.appnexus.oas.client.domain.Creative;

/**
 * Service which provides functions for {@link Creative}s related operations
 * 
 */
public interface CreativeService {


	/**
	 * Adds a Creative to OAS
	 * 
	 * @param creative
	 *            {@link Creative} objects with values properly populated
	 */
	public void add(final Creative creative);

}
