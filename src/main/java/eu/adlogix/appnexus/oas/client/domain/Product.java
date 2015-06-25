/**
 * 
 */
package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * OAS Product
 */
@AllArgsConstructor
@Data
public class Product {
	/**
	 * Unique ID of the product
	 */
	private final String id;
	/**
	 * Name of the Product
	 */
	private final String name;
}
