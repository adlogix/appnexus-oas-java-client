package eu.adlogix.appnexus.oas.client.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.CampaignDeliveryByPageAndPosition;
import eu.adlogix.appnexus.oas.client.domain.CampaignDetail;
import eu.adlogix.appnexus.oas.client.domain.CampaignDetail.CampaignDetailDeliveryHistoryRow;
import eu.adlogix.appnexus.oas.client.domain.PageAtPositionDeliveryInformation;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;
import eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils;

import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.DATE_FORMATTER;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ReportServiceTest {

	public final List<PageAtPositionDeliveryInformation.Row> expectedHistoStats() {
		List<PageAtPositionDeliveryInformation.Row> expectedHistoStats = new ArrayList<PageAtPositionDeliveryInformation.Row>();

		expectedHistoStats.add(new PageAtPositionDeliveryInformation.Row(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "cote_maison", "Frame1", 71203, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformation.Row(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "cote_maison", "Left", 71203, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformation.Row(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "cote_maison/CALCULETTES", "Middle", 1436, 3));
		expectedHistoStats.add(new PageAtPositionDeliveryInformation.Row(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "express", "Bottom", 3, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformation.Row(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "express", "Frame1", 3, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformation.Row(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(3), "express/ECONOMIE_HP", "Middle3", 1591, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformation.Row(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(3), "express/ECONOMIE_HP", "Right2", 1300, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformation.Row(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "corriere.it/tv", "x85", 0, 3039));
		expectedHistoStats.add(new PageAtPositionDeliveryInformation.Row(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "corriere.it/tv", "x96", 166551, 0));
		return expectedHistoStats;
	}


	@Test
	public void getPageAtPositionDeliveryInformation_MultiplePage_CorrectlyExecutes() throws ServiceException,
			FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException {

		OasApiService mockedApiService = mock(OasApiService.class);
		ReportService service = new ReportService(mockedApiService);

		final String expectedPageOneRequest = fileToString("inventory-report-page1-request-test.xml");
		final String mockedPageOneAnswer = fileToString("inventory-report-page1-answer-test.xml");
		when(mockedApiService.callApi(expectedPageOneRequest, true)).thenReturn(mockedPageOneAnswer);

		final String expectedPageTwoRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("inventory-report-page2-request-test.xml", ReportServiceTest.class));
		final String mockedPageTwoAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("inventory-report-page2-answer-test.xml", ReportServiceTest.class));
		when(mockedApiService.callApi(expectedPageTwoRequest, true)).thenReturn(mockedPageTwoAnswer);

		final DateTime testedStartDate = new DateTime().withYear(2011).withMonthOfYear(4).withDayOfMonth(1);
		final DateTime testedEndDate = new DateTime().withYear(2011).withMonthOfYear(5).withDayOfMonth(10);

		final PageAtPositionDeliveryInformation actualHistoStats = service.getPageAtPositionDeliveryInformation(testedStartDate, testedEndDate);

		assertListHistoStatItemEquals(actualHistoStats.getRows(), expectedHistoStats(), "issue with inventory-report parsing");
	}

	private void assertListHistoStatItemEquals(List<PageAtPositionDeliveryInformation.Row> actuals,
			List<PageAtPositionDeliveryInformation.Row> expecteds, String msg) {
		assertEquals(actuals.size(), expecteds.size(), "issue with sizes for " + msg);

		int cnt = 0;
		for (PageAtPositionDeliveryInformation.Row actual : actuals) {
			assertHistoStatItemEquals(actual, expecteds.get(cnt), "issue for item " + cnt + " for " + msg);
			cnt++;
		}
	}

	private void assertHistoStatItemEquals(PageAtPositionDeliveryInformation.Row actual,
			PageAtPositionDeliveryInformation.Row expected, String msg) {
		assertEquals(actual.getDate().getYear(), expected.getDate().getYear(), "issue with year date for " + msg);
		assertEquals(actual.getDate().getDayOfYear(), expected.getDate().getDayOfYear(), "issue with day date for "
				+ msg);
		assertEquals(actual.getPageUrl(), expected.getPageUrl(), "issue with pageUrl for " + msg);
		assertEquals(actual.getPositionName(), expected.getPositionName(), "issue with positionName for " + msg);
		assertEquals(actual.getImpressions(), expected.getImpressions(), "issue with impressions for " + msg);
		assertEquals(actual.getClicks(), expected.getClicks(), "issue with clicks for " + msg);
	}

	@Test
	public void getCampaignDetail_ValidateCampaignDetailDeliveryHistoryRowsOnlyHistoryInResponse_CorrectlyExecutes()
			throws ServiceException,
			FileNotFoundException, URISyntaxException, IOException, ResourceNotFoundException {
		OasApiService mockedApiService = mock(OasApiService.class);
		ReportService service = new ReportService(mockedApiService);

		final String expectedLiveDeliRequest = fileToString("expected-request-addeliveryreport-inventory.xml");
		final String mockedLiveDeliAnswer = fileToString("expected-response-addeliveryreport-inventory.xml");
		when(mockedApiService.callApi(expectedLiveDeliRequest, false)).thenReturn(mockedLiveDeliAnswer);

		CampaignDetail detail = service.getCampaignDetail("VALENTINOSPA_REDVALEN_MRepHP_Abb_230315_21820", DATE_FORMATTER.parseDateTime("2015-03-23"), DATE_FORMATTER.parseDateTime("2015-03-26"));

		List<CampaignDetailDeliveryHistoryRow> rows = detail.getDeliveryHistoryRows();

		assertEquals(rows.get(0), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2015-03-26"), 211597l, 944l));
		assertEquals(rows.get(1), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2015-03-25"), 222752l, 1146l));
		assertEquals(rows.get(2), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2015-03-24"), 232027l, 1164l));
		assertEquals(rows.get(3), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2015-03-23"), 240014l, 1456l));

	}

	private String fileToString(String fileName) throws URISyntaxException, FileNotFoundException, IOException,
			ResourceNotFoundException {
		return StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString(fileName, ReportServiceTest.class));
	}

	@Test
	public void getCampaignDetail_ValidateCampaignDetailDeliveryHistoryRows_CorrectlyExecutes()
			throws ServiceException, FileNotFoundException, URISyntaxException, IOException,
			ResourceNotFoundException {
		OasApiService mockedApiService = mock(OasApiService.class);
		ReportService service = new ReportService(mockedApiService);

		final String expectedLiveDeliRequest = fileToString("expected-request-adstatusreport-livedeli-noenddate.xml");
		final String mockedLiveDeliAnswer = fileToString("expected-answer-adstatusreport-livedeli-noenddate.xml");
		when(mockedApiService.callApi(expectedLiveDeliRequest, false)).thenReturn(mockedLiveDeliAnswer);

		CampaignDetail detail = service.getCampaignDetail("0312_AXA-CENTRAL_XPR_HAB_5894", DATE_FORMATTER.parseDateTime("2012-02-22"), DATE_FORMATTER.parseDateTime("2012-03-04"));

		List<CampaignDetailDeliveryHistoryRow> rows = detail.getDeliveryHistoryRows();

		assertEquals(rows.get(0), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2012-03-02"), 3l, 0l));
		assertEquals(rows.get(1), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2012-03-01"), 14l, 0l));
		assertEquals(rows.get(2), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2012-02-29"), 214l, 3l));
		assertEquals(rows.get(3), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2012-02-28"), 394l, 20l));
		assertEquals(rows.get(4), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2012-02-27"), 139l, 11l));
		assertEquals(rows.get(5), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2012-02-24"), 4l, 0l));
		assertEquals(rows.get(6), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2012-02-23"), 1l, 0l));
		assertEquals(rows.get(7), new CampaignDetailDeliveryHistoryRow(DATE_FORMATTER.parseDateTime("2012-02-22"), 47l, 0l));

	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "endDate shouldn't have a null value")
	public void getCampaignDetail_EndDateNull_CorrectlyExecutes() throws ServiceException, FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException {

		OasApiService mockedApiService = mock(OasApiService.class);
		ReportService service = new ReportService(mockedApiService);

		service.getCampaignDetail("0312_AXA-CENTRAL_XPR_HAB_5894", DATE_FORMATTER.parseDateTime("2012-02-22"), null);
	}

	@Test(expectedExceptions = OasServerSideException.class, expectedExceptionsMessageRegExp = "OAS Error \\[408\\]: 'You do not have enough permission to see Delivery.Campaign.Base.T280.01 report.'")
	public void getCampaignDeliveryByPageAndPosition_SameDayWithError_ThrowsException() throws FileNotFoundException,
			URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		ReportService service = new ReportService(mockedApiService);

		final String expectedAdTrafficRequestOfDay = fileToString("expected-request-trafficonedayreport-readcampaign.xml");
		final String answerAdTrafficRequestOfDay = fileToString("expected-answer-trafficonedayreport-readcampaign.xml");
		when(mockedApiService.callApi(expectedAdTrafficRequestOfDay, true)).thenReturn(answerAdTrafficRequestOfDay);

		service.getCampaignDeliveryByPageAndPosition("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278", DATE_FORMATTER.parseDateTime("2012-02-27"));

		verify(mockedApiService).callApi(expectedAdTrafficRequestOfDay, true);
	}

	@Test
	public void getCampaignDeliveryByPageAndPosition_SingleDayNoError_NoError() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		ReportService service = new ReportService(mockedApiService);

		final String expectedAdTrafficRequestOfDay23 = fileToString("expected-request-addeliveryreport-report-23.xml");
		final String answerAdTrafficRequestOfDay23 = fileToString("expected-response-addeliveryreport-report-23.xml");
		when(mockedApiService.callApi(expectedAdTrafficRequestOfDay23, true)).thenReturn(answerAdTrafficRequestOfDay23);

		CampaignDeliveryByPageAndPosition delivery = service.getCampaignDeliveryByPageAndPosition("VALENTINOSPA_REDVALEN_MRepHP_Abb_230315_21820", DATE_FORMATTER.parseDateTime("2015-03-23"));

		assertEquals(delivery.getRows().get(0), new CampaignDeliveryByPageAndPosition.Row("mrepubblica.it/home", "Top3", 120011l, 461l));
		assertEquals(delivery.getRows().get(1), new CampaignDeliveryByPageAndPosition.Row("mrepubblica.it/home", "Middle1", 120003l, 995l));

		verify(mockedApiService).callApi(expectedAdTrafficRequestOfDay23, true);
	}

	@Test
	public void getCampaignDeliveryByPageAndPosition_TwoDayNoError_NoError() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		ReportService service = new ReportService(mockedApiService);

		final String expectedAdTrafficRequest = fileToString("expected-request-addeliveryreport-1.xml");
		final String answerAdTrafficRequest = fileToString("expected-answer-addeliveryreport-1.xml");
		when(mockedApiService.callApi(expectedAdTrafficRequest, true)).thenReturn(answerAdTrafficRequest);

		CampaignDeliveryByPageAndPosition delivery = service.getCampaignDeliveryByPageAndPosition("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278", DATE_FORMATTER.parseDateTime("2012-02-27"), DATE_FORMATTER.parseDateTime("2012-02-28"));

		assertEquals(delivery.getRows().size(), 33);
		assertEquals(delivery.getRows().get(0), new CampaignDeliveryByPageAndPosition.Row("express_styles/SAVEURS_RG", "Middle", 30423l, 100l));
		assertEquals(delivery.getRows().get(1), new CampaignDeliveryByPageAndPosition.Row("express_styles/VIP_RG", "Middle", 19931l, 33l));
		assertEquals(delivery.getRows().get(2), new CampaignDeliveryByPageAndPosition.Row("express_styles/DIVERS", "Middle", 2619l, 11l));
		assertEquals(delivery.getRows().get(3), new CampaignDeliveryByPageAndPosition.Row("express_styles/MODE_RG", "Middle", 2451l, 9l));
		assertEquals(delivery.getRows().get(32), new CampaignDeliveryByPageAndPosition.Row("express_styles/BLOGS_HP", "Middle", 1l, 0l));

		verify(mockedApiService).callApi(expectedAdTrafficRequest, true);
	}
}
