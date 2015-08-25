package eu.adlogix.appnexus.oas.client.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.joda.time.DateTime;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import eu.adlogix.appnexus.oas.client.domain.CompanionPosition;
import eu.adlogix.appnexus.oas.client.domain.Page;
import eu.adlogix.appnexus.oas.client.domain.Position;
import eu.adlogix.appnexus.oas.client.domain.Section;
import eu.adlogix.appnexus.oas.client.domain.Site;
import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;
import eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils;

public class DefaultNetworkServiceTest {
	@Test
	public void getAllSites_NoError_ReturnAllSites() throws FileNotFoundException, URISyntaxException, IOException,
			ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listsites.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listsites.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = service.getAllSites();

		assertEquals(sites.size(), 41);

		Site site1 = (Site) sites.get(0);
		assertEquals("118218", site1.getId());
		assertEquals("", site1.getName());
		assertEquals("www.118218.fr", site1.getDomain());

		Site site2 = (Site) sites.get(1);
		assertEquals("12345", site2.getId());
		assertEquals("Eriks site", site2.getName());
		assertEquals("erikssite.se", site2.getDomain());

	}


	@Test
	public void getAllPagesWithPositionsModifiedSinceDate_NoPositions_ReturnPagesOnly() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-nopositions.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null, null));
		sites.add(new Site("dada", "dada", null));
		sites.add(new Site("Aperol", "aperol", "aperol"));
		sites.add(new Site("cartest.it", null, "cartest"));

		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(null, sites);

		assertEquals(pages.size(), 4);

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", Page.getSite().getId());
				assertEquals("aperol", Page.getSite().getName());
				assertEquals("aperol", Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertEquals("cartest", Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			}
		}

	}

	@Test
	public void getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate_NoPositions_ReturnPagesOnly()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-nopositions.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Page> pages = service.getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate(null);

		assertEquals(pages.size(), 4);

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertNull(Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertNull(Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertNull(Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertNull(Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			}
		}

	}

	@Test
	public void getAllPagesWithPositionsModifiedSinceDate_WithPositions_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-withpositions.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null, null));
		sites.add(new Site("dada", "dada", "dada"));

		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(null, sites);

		assertEquals(pages.size(), 2);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals("dada", Page.getSite().getDomain());
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
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-withpositions.xml", DefaultNetworkServiceTest.class));
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
				assertNull(Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
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
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null, null));
		sites.add(new Site("dada", "dada", "dada"));
		sites.add(new Site("Aperol", "aperol", null));
		sites.add(new Site("cartest.it", null, "cartest.it"));

		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(null, sites);

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
				assertEquals(null, Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));

			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals("dada", Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 4);
				assertTrue(Page.getPositions().contains(leftPostion) && Page.getPositions().contains(rightPostion)
						&& Page.getPositions().contains(topPostion) && Page.getPositions().contains(topRightPostion));
			} else if (Page.getUrl().equals("www.dada.it/sport")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals("dada", Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 4);
				assertTrue(Page.getPositions().contains(leftPostion) && Page.getPositions().contains(rightPostion)
						&& Page.getPositions().contains(topPostion) && Page.getPositions().contains(topRightPostion));

			} else if (Page.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", Page.getSite().getId());
				assertEquals("aperol", Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertEquals("cartest.it", Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());

			}

		}
	}

	@Test
	public void getAllPagesWithPositionsWithoutSiteDetailsModifiedSinceDate_WithAndWithoutPositions_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages.xml", DefaultNetworkServiceTest.class));
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
				assertNull(Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));

			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 4);
				assertTrue(Page.getPositions().contains(leftPostion) && Page.getPositions().contains(rightPostion)
						&& Page.getPositions().contains(topPostion) && Page.getPositions().contains(topRightPostion));
			} else if (Page.getUrl().equals("www.dada.it/sport")) {
				assertEquals("dada", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 4);
				assertTrue(Page.getPositions().contains(leftPostion) && Page.getPositions().contains(rightPostion)
						&& Page.getPositions().contains(topPostion) && Page.getPositions().contains(topRightPostion));

			} else if (Page.getUrl().equals("www.aperol.com")) {
				assertEquals("Aperol", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());
			} else if (Page.getUrl().equals("www.cartest.it")) {
				assertEquals("cartest.it", Page.getSite().getId());
				assertNull(Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertTrue(Page.getPositions().isEmpty());

			}

		}
	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "allSites shouldn't be empty")
	public void getAllPagesWithPositionsModifiedSinceDate_EmptySitesMapParameter_ThrowException()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		service.getAllPagesWithPositionsModifiedSinceDate(null, new ArrayList<Site>());

	}

	@Test
	public void getAllPagesWithPositionsModifiedSinceDate_WithPositionsAndLastModifiedParam_ReturnPagesAndPositions()
			throws FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException,
			ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpages-modifieddate.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpages-withpositions.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		List<Site> sites = Lists.newArrayList();
		sites.add(new Site("adsolutions", null, null));
		sites.add(new Site("dada", "dada", "dada"));

		DateTime lastModifiedDate = new DateTime(2014, 5, 10, 0, 0, 0, 0);
		List<Page> pages = service.getAllPagesWithPositionsModifiedSinceDate(lastModifiedDate, sites);

		assertEquals(pages.size(), 2);

		Position rightPostion = new Position("Right");
		Position topPostion = new Position("Top");
		Position leftPostion = new Position("Left");

		for (Page Page : pages) {
			assertNotNull(Page.getUrl());
			if (Page.getUrl().equals("www.adsolutions.com/adservering")) {
				assertEquals("adsolutions", Page.getSite().getId());
				assertEquals(null, Page.getSite().getName());
				assertEquals(null, Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(topPostion));
			} else if (Page.getUrl().equals("www.dada.it/female/Magazine")) {
				assertEquals("dada", Page.getSite().getId());
				assertEquals("dada", Page.getSite().getName());
				assertEquals("dada", Page.getSite().getDomain());
				assertEquals(Page.getPositions().size(), 2);
				assertTrue(Page.getPositions().contains(rightPostion) && Page.getPositions().contains(leftPostion));
			}
		}

	}

	@Test
	public void readSection_WithSinglePage_ReturnSectionWithSinglePage() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection.xml", DefaultNetworkServiceTest.class));
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
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection-nopositions.xml", DefaultNetworkServiceTest.class));
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
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection-nopages.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		Section section = service.readSection("Finegil.Centro.Necro");

		assertEquals(section.getId(), "Finegil.Centro.Necro");

		assertTrue(section.getPages().isEmpty());

	}

	@Test
	public void readSection_WithMultiplePages_ReturnSectionWithMultiplePages() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection-multiplepages.xml", DefaultNetworkServiceTest.class));
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
	public void getSectionListModifiedSinceDate_WithoutLastModifiedDate_ReturnSectionList()
			throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listsections.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listsections.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		final String expectedSectionRequest1 = (TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", DefaultNetworkServiceTest.class));
		final String mockedSectionAnswer1 = (TestFileUtils.getTestResourceAsString("expected-answer-readsection.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedSectionRequest1, true)).thenReturn(mockedSectionAnswer1);

		final String expectedSectionRequest2 = (TestFileUtils.getTestResourceAsString("expected-request-readsection-2.xml", DefaultNetworkServiceTest.class));
		final String mockedSectionAnswer2 = (TestFileUtils.getTestResourceAsString("expected-answer-readsection-2.xml", DefaultNetworkServiceTest.class));
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
	public void getAllSections_HasSections_ReturnSectionList() throws FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listsections.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listsections.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		final String expectedSectionRequest1 = (TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", DefaultNetworkServiceTest.class));
		final String mockedSectionAnswer1 = (TestFileUtils.getTestResourceAsString("expected-answer-readsection.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedSectionRequest1, true)).thenReturn(mockedSectionAnswer1);

		final String expectedSectionRequest2 = (TestFileUtils.getTestResourceAsString("expected-request-readsection-2.xml", DefaultNetworkServiceTest.class));
		final String mockedSectionAnswer2 = (TestFileUtils.getTestResourceAsString("expected-answer-readsection-2.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedSectionRequest2, true)).thenReturn(mockedSectionAnswer2);

		List<Section> sections = service.getAllSections();

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
	public void getSectionListModifiedSinceDate_WithLastModifiedDate_ReturnSectionList() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listsections-modifieddate.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listsections.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, true)).thenReturn(mockedpAnswer);

		final String expectedSectionRequest1 = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection.xml", DefaultNetworkServiceTest.class));
		final String mockedSectionAnswer1 = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedSectionRequest1, true)).thenReturn(mockedSectionAnswer1);

		final String expectedSectionRequest2 = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-readsection-2.xml", DefaultNetworkServiceTest.class));
		final String mockedSectionAnswer2 = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-readsection-2.xml", DefaultNetworkServiceTest.class));
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

	@Test
	public void getAllPositions_NoError_ReturnPositionList() throws FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-listpositions.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-listpositions.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		List<Position> positions = service.getAllPositions();

		assertEquals(positions.size(), 126);

		assertEquals(positions.get(0), new Position("Bottom", "B"));
		assertEquals(positions.get(1), new Position("Bottom1", "B1"));

		assertEquals(positions.get(124), new Position("x95", "95"));
		assertEquals(positions.get(125), new Position("x96", "96"));
	}

	@Test
	public void getPositionsByName_NoError_ReturnPosition() throws FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-getpositionbyname.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-getpositionbyname.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		Position position = service.getPositionByName("x76");

		assertEquals(position, new Position("x76", "76"));
	}

	public void getPositionsByName_NotFound_NullRetured() throws FileNotFoundException, URISyntaxException,
			IOException,
			ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-getpositionbyname-notexistname.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-getpositionbyname-notexistname.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		Position position = service.getPositionByName("x7600");

		assertNull(position);
	}

	@Test
	public void getAllCompanionPositions_NoError_ReturnPositionList() throws FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		NetworkService service = new DefaultNetworkService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-request-getallcompanionpositions.xml", DefaultNetworkServiceTest.class));
		final String mockedpAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-answer-getallcompanionpositions.xml", DefaultNetworkServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		List<CompanionPosition> positions = service.getAllCompanionsPositions();

		assertEquals(positions.size(), 51);

		assertEquals(positions.get(0), new CompanionPosition("01/02/03"));
		assertEquals(positions.get(1), new CompanionPosition("B"));

		assertEquals(positions.get(49), new CompanionPosition("T1/R"));
		assertEquals(positions.get(50), new CompanionPosition("TL/TR"));
	}
}
