package eu.adlogix.appnexus.oas.client.service;

import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;

/**
 * Service which provides functions for {@link InsertionOrder} related
 * operations
 * 
 */

public interface InsertionOrderService {

	/**
	 * Adds a new {@link InsertionOrder}
	 * 
	 * @param insertionOrder
	 *            {@link InsertionOrder}
	 * @return
	 */
	public void add(final InsertionOrder insertionOrder);


	/**
	 * Updates an existing {@link InsertionOrder}
	 * 
	 * @param insertionOrder
	 *            {@link InsertionOrder}
	 * @return
	 */
	public void update(final InsertionOrder insertionOrder);


	/**
	 * Retrieves an existing {@link InsertionOrder} by the given insertion order
	 * id
	 * 
	 * @param id
	 *            OAS insertion order Id
	 * 
	 * @return {@link InsertionOrder}
	 * 
	 */
	public InsertionOrder getById(final String id);

}
