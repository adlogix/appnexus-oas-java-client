package eu.adlogix.appnexus.oas.client.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

import java.util.List;
import java.util.Properties;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.certificate.TestCredentials;
import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.utils.file.AdlTestFileUtils;
import eu.adlogix.appnexus.oas.utils.string.StringTestUtils;

public class AdvertiserServiceTest {

	@Test
	public final void getAdvertiserById_ExistingAdvertiser_ReturnAdvertiser() throws Exception {
		
		XaxisApiService mockedApiService=mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		AdvertiserService service = new AdvertiserService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-read-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("read-advertiser-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = service.getAdvertiserById("TestAdvertiser_01");
		assertEquals(advertiser.getId(), "TestAdvertiser_01");
		assertEquals(advertiser.getOrganization(), "AdvertiserTest");
	}

	@Test(expectedExceptions = { RuntimeException.class })
	public final void getAdvertiserById_InvalidId_ThrowException() throws Exception {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		AdvertiserService service = new AdvertiserService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-read-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("read-advertiser-invalid-id-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		service.getAdvertiserById("TestAdvertiser_01");
	}

	@Test
	public final void addAdvertiser_ValidParameters_SuccessfullyAdd() throws Exception {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		AdvertiserService service = new AdvertiserService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-add-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("add-advertiser-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = new Advertiser("TestAdvertiser_01", "AdvertiserTest");
		service.addAdvertiser(advertiser);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test(expectedExceptions = { RuntimeException.class })
	public final void addAdvertiser_AlreadyExisting_ThrowException() throws Exception {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		AdvertiserService service = new AdvertiserService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-add-advertiser-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("add-advertiser-id-already-exists-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Advertiser advertiser = new Advertiser("TestAdvertiser_01", "AdvertiserTest");
		service.addAdvertiser(advertiser);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public final void getAllAdvertisers_NoExceptions_ReturnAllAdvertisers() throws Exception {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		AdvertiserService service = new AdvertiserService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-list-advertisers-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("list-advertisers-response.xml", this.getClass()));
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

	private Properties getTestCredentials() {
		return TestCredentials.getTestCredentials();
	}

	private static String normalizeNewLinesToCurPlatform(String source) {
		return (execPlatform.indexOf("windows") >= 0) ? source : StringTestUtils.normalizeNewLinesToCurPlatform(source);
	}

	private static final String execPlatform = System.getProperty("os.name").toLowerCase();
}
