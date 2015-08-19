package eu.adlogix.appnexus.oas.client.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.CompetitiveCategory;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;
import eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils;

public class CompetitiveCategoryServiceTest {
	@Test
	public void getAll_NoError_ReturnCategories() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		CompetitiveCategoryService service = new CompetitiveCategoryService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-get-all-competitive-categories.xml", CompetitiveCategoryServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-response-get-all-competitive-categories.xml", CompetitiveCategoryServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		List<CompetitiveCategory> categories = service.getAll();

		assertEquals(categories.size(), 38);

		assertEquals(categories.get(0), new CompetitiveCategory("Airlines"));
		assertEquals(categories.get(1), new CompetitiveCategory("Alcohol"));
		assertEquals(categories.get(2), new CompetitiveCategory("Automobiles"));
		assertEquals(categories.get(3), new CompetitiveCategory("Banking"));

		assertEquals(categories.get(36), new CompetitiveCategory("Utilities"));
		assertEquals(categories.get(37), new CompetitiveCategory("WebSites"));

		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void add_NoError_Success() throws FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		CompetitiveCategoryService service = new CompetitiveCategoryService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-add-competitive-category.xml", CompetitiveCategoryServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-response-add-competitive-category.xml", CompetitiveCategoryServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		service.add(new CompetitiveCategory("testCompetitiveCategory00"));

		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test(expectedExceptions = OasServerSideException.class, expectedExceptionsMessageRegExp = "OAS Error \\[512\\]: 'ID Already Exists.'")
	public void add_DuplicateError_NoException() throws FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		CompetitiveCategoryService service = new CompetitiveCategoryService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-add-competitive-category.xml", CompetitiveCategoryServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-response-id-already-exists-error.xml", CompetitiveCategoryServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		service.add(new CompetitiveCategory("testCompetitiveCategory00"));

		verify(mockedApiService).callApi(expectedRequest, false);
	}
}
