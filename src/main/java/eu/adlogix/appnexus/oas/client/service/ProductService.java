
package eu.adlogix.appnexus.oas.client.service;

import eu.adlogix.appnexus.oas.client.domain.Product;

/**
 * Service which provides functions for OAS {@link Product} related activities
 */
public interface ProductService {

	/**
	 * Creates a {@link Product} on OAS
	 * 
	 * @param product
	 *            The Product to be created
	 */
	public void add(final Product product);

	/**
	 * Get {@link Product} by ID
	 * 
	 * @param productId
	 *            the Product ID
	 * @return the {@link Product}
	 */
	public Product getById(final String productId);
}
