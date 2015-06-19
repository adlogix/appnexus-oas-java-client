package eu.adlogix.appnexus.oas.client.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

import java.util.List;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;
import eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils;

public class AdvertiserServiceTest {

	@Test
	public final void getAdvertiserById_ExistingAdvertiser_ReturnAdvertiser() throws Exception {
		
		OasApiService mockedApiService=mock(OasApiService.class);
		AdvertiserService service = new AdvertiserService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-advertiser-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = service.getAdvertiserById("TestAdvertiser_01");
		assertEquals(advertiser.getId(), "TestAdvertiser_01");
		assertEquals(advertiser.getOrganization(), "AdvertiserTest");
		assertEquals(advertiser.getBillingInformation().getCountry(), "US");
		assertEquals(advertiser.getBillingInformation().getMethod(), "M");
	}

	@Test(expectedExceptions = { OasServerSideException.class })
	public final void getAdvertiserById_InvalidId_ThrowException() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		AdvertiserService service = new AdvertiserService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-advertiser-invalid-id-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		service.getAdvertiserById("TestAdvertiser_01");
	}

	@Test
	public final void addAdvertiser_ValidParameters_SuccessfullyAdd() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		AdvertiserService service = new AdvertiserService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-advertiser-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = new Advertiser("TestAdvertiser_01", "AdvertiserTest");
		service.addAdvertiser(advertiser);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test(expectedExceptions = { OasServerSideException.class })
	public final void addAdvertiser_AlreadyExisting_ThrowException() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		AdvertiserService service = new AdvertiserService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-advertiser-id-already-exists-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = new Advertiser("TestAdvertiser_01", "AdvertiserTest");
		service.addAdvertiser(advertiser);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public final void getAllAdvertisers_NoExceptions_ReturnAllAdvertisers() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		AdvertiserService service = new AdvertiserService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-list-advertisers-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("list-advertisers-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedAnswer);

		List<Advertiser> advertisers = service.getAllAdvertisers();
		assertEquals(advertisers.size(), 5);

		Advertiser advertiser1 = advertisers.get(0);
		assertEquals(advertiser1.getId(), "Advertiser_Test_1");
		assertEquals(advertiser1.getOrganization(), "");

		Advertiser advertiser2 = advertisers.get(1);
		assertEquals(advertiser2.getId(), "Advertiser_Test_2");
		assertEquals(advertiser2.getOrganization(), "Advertiser_Test");
	}

	private static String normalizeNewLinesToCurPlatform(String source) {
		return (execPlatform.indexOf("windows") >= 0) ? source : StringTestUtils.normalizeNewLinesToCurPlatform(source);
	}

	private static final String execPlatform = System.getProperty("os.name").toLowerCase();
}
