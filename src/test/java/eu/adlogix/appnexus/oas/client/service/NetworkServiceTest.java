package eu.adlogix.appnexus.oas.client.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import org.testng.annotations.Test;
import org.testng.collections.Lists;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.certificate.TestCredentials;
import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.domain.Position;
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

	@Test
	public void getAllPagesWithPositionsModifiedSinceDate_NoPositions_ReturnPagesOnly() throws FileNotFoundException,
			URISyntaxException, IOException, AdlResourceNotFoundException, ServiceException {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		NetworkService service = new NetworkService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-answer-listpages-nopositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null));
		sites.add(new Site("dada", "dada"));
		sites.add(new Site("Aperol", "aperol"));
		sites.add(new Site("cartest.it", null));

		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(sites, null);

		assertEquals(pages.size(), 4);

		for (Page oasPage : pages) {
			assertNotNull(oasPage.getUrl());
			if (oasPage.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", oasPage.getSite().getId());
				assertEquals(null, oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", oasPage.getSite().getId());
				assertEquals("dada", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", oasPage.getSite().getId());
				assertEquals("aperol", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", oasPage.getSite().getId());
				assertEquals(null, oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			}
		}

	}

	@Test
	public void getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate_NoPositions_ReturnPagesOnly()
			throws FileNotFoundException, URISyntaxException, IOException, AdlResourceNotFoundException,
			ServiceException {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		NetworkService service = new NetworkService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-answer-listpages-nopositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Page> pages = service.getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate(null);

		assertEquals(pages.size(), 4);

		for (Page oasPage : pages) {
			assertNotNull(oasPage.getUrl());
			if (oasPage.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", oasPage.getSite().getId());
				assertEquals("adsolutions", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", oasPage.getSite().getId());
				assertEquals("dada", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", oasPage.getSite().getId());
				assertEquals("Aperol", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", oasPage.getSite().getId());
				assertEquals("cartest.it", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			}
		}

	}

	@Test
	public void getAllPagesWithPositionsModifiedSinceDate_WithPositions_ReturnPagesAndPositions()
			throws FileNotFoundException,
			URISyntaxException, IOException, AdlResourceNotFoundException, ServiceException {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		NetworkService service = new NetworkService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-answer-listpages-withpositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null));
		sites.add(new Site("dada", "dada"));

		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(sites, null);

		assertEquals(pages.size(), 2);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");

		for (Page oasPage : pages) {
			assertNotNull(oasPage.getUrl());
			if (oasPage.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", oasPage.getSite().getId());
				assertEquals(null, oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 2);
				assertTrue(oasPage.getPositions().contains(rightPostion) && oasPage.getPositions().contains(topPostion));
			} else if (oasPage.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", oasPage.getSite().getId());
				assertEquals("dada", oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 2);
				assertTrue(oasPage.getPositions().contains(rightPostion)
						&& oasPage.getPositions().contains(leftPostion));
			}
		}
	}

	@Test
	public void getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate_WithPositions_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, AdlResourceNotFoundException,
			ServiceException {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		NetworkService service = new NetworkService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-answer-listpages-withpositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Page> pages = service.getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate(null);

		assertEquals(pages.size(), 2);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");

		for (Page oasPage : pages) {
			assertNotNull(oasPage.getUrl());
			if (oasPage.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", oasPage.getSite().getId());
				assertEquals("adsolutions", oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 2);
				assertTrue(oasPage.getPositions().contains(rightPostion) && oasPage.getPositions().contains(topPostion));
			} else if (oasPage.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", oasPage.getSite().getId());
				assertEquals("dada", oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 2);
				assertTrue(oasPage.getPositions().contains(rightPostion)
						&& oasPage.getPositions().contains(leftPostion));
			}
		}
	}

	@Test
	public void getAllPagesWithPositionsModifiedSinceDate_WithAndWithoutPositions_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, AdlResourceNotFoundException,
			ServiceException {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		NetworkService service = new NetworkService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-answer-listpages.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null));
		sites.add(new Site("dada", "dada"));
		sites.add(new Site("Aperol", "aperol"));
		sites.add(new Site("cartest.it", null));

		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(sites, null);

		assertEquals(pages.size(), 6);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");
		Position topRightPostion = new Position("TopRight");

		for (Page oasPage : pages) {
			assertNotNull(oasPage.getUrl());
			if (oasPage.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", oasPage.getSite().getId());
				assertEquals(null, oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", oasPage.getSite().getId());
				assertEquals(null, oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 2);
				assertTrue(oasPage.getPositions().contains(rightPostion) && oasPage.getPositions().contains(topPostion));

			} else if (oasPage.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", oasPage.getSite().getId());
				assertEquals("dada", oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 4);
				assertTrue(oasPage.getPositions().contains(leftPostion)
						&& oasPage.getPositions().contains(rightPostion) && oasPage.getPositions().contains(topPostion)
						&& oasPage.getPositions().contains(topRightPostion));
			} else if (oasPage.getUrl().equals("www.dada.it/sport")) {
				assertEquals("dada", oasPage.getSite().getId());
				assertEquals("dada", oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 4);
				assertTrue(oasPage.getPositions().contains(leftPostion)
						&& oasPage.getPositions().contains(rightPostion) && oasPage.getPositions().contains(topPostion)
						&& oasPage.getPositions().contains(topRightPostion));

			} else if (oasPage.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", oasPage.getSite().getId());
				assertEquals("aperol", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", oasPage.getSite().getId());
				assertEquals(null, oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());

			}

		}
	}

	@Test
	public void getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate_WithAndWithoutPositions_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, AdlResourceNotFoundException,
			ServiceException {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		NetworkService service = new NetworkService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("expected-answer-listpages.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Page> pages = service.getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate(null);

		assertEquals(pages.size(), 6);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");
		Position topRightPostion = new Position("TopRight");

		for (Page oasPage : pages) {
			assertNotNull(oasPage.getUrl());
			if (oasPage.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", oasPage.getSite().getId());
				assertEquals("adsolutions", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", oasPage.getSite().getId());
				assertEquals("adsolutions", oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 2);
				assertTrue(oasPage.getPositions().contains(rightPostion) && oasPage.getPositions().contains(topPostion));

			} else if (oasPage.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", oasPage.getSite().getId());
				assertEquals("dada", oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 4);
				assertTrue(oasPage.getPositions().contains(leftPostion)
						&& oasPage.getPositions().contains(rightPostion) && oasPage.getPositions().contains(topPostion)
						&& oasPage.getPositions().contains(topRightPostion));
			} else if (oasPage.getUrl().equals("www.dada.it/sport")) {
				assertEquals("dada", oasPage.getSite().getId());
				assertEquals("dada", oasPage.getSite().getName());
				assertEquals(oasPage.getPositions().size(), 4);
				assertTrue(oasPage.getPositions().contains(leftPostion)
						&& oasPage.getPositions().contains(rightPostion) && oasPage.getPositions().contains(topPostion)
						&& oasPage.getPositions().contains(topRightPostion));

			} else if (oasPage.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", oasPage.getSite().getId());
				assertEquals("Aperol", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());
			} else if (oasPage.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", oasPage.getSite().getId());
				assertEquals("cartest.it", oasPage.getSite().getName());
				assertTrue(oasPage.getPositions().isEmpty());

			}

		}
	}
}
