package eu.adlogix.appnexus.oas.client.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.TargetingCodeData;
import eu.adlogix.appnexus.oas.client.domain.enums.TargetingCode;
import eu.adlogix.appnexus.oas.client.exceptions.ResourceNotFoundException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;
import eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TargetingServiceTest {
	@Test
	public void getTargetingCodeDataLists_DeviceGroup_Success() throws FileNotFoundException, URISyntaxException,
			IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		TargetingService service = new TargetingService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-devicegroup-request.xml", TargetingServiceTest.class));
		final String expectedAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-devicegroup-answer.xml", TargetingServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(expectedAnswer);

		TargetingCode targetingType = TargetingCode.DEVICE_GROUP;
		List<TargetingCodeData> targetings = service.getTargetingCodeDataLists(targetingType);

		verify(mockedApiService).callApi(expectedRequest, false);

		assertEquals(targetings.size(), 6);

		validateTarget(targetings, 0, "427", "allblackberries", StringUtils.EMPTY);
		validateTarget(targetings, 1, "429", "ipadipphonetc", StringUtils.EMPTY);
		validateTarget(targetings, 2, "370", "iphones", "all iphones");
		validateTarget(targetings, 3, "409", "malik", StringUtils.EMPTY);
		validateTarget(targetings, 4, "371", "mbcGPphone", "mbcPhone group");
		validateTarget(targetings, 5, "121", "prova", "prova");
	}

	private void validateTarget(List<TargetingCodeData> targetings, int index, String adServerKey, String adServerValue, String desc) {
		TargetingCodeData targeting = targetings.get(index);
		assertEquals(targeting.getCode(), adServerKey);
		assertEquals(targeting.getName(), adServerValue);
		assertEquals(targeting.getDescription(), desc);
	}

	@Test
	public void getTargetingCodeDataLists_BrowserNoDescInResponse_Success() throws FileNotFoundException,
			URISyntaxException, IOException, ResourceNotFoundException, ServiceException {

		OasApiService mockedApiService = mock(OasApiService.class);
		TargetingService service = new TargetingService(mockedApiService);

		final String expectedRequest = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-browser-request.xml", TargetingServiceTest.class));
		final String expectedAnswer = StringTestUtils.normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-browser-answer.xml", TargetingServiceTest.class));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(expectedAnswer);

		TargetingCode targetingType = TargetingCode.BROWSER;
		List<TargetingCodeData> targetings = service.getTargetingCodeDataLists(targetingType);

		verify(mockedApiService).callApi(expectedRequest, false);

		assertEquals(targetings.size(), 17);

		validateTarget(targetings, 0, "aol", "AOL", null);
		validateTarget(targetings, 1, "Blackberry", "Blackberry", null);
		validateTarget(targetings, 2, "firefox", "Firefox", null);
		validateTarget(targetings, 3, "FF_Mobile", "Firefox for Mobile", null);
		validateTarget(targetings, 4, "chrome", "Google Chrome", null);
		validateTarget(targetings, 5, "IE_Mobile", "IE Mobile", null);
		validateTarget(targetings, 16, "webtv", "WebTV", null);
	}
}
