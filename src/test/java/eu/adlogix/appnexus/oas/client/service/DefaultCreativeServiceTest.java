package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.DATE_FORMATTER;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.TIME_FORMATTER;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import org.testng.annotations.Test;
import org.testng.collections.Lists;

import eu.adlogix.appnexus.oas.client.domain.Creative;
import eu.adlogix.appnexus.oas.client.domain.Creative.CreativeFile;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;
import eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils;

public class DefaultCreativeServiceTest {

	@Test
	public void add_CreativeWithFiles_NoError() throws FileNotFoundException, URISyntaxException,
			IOException,
			ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		CreativeService service = new DefaultCreativeService(mockedApiService);

		final String expectedRequest = loadXml("expected-add-creative-request.xml");
		final String mockedpAnswer = loadXml("successful-creative-add-answer.xml");
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		Creative creative = new Creative();
		creative.setId("MyCreativeId_102");
		creative.setCampaignId("TestCampaignId");
		creative.setName("Api Creative");
		creative.setDescription("This is a creative");
		creative.setClickUrl("http://google.com");
		creative.setPositionNames(Lists.newArrayList("TopLeft", "BottomLeft"));
		creative.setCreativeTypeId("unknown_type");
		creative.setDisplay(true);
		creative.setHeight(30);
		creative.setWidth(30);
		creative.setRichMediaCpm(30);
		creative.setTargetWindow("_self");
		creative.setAltText("alt text value");
		creative.setDiscountImpressions(false);

		creative.setStartDate(DATE_FORMATTER.parseLocalDate("2005-12-01"));
		creative.setStartTime(TIME_FORMATTER.parseLocalTime("04:00"));
		creative.setEndDate(DATE_FORMATTER.parseLocalDate("2006-01-31"));
		creative.setEndTime(TIME_FORMATTER.parseLocalTime("15:59"));

		creative.setWeight(10);
		creative.setExpireImmediately(false);
		creative.setNoCache(false);

		creative.setExtraHtml("extra html");
		creative.setExtraText("extra text");

		creative.setBrowserV(Lists.newArrayList("explorer6", "netscape7"));
		creative.setSequenceNo(4);
		creative.setCountOnDownload(true);

		creative.setCreativeFile(new CreativeFile("hi.html", "text/html", "2AojmQpIgeVGBIqEgNlBBL8xtNs2jld8hIdwFB4CQxIog/4IQSe0CUOxBQyPVXpTaYNZWvTZtgaNpnP6LR6zW6735gIADs=+PC9hPgo="));

		creative.setComponentFiles(Lists.newArrayList(new CreativeFile("hi2.gif", "image/gif", "2AojmQpIgeVGBIqEgNlBBL8xtNs2jld8hIdwFB4CQxIog/4IQSe0CUOxBQyPVXpTaYNZWvTZtgaNpnP6LR6zW6735gIADs=+PC9hPgo="), new CreativeFile("hi1.gif", "image/gif", "2AojmQpIgeVGBIqEgNlBBL8xtNs2jld8hIdwFB4CQxIog/4IQSe0CUOxBQyPVXpTaYNZWvTZtgaNpnP6LR6zW6735gIADs=+PC9hPgo=")));

		service.add(creative);

