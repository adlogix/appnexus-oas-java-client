package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils.normalizeNewLinesToCurPlatform;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import java.util.Arrays;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.InsertionOrder;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;

public class InsertionOrderServiceTest {

	@Test
	public final void addInsertionOrder_ValidParameters_SuccessfullyAdd() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new InsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-insertionorder-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-insertionorder-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setAdvertiserId("test_advertiser");
		insertionOrder.setDescription("test");
		insertionOrder.setBookedImpressions(1000l);
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_01", "campaign_02" }));

		service.addInsertionOrder(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);



	}

	@Test(expectedExceptions = { OasServerSideException.class })
	public final void addInsertionOrder_IdAlreadyExists_ThrowException() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new InsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-insertionorder-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-insertionorder-id-already-exists-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setAdvertiserId("test_advertiser");
		insertionOrder.setDescription("test");
		insertionOrder.setBookedImpressions(1000l);
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_01", "campaign_02" }));

		service.addInsertionOrder(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public final void updateInsertionOrder_UpdateBookedImps_SuccessfullyUpdate() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new InsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-update-io-bookedimps-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("update-insertionorder-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setBookedImpressions(5000l);

		service.updateInsertionOrder(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public final void updateInsertionOrder_UpdateClicks_SuccessfullyUpdate() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new InsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-update-io-bookedclicks-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("update-insertionorder-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setBookedClicks(2500l);

		service.updateInsertionOrder(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public final void updateInsertionOrder_UpdateCampaigns_SuccessfullyUpdate() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new InsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-update-io-campaigns-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("update-insertionorder-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_01", "campaign_02" }));

		service.updateInsertionOrder(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public final void getInsertionOrderById_ExistingInsertionOrder_ReturnInsertionOrder() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new InsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-insertionorder-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-insertionorder-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = service.getInsertionOrderById("test_insertionorder");
		assertEquals(insertionOrder.getId(), "test_insertionorder");
		assertEquals(insertionOrder.getDescription(), "test_description");
		assertEquals(insertionOrder.getCampaignsBy(), "A");
		assertEquals(insertionOrder.getAdvertiserId(), "test_01");
		assertEquals(insertionOrder.getAgencyId(), "unknown_agency");
		assertEquals(insertionOrder.getStatus(), "P");
		assertEquals(insertionOrder.getBookedImpressions().longValue(), 5000l);
		assertEquals(insertionOrder.getBookedClicks().longValue(), 2500l);

		assertNotNull(insertionOrder.getCampaignIds());
		assertEquals(insertionOrder.getCampaignIds().size(), 2);
		assertEquals(insertionOrder.getCampaignIds().get(0), "campaign_01");
		assertEquals(insertionOrder.getCampaignIds().get(1), "campaign_02");

		assertEquals(insertionOrder.getInternalQuickReport(), "short");
		assertEquals(insertionOrder.getExternalQuickReport(), "to-date");
	}

	@Test(expectedExceptions = { OasServerSideException.class })
	public final void getInsertionOrderById_InvalidId_ThrowException() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new InsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-insertionorder-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-insertionorder-invalid-id-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);
		service.getInsertionOrderById("test_insertionorder");

	}

}
