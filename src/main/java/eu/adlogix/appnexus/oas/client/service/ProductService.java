
package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import eu.adlogix.appnexus.oas.client.domain.Product;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Service to for OAS {@link Product} related activities
 */
public class ProductService extends AbstractOasService {

	private final XmlRequestGenerator readProductRequestGenerator = new XmlRequestGenerator("read-product");
	private final XmlRequestGenerator addProductRequestGenerator = new XmlRequestGenerator("add-product");

	protected ProductService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Creates a {@link Product} on OAS
	 * 
	 * @param product
	 *            The Product to be created
	 */
	public final void createProduct(final Product product) {

		checkNotNull(product, "product");
		checkNotEmpty(product.getId(), "product ID");
		checkNotEmpty(product.getName(), "product Name");

		@SuppressWarnings("serial")
		final Map<String, Object> requestParameters = new HashMap<String, Object>() {

			{
				put("productId", product.getId());
				put("productName", product.getName());
				put("notes", product.getNotes());
			}
		};

		performRequest(addProductRequestGenerator, requestParameters);
	}

	/**
	 * Get {@link Product} by ID
	 * 
	 * @param productId
	 *            the Product ID
	 * @return the {@link Product}
	 */
	public Product getProduct(final String productId) {

		checkNotEmpty(productId, "productId");

		@SuppressWarnings("serial")
		final Map<String, Object> requestParameters = new HashMap<String, Object>() {
			{
				put("productId", productId);
			}
		};

		final ResponseParser parser = performRequest(readProductRequestGenerator, requestParameters);

		String productResponseId = parser.getTrimmedElement("//Product/Id");
		String productResponseName = parser.getTrimmedElement("//Product/Name");

		return new Product(productResponseId, productResponseName);
	}
}
