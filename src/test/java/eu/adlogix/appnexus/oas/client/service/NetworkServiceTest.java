package eu.adlogix.appnexus.oas.client.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import org.joda.time.DateTime;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import eu.adlogix.appnexus.oas.client.certificate.TestCredentials;
import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.domain.Position;
import eu.adlogix.appnexus.oas.client.domain.Section;
import eu.adlogix.appnexus.oas.client.domain.Site;
import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;
import eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils;

public class NetworkServiceTest {
	@Test
	public void getAllSites_NoError_ReturnAllSites() throws FileNotFoundException, URISyntaxException, IOException,
			ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listsites.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listsites.xml", NetworkServiceTest.class));
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
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-nopositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null));
		sites.add(new Site("dada", "dada"));
		sites.add(new Site("Aperol", "aperol"));
		sites.add(new Site("cartest.it", null));

		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(sites, null);

		assertEquals(pages.size(), 4);

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", Page.getSite().getId());
				assertEquals("aperol", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			}
		}

	}

	@Test
	public void getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate_NoPositions_ReturnPagesOnly()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-nopositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Page> pages = service.getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate(null);

		assertEquals(pages.size(), 4);

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals("adsolutions", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", Page.getSite().getId());
				assertEquals("Aperol", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", Page.getSite().getId());
				assertEquals("cartest.it", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			}
		}

	}

	@Test
	public void getAllPagesWithPositionsModifiedSinceDate_WithPositions_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-withpositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null));
		sites.add(new Site("dada", "dada"));

		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(sites, null);

		assertEquals(pages.size(), 2);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(leftPostion));
			}
		}
	}

	@Test
	public void getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate_WithPositions_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-withpositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Page> pages = service.getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate(null);

		assertEquals(pages.size(), 2);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals("adsolutions", Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(leftPostion));
			}
		}
	}

	@Test
	public void getAllPagesWithPositionsModifiedSinceDate_WithAndWithoutPositions_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages.xml", NetworkServiceTest.class));
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

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));

			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 4);
				assertTrue(Page.getPositions().contains(leftPostion) && Page.getPositions().contains(rightPostion)
						&& Page.getPositions().contains(topPostion) && Page.getPositions().contains(topRightPostion));
			} else if (Page.getUrl().equals("www.dada.it/sport")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 4);
				assertTrue(Page.getPositions().contains(leftPostion) && Page.getPositions().contains(rightPostion)
						&& Page.getPositions().contains(topPostion) && Page.getPositions().contains(topRightPostion));

			} else if (Page.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", Page.getSite().getId());
				assertEquals("aperol", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());

			}

		}
	}

	@Test
	public void getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate_WithAndWithoutPositions_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Page> pages = service.getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate(null);

		assertEquals(pages.size(), 6);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");
		Position topRightPostion = new Position("TopRight");

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals("adsolutions", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals("adsolutions", Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));

			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 4);
				assertTrue(Page.getPositions().contains(leftPostion) && Page.getPositions().contains(rightPostion)
						&& Page.getPositions().contains(topPostion) && Page.getPositions().contains(topRightPostion));
			} else if (Page.getUrl().equals("www.dada.it/sport")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 4);
				assertTrue(Page.getPositions().contains(leftPostion) && Page.getPositions().contains(rightPostion)
						&& Page.getPositions().contains(topPostion) && Page.getPositions().contains(topRightPostion));

			} else if (Page.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", Page.getSite().getId());
				assertEquals("Aperol", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", Page.getSite().getId());
				assertEquals("cartest.it", Page.getSite().getName());
				assertTrue(Page.getPositions().isEmpty());

			}

		}
	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = ".*Empty allSites parameter was passed. Expected a non empty site list.*")
	public void getAllPagesWithPositionsModifiedSinceDate_EmptySitesMapParameter_ThrowException()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		service.getAllPagesWithPositionsModifiedSinceDate(new ArrayList<Site>(), null);

	}

	@Test
	public void getAllPagesWithPositionsModifiedSinceDate_WithPositionsAndLastModifiedParam_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages-modifieddate.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-withpositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null));
		sites.add(new Site("dada", "dada"));

		DateTime lastModifiedDate = new DateTime(2014, 5, 10, 0, 0, 0, 0);
		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(sites, lastModifiedDate);

		assertEquals(pages.size(), 2);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(leftPostion));
			}
		}

	}

	@Test
	public void readSection_WithSinglePage_ReturnSectionWithSinglePage() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		Section section = service.readSection("Finegil.Centro.Necro");

		assertEquals(section.getId(), "Finegil.Centro.Necro");

		List<Page> list = section.getPages();
		assertEquals(list.size(), 1);
		Page actualPage = list.get(0);
		assertEquals(actualPage.getUrl(), "quotidianiespresso.it/qe/ilcentro/home");
		assertEquals(actualPage.getPositions().size(), 1);
		assertEquals(actualPage.getPositions().get(0).getName(), "x96");

	}

	@Test
	public void readSection_WithSinglePageAndNoPositions_ReturnSectionWithSinglePageWithoutPositions()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection-nopositions.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		Section section = service.readSection("Finegil.Centro.Necro");

		assertEquals(section.getId(), "Finegil.Centro.Necro");

		List<Page> list = section.getPages();
		assertEquals(list.size(), 1);
		Page actualPage = list.get(0);
		assertEquals(actualPage.getUrl(), "quotidianiespresso.it/qe/ilcentro/home");
		assertTrue(actualPage.getPositions().isEmpty());

	}

	@Test
	public void readSection_WithNoPages_ReturnSectionOnly() throws FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection-nopages.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		Section section = service.readSection("Finegil.Centro.Necro");

		assertEquals(section.getId(), "Finegil.Centro.Necro");

		assertTrue(section.getPages().isEmpty());

	}

	@Test
	public void readSection_WithMultiplePages_ReturnSectionWithMultiplePages() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection-multiplepages.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		Section section = service.readSection("Finegil.Centro.Necro");

		assertEquals(section.getId(), "Finegil.Centro.Necro");

		List<Page> pages = section.getPages();
		assertEquals(pages.size(), 5);

		Page page1 = pages.get(0);
		assertNotNull(page1.getUrl());
		assertEquals(page1.getPositions().size(), 1);

		Page page2 = pages.get(1);
		assertNotNull(page2.getUrl());
		assertTrue(page2.getPositions().isEmpty());

		Page page3 = pages.get(2);
		assertNotNull(page3.getUrl());
		assertTrue(page3.getPositions().isEmpty());

		Page page4 = pages.get(3);
		assertNotNull(page4.getUrl());
		assertEquals(page4.getPositions().size(), 2);

		Page page5 = pages.get(4);
		assertNotNull(page5.getUrl());
		assertTrue(page5.getPositions().isEmpty());

	}

	@Test
	public void getSectionList_WithoutLastModifiedDate_ReturnSectionList() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listsections.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listsections.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		final String expectedSectionRequest1 = (TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", NetworkServiceTest.class));
		final String mockedSectionAnswer1 = (TestFileUtils.getTestResourceAsString("expected-answer-readsection.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedSectionRequest1, true)).thenReturn(mockedSectionAnswer1);

		final String expectedSectionRequest2 = (TestFileUtils.getTestResourceAsString("expected-request-readsection-2.xml", NetworkServiceTest.class));
		final String mockedSectionAnswer2 = (TestFileUtils.getTestResourceAsString("expected-answer-readsection-2.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedSectionRequest2, true)).thenReturn(mockedSectionAnswer2);

		List<Section> sections = service.getSectionListModifiedSinceDate(null);

		assertEquals(sections.size(), 2);

		Section section1 = sections.get(0);
		assertEquals(section1.getId(), "383section");
		assertTrue(section1.getPages().isEmpty());

		Section section2 = sections.get(1);
		assertEquals(section2.getId(), "Finegil.Centro.Necro");
		assertEquals(section2.getPages().size(), 1);
		Page section2Page = section2.getPages().get(0);
		assertEquals(section2Page.getUrl(), "quotidianiespresso.it/qe/ilcentro/home");
		assertEquals(section2Page.getPositions().size(), 1);
		Position section2PagePosition = section2Page.getPositions().get(0);
		assertEquals(section2PagePosition.getName(), "x96");

	}

	@Test
	public void getSectionList_WithLastModifiedDate_ReturnSectionList() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new NetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listsections-modifieddate.xml", NetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listsections.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		final String expectedSectionRequest1 = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", NetworkServiceTest.class));
		final String mockedSectionAnswer1 = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedSectionRequest1, true)).thenReturn(mockedSectionAnswer1);

		final String expectedSectionRequest2 = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection-2.xml", NetworkServiceTest.class));
		final String mockedSectionAnswer2 = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection-2.xml", NetworkServiceTest.class));
		when(mockedApiService.callApi(expectedSectionRequest2, true)).thenReturn(mockedSectionAnswer2);

		List<Section> sections = service.getSectionListModifiedSinceDate(new DateTime(2014, 5, 10, 0, 0, 0, 0));

		assertEquals(sections.size(), 2);

		Section section1 = sections.get(0);
		assertEquals(section1.getId(), "383section");
		assertTrue(section1.getPages().isEmpty());

		Section section2 = sections.get(1);
		assertEquals(section2.getId(), "Finegil.Centro.Necro");
		assertEquals(section2.getPages().size(), 1);
		Page section2Page = section2.getPages().get(0);
		assertEquals(section2Page.getUrl(), "quotidianiespresso.it/qe/ilcentro/home");
		assertEquals(section2Page.getPositions().size(), 1);
		Position section2PagePosition = section2Page.getPositions().get(0);
		assertEquals(section2PagePosition.getName(), "x96");

	}
}
