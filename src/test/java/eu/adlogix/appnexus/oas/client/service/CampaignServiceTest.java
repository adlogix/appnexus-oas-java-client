package eu.adlogix.appnexus.oas.client.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.RdbTargeting;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.domain.Targeting;
import eu.adlogix.appnexus.oas.client.domain.Targeting.TargetingType;
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

		List<Targeting> targetingList = campaign.getCommonTargeting();
		for (Targeting targeting : targetingList) {
			if (targeting.getTargetingType().equals(TargetingType.TOP_DOMAIN)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "US", "COM", "EDU" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

			} else if (targeting.getTargetingType().equals(TargetingType.BANDWIDTH)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "LAN", "DSL/Cable" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

			} else if (targeting.getTargetingType().equals(TargetingType.CONTINENT)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "AS", "EU", "NA" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

			} else if (targeting.getTargetingType().equals(TargetingType.COUNTRY)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "US", "CA", "AF" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

			} else if (targeting.getTargetingType().equals(TargetingType.STATE)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "AL:BERAT", "AL:FIER", "AM:ARTASHAT" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

			} else if (targeting.getTargetingType().equals(TargetingType.MSA)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "0240", "0280", "6280" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

			} else if (targeting.getTargetingType().equals(TargetingType.DMA)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "507", "508" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

			} else if (targeting.getTargetingType().equals(TargetingType.OS)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "winxp", "unix" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

			} else if (targeting.getTargetingType().equals(TargetingType.BROWSER)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "msie", "netscape", "mozilla" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

			} else if (targeting.getTargetingType().equals(TargetingType.BROWSER_VERSIONS)) {
				assertEquals(targeting.getValues(), Arrays.asList(new String[] { "explorer6", "mozilla0", "mozilla1",
						"netscape7" }));
				assertEquals(targeting.getExculde().booleanValue(), false);

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
		assertEquals(segmentTargeting.getExculde().booleanValue(), false);
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
		campaign.setCreativeTargetid("SampleCreativeTarget");
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
		campaign.setCreativeTargetid("SampleCreativeTarget");
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
	public void addCampaign_WithCommonTargetingAndZones_Success() throws Exception {

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

		List<Targeting> commonTargeting = new ArrayList<Targeting>();

		Targeting topLevelDomain = new Targeting(TargetingType.TOP_DOMAIN);
		topLevelDomain.setExculde(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		commonTargeting.add(topLevelDomain);

		Targeting bandwidthTargeting = new Targeting(TargetingType.BANDWIDTH);
		bandwidthTargeting.setExculde(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		commonTargeting.add(bandwidthTargeting);

		Targeting continentTargeting = new Targeting(TargetingType.CONTINENT);
		continentTargeting.setExculde(false);
		continentTargeting.setValues(Arrays.asList("AU", "EU"));
		commonTargeting.add(continentTargeting);

		Targeting countryTargeting = new Targeting(TargetingType.COUNTRY);
		countryTargeting.setExculde(true);
		countryTargeting.setValues(Arrays.asList("BE", "ZA"));
		commonTargeting.add(countryTargeting);

		Targeting stateTargeting = new Targeting(TargetingType.STATE);
		stateTargeting.setExculde(false);
		stateTargeting.setValues(Arrays.asList("BE:BRUSSELS"));
		commonTargeting.add(stateTargeting);

		Targeting msaTargeting = new Targeting(TargetingType.MSA);
		msaTargeting.setExculde(false);
		msaTargeting.setValues(Arrays.asList("11220"));
		commonTargeting.add(msaTargeting);

		Targeting dmaTargeting = new Targeting(TargetingType.DMA);
		dmaTargeting.setExculde(false);
		dmaTargeting.setValues(Arrays.asList("803", "501", "650"));
		commonTargeting.add(dmaTargeting);

		Targeting osTargeting = new Targeting(TargetingType.OS);
		osTargeting.setExculde(false);
		osTargeting.setValues(Arrays.asList("winxp", "unix"));
		commonTargeting.add(osTargeting);

		Targeting browserTargeting = new Targeting(TargetingType.BROWSER);
		browserTargeting.setExculde(false);
		browserTargeting.setValues(Arrays.asList("opera", "firefox"));
		commonTargeting.add(browserTargeting);

		Targeting browserVersionTargeting = new Targeting(TargetingType.BROWSER_VERSIONS);
		browserVersionTargeting.setExculde(false);
		browserVersionTargeting.setValues(Arrays.asList("firefox19", "opera12"));
		commonTargeting.add(browserVersionTargeting);

		campaign.setCommonTargeting(commonTargeting);

		campaign.setZones(Arrays.asList(new String[] { "1", "2" }));
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
		segmentTargeting.setExculde(true);
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

		campaign.setCommonTargeting(new ArrayList<Targeting>());
		campaign.setZones(new ArrayList<String>());
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
	public void updateCampaign_WithStartDateAndEndDateWithoutTimes_Success() throws Exception {

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
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setPaymentMethod("B");
		campaign.setCompletion("S");
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
	public void updateCampaign_WithTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new CampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-targeting.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus("W");
		campaign.setImpressions(1000l);
		campaign.setWeight("100");
		campaign.setPriorityLevel("12");
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach("O");
		campaign.setDailyImps(50l);
		campaign.setSmoothOrAsap("S");
		campaign.setImpressionsOverrun(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		campaign.setUserTimeZone("N");

		campaign.setExcludeTargets(false);
		List<Targeting> commonTargeting = new ArrayList<Targeting>();

		Targeting topLevelDomain = new Targeting(TargetingType.TOP_DOMAIN);
		topLevelDomain.setExculde(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		commonTargeting.add(topLevelDomain);

		Targeting bandwidthTargeting = new Targeting(TargetingType.BANDWIDTH);
		bandwidthTargeting.setExculde(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		commonTargeting.add(bandwidthTargeting);

		Targeting continentTargeting = new Targeting(TargetingType.CONTINENT);
		continentTargeting.setExculde(false);
		continentTargeting.setValues(Arrays.asList("AU", "EU"));
		commonTargeting.add(continentTargeting);

		Targeting countryTargeting = new Targeting(TargetingType.COUNTRY);
		countryTargeting.setExculde(true);
		countryTargeting.setValues(Arrays.asList("BE", "ZA"));
		commonTargeting.add(countryTargeting);

		Targeting stateTargeting = new Targeting(TargetingType.STATE);
		stateTargeting.setExculde(false);
		stateTargeting.setValues(Arrays.asList("BE:BRUSSELS"));
		commonTargeting.add(stateTargeting);

		Targeting msaTargeting = new Targeting(TargetingType.MSA);
		msaTargeting.setExculde(false);
		msaTargeting.setValues(Arrays.asList("11220"));
		commonTargeting.add(msaTargeting);

		Targeting dmaTargeting = new Targeting(TargetingType.DMA);
		dmaTargeting.setExculde(false);
		dmaTargeting.setValues(Arrays.asList("803", "501", "650"));
		commonTargeting.add(dmaTargeting);

		Targeting osTargeting = new Targeting(TargetingType.OS);
		osTargeting.setExculde(false);
		osTargeting.setValues(Arrays.asList("winxp", "unix"));
		commonTargeting.add(osTargeting);

		Targeting browserTargeting = new Targeting(TargetingType.BROWSER);
		browserTargeting.setExculde(false);
		browserTargeting.setValues(Arrays.asList("opera", "firefox"));
		commonTargeting.add(browserTargeting);

		Targeting browserVersionTargeting = new Targeting(TargetingType.BROWSER_VERSIONS);
		browserVersionTargeting.setExculde(false);
		browserVersionTargeting.setValues(Arrays.asList("firefox19", "opera12"));
		commonTargeting.add(browserVersionTargeting);

		campaign.setCommonTargeting(commonTargeting);

		campaign.setZones(Lists.newArrayList("1", "2"));

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
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach("O");
		campaign.setSmoothOrAsap("S");
		campaign.setImpressionsOverrun(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(0l);
		campaign.setUserTimeZone("N");

		campaign.setExcludeTargets(false);
		List<Targeting> commonTargeting = new ArrayList<Targeting>();

		Targeting topLevelDomain = new Targeting(TargetingType.TOP_DOMAIN);
		topLevelDomain.setExculde(false);
		topLevelDomain.setValues(Arrays.asList("US"));
		commonTargeting.add(topLevelDomain);

		Targeting bandwidthTargeting = new Targeting(TargetingType.BANDWIDTH);
		bandwidthTargeting.setExculde(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN"));
		commonTargeting.add(bandwidthTargeting);

		Targeting continentTargeting = new Targeting(TargetingType.CONTINENT);
		continentTargeting.setExculde(false);
		continentTargeting.setValues(Arrays.asList("AU"));
		commonTargeting.add(continentTargeting);

		Targeting countryTargeting = new Targeting(TargetingType.COUNTRY);
		countryTargeting.setExculde(true);
		countryTargeting.setValues(Arrays.asList("BE"));
		commonTargeting.add(countryTargeting);

		Targeting stateTargeting = new Targeting(TargetingType.STATE);
		stateTargeting.setExculde(false);
		stateTargeting.setValues(Arrays.asList("BE:BRUSSELS"));
		commonTargeting.add(stateTargeting);

		Targeting msaTargeting = new Targeting(TargetingType.MSA);
		msaTargeting.setExculde(false);
		msaTargeting.setValues(Arrays.asList("11220"));
		commonTargeting.add(msaTargeting);

		Targeting dmaTargeting = new Targeting(TargetingType.DMA);
		dmaTargeting.setExculde(false);
		dmaTargeting.setValues(Arrays.asList("803"));
		commonTargeting.add(dmaTargeting);

		Targeting osTargeting = new Targeting(TargetingType.OS);
		osTargeting.setExculde(false);
		osTargeting.setValues(Arrays.asList("winxp"));
		commonTargeting.add(osTargeting);

		Targeting browserTargeting = new Targeting(TargetingType.BROWSER);
		browserTargeting.setExculde(false);
		browserTargeting.setValues(Arrays.asList("opera"));
		commonTargeting.add(browserTargeting);

		Targeting browserVersionTargeting = new Targeting(TargetingType.BROWSER_VERSIONS);
		browserVersionTargeting.setExculde(false);
		browserVersionTargeting.setValues(Arrays.asList("firefox19"));
		commonTargeting.add(browserVersionTargeting);

		campaign.setCommonTargeting(commonTargeting);

		campaign.setZones(Lists.newArrayList("1"));

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

		List<Targeting> commonTargeting = new ArrayList<Targeting>();

		Targeting topLevelDomain = new Targeting(TargetingType.TOP_DOMAIN);
		topLevelDomain.setExculde(false);
		topLevelDomain.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(topLevelDomain);

		Targeting bandwidthTargeting = new Targeting(TargetingType.BANDWIDTH);
		bandwidthTargeting.setExculde(true);
		bandwidthTargeting.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(bandwidthTargeting);

		Targeting continentTargeting = new Targeting(TargetingType.CONTINENT);
		continentTargeting.setExculde(false);
		continentTargeting.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(continentTargeting);

		Targeting countryTargeting = new Targeting(TargetingType.COUNTRY);
		countryTargeting.setExculde(true);
		countryTargeting.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(countryTargeting);

		Targeting stateTargeting = new Targeting(TargetingType.STATE);
		stateTargeting.setExculde(false);
		stateTargeting.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(stateTargeting);

		Targeting msaTargeting = new Targeting(TargetingType.MSA);
		msaTargeting.setExculde(false);
		msaTargeting.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(msaTargeting);

		Targeting dmaTargeting = new Targeting(TargetingType.DMA);
		dmaTargeting.setExculde(false);
		dmaTargeting.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(dmaTargeting);

		Targeting osTargeting = new Targeting(TargetingType.OS);
		osTargeting.setExculde(false);
		osTargeting.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(osTargeting);

		Targeting browserTargeting = new Targeting(TargetingType.BROWSER);
		browserTargeting.setExculde(false);
		browserTargeting.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(browserTargeting);

		Targeting browserVersionTargeting = new Targeting(TargetingType.BROWSER_VERSIONS);
		browserVersionTargeting.setExculde(false);
		browserVersionTargeting.setValues(EMPTY_STRING_LIST);
		commonTargeting.add(browserVersionTargeting);

		campaign.setCommonTargeting(commonTargeting);

		campaign.setZones(EMPTY_STRING_LIST);

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
		campaign.setStatus("W");
		campaign.setImpressions(1000l);
		campaign.setWeight("1000");
		campaign.setPriorityLevel("15");
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
		campaign.setDayOfWeek(Lists.newArrayList("2", "3", "4"));
		campaign.setUserTimeZone("N");

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
		campaign.setUserTimeZone("N");

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
		campaign.setUserTimeZone("N");

		campaign.setExcludeTargets(false);

		campaign.setExcludeTargets(false);
		List<Targeting> commonTargeting = new ArrayList<Targeting>();

		Targeting topLevelDomain = new Targeting(TargetingType.TOP_DOMAIN);
		topLevelDomain.setExculde(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		commonTargeting.add(topLevelDomain);

		Targeting bandwidthTargeting = new Targeting(TargetingType.BANDWIDTH);
		bandwidthTargeting.setExculde(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		commonTargeting.add(bandwidthTargeting);

		campaign.setCommonTargeting(commonTargeting);

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentClusterMatch("L");
		segmentTargeting.setExculde(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		service.updateCampaign(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}
}
