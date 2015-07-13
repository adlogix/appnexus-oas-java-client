/**
 * 
 */
package eu.adlogix.appnexus.oas.client.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OAS Product
 */
@NoArgsConstructor
@Data
public class Product {
	/**
	 * Unique ID of the product
	 */
	private String id;
	/**
	 * Name of the Product
	 */
	private String name;
	/**
	 * Notes
	 */
	private String notes;

	public Product(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
