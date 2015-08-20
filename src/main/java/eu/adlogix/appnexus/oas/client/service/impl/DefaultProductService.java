package eu.adlogix.appnexus.oas.client.service.impl;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import eu.adlogix.appnexus.oas.client.domain.Product;
import eu.adlogix.appnexus.oas.client.service.AbstractOasService;
import eu.adlogix.appnexus.oas.client.service.OasApiService;
import eu.adlogix.appnexus.oas.client.service.ProductService;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Default implementation of {@link ProductService} which provides functions for
 * OAS {@link Product} related activities
 */
public class DefaultProductService extends AbstractOasService implements ProductService {

	private final XmlRequestGenerator readProductRequestGenerator = new XmlRequestGenerator("read-product");
	private final XmlRequestGenerator addProductRequestGenerator = new XmlRequestGenerator("add-product");

	public DefaultProductService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Creates a {@link Product} on OAS
	 * 
	 * @param product
	 *            The Product to be created
	 */
	public final void add(final Product product) {

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
	public Product getById(final String productId) {

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
