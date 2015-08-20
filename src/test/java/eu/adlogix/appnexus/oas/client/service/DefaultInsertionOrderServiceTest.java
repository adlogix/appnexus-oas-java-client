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
import eu.adlogix.appnexus.oas.client.domain.enums.CampaignsBy;
import eu.adlogix.appnexus.oas.client.domain.enums.InsertionOrderStatus;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;

public class DefaultInsertionOrderServiceTest {

	@Test
	public final void add_ValidParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new DefaultInsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-insertionorder-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-insertionorder-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setAdvertiserId("test_advertiser");
		insertionOrder.setDescription("test");
		insertionOrder.setBookedImpressions(1000l);
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_01", "campaign_02" }));

		service.add(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);



	}

	@Test(expectedExceptions = { OasServerSideException.class })
	public final void add_IdAlreadyExists_ThrowException() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new DefaultInsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-insertionorder-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-insertionorder-id-already-exists-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setAdvertiserId("test_advertiser");
		insertionOrder.setDescription("test");
		insertionOrder.setBookedImpressions(1000l);
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_01", "campaign_02" }));

		service.add(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public final void update_UpdateBookedImps_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new DefaultInsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-update-io-bookedimps-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("update-insertionorder-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setBookedImpressions(5000l);

		service.update(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public final void update_UpdateClicks_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new DefaultInsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-update-io-bookedclicks-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("update-insertionorder-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setBookedClicks(2500l);

		service.update(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public final void update_UpdateCampaigns_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new DefaultInsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-update-io-campaigns-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("update-insertionorder-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = new InsertionOrder();
		insertionOrder.setId("test_insertionorder");
		insertionOrder.setCampaignIds(Arrays.asList(new String[] { "campaign_01", "campaign_02" }));

		service.update(insertionOrder);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public final void getById_ExistingInsertionOrder_ReturnInsertionOrder() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new DefaultInsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-insertionorder-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-insertionorder-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		InsertionOrder insertionOrder = service.getById("test_insertionorder");
		assertEquals(insertionOrder.getId(), "test_insertionorder");
		assertEquals(insertionOrder.getDescription(), "test_description");
		assertEquals(insertionOrder.getCampaignsBy(), CampaignsBy.ADVERTISER);
		assertEquals(insertionOrder.getAdvertiserId(), "test_01");
		assertEquals(insertionOrder.getAgencyId(), "unknown_agency");
		assertEquals(insertionOrder.getStatus(), InsertionOrderStatus.PENDING);
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
	public final void getById_InvalidId_ThrowException() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		InsertionOrderService service = new DefaultInsertionOrderService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-insertionorder-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-insertionorder-invalid-id-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);
		service.getById("test_insertionorder");

	}

}
