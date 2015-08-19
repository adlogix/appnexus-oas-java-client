package eu.adlogix.appnexus.oas.client.runner;

import eu.adlogix.appnexus.oas.client.domain.Product;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.service.ProductService;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class AddProductRunner {

	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		ProductService service = factory.getProductService();
		Product product = new Product();
		product.setId("TestProduct01");
		product.setName("Test Product 01");
		product.setNotes("test note");
		service.add(product);
	}
}
