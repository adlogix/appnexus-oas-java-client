package eu.adlogix.appnexus.oas.client.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.ExcludableTargeting;
import eu.adlogix.appnexus.oas.client.domain.RdbTargeting;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.domain.Targeting;
import eu.adlogix.appnexus.oas.client.domain.TargetingCode;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;

import static eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils.normalizeNewLinesToCurPlatform;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class CampaignServiceTest {

	private static final List<String> EMPTY_STRING_LIST = Lists.newArrayList();

	@Test
	public void getCampaignById_ExistingCampaign_ReturnCampaign() throws Exception {

		final String campaignId = "0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278";

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-campaign-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-campaign-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = service.getCampaignById(campaignId);

		assertEquals(campaign.getId(), "0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		assertEquals(campaign.getType(), "RM");
		assertEquals(campaign.getInsertionOrderId(), "IOID");
		assertEquals(campaign.getAdvertiserId(), "GROUPEZENITHOPTIMEDIA_ZENI0D");
		assertEquals(campaign.getName(), "[3] 1017981imps entree site du 27/02 au 31/03");
		assertEquals(campaign.getAgencyId(), "unknown_agency");
		assertEquals(campaign.getDescription(), "[3] 1017981imps entree site du 27/02 au 31/03");
		assertEquals(campaign.getCampaignManager(), "Campaign_mangaer_01");
		assertEquals(campaign.getProductId(), "default-product");
		assertEquals(campaign.getStatus(), "L");

		List<String> groupsExpected = Arrays.asList(new String[] { "GROUPE_ZENITHOPTIMEDIA_0212_229",
				"GROUPE_GROUPE_ZE_0212_229" });
		assertEquals(campaign.getCampaignGroupIds(), groupsExpected);

		List<String> competitiveCategoryIdsExpected = Arrays.asList(new String[] { "Luxe", "intrusif" });
		assertEquals(campaign.getCompetitiveCategroryIds(), competitiveCategoryIdsExpected);

		List<String> usersExpected = Arrays.asList(new String[] { "User_001" });
		assertEquals(campaign.getExternalUserIds(), usersExpected);

		assertEquals(campaign.getInternalQuickReport(), "to-date");
		assertEquals(campaign.getExternalQuickReport(), "short");

		assertEquals(campaign.getImpressions().longValue(), 1169118l);
		assertEquals(campaign.getClicks().longValue(), 0l);
		assertEquals(campaign.getUniques().longValue(), 0l);
		assertEquals(campaign.getWeight(), "1000");
		assertEquals(campaign.getPriorityLevel(), "5");
		assertEquals(campaign.getCompletion(), "S");

		assertEquals(campaign.getStartDate(), new LocalDate(2012, 2, 27));
		assertEquals(campaign.getStartTime(), new LocalTime(04, 00));
		assertEquals(campaign.getEndDate(), new LocalDate(2012, 3, 30));
		assertEquals(campaign.getEndTime(), new LocalTime(20, 59));

		assertEquals(campaign.getReach(), "D");
		assertEquals(campaign.getDailyImps(), new Long(54642l));
		assertEquals(campaign.getDailyClicks(), new Long(0));
		assertEquals(campaign.getDailyUniques(), new Long(0));
		assertEquals(campaign.getSmoothOrAsap(), "S");
		assertEquals(campaign.getImpressionsOverrun(), new Long(50));

		assertEquals(campaign.getCompanionPositions(), Arrays.asList(new String[] { "M/M2/T" }));
		assertEquals(campaign.getStrictCompanions(), "Y");
		assertEquals(campaign.getPrimaryImpsPerVisitor(), new Long(4));
		assertEquals(campaign.getPrimaryClicksPerVisitor(), new Long(0));
		assertEquals(campaign.getPrimaryFrequencyScope(), new Long(1));
		assertEquals(campaign.getSecondaryImpsPerVisitor(), new Long(6));
		assertEquals(campaign.getSecondaryFrequencyScope(), new Long(0));

		assertEquals(campaign.getHourOfDay(), Arrays.asList(new String[] { "18", "19", "20" }));
		assertEquals(campaign.getDayOfWeek(), Arrays.asList(new String[] { "0", "2", "4", "6" }));
		assertEquals(campaign.getUserTimeZone(), "N");

		assertEquals(campaign.getSectionIds(), Arrays.asList(new String[] { "ALL_EXPRESS_STYLES" }));
		assertEquals(campaign.getPageUrls(), Arrays.asList(new String[] { "express_styles/CONCOURS",
				"express_styles/SHOPPING_HP", "express_styles/VIP_RG", "express_styles/PHOTO_RG_DIAPO",
				"express_styles/NOUVELLEGENERATION_RG", "express_styles/RSS" }));

		assertEquals(campaign.getExcludeTargets().booleanValue(), false);

		List<Targeting> targetingList = campaign.getTargeting();
		for (Targeting targeting : targetingList) {
			if (targeting.getCode().equals(TargetingCode.TOP_DOMAIN)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "US", "COM", "EDU" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			} else if (targeting.getCode().equals(TargetingCode.BANDWIDTH)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "LAN", "DSL/Cable" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			} else if (targeting.getCode().equals(TargetingCode.CONTINENT)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "AS", "EU", "NA" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			} else if (targeting.getCode().equals(TargetingCode.COUNTRY)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "US", "CA", "AF" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			} else if (targeting.getCode().equals(TargetingCode.STATE)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "AL:BERAT", "AL:FIER", "AM:ARTASHAT" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			} else if (targeting.getCode().equals(TargetingCode.MSA)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "0240", "0280", "6280" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			} else if (targeting.getCode().equals(TargetingCode.DMA)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "507", "508" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			} else if (targeting.getCode().equals(TargetingCode.OS)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "winxp", "unix" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			} else if (targeting.getCode().equals(TargetingCode.BROWSER)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "msie", "netscape", "mozilla" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			} else if (targeting.getCode().equals(TargetingCode.BROWSER_VERSIONS)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "explorer6", "mozilla0", "mozilla1",
						"netscape7" }));
				assertEquals(((ExcludableTargeting) targeting).getExclude().booleanValue(), false);

			}
		}

		RdbTargeting rdbTargeting = campaign.getRdbTargeting();
		assertEquals(rdbTargeting.getAgeExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getAgeFrom().intValue(), 15);
		assertEquals(rdbTargeting.getAgeTo().intValue(), 45);
		assertEquals(rdbTargeting.getGenderExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getGender().toString(), "E");
		assertEquals(rdbTargeting.getIncomeExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getIncomeFrom().intValue(), 1000);
		assertEquals(rdbTargeting.getIncomeTo().intValue(), 10000);
		assertEquals(rdbTargeting.getSubscriberCodeExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getSubscriberCode(), "");
		assertEquals(rdbTargeting.getPreferenceFlagsExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getPreferenceFlags(), "");

		SegmentTargeting segmentTargeting = campaign.getSegmentTargeting();
		assertEquals(segmentTargeting.getSegmentClusterMatch(), "A");
		assertEquals(segmentTargeting.getExclude().booleanValue(), false);
		assertEquals(segmentTargeting.getValues(), Arrays.asList(new String[] { "HockeyFans" }));

		assertEquals(campaign.getExcludedSiteIds(), Arrays.asList(new String[] { "site_001" }));
		assertEquals(campaign.getExcludedPageUrls(), Arrays.asList(new String[] { "express_page/TEST" }));

		assertEquals(campaign.getCpm(), new Double(9.8266));
		assertEquals(campaign.getCpc(), new Double(0.0));
		assertEquals(campaign.getCpa(), new Double(0.0));
		assertEquals(campaign.getFlatRate(), new Double(0.5));
		assertEquals(campaign.getTax(), new Double(1.0));
		assertEquals(campaign.getAgencyCommission(), new Double(10.0));
		assertEquals(campaign.getPaymentMethod(), "C");
		assertEquals(campaign.getIsYieldManaged(), "N");
		assertEquals(campaign.getBillTo(), "G");
		assertEquals(campaign.getCurrency(), "EUR");

	}

	@Test
	public void addCampaign_DefaultTypeWithMandatoryParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-mandatory-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("B");
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_DefaultTypeWithAdditionalParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-default-type-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("Test_Campaign");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("12");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setDescription("Added from API");
		campaign.setCompletion("S");
		campaign.setStartDate(new LocalDate(2016, 1, 1));
		campaign.setEndDate(new LocalDate(2016, 1, 31));
		campaign.setStartTime(new LocalTime(0, 0));
		campaign.setEndTime(new LocalTime(0, 59));
		campaign.setPaymentMethod("B");
		campaign.setCampaignGroupIds(Arrays.asList(new String[] { "campaign_group_01" }));
		campaign.setImpressions(250000l);
		campaign.setWeight("100");
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(1l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(1l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		campaign.setUserTimeZone("N");
		campaign.setPageUrls(Arrays.asList(new String[] { "test.com/home" }));
		campaign.setCpm(4000.0);
		campaign.setPaymentMethod("C");
		campaign.setCurrency("EUR");
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test(expectedExceptions = { OasServerSideException.class })
	public final void addCampaign_IdAlreadyExists_ThrowException() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-mandatory-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-id-already-exists-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("B");
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void addCampaign_CltTypeWithMandatoryParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-clt-campaign-mandatory-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setType("CLT");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setCreativeTargetId("SampleCreativeTarget");
		campaign.setProductId("default-product");
		campaign.setPaymentMethod("B");
		campaign.setPriorityLevel("0");
		campaign.setCompletion("S");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		service.addCampaign(campaign);
	}

	@Test
	public void addCampaign_CltTypeWithAdditionalParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-clt-type-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setType("CLT");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setCreativeTargetId("SampleCreativeTarget");
		campaign.setProductId("default-product");
		campaign.setDescription("Added from API");
		campaign.setCampaignGroupIds(Arrays.asList(new String[] { "campaign_group_01" }));
		campaign.setPaymentMethod("B");
		campaign.setImpressions(250000l);
		campaign.setWeight("100");
		campaign.setPriorityLevel("0");
		campaign.setCompletion("S");
		campaign.setStartDate(new LocalDate(2016, 1, 1));
		campaign.setEndDate(new LocalDate(2016, 1, 31));
		campaign.setStartTime(new LocalTime(0, 0));
		campaign.setEndTime(new LocalTime(0, 59));
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(1l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(1l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		campaign.setUserTimeZone("N");
		campaign.setPageUrls(Arrays.asList(new String[] { "test.com/home" }));
		campaign.setCpm(4000.0);
		campaign.setPaymentMethod("C");
		campaign.setCurrency("EUR");
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void addCampaign_WithCompanionPositionsAndStrictCompanions_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-companionparameters-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("B");
		campaign.setCompanionPositions(Arrays.asList(new String[] { "B/T" }));
		campaign.setStrictCompanions("N");
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCompanionPositions_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-companion-positions-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("B");
		campaign.setCompanionPositions(Arrays.asList(new String[] { "B/T" }));
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCpmParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-cpm-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");
		campaign.setImpressions(250000l);
		campaign.setDailyImps(50l);
		campaign.setImpressionsOverrun(10l);
		campaign.setPrimaryImpsPerVisitor(10l);
		campaign.setPrimaryFrequencyScope(1l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setSecondaryImpsPerVisitor(10l);
		campaign.setSecondaryFrequencyScope(2l);
		campaign.setCpm(4000.0);
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCpcParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-cpc-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");
		campaign.setClicks(250000l);
		campaign.setDailyClicks(50l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryClicksPerVisitor(10l);
		campaign.setPrimaryFrequencyScope(1l);
		campaign.setCpc(4.0);
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-targeting-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");

		List<Targeting> targeting = new ArrayList<Targeting>();

		ExcludableTargeting topLevelDomain = new ExcludableTargeting(TargetingCode.TOP_DOMAIN);
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		targeting.add(topLevelDomain);

		ExcludableTargeting bandwidthTargeting = new ExcludableTargeting(TargetingCode.BANDWIDTH);
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		targeting.add(bandwidthTargeting);

		ExcludableTargeting continentTargeting = new ExcludableTargeting(TargetingCode.CONTINENT);
		continentTargeting.setExclude(false);
		continentTargeting.setValues(Arrays.asList("AU", "EU"));
		targeting.add(continentTargeting);

		ExcludableTargeting countryTargeting = new ExcludableTargeting(TargetingCode.COUNTRY);
		countryTargeting.setExclude(true);
		countryTargeting.setValues(Arrays.asList("BE", "ZA"));
		targeting.add(countryTargeting);

		ExcludableTargeting stateTargeting = new ExcludableTargeting(TargetingCode.STATE);
		stateTargeting.setExclude(false);
		stateTargeting.setValues(Arrays.asList("BE:BRUSSELS"));
		targeting.add(stateTargeting);

		ExcludableTargeting msaTargeting = new ExcludableTargeting(TargetingCode.MSA);
		msaTargeting.setExclude(false);
		msaTargeting.setValues(Arrays.asList("11220"));
		targeting.add(msaTargeting);

		ExcludableTargeting dmaTargeting = new ExcludableTargeting(TargetingCode.DMA);
		dmaTargeting.setExclude(false);
		dmaTargeting.setValues(Arrays.asList("803", "501", "650"));
		targeting.add(dmaTargeting);

		ExcludableTargeting osTargeting = new ExcludableTargeting(TargetingCode.OS);
		osTargeting.setExclude(false);
		osTargeting.setValues(Arrays.asList("winxp", "unix"));
		targeting.add(osTargeting);

		ExcludableTargeting browserTargeting = new ExcludableTargeting(TargetingCode.BROWSER);
		browserTargeting.setExclude(false);
		browserTargeting.setValues(Arrays.asList("opera", "firefox"));
		targeting.add(browserTargeting);

		ExcludableTargeting browserVersionTargeting = new ExcludableTargeting(TargetingCode.BROWSER_VERSIONS);
		browserVersionTargeting.setExclude(false);
		browserVersionTargeting.setValues(Arrays.asList("firefox19", "opera12"));
		targeting.add(browserVersionTargeting);

		Targeting zoneTargeting = new Targeting(TargetingCode.ZONE);
		zoneTargeting.setValues(Arrays.asList(new String[] { "1", "2" }));
		targeting.add(zoneTargeting);

		campaign.setTargeting(targeting);

		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithSegmentTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-segmenttargeting-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentClusterMatch("L");
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithRdbTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-rdbtargeting-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");

		RdbTargeting rdbTargeting = new RdbTargeting();
		rdbTargeting.setAgeExclude(true);
		rdbTargeting.setAgeFrom(13);
		rdbTargeting.setAgeTo(30);
		rdbTargeting.setGenderExclude(true);
		rdbTargeting.setGender("M");
		rdbTargeting.setIncomeExclude(true);
		rdbTargeting.setIncomeFrom(13l);
		rdbTargeting.setIncomeTo(30l);
		rdbTargeting.setSubscriberCodeExclude(true);
		rdbTargeting.setSubscriberCode("TEST");
		rdbTargeting.setPreferenceFlagsExclude(true);
		rdbTargeting.setPreferenceFlags("012345678911");
		campaign.setRdbTargeting(rdbTargeting);
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithEmptyTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-empty-targeting-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");

		Targeting zoneTargeting = new Targeting(TargetingCode.ZONE);
		zoneTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setTargeting(Lists.newArrayList(zoneTargeting));
		campaign.setSegmentTargeting(new SegmentTargeting());
		campaign.setRdbTargeting(new RdbTargeting());
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithHourOfDayAndDayOfWeek_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-hod-dow-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");
		campaign.setHourOfDay(Arrays.asList(new String[] { "18", "19", "20" }));
		campaign.setDayOfWeek(Arrays.asList(new String[] { "2", "3", "4" }));
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCpmParamsFixedReachAndZeroFrequencyImps_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpm-fixedreach-no-frequencyimps-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");
		campaign.setReach("F");
		campaign.setImpressions(250000l);
		campaign.setDailyImps(50l);
		campaign.setCpm(4000.0);
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(0l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		service.addCampaign(campaign);

		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCpcParamsFixedReachAndZeroFrequencyClicks_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpc-fixedreach-no-frequencyclicks-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");
		campaign.setReach("F");
		campaign.setImpressions(1l);
		campaign.setDailyImps(1l);
		campaign.setClicks(250000l);
		campaign.setDailyClicks(50l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(0l);
		campaign.setCpc(4.0);
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCpmParamsDynamicReachAndZeroFrequencyImps_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpm-dynamicreach-no-frequencyimps-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");
		campaign.setReach("D");
		campaign.setImpressions(250000l);
		campaign.setDailyImps(50l);
		campaign.setCpm(4000.0);
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(0l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCpcParamsDynamicReachAndZeroFrequencyClicks_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpc-dynamicreach-no-frequencyclicks-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");
		campaign.setReach("D");
		campaign.setImpressions(1l);
		campaign.setDailyImps(1l);
		campaign.setClicks(250000l);
		campaign.setDailyClicks(50l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(0l);
		campaign.setCpc(4.0);

		service.addCampaign(campaign);

		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCpmParamsOpenReachAndZeroFrequencyImps_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpm-openreach-no-frequencyimps-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");
		campaign.setReach("O");
		campaign.setImpressions(250000l);
		campaign.setCpm(4000.0);
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(0l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		service.addCampaign(campaign);

		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCpcParamsOpenReachAndZeroFrequencyClicks_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpc-openreach-no-frequencyclicks-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("C");
		campaign.setReach("O");
		campaign.setClicks(250000l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(0l);
		campaign.setCpc(4.0);
		service.addCampaign(campaign);

		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithCampaignManager_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-campaignmanager-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("B");
		campaign.setCampaignManager("test_campaign_manager");
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void addCampaign_WithStartDateTimeAndEndDateTime_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-startdate-enddate-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel("1");
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setCompletion("S");
		campaign.setPaymentMethod("B");
		campaign.setStartDate(new LocalDate(2016, 6, 29));
		campaign.setStartTime(new LocalTime(4, 00));
		campaign.setEndDate(new LocalDate(2016, 7, 8));
		campaign.setEndTime(new LocalTime(5, 59));
		service.addCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void updateCampaign_WithCpm_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-cpm.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");
		campaign.setImpressions(1000l);
		campaign.setWeight("100");
		campaign.setPriorityLevel("12");
		campaign.setCompletion("S");
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach("O");
		campaign.setDailyImps(50l);
		campaign.setSmoothOrAsap("S");
		campaign.setImpressionsOverrun(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		campaign.setUserTimeZone("N");

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void updateCampaign_WithCpc_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-cpc.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");
		campaign.setClicks(1000l);
		campaign.setWeight("100");
		campaign.setPriorityLevel("12");
		campaign.setCompletion("S");
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach("O");
		campaign.setDailyClicks(50l);
		campaign.setSmoothOrAsap("S");
		campaign.setImpressionsOverrun(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		campaign.setUserTimeZone("N");

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void updateCampaign_WithTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-targeting.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");

		campaign.setExcludeTargets(false);
		List<Targeting> targeting = new ArrayList<Targeting>();

		ExcludableTargeting topLevelDomain = new ExcludableTargeting(TargetingCode.TOP_DOMAIN);
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		targeting.add(topLevelDomain);

		ExcludableTargeting bandwidthTargeting = new ExcludableTargeting(TargetingCode.BANDWIDTH);
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		targeting.add(bandwidthTargeting);

		ExcludableTargeting continentTargeting = new ExcludableTargeting(TargetingCode.CONTINENT);
		continentTargeting.setExclude(false);
		continentTargeting.setValues(Arrays.asList("AU", "EU"));
		targeting.add(continentTargeting);

		ExcludableTargeting countryTargeting = new ExcludableTargeting(TargetingCode.COUNTRY);
		countryTargeting.setExclude(true);
		countryTargeting.setValues(Arrays.asList("BE", "ZA"));
		targeting.add(countryTargeting);

		ExcludableTargeting stateTargeting = new ExcludableTargeting(TargetingCode.STATE);
		stateTargeting.setExclude(false);
		stateTargeting.setValues(Arrays.asList("US:NJ", "US:PA"));
		targeting.add(stateTargeting);

		ExcludableTargeting msaTargeting = new ExcludableTargeting(TargetingCode.MSA);
		msaTargeting.setExclude(false);
		msaTargeting.setValues(Arrays.asList("11220", "10980"));
		targeting.add(msaTargeting);

		ExcludableTargeting dmaTargeting = new ExcludableTargeting(TargetingCode.DMA);
		dmaTargeting.setExclude(false);
		dmaTargeting.setValues(Arrays.asList("803", "501", "650"));
		targeting.add(dmaTargeting);

		ExcludableTargeting osTargeting = new ExcludableTargeting(TargetingCode.OS);
		osTargeting.setExclude(false);
		osTargeting.setValues(Arrays.asList("winxp", "unix"));
		targeting.add(osTargeting);

		ExcludableTargeting browserTargeting = new ExcludableTargeting(TargetingCode.BROWSER);
		browserTargeting.setExclude(false);
		browserTargeting.setValues(Arrays.asList("opera", "firefox"));
		targeting.add(browserTargeting);

		ExcludableTargeting browserVersionTargeting = new ExcludableTargeting(TargetingCode.BROWSER_VERSIONS);
		browserVersionTargeting.setExclude(false);
		browserVersionTargeting.setValues(Arrays.asList("firefox19", "opera12"));
		targeting.add(browserVersionTargeting);

		Targeting zoneTargeting = new Targeting(TargetingCode.ZONE);
		zoneTargeting.setValues(Arrays.asList(new String[] { "1", "2" }));
		targeting.add(zoneTargeting);

		campaign.setTargeting(targeting);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void updateCampaign_WithTargetingHavingSingleValue_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-targeting-single-value.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");
		campaign.setImpressions(1000l);
		campaign.setWeight("0");
		campaign.setPriorityLevel("0");
		campaign.setCompletion("S");
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setImpressionsOverrun(0l);
		campaign.setStrictCompanions("N");
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		campaign.setUserTimeZone("N");

		campaign.setExcludeTargets(false);
		List<Targeting> targeting = new ArrayList<Targeting>();

		ExcludableTargeting topLevelDomain = new ExcludableTargeting(TargetingCode.TOP_DOMAIN);
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US"));
		targeting.add(topLevelDomain);

		ExcludableTargeting bandwidthTargeting = new ExcludableTargeting(TargetingCode.BANDWIDTH);
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN"));
		targeting.add(bandwidthTargeting);

		ExcludableTargeting continentTargeting = new ExcludableTargeting(TargetingCode.CONTINENT);
		continentTargeting.setExclude(false);
		continentTargeting.setValues(Arrays.asList("AU"));
		targeting.add(continentTargeting);

		ExcludableTargeting countryTargeting = new ExcludableTargeting(TargetingCode.COUNTRY);
		countryTargeting.setExclude(true);
		countryTargeting.setValues(Arrays.asList("BE"));
		targeting.add(countryTargeting);

		ExcludableTargeting stateTargeting = new ExcludableTargeting(TargetingCode.STATE);
		stateTargeting.setExclude(false);
		stateTargeting.setValues(Arrays.asList("BE:BRUSSELS"));
		targeting.add(stateTargeting);

		ExcludableTargeting msaTargeting = new ExcludableTargeting(TargetingCode.MSA);
		msaTargeting.setExclude(false);
		msaTargeting.setValues(Arrays.asList("11220"));
		targeting.add(msaTargeting);

		ExcludableTargeting dmaTargeting = new ExcludableTargeting(TargetingCode.DMA);
		dmaTargeting.setExclude(false);
		dmaTargeting.setValues(Arrays.asList("803"));
		targeting.add(dmaTargeting);

		ExcludableTargeting osTargeting = new ExcludableTargeting(TargetingCode.OS);
		osTargeting.setExclude(false);
		osTargeting.setValues(Arrays.asList("winxp"));
		targeting.add(osTargeting);

		ExcludableTargeting browserTargeting = new ExcludableTargeting(TargetingCode.BROWSER);
		browserTargeting.setExclude(false);
		browserTargeting.setValues(Arrays.asList("opera"));
		targeting.add(browserTargeting);

		ExcludableTargeting browserVersionTargeting = new ExcludableTargeting(TargetingCode.BROWSER_VERSIONS);
		browserVersionTargeting.setExclude(false);
		browserVersionTargeting.setValues(Arrays.asList("firefox19"));
		targeting.add(browserVersionTargeting);

		Targeting zoneTargeting = new Targeting(TargetingCode.ZONE);
		zoneTargeting.setValues(Arrays.asList(new String[] { "1" }));
		targeting.add(zoneTargeting);

		campaign.setTargeting(targeting);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithTargetingHavingEmptyValue_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-targeting-empty-value.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");

		List<Targeting> targeting = new ArrayList<Targeting>();

		ExcludableTargeting topLevelDomain = new ExcludableTargeting(TargetingCode.TOP_DOMAIN);
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(EMPTY_STRING_LIST);
		targeting.add(topLevelDomain);

		ExcludableTargeting bandwidthTargeting = new ExcludableTargeting(TargetingCode.BANDWIDTH);
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(bandwidthTargeting);

		ExcludableTargeting continentTargeting = new ExcludableTargeting(TargetingCode.CONTINENT);
		continentTargeting.setExclude(false);
		continentTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(continentTargeting);

		ExcludableTargeting countryTargeting = new ExcludableTargeting(TargetingCode.COUNTRY);
		countryTargeting.setExclude(true);
		countryTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(countryTargeting);

		ExcludableTargeting stateTargeting = new ExcludableTargeting(TargetingCode.STATE);
		stateTargeting.setExclude(false);
		stateTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(stateTargeting);

		ExcludableTargeting msaTargeting = new ExcludableTargeting(TargetingCode.MSA);
		msaTargeting.setExclude(false);
		msaTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(msaTargeting);

		ExcludableTargeting dmaTargeting = new ExcludableTargeting(TargetingCode.DMA);
		dmaTargeting.setExclude(false);
		dmaTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(dmaTargeting);

		ExcludableTargeting osTargeting = new ExcludableTargeting(TargetingCode.OS);
		osTargeting.setExclude(false);
		osTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(osTargeting);

		ExcludableTargeting browserTargeting = new ExcludableTargeting(TargetingCode.BROWSER);
		browserTargeting.setExclude(false);
		browserTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(browserTargeting);

		ExcludableTargeting browserVersionTargeting = new ExcludableTargeting(TargetingCode.BROWSER_VERSIONS);
		browserVersionTargeting.setExclude(false);
		browserVersionTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(browserVersionTargeting);

		Targeting zoneTargeting = new Targeting(TargetingCode.ZONE);
		zoneTargeting.setValues(EMPTY_STRING_LIST);
		targeting.add(zoneTargeting);

		campaign.setTargeting(targeting);
		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithHourOfDay_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-hourofday.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");
		campaign.setImpressions(1000l);
		campaign.setWeight("1000");
		campaign.setPriorityLevel("15");
		campaign.setCompletion("E");
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach("F");
		campaign.setDailyImps(999999999l);
		campaign.setSmoothOrAsap("A");
		campaign.setImpressionsOverrun(0l);
		campaign.setStrictCompanions("N");
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		campaign.setHourOfDay(Lists.newArrayList("18", "19", "20"));
		campaign.setUserTimeZone("N");

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithHourOfDayAndDateOfWeek_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-hod-dow.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setHourOfDay(Lists.newArrayList("18", "19", "20"));
		campaign.setDayOfWeek(Lists.newArrayList("2", "3", "4"));

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithHourOfDayAndDateOfWeekEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-hod-dow-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setHourOfDay(EMPTY_STRING_LIST);
		campaign.setDayOfWeek(EMPTY_STRING_LIST);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithRdbTargeting_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-rdb-targeting.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setExcludeTargets(false);

		RdbTargeting rdbTargeting = new RdbTargeting();
		rdbTargeting.setAgeExclude(true);
		rdbTargeting.setAgeFrom(13);
		rdbTargeting.setAgeTo(30);
		rdbTargeting.setGenderExclude(true);
		rdbTargeting.setGender("M");
		rdbTargeting.setIncomeExclude(true);
		rdbTargeting.setIncomeFrom(13l);
		rdbTargeting.setIncomeTo(30l);
		rdbTargeting.setSubscriberCodeExclude(true);
		rdbTargeting.setSubscriberCode("TEST");
		rdbTargeting.setPreferenceFlagsExclude(true);
		rdbTargeting.setPreferenceFlags("012345678911");
		campaign.setRdbTargeting(rdbTargeting);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithSegmentTargeting_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-segment-targeting.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");
		campaign.setImpressions(1000l);
		campaign.setWeight("0");
		campaign.setPriorityLevel("0");
		campaign.setCompletion("S");
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setImpressionsOverrun(0l);
		campaign.setStrictCompanions("N");
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		campaign.setUserTimeZone("N");

		campaign.setExcludeTargets(false);

		campaign.setExcludeTargets(false);
		List<Targeting> targeting = new ArrayList<Targeting>();

		ExcludableTargeting topLevelDomain = new ExcludableTargeting(TargetingCode.TOP_DOMAIN);
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		targeting.add(topLevelDomain);

		ExcludableTargeting bandwidthTargeting = new ExcludableTargeting(TargetingCode.BANDWIDTH);
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		targeting.add(bandwidthTargeting);

		campaign.setTargeting(targeting);

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentClusterMatch("L");
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithSegmentTargetingEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-segment-targeting-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setSegmentTargeting(segmentTargeting);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithOnlyState_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-secondpush-request-with-only-state.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setStatus("X");

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithOnlyCompanion_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-secondpush-request-with-only-companion.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setCompanionPositions(Arrays.asList(new String[] { "B/T" }));
		campaign.setStrictCompanions("Y");

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithOnlyHourOfDay_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-secondpush-request-with-only-hourofday.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setHourOfDay(Lists.newArrayList("18", "19", "20"));

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithStartAndEndTimesHasNonStandardMinuteValues_StartTimeHas0MinsAndEndTime59Mins()
			throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-secondpush-request-with-starttime-endtime.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setStartTime(new LocalTime(8, 30));
		campaign.setEndTime(new LocalTime(17, 0));

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithExcludeSiteAndPageIds_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-exclude-site-page-ids.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setExcludedSiteIds(Lists.newArrayList("mkarlov.com", "apiSite2"));
		campaign.setExcludedPageUrls(Lists.newArrayList("www.spon.de/sport/wm-spezial/center", "www.testfz.com", "www.mkarlov.com/sports"));

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithExcludeSiteAndPageIdsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-exclude-site-page-ids-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setExcludedSiteIds(EMPTY_STRING_LIST);
		campaign.setExcludedPageUrls(EMPTY_STRING_LIST);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithPageUrls_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-page-urls.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setPageUrls(Lists.newArrayList("www.spon.de/sport/wm-spezial/center", "www.testfz.com", "www.mkarlov.com/sports"));

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithPageUrlsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-page-urls-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setPageUrls(EMPTY_STRING_LIST);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithSectionIds_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-section-ids.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setSectionIds(Lists.newArrayList("383section", "AASec", "Adulte"));

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithSectionIdsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-section-ids-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setSectionIds(EMPTY_STRING_LIST);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithCampaignGroupIds_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-campaign-groups.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setCampaignGroupIds(Lists.newArrayList("0521_AGEN313394_Campaig_010313_12948_152", "055CASHME388747_Campaig_010313_12947_152"));

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithCampaignGroupIdsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-campaign-groups-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setCampaignGroupIds(EMPTY_STRING_LIST);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithExternalUserIds_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-user-ids.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_2_clt");
		campaign.setExternalUserIds(Lists.newArrayList("AAExt", "alaExt"));

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void updateCampaign_WithExternalUserIdsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-user-ids-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_2_clt");
		campaign.setExternalUserIds(EMPTY_STRING_LIST);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}
}
