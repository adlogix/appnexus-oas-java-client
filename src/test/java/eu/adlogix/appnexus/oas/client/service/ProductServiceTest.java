package eu.adlogix.appnexus.oas.client.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.Product;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;
import eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils;

public class ProductServiceTest {
	@Test
	public void getProduct_WithExistingProduct_ReturnTrue() throws FileNotFoundException, URISyntaxException,
			IOException, ServiceException, ResourceNotFoundException {

		OasApiService mockedApiService = mock(OasApiService.class);
		ProductService service = new ProductService(mockedApiService);

		final String expectedRequest = getXml("expected-request-readproduct.xml");
		final String mockedpAnswer = getXml("expected-answer-readproduct.xml");

		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		Product response = service.getProduct("TestProduct");

		assertEquals(response, new Product("TestProduct", "Test Product"));

	}

	private String getXml(String fileName) throws URISyntaxException, FileNotFoundException, IOException,
			ResourceNotFoundException {
		return StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString(fileName, ProductServiceTest.class));
	}

	@Test(expectedExceptions = OasServerSideException.class, expectedExceptionsMessageRegExp = "OAS Error \\[544\\]: 'Invalid ProductId.'")
	public void getProduct_WithNonExistingProduct_ReturnFalse() throws FileNotFoundException, URISyntaxException,
			IOException, ServiceException, ResourceNotFoundException {

		OasApiService mockedApiService = mock(OasApiService.class);
		ProductService service = new ProductService(mockedApiService);

		final String expectedRequest = getXml("expected-request-readproduct-nonexisting.xml");
		final String mockedpAnswer = getXml("expected-answer-readproduct-nonexisting.xml");

		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		Product response = service.getProduct("TestProduct02");

		assertEquals(response, false);
	}

	@Test
	public void createProduct_WithValidParameters_Success() throws FileNotFoundException, URISyntaxException,
			IOException, ServiceException, ResourceNotFoundException {

		OasApiService mockedApiService = mock(OasApiService.class);
		ProductService service = new ProductService(mockedApiService);

		final String expectedRequest = getXml("expected-request-createproduct.xml");
		final String mockedpAnswer = getXml("expected-answer-createproduct-success.xml");

		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		Product product = new Product();
		product.setId("TestProduct02");
		product.setName("Test Product 02");
		product.setNotes("test note");
		service.createProduct(product);

		assertTrue(true);

	}

	@Test(expectedExceptions = OasServerSideException.class, expectedExceptionsMessageRegExp = "OAS Error \\[506\\]: 'Test_Product_1234567890_1234567890 contains too many characters for Id'")
	public void createProduct_WithInvalidProductId_ThrowException() throws FileNotFoundException, URISyntaxException,
			IOException, ServiceException, ResourceNotFoundException {

		OasApiService mockedApiService = mock(OasApiService.class);
		ProductService service = new ProductService(mockedApiService);

		final String expectedRequest = getXml("expected-request-createproduct-error.xml");
		final String mockedpAnswer = getXml("expected-answer-createproduct-error.xml");

		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		service.createProduct(new Product("Test_Product_1234567890_1234567890", "Test Product"));

	}

}
