package eu.adlogix.appnexus.oas.client.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import org.joda.time.DateTime;
import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.certificate.TestCredentials;
import eu.adlogix.appnexus.oas.client.domain.PageAtPositionDeliveryInformationRow;
import eu.adlogix.appnexus.oas.utils.file.AdlResourceNotFoundException;
import eu.adlogix.appnexus.oas.utils.file.AdlTestFileUtils;
import eu.adlogix.appnexus.oas.utils.string.StringTestUtils;

public class ReportServiceTest {

	public final List<PageAtPositionDeliveryInformationRow> expectedHistoStats() {
		List<PageAtPositionDeliveryInformationRow> expectedHistoStats = new ArrayList<PageAtPositionDeliveryInformationRow>();

		expectedHistoStats.add(new PageAtPositionDeliveryInformationRow(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "cote_maison", "Frame1", 71203, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformationRow(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "cote_maison", "Left", 71203, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformationRow(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "cote_maison/CALCULETTES", "Middle", 1436, 3));
		expectedHistoStats.add(new PageAtPositionDeliveryInformationRow(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "express", "Bottom", 3, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformationRow(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "express", "Frame1", 3, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformationRow(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(3), "express/ECONOMIE_HP", "Middle3", 1591, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformationRow(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(3), "express/ECONOMIE_HP", "Right2", 1300, 0));
		expectedHistoStats.add(new PageAtPositionDeliveryInformationRow(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "corriere.it/tv", "x85", 0, 3039));
		expectedHistoStats.add(new PageAtPositionDeliveryInformationRow(new DateTime().withYear(2011)
				.withMonthOfYear(3)
				.withDayOfMonth(1), "corriere.it/tv", "x96", 166551, 0));
		return expectedHistoStats;
	}

	private Properties getTestCredentials() {
		return TestCredentials.getTestCredentials();
	}

	@Test
	public void getPageAtPositionDeliveryInformation_MultiplePage_CorrectlyExecutes() throws ServiceException,
			FileNotFoundException, URISyntaxException,
			IOException, AdlResourceNotFoundException {

		XaxisApiService mockedApiService = mock(XaxisApiService.class);
		CertificateManager mockedCertificateManager = mock(CertificateManager.class);
		ReportService service = new ReportService(getTestCredentials(), mockedApiService, mockedCertificateManager);

		final String expectedPageOneRequest = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("inventory-report-page1-request-test.xml", ReportServiceTest.class));
		final String mockedPageOneAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("inventory-report-page1-answer-test.xml", ReportServiceTest.class));
		when(mockedApiService.callApi(expectedPageOneRequest, true)).thenReturn(mockedPageOneAnswer);

		final String expectedPageTwoRequest = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("inventory-report-page2-request-test.xml", ReportServiceTest.class));
		final String mockedPageTwoAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(AdlTestFileUtils.getTestResourceAsString("inventory-report-page2-answer-test.xml", ReportServiceTest.class));
		when(mockedApiService.callApi(expectedPageTwoRequest, true)).thenReturn(mockedPageTwoAnswer);

		final DateTime testedStartDate = new DateTime().withYear(2011).withMonthOfYear(4).withDayOfMonth(1);
		final DateTime testedEndDate = new DateTime().withYear(2011).withMonthOfYear(5).withDayOfMonth(10);

		final List<PageAtPositionDeliveryInformationRow> actualHistoStats = service.getPageAtPositionDeliveryInformation(testedStartDate, testedEndDate);

		assertListHistoStatItemEquals(actualHistoStats, expectedHistoStats(), "issue with inventory-report parsing");
	}

	private void assertListHistoStatItemEquals(List<PageAtPositionDeliveryInformationRow> actuals,
			List<PageAtPositionDeliveryInformationRow> expecteds, String msg) {
		assertEquals(actuals.size(), expecteds.size(), "issue with sizes for " + msg);

		int cnt = 0;
		for (PageAtPositionDeliveryInformationRow actual : actuals) {
			assertHistoStatItemEquals(actual, expecteds.get(cnt), "issue for item " + cnt + " for " + msg);
			cnt++;
		}
	}

	private void assertHistoStatItemEquals(PageAtPositionDeliveryInformationRow actual,
			PageAtPositionDeliveryInformationRow expected, String msg) {
		assertEquals(actual.getDate().getYear(), expected.getDate().getYear(), "issue with year date for " + msg);
		assertEquals(actual.getDate().getDayOfYear(), expected.getDate().getDayOfYear(), "issue with day date for "
				+ msg);
		assertEquals(actual.getPageUrl(), expected.getPageUrl(), "issue with pageUrl for " + msg);
		assertEquals(actual.getPositionName(), expected.getPositionName(), "issue with positionName for " + msg);
		assertEquals(actual.getImpressions(), expected.getImpressions(), "issue with impressions for " + msg);
		assertEquals(actual.getClicks(), expected.getClicks(), "issue with clicks for " + msg);
	}
}
