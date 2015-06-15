package eu.adlogix.appnexus.oas.client.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.certificate.TestCredentials;
import eu.adlogix.appnexus.oas.client.domain.Site;
import eu.adlogix.testutils.file.AdlTestFileUtils;
import eu.adlogix.testutils.string.StringTestUtils;
import eu.adlogix.utils.file.AdlResourceNotFoundException;

public class NetworkServiceTest {
	@Test
	public void getAllSites_NoError_ReturnAllSites() throws FileNotFoundException, URISyntaxException, IOException,
			AdlResourceNotFoundException, ServiceException {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		NetworkService service = new NetworkService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-request-listsites.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-answer-listsites.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = service.getAllSites();

		assertEquals(sites.size(), 41);

		Site site1 = (Site) sites.get(0);
		assertEquals("118218", site1.getId());
		assertEquals("", site1.getName());

		Site site2 = (Site) sites.get(1);
		assertEquals("12345", site2.getId());
		assertEquals("Eriks site", site2.getName());

	}

	private Properties getTestCredentials() {
		return TestCredentials.getTestCredentials();
	}
}
