package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils.normalizeNewLinesToCurPlatform;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

import java.util.List;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;

public class AdvertiserServiceTest {

	@Test
	public final void getAdvertiserById_ExistingAdvertiser_ReturnAdvertiser() throws Exception {
		
		OasApiService mockedApiService=mock(OasApiService.class);
		AdvertiserService service = new AdvertiserService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-advertiser-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = service.getAdvertiserById("test_advertiser_01");
		assertEquals(advertiser.getId(), "test_advertiser_01");
		assertEquals(advertiser.getOrganization(), "Adlogix");
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

		service.getAdvertiserById("test_advertiser_01");
	}

	@Test
	public final void addAdvertiser_ValidParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		AdvertiserService service = new AdvertiserService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-advertiser-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = new Advertiser();
		advertiser.setId("test_advertiser_01");
		advertiser.setOrganization("Adlogix");
		service.addAdvertiser(advertiser);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test(expectedExceptions = { OasServerSideException.class })
	public final void addAdvertiser_IdAlreadyExists_ThrowException() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		AdvertiserService service = new AdvertiserService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-advertiser-id-already-exists-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = new Advertiser();
		advertiser.setId("test_advertiser_01");
		advertiser.setOrganization("Adlogix");
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
		assertEquals(advertiser1.getId(), "test_advertiser_01");
		assertEquals(advertiser1.getOrganization(), "");

		Advertiser advertiser2 = advertisers.get(1);
		assertEquals(advertiser2.getId(), "test_advertiser_02");
		assertEquals(advertiser2.getOrganization(), "Adlogix");
	}

	@Test
	public final void updateAdvertiser_UpdateOrganization_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		AdvertiserService service = new AdvertiserService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-update-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("update-advertiser-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = new Advertiser();
		advertiser.setId("test_advertiser_01");
		advertiser.setOrganization("Adlogix_Advertiser");
		service.updateAdvertiser(advertiser);
		verify(mockedApiService).callApi(expectedRequest, false);

	}
}