		verify(mockedApiService).callApi(expectedRequest, false);
	}

	private String loadXml(String fileName) throws URISyntaxException, FileNotFoundException, IOException, ResourceNotFoundException {
		return StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString(fileName, DefaultCreativeServiceTest.class));
	}

	@Test
	public void add_CreativeWithRedirectUrl_NoError() throws FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		CreativeService service = new DefaultCreativeService(mockedApiService);

		final String expectedRequest = loadXml("expected-add-creative-request-with-redirecturl.xml");
		final String mockedpAnswer = loadXml("successful-creative-add-answer.xml");
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		Creative creative = new Creative();
		creative.setId("MyCreativeId_102");
		creative.setCampaignId("TestCampaignId");
		creative.setName("Api Creative");
		creative.setDescription("This is a creative");
		creative.setClickUrl("http://google.com");
		creative.setPositionNames(Lists.newArrayList("TopLeft", "BottomLeft"));
		creative.setCreativeTypeId("unknown_type");
		creative.setDisplay(true);
		creative.setHeight(30);
		creative.setWidth(30);
		creative.setRichMediaCpm(30);
		creative.setTargetWindow("_self");
		creative.setAltText("alt text value");
		creative.setDiscountImpressions(false);

		creative.setStartDate(DATE_FORMATTER.parseLocalDate("2005-12-01"));
		creative.setStartTime(TIME_FORMATTER.parseLocalTime("04:00"));
		creative.setEndDate(DATE_FORMATTER.parseLocalDate("2006-01-31"));
		creative.setEndTime(TIME_FORMATTER.parseLocalTime("15:59"));

		creative.setWeight(10);
		creative.setExpireImmediately(false);
		creative.setNoCache(false);

		creative.setExtraHtml("extra html");
		creative.setExtraText("extra text");

		creative.setBrowserV(Lists.newArrayList("explorer6", "netscape7"));
		creative.setSequenceNo(4);
		creative.setCountOnDownload(true);

		creative.setRedirectUrl("http://x.y.z/image.gif");

		service.add(creative);

		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void add_CreativeWithoutFilesNorRedirectUrl_NoError() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		CreativeService service = new DefaultCreativeService(mockedApiService);

		final String expectedRequest = loadXml("expected-add-creative-request-without-file-and-redirecturl.xml");
		final String mockedpAnswer = loadXml("successful-creative-add-answer.xml");
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		Creative creative = new Creative();
		creative.setId("9337933_102");
		creative.setCampaignId("TestCampaignId");
		creative.setName("skin_repubblica_hp");
		creative.setDescription("skin_repubblica_hp");
		creative.setClickUrl("http://default.def");
		creative.setPositionNames(Lists.newArrayList("Top3"));
		creative.setCreativeTypeId("unknown_type");
		creative.setDisplay(true);
		creative.setTargetWindow("_blank");
		creative.setDiscountImpressions(false);

		creative.setStartTime(TIME_FORMATTER.parseLocalTime("00:00"));
		creative.setEndTime(TIME_FORMATTER.parseLocalTime("23:59"));

		creative.setWeight(10);
		creative.setExpireImmediately(false);
		creative.setNoCache(false);

		creative.setSequenceNo(0);
		creative.setCountOnDownload(false);

		service.add(creative);

		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test(expectedExceptions = OasServerSideException.class, expectedExceptionsMessageRegExp = "OAS Error \\[573\\]: 'enddate_past'")
	public void add_CreativeWithoutFilesNorRedirectUrl_DateInPastError() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		CreativeService service = new DefaultCreativeService(mockedApiService);

		final String expectedRequest = loadXml("expected-add-creative-request-without-file-and-redirecturl.xml");
		final String mockedpAnswer = loadXml("enddatepast-creative-add-answer.xml");
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedpAnswer);

		Creative creative = new Creative();
		creative.setId("9337933_102");
		creative.setCampaignId("TestCampaignId");
		creative.setName("skin_repubblica_hp");
		creative.setDescription("skin_repubblica_hp");
		creative.setClickUrl("http://default.def");
		creative.setPositionNames(Lists.newArrayList("Top3"));
		creative.setCreativeTypeId("unknown_type");
		creative.setDisplay(true);
		creative.setTargetWindow("_blank");
		creative.setDiscountImpressions(false);

		creative.setStartTime(TIME_FORMATTER.parseLocalTime("00:00"));
		creative.setEndTime(TIME_FORMATTER.parseLocalTime("23:59"));

		creative.setWeight(10);
		creative.setExpireImmediately(false);
		creative.setNoCache(false);

		creative.setSequenceNo(0);
		creative.setCountOnDownload(false);

		service.add(creative);

		verify(mockedApiService).callApi(expectedRequest, false);
	}
}
