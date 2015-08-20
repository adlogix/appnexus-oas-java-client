package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.string.StringTestUtils.normalizeNewLinesToCurPlatform;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.CampaignExcludableTargetValues;
import eu.adlogix.appnexus.oas.client.domain.CampaignTargetValues;
import eu.adlogix.appnexus.oas.client.domain.MobileTargetings;
import eu.adlogix.appnexus.oas.client.domain.RdbTargeting;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.domain.enums.BillTo;
import eu.adlogix.appnexus.oas.client.domain.enums.CampaignStatus;
import eu.adlogix.appnexus.oas.client.domain.enums.CampaignType;
import eu.adlogix.appnexus.oas.client.domain.enums.Completion;
import eu.adlogix.appnexus.oas.client.domain.enums.DayOfWeek;
import eu.adlogix.appnexus.oas.client.domain.enums.FrequencyScope;
import eu.adlogix.appnexus.oas.client.domain.enums.Gender;
import eu.adlogix.appnexus.oas.client.domain.enums.HourOfDay;
import eu.adlogix.appnexus.oas.client.domain.enums.PaymentMethod;
import eu.adlogix.appnexus.oas.client.domain.enums.Reach;
import eu.adlogix.appnexus.oas.client.domain.enums.SegmentType;
import eu.adlogix.appnexus.oas.client.domain.enums.SmoothAsap;
import eu.adlogix.appnexus.oas.client.domain.enums.TargetingCode;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.utils.file.TestFileUtils;

public class DefaultCampaignServiceTest {

	private static final List<String> EMPTY_STRING_LIST = Lists.newArrayList();

	@Test
	public void getById_ExistingCampaign_ReturnCampaign() throws Exception {

		final String campaignId = "0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278";

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-campaign-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-campaign-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = service.getById(campaignId);

		assertEquals(campaign.getId(), "0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		assertEquals(campaign.getType(), CampaignType.REGULAR);
		assertEquals(campaign.getInsertionOrderId(), "IOID");
		assertEquals(campaign.getAdvertiserId(), "GROUPEZENITHOPTIMEDIA_ZENI0D");
		assertEquals(campaign.getName(), "[3] 1017981imps entree site du 27/02 au 31/03");
		assertEquals(campaign.getAgencyId(), "unknown_agency");
		assertEquals(campaign.getDescription(), "[3] 1017981imps entree site du 27/02 au 31/03");
		assertEquals(campaign.getCampaignManager(), "Campaign_mangaer_01");
		assertEquals(campaign.getProductId(), "default-product");
		assertEquals(campaign.getStatus(), CampaignStatus.LIVE);

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
		assertEquals(campaign.getWeight().longValue(), 1000l);
		assertEquals(campaign.getPriorityLevel().longValue(), 5l);
		assertEquals(campaign.getCompletion(), Completion.SOONEST);

		assertEquals(campaign.getStartDate(), new LocalDate(2012, 2, 27));
		assertEquals(campaign.getStartTime(), new LocalTime(04, 00));
		assertEquals(campaign.getEndDate(), new LocalDate(2012, 3, 30));
		assertEquals(campaign.getEndTime(), new LocalTime(20, 59));

		assertEquals(campaign.getReach(), Reach.DYNAMIC);
		assertEquals(campaign.getDailyImps(), new Long(54642l));
		assertEquals(campaign.getDailyClicks(), new Long(0));
		assertEquals(campaign.getDailyUniques(), new Long(0));
		assertEquals(campaign.getSmoothOrAsap(), SmoothAsap.SMOOTH);
		assertEquals(campaign.getImpressionsOverrun(), new Long(50));

		assertEquals(campaign.getCompanionPositions(), Arrays.asList(new String[] { "M/M2/T" }));
		assertEquals(campaign.getStrictCompanions().booleanValue(), true);
		assertEquals(campaign.getPrimaryImpsPerVisitor(), new Long(4));
		assertEquals(campaign.getPrimaryClicksPerVisitor(), new Long(0));
		assertEquals(campaign.getPrimaryFrequencyScope(), FrequencyScope.SESSION);
		assertEquals(campaign.getSecondaryImpsPerVisitor(), new Long(6));
		assertEquals(campaign.getSecondaryFrequencyScope(), FrequencyScope.ZERO);

		assertEquals(campaign.getHourOfDay(), Arrays.asList(new HourOfDay[] { HourOfDay.EIGHTEEN, HourOfDay.NINETEEN,
				HourOfDay.TWENTY }));
		assertEquals(campaign.getDayOfWeek(), Arrays.asList(new DayOfWeek[] { DayOfWeek.SUNDAY, DayOfWeek.TUESDAY,
				DayOfWeek.THURSDAY, DayOfWeek.SATURDAY }));
		assertEquals(campaign.getUserTimeZone().booleanValue(), false);

		assertEquals(campaign.getSectionIds(), Arrays.asList(new String[] { "ALL_EXPRESS_STYLES" }));
		assertEquals(campaign.getPageUrls(), Arrays.asList(new String[] { "express_styles/CONCOURS",
				"express_styles/SHOPPING_HP", "express_styles/VIP_RG", "express_styles/PHOTO_RG_DIAPO",
				"express_styles/NOUVELLEGENERATION_RG", "express_styles/RSS" }));

		assertEquals(campaign.getExcludeTargets().booleanValue(), false);

		CampaignExcludableTargetValues targeting = campaign.getTopDomainTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "US", "COM", "EDU" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getBandwidthTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "LAN", "DSL/Cable" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getContinentTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "AS", "EU", "NA" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getCountryTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "US", "CA", "AF" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getStateTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "AL:BERAT", "AL:FIER", "AM:ARTASHAT" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getMsaTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "0240", "0280", "6280" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getDmaTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "507", "508" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getOsTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "winxp", "unix" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getBrowserTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "msie", "netscape", "mozilla" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getBrowserVTargeting();
		assertEquals(targeting.getValues(), Arrays.asList(new String[] { "explorer6", "mozilla0", "mozilla1",
				"netscape7" }));
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		CampaignTargetValues zoneCampaignTargeting = campaign.getZoneTargeting();
		assertEquals(zoneCampaignTargeting.getValues(), EMPTY_STRING_LIST);

		MobileTargetings mobileCampaignTargetingGroup = campaign.getMobileTargeting();
		assertEquals(mobileCampaignTargetingGroup.getExcludeMobileDevice(), null);
		Map<TargetingCode, CampaignTargetValues> mobileCampaignTargetings = mobileCampaignTargetingGroup.getTargetings();
		assertEquals(mobileCampaignTargetings.size(), 1);
		assertEquals(mobileCampaignTargetings.get(TargetingCode.DEVICE_GROUP).getValues(), EMPTY_STRING_LIST);

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
		assertEquals(segmentTargeting.getSegmentType(), SegmentType.ANY);
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
		assertEquals(campaign.getPaymentMethod(), PaymentMethod.CASH);
		assertEquals(campaign.getIsYieldManaged().booleanValue(), false);
		assertEquals(campaign.getBillTo(), BillTo.AGENCY);
		assertEquals(campaign.getCurrency(), "EUR");

	}

	@Test
	public void getById_ExistingCltCampaignWithMobileZoneTargeting_ReturnCampaign() throws Exception {

		final String campaignId = "test_campaign_gunith_2_clt";

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-read-clt-campaign-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("read-clt-campaign-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = service.getById(campaignId);

		assertEquals(campaign.getId(), campaignId);
		assertEquals(campaign.getType(), CampaignType.CLT);
		assertEquals(campaign.getInsertionOrderId(), StringUtils.EMPTY);
		assertEquals(campaign.getAdvertiserId(), "test_advertiser");
		assertEquals(campaign.getName(), "test");
		assertEquals(campaign.getAgencyId(), "unknown_agency");
		assertEquals(campaign.getDescription(), StringUtils.EMPTY);
		assertEquals(campaign.getCampaignManager(), StringUtils.EMPTY);
		assertEquals(campaign.getProductId(), "default-product");
		assertEquals(campaign.getStatus(), CampaignStatus.WORK_IN_PROGRESS);

		assertEquals(campaign.getCampaignGroupIds(), EMPTY_STRING_LIST);

		assertEquals(campaign.getCompetitiveCategroryIds(), EMPTY_STRING_LIST);

		assertEquals(campaign.getExternalUserIds(), EMPTY_STRING_LIST);

		assertEquals(campaign.getInternalQuickReport(), "to-date");
		assertEquals(campaign.getExternalQuickReport(), "short");

		assertEquals(campaign.getImpressions().longValue(), 0l);
		assertEquals(campaign.getClicks().longValue(), 0l);
		assertEquals(campaign.getUniques().longValue(), 0l);
		assertEquals(campaign.getWeight().longValue(), 0l);
		assertEquals(campaign.getPriorityLevel().longValue(), 1l);
		assertEquals(campaign.getCompletion(), Completion.SOONEST);

		assertEquals(campaign.getStartDate(), null);
		assertEquals(campaign.getStartTime(), null);
		assertEquals(campaign.getEndDate(), null);
		assertEquals(campaign.getEndTime(), null);

		assertEquals(campaign.getReach(), Reach.OPEN);
		assertEquals(campaign.getDailyImps(), new Long(0l));
		assertEquals(campaign.getDailyClicks(), new Long(0));
		assertEquals(campaign.getDailyUniques(), new Long(0));
		assertEquals(campaign.getSmoothOrAsap(), SmoothAsap.SMOOTH);
		assertEquals(campaign.getImpressionsOverrun(), new Long(0));

		assertEquals(campaign.getCompanionPositions(), EMPTY_STRING_LIST);
		assertEquals(campaign.getStrictCompanions(), null);
		assertEquals(campaign.getPrimaryImpsPerVisitor(), new Long(0));
		assertEquals(campaign.getPrimaryClicksPerVisitor(), new Long(0));
		assertEquals(campaign.getPrimaryFrequencyScope(), FrequencyScope.ZERO);
		assertEquals(campaign.getSecondaryImpsPerVisitor(), new Long(0));
		assertEquals(campaign.getSecondaryFrequencyScope(), FrequencyScope.ZERO);

		assertEquals(campaign.getHourOfDay(), EMPTY_STRING_LIST);
		assertEquals(campaign.getDayOfWeek(), EMPTY_STRING_LIST);
		assertEquals(campaign.getUserTimeZone().booleanValue(), false);

		assertEquals(campaign.getSectionIds(), EMPTY_STRING_LIST);
		assertEquals(campaign.getPageUrls(), EMPTY_STRING_LIST);

		assertEquals(campaign.getExcludeTargets().booleanValue(), false);

		CampaignExcludableTargetValues targeting = campaign.getTopDomainTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getBandwidthTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getContinentTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getCountryTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getStateTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getMsaTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getDmaTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getOsTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getBrowserTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		targeting = campaign.getBrowserVTargeting();
		assertEquals(targeting.getValues(), EMPTY_STRING_LIST);
		assertEquals(((CampaignExcludableTargetValues) targeting).getExclude().booleanValue(), false);

		CampaignTargetValues zoneCampaignTargeting = campaign.getZoneTargeting();
		assertEquals(zoneCampaignTargeting.getValues(), Lists.newArrayList("1", "2"));

		MobileTargetings mobileCampaignTargetingGroup = campaign.getMobileTargeting();
		assertEquals(mobileCampaignTargetingGroup.getExcludeMobileDevice().booleanValue(), false);
		Map<TargetingCode, CampaignTargetValues> mobileCampaignTargetings = mobileCampaignTargetingGroup.getTargetings();
		assertEquals(mobileCampaignTargetings.size(), 1);
		assertEquals(mobileCampaignTargetings.get(TargetingCode.DEVICE_GROUP).getValues(), Lists.newArrayList("427", "429"));

		RdbTargeting rdbTargeting = campaign.getRdbTargeting();
		assertEquals(rdbTargeting.getAgeExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getAgeFrom(), null);
		assertEquals(rdbTargeting.getAgeTo(), null);
		assertEquals(rdbTargeting.getGenderExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getGender(), Gender.E);
		assertEquals(rdbTargeting.getIncomeExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getIncomeFrom(), null);
		assertEquals(rdbTargeting.getIncomeTo(), null);
		assertEquals(rdbTargeting.getSubscriberCodeExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getSubscriberCode(), "");
		assertEquals(rdbTargeting.getPreferenceFlagsExclude().booleanValue(), false);
		assertEquals(rdbTargeting.getPreferenceFlags(), "");

		SegmentTargeting segmentTargeting = campaign.getSegmentTargeting();
		assertEquals(segmentTargeting.getSegmentType(), null);
		assertEquals(segmentTargeting.getExclude().booleanValue(), false);
		assertEquals(segmentTargeting.getValues(), EMPTY_STRING_LIST);

		assertEquals(campaign.getExcludedSiteIds(), EMPTY_STRING_LIST);
		assertEquals(campaign.getExcludedPageUrls(), EMPTY_STRING_LIST);

		assertEquals(campaign.getCpm(), new Double(0.0));
		assertEquals(campaign.getCpc(), new Double(0.0));
		assertEquals(campaign.getCpa(), new Double(0.0));
		assertEquals(campaign.getFlatRate(), new Double(0.0));
		assertEquals(campaign.getTax(), new Double(0.0));
		assertEquals(campaign.getAgencyCommission(), new Double(0.0));
		assertEquals(campaign.getPaymentMethod(), PaymentMethod.CASH);
		assertEquals(campaign.getIsYieldManaged().booleanValue(), false);
		assertEquals(campaign.getBillTo(), BillTo.AGENCY);
		assertEquals(campaign.getCurrency(), "USD");

	}

	@Test
	public void add_DefaultTypeWithMandatoryParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-mandatory-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.BARTER);
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_DefaultTypeWithAdditionalParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-default-type-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("Test_Campaign");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(12l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setDescription("Added from API");
		campaign.setCompletion(Completion.SOONEST);
		campaign.setStartDate(new LocalDate(2016, 1, 1));
		campaign.setEndDate(new LocalDate(2016, 1, 31));
		campaign.setStartTime(new LocalTime(0, 0));
		campaign.setEndTime(new LocalTime(0, 59));
		campaign.setPaymentMethod(PaymentMethod.BARTER);
		campaign.setCampaignGroupIds(Arrays.asList(new String[] { "campaign_group_01" }));
		campaign.setImpressions(250000l);
		campaign.setWeight(100l);
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(1l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.SESSION);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setUserTimeZone(false);
		campaign.setPageUrls(Arrays.asList(new String[] { "test.com/home" }));
		campaign.setCpm(4000.0);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setCurrency("EUR");
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test(expectedExceptions = { OasServerSideException.class })
	public final void add_IdAlreadyExists_ThrowException() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-mandatory-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-id-already-exists-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.BARTER);
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void add_CltTypeWithMandatoryParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-clt-campaign-mandatory-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setType(CampaignType.CLT);
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setCreativeTargetId("SampleCreativeTarget");
		campaign.setProductId("default-product");
		campaign.setPaymentMethod(PaymentMethod.BARTER);
		campaign.setPriorityLevel(0l);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		service.add(campaign);
	}

	@Test
	public void add_CltTypeWithAdditionalParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-clt-type-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setType(CampaignType.CLT);
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setCreativeTargetId("SampleCreativeTarget");
		campaign.setProductId("default-product");
		campaign.setDescription("Added from API");
		campaign.setCampaignGroupIds(Arrays.asList(new String[] { "campaign_group_01" }));
		campaign.setPaymentMethod(PaymentMethod.BARTER);
		campaign.setImpressions(250000l);
		campaign.setWeight(100l);
		campaign.setPriorityLevel(0l);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setStartDate(new LocalDate(2016, 1, 1));
		campaign.setEndDate(new LocalDate(2016, 1, 31));
		campaign.setStartTime(new LocalTime(0, 0));
		campaign.setEndTime(new LocalTime(0, 59));
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(1l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.SESSION);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setUserTimeZone(false);
		campaign.setPageUrls(Arrays.asList(new String[] { "test.com/home" }));
		campaign.setCpm(4000.0);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setCurrency("EUR");
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void add_WithCompanionPositionsAndStrictCompanions_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-companionparameters-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.BARTER);
		campaign.setCompanionPositions(Arrays.asList(new String[] { "B/T" }));
		campaign.setStrictCompanions(false);
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCompanionPositions_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-companion-positions-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.BARTER);
		campaign.setCompanionPositions(Arrays.asList(new String[] { "B/T" }));
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCpmParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-cpm-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setImpressions(250000l);
		campaign.setDailyImps(50l);
		campaign.setImpressionsOverrun(10l);
		campaign.setPrimaryImpsPerVisitor(10l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.SESSION);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setSecondaryImpsPerVisitor(10l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.HOURLY);
		campaign.setCpm(4000.0);
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCpcParameters_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-cpc-params-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setClicks(250000l);
		campaign.setDailyClicks(50l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryClicksPerVisitor(10l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.SESSION);
		campaign.setCpc(4.0);
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-targeting-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);

		CampaignExcludableTargetValues topLevelDomain = new CampaignExcludableTargetValues();
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		campaign.setTopDomainTargeting(topLevelDomain);

		CampaignExcludableTargetValues bandwidthTargeting = new CampaignExcludableTargetValues();
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		campaign.setBandwidthTargeting(bandwidthTargeting);

		CampaignExcludableTargetValues continentTargeting = new CampaignExcludableTargetValues();
		continentTargeting.setExclude(false);
		continentTargeting.setValues(Arrays.asList("AU", "EU"));
		campaign.setContinentTargeting(continentTargeting);

		CampaignExcludableTargetValues countryTargeting = new CampaignExcludableTargetValues();
		countryTargeting.setExclude(true);
		countryTargeting.setValues(Arrays.asList("BE", "ZA"));
		campaign.setCountryTargeting(countryTargeting);

		CampaignExcludableTargetValues stateTargeting = new CampaignExcludableTargetValues();
		stateTargeting.setExclude(false);
		stateTargeting.setValues(Arrays.asList("BE:BRUSSELS"));
		campaign.setStateTargeting(stateTargeting);

		CampaignExcludableTargetValues msaTargeting = new CampaignExcludableTargetValues();
		msaTargeting.setExclude(false);
		msaTargeting.setValues(Arrays.asList("11220"));
		campaign.setMsaTargeting(msaTargeting);

		CampaignExcludableTargetValues dmaTargeting = new CampaignExcludableTargetValues();
		dmaTargeting.setExclude(false);
		dmaTargeting.setValues(Arrays.asList("803", "501", "650"));
		campaign.setDmaTargeting(dmaTargeting);

		CampaignExcludableTargetValues osTargeting = new CampaignExcludableTargetValues();
		osTargeting.setExclude(false);
		osTargeting.setValues(Arrays.asList("winxp", "unix"));
		campaign.setOsTargeting(osTargeting);

		CampaignExcludableTargetValues browserTargeting = new CampaignExcludableTargetValues();
		browserTargeting.setExclude(false);
		browserTargeting.setValues(Arrays.asList("opera", "firefox"));
		campaign.setBrowserTargeting(browserTargeting);

		CampaignExcludableTargetValues browserVersionTargeting = new CampaignExcludableTargetValues();
		browserVersionTargeting.setExclude(false);
		browserVersionTargeting.setValues(Arrays.asList("firefox19", "opera12"));
		campaign.setBrowserVTargeting(browserVersionTargeting);

		CampaignTargetValues zoneTargeting = new CampaignTargetValues();
		zoneTargeting.setValues(Arrays.asList(new String[] { "1", "2" }));
		campaign.setZoneTargeting(zoneTargeting);

		MobileTargetings mobileTargeting = new MobileTargetings();
		mobileTargeting.setExcludeMobileDevice(false);

		CampaignTargetValues deviceGroupTargeting = new CampaignTargetValues();
		deviceGroupTargeting.setValues(Arrays.asList(new String[] { "427", "429" }));
		mobileTargeting.setDeviceGroupTargeting(deviceGroupTargeting);
		campaign.setMobileTargeting(mobileTargeting);

		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithSegmentTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-segmenttargeting-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentType(SegmentType.ALL);
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithRdbTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-rdbtargeting-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);

		RdbTargeting rdbTargeting = new RdbTargeting();
		rdbTargeting.setAgeExclude(true);
		rdbTargeting.setAgeFrom(13);
		rdbTargeting.setAgeTo(30);
		rdbTargeting.setGenderExclude(true);
		rdbTargeting.setGender(Gender.M);
		rdbTargeting.setIncomeExclude(true);
		rdbTargeting.setIncomeFrom(13l);
		rdbTargeting.setIncomeTo(30l);
		rdbTargeting.setSubscriberCodeExclude(true);
		rdbTargeting.setSubscriberCode("TEST");
		rdbTargeting.setPreferenceFlagsExclude(true);
		rdbTargeting.setPreferenceFlags("012345678911");
		campaign.setRdbTargeting(rdbTargeting);
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithEmptyTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-empty-targeting-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);

		CampaignTargetValues zoneTargeting = new CampaignTargetValues();
		zoneTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setZoneTargeting(zoneTargeting);
		campaign.setSegmentTargeting(new SegmentTargeting());
		campaign.setRdbTargeting(new RdbTargeting());
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithHourOfDayAndDayOfWeek_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-hod-dow-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setHourOfDay(Arrays.asList(HourOfDay.EIGHTEEN, HourOfDay.NINETEEN, HourOfDay.TWENTY));
		campaign.setDayOfWeek(Arrays.asList(new DayOfWeek[] { DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
				DayOfWeek.THURSDAY }));
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCpmParamsFixedReachAndZeroFrequencyImps_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpm-fixedreach-no-frequencyimps-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setReach(Reach.FIXED);
		campaign.setImpressions(250000l);
		campaign.setDailyImps(50l);
		campaign.setCpm(4000.0);
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.ZERO);
		service.add(campaign);

		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCpcParamsFixedReachAndZeroFrequencyClicks_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpc-fixedreach-no-frequencyclicks-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setReach(Reach.FIXED);
		campaign.setImpressions(1l);
		campaign.setDailyImps(1l);
		campaign.setClicks(250000l);
		campaign.setDailyClicks(50l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setCpc(4.0);
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCpmParamsDynamicReachAndZeroFrequencyImps_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpm-dynamicreach-no-frequencyimps-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setReach(Reach.DYNAMIC);
		campaign.setImpressions(250000l);
		campaign.setDailyImps(50l);
		campaign.setCpm(4000.0);
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.ZERO);
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCpcParamsDynamicReachAndZeroFrequencyClicks_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpc-dynamicreach-no-frequencyclicks-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setReach(Reach.DYNAMIC);
		campaign.setImpressions(1l);
		campaign.setDailyImps(1l);
		campaign.setClicks(250000l);
		campaign.setDailyClicks(50l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setCpc(4.0);

		service.add(campaign);

		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCpmParamsOpenReachAndZeroFrequencyImps_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpm-openreach-no-frequencyimps-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setReach(Reach.OPEN);
		campaign.setImpressions(250000l);
		campaign.setCpm(4000.0);
		campaign.setImpressionsOverrun(0l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.ZERO);
		service.add(campaign);

		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCpcParamsOpenReachAndZeroFrequencyClicks_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-cpc-openreach-no-frequencyclicks-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.CASH);
		campaign.setReach(Reach.OPEN);
		campaign.setClicks(250000l);
		campaign.setPrimaryImpsPerVisitor(0l);
		campaign.setPrimaryClicksPerVisitor(0l);
		campaign.setPrimaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setCpc(4.0);
		service.add(campaign);

		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithCampaignManager_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-campaignmanager-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.BARTER);
		campaign.setCampaignManager("test_campaign_manager");
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void add_WithStartDateTimeAndEndDateTime_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-add-campaign-with-startdate-enddate-request.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_01");
		campaign.setAdvertiserId("test_advertiser");
		campaign.setAgencyId("unknown_agency");
		campaign.setName("test");
		campaign.setProductId("default-product");
		campaign.setPriorityLevel(1l);
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setPaymentMethod(PaymentMethod.BARTER);
		campaign.setStartDate(new LocalDate(2016, 6, 29));
		campaign.setStartTime(new LocalTime(4, 00));
		campaign.setEndDate(new LocalDate(2016, 7, 8));
		campaign.setEndTime(new LocalTime(5, 59));
		service.add(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void update_WithCpm_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-cpm.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);
		campaign.setImpressions(1000l);
		campaign.setWeight(100l);
		campaign.setPriorityLevel(12l);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach(Reach.OPEN);
		campaign.setDailyImps(50l);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setImpressionsOverrun(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setUserTimeZone(false);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void update_WithCpc_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-cpc.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);
		campaign.setClicks(1000l);
		campaign.setWeight(100l);
		campaign.setPriorityLevel(12l);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach(Reach.OPEN);
		campaign.setDailyClicks(50l);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setImpressionsOverrun(0l);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setUserTimeZone(false);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void update_WithTargeting_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-targeting.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");

		campaign.setExcludeTargets(false);
		
		CampaignExcludableTargetValues topLevelDomain = new CampaignExcludableTargetValues();
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US", "COM", "EDU"));
		campaign.setTopDomainTargeting(topLevelDomain);

		CampaignExcludableTargetValues bandwidthTargeting = new CampaignExcludableTargetValues();
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN", "DSL/Cable"));
		campaign.setBandwidthTargeting(bandwidthTargeting);

		CampaignExcludableTargetValues continentTargeting = new CampaignExcludableTargetValues();
		continentTargeting.setExclude(false);
		continentTargeting.setValues(Arrays.asList("AU", "EU"));
		campaign.setContinentTargeting(continentTargeting);

		CampaignExcludableTargetValues countryTargeting = new CampaignExcludableTargetValues();
		countryTargeting.setExclude(true);
		countryTargeting.setValues(Arrays.asList("BE", "ZA"));
		campaign.setCountryTargeting(countryTargeting);

		CampaignExcludableTargetValues stateTargeting = new CampaignExcludableTargetValues();
		stateTargeting.setExclude(false);
		stateTargeting.setValues(Arrays.asList("US:NJ", "US:PA"));
		campaign.setStateTargeting(stateTargeting);

		CampaignExcludableTargetValues msaTargeting = new CampaignExcludableTargetValues();
		msaTargeting.setExclude(false);
		msaTargeting.setValues(Arrays.asList("11220", "10980"));
		campaign.setMsaTargeting(msaTargeting);

		CampaignExcludableTargetValues dmaTargeting = new CampaignExcludableTargetValues();
		dmaTargeting.setExclude(false);
		dmaTargeting.setValues(Arrays.asList("803", "501", "650"));
		campaign.setDmaTargeting(dmaTargeting);

		CampaignExcludableTargetValues osTargeting = new CampaignExcludableTargetValues();
		osTargeting.setExclude(false);
		osTargeting.setValues(Arrays.asList("winxp", "unix"));
		campaign.setOsTargeting(osTargeting);

		CampaignExcludableTargetValues browserTargeting = new CampaignExcludableTargetValues();
		browserTargeting.setExclude(false);
		browserTargeting.setValues(Arrays.asList("opera", "firefox"));
		campaign.setBrowserTargeting(browserTargeting);

		CampaignExcludableTargetValues browserVersionTargeting = new CampaignExcludableTargetValues();
		browserVersionTargeting.setExclude(false);
		browserVersionTargeting.setValues(Arrays.asList("firefox19", "opera12"));
		campaign.setBrowserVTargeting(browserVersionTargeting);

		MobileTargetings mobileTargeting = new MobileTargetings();
		mobileTargeting.setExcludeMobileDevice(false);

		CampaignTargetValues deviceGroupTargeting = new CampaignTargetValues();
		deviceGroupTargeting.setValues(Arrays.asList(new String[] { "427", "429" }));
		mobileTargeting.setDeviceGroupTargeting(deviceGroupTargeting);
		campaign.setMobileTargeting(mobileTargeting);

		CampaignTargetValues zoneTargeting = new CampaignTargetValues();
		zoneTargeting.setValues(Arrays.asList(new String[] { "1", "2" }));
		campaign.setZoneTargeting(zoneTargeting);


		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);

	}

	@Test
	public void update_WithTargetingHavingSingleValue_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-targeting-single-value.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);
		campaign.setImpressions(1000l);
		campaign.setWeight(0l);
		campaign.setPriorityLevel(0l);
		campaign.setCompletion(Completion.SOONEST);
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach(Reach.OPEN);
		campaign.setSmoothOrAsap(SmoothAsap.SMOOTH);
		campaign.setImpressionsOverrun(0l);
		campaign.setStrictCompanions(false);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setUserTimeZone(false);

		campaign.setExcludeTargets(false);

		CampaignExcludableTargetValues topLevelDomain = new CampaignExcludableTargetValues();
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(Arrays.asList("US"));
		campaign.setTopDomainTargeting(topLevelDomain);

		CampaignExcludableTargetValues bandwidthTargeting = new CampaignExcludableTargetValues();
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(Arrays.asList("LAN"));
		campaign.setBandwidthTargeting(bandwidthTargeting);

		CampaignExcludableTargetValues continentTargeting = new CampaignExcludableTargetValues();
		continentTargeting.setExclude(false);
		continentTargeting.setValues(Arrays.asList("AU"));
		campaign.setContinentTargeting(continentTargeting);

		CampaignExcludableTargetValues countryTargeting = new CampaignExcludableTargetValues();
		countryTargeting.setExclude(true);
		countryTargeting.setValues(Arrays.asList("BE"));
		campaign.setCountryTargeting(countryTargeting);

		CampaignExcludableTargetValues stateTargeting = new CampaignExcludableTargetValues();
		stateTargeting.setExclude(false);
		stateTargeting.setValues(Arrays.asList("BE:BRUSSELS"));
		campaign.setStateTargeting(stateTargeting);

		CampaignExcludableTargetValues msaTargeting = new CampaignExcludableTargetValues();
		msaTargeting.setExclude(false);
		msaTargeting.setValues(Arrays.asList("11220"));
		campaign.setMsaTargeting(msaTargeting);

		CampaignExcludableTargetValues dmaTargeting = new CampaignExcludableTargetValues();
		dmaTargeting.setExclude(false);
		dmaTargeting.setValues(Arrays.asList("803"));
		campaign.setDmaTargeting(dmaTargeting);

		CampaignExcludableTargetValues osTargeting = new CampaignExcludableTargetValues();
		osTargeting.setExclude(false);
		osTargeting.setValues(Arrays.asList("winxp"));
		campaign.setOsTargeting(osTargeting);

		CampaignExcludableTargetValues browserTargeting = new CampaignExcludableTargetValues();
		browserTargeting.setExclude(false);
		browserTargeting.setValues(Arrays.asList("opera"));
		campaign.setBrowserTargeting(browserTargeting);

		CampaignExcludableTargetValues browserVersionTargeting = new CampaignExcludableTargetValues();
		browserVersionTargeting.setExclude(false);
		browserVersionTargeting.setValues(Arrays.asList("firefox19"));
		campaign.setBrowserVTargeting(browserVersionTargeting);

		CampaignTargetValues zoneTargeting = new CampaignTargetValues();
		zoneTargeting.setValues(Arrays.asList(new String[] { "1" }));
		campaign.setZoneTargeting(zoneTargeting);

		MobileTargetings mobileTargeting = new MobileTargetings();
		CampaignTargetValues deviceGroupTargeting = new CampaignTargetValues();
		deviceGroupTargeting.setValues(Arrays.asList(new String[] { "427" }));
		mobileTargeting.setDeviceGroupTargeting(deviceGroupTargeting);
		campaign.setMobileTargeting(mobileTargeting);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithTargetingHavingEmptyValue_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-targeting-empty-value.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");

		CampaignExcludableTargetValues topLevelDomain = new CampaignExcludableTargetValues();
		topLevelDomain.setExclude(false);
		topLevelDomain.setValues(EMPTY_STRING_LIST);
		campaign.setTopDomainTargeting(topLevelDomain);

		CampaignExcludableTargetValues bandwidthTargeting = new CampaignExcludableTargetValues();
		bandwidthTargeting.setExclude(true);
		bandwidthTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setBandwidthTargeting(bandwidthTargeting);

		CampaignExcludableTargetValues continentTargeting = new CampaignExcludableTargetValues();
		continentTargeting.setExclude(false);
		continentTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setContinentTargeting(continentTargeting);

		CampaignExcludableTargetValues countryTargeting = new CampaignExcludableTargetValues();
		countryTargeting.setExclude(true);
		countryTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setCountryTargeting(countryTargeting);

		CampaignExcludableTargetValues stateTargeting = new CampaignExcludableTargetValues();
		stateTargeting.setExclude(false);
		stateTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setStateTargeting(stateTargeting);

		CampaignExcludableTargetValues msaTargeting = new CampaignExcludableTargetValues();
		msaTargeting.setExclude(false);
		msaTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setMsaTargeting(msaTargeting);

		CampaignExcludableTargetValues dmaTargeting = new CampaignExcludableTargetValues();
		dmaTargeting.setExclude(false);
		dmaTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setDmaTargeting(dmaTargeting);

		CampaignExcludableTargetValues osTargeting = new CampaignExcludableTargetValues();
		osTargeting.setExclude(false);
		osTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setOsTargeting(osTargeting);

		CampaignExcludableTargetValues browserTargeting = new CampaignExcludableTargetValues();
		browserTargeting.setExclude(false);
		browserTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setBrowserTargeting(browserTargeting);

		CampaignExcludableTargetValues browserVersionTargeting = new CampaignExcludableTargetValues();
		browserVersionTargeting.setExclude(false);
		browserVersionTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setBrowserVTargeting(browserVersionTargeting);

		CampaignTargetValues zoneTargeting = new CampaignTargetValues();
		zoneTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setZoneTargeting(zoneTargeting);

		MobileTargetings mobileTargeting = new MobileTargetings();
		CampaignTargetValues deviceGroupTargeting = new CampaignTargetValues();
		deviceGroupTargeting.setValues(EMPTY_STRING_LIST);
		mobileTargeting.setDeviceGroupTargeting(deviceGroupTargeting);
		campaign.setMobileTargeting(mobileTargeting);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithTargetingHavingNullValue_Success() throws Exception {

		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-targeting-null-value.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");

		campaign.setTopDomainTargeting(null);
		campaign.setBandwidthTargeting(null);
		campaign.setContinentTargeting(null);
		campaign.setCountryTargeting(null);
		campaign.setStateTargeting(null);
		campaign.setMsaTargeting(null);
		campaign.setDmaTargeting(null);
		campaign.setOsTargeting(null);
		campaign.setBrowserTargeting(null);
		campaign.setBrowserVTargeting(null);
		campaign.setZoneTargeting(null);
		campaign.setMobileTargeting(null);
		campaign.setSegmentTargeting(null);
		campaign.setRdbTargeting(null);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithHourOfDay_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-hourofday.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setStatus(CampaignStatus.WORK_IN_PROGRESS);
		campaign.setImpressions(1000l);
		campaign.setWeight(1000l);
		campaign.setPriorityLevel(15l);
		campaign.setCompletion(Completion.END_DATE);
		campaign.setStartDate(new LocalDate(2010, 10, 31));
		campaign.setEndDate(new LocalDate(2010, 10, 31));
		campaign.setReach(Reach.FIXED);
		campaign.setDailyImps(999999999l);
		campaign.setSmoothOrAsap(SmoothAsap.ASAP);
		campaign.setImpressionsOverrun(0l);
		campaign.setStrictCompanions(false);
		campaign.setSecondaryImpsPerVisitor(0l);
		campaign.setSecondaryFrequencyScope(FrequencyScope.ZERO);
		campaign.setHourOfDay(Lists.newArrayList(HourOfDay.EIGHTEEN, HourOfDay.NINETEEN, HourOfDay.TWENTY));
		campaign.setUserTimeZone(false);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithHourOfDayAndDayOfWeek_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-hod-dow.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setHourOfDay(Arrays.asList(HourOfDay.EIGHTEEN, HourOfDay.NINETEEN, HourOfDay.TWENTY));
		campaign.setDayOfWeek(Lists.newArrayList(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithHourOfDayAndDayOfWeekEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-hod-dow-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");
		campaign.setHourOfDay(new ArrayList<HourOfDay>());
		campaign.setDayOfWeek(new ArrayList<DayOfWeek>());

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithRdbTargeting_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

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
		rdbTargeting.setGender(Gender.M);
		rdbTargeting.setIncomeExclude(true);
		rdbTargeting.setIncomeFrom(13l);
		rdbTargeting.setIncomeTo(30l);
		rdbTargeting.setSubscriberCodeExclude(true);
		rdbTargeting.setSubscriberCode("TEST");
		rdbTargeting.setPreferenceFlagsExclude(true);
		rdbTargeting.setPreferenceFlags("012345678911");
		campaign.setRdbTargeting(rdbTargeting);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithSegmentTargeting_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-segment-targeting.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setSegmentType(SegmentType.ALL);
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(Arrays.asList(new String[] { "AlaSegTest1", "AlaSegTest2" }));
		campaign.setSegmentTargeting(segmentTargeting);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithSegmentTargetingEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-segment-targeting-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("ADID");

		SegmentTargeting segmentTargeting = new SegmentTargeting();
		segmentTargeting.setExclude(true);
		segmentTargeting.setValues(EMPTY_STRING_LIST);
		campaign.setSegmentTargeting(segmentTargeting);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithOnlyState_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-secondpush-request-with-only-state.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setStatus(CampaignStatus.CANCELLED);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithOnlyCompanion_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-secondpush-request-with-only-companion.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setCompanionPositions(Arrays.asList(new String[] { "B/T" }));
		campaign.setStrictCompanions(true);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithOnlyHourOfDay_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-secondpush-request-with-only-hourofday.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setHourOfDay(Arrays.asList(HourOfDay.EIGHTEEN, HourOfDay.NINETEEN, HourOfDay.TWENTY));

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithStartAndEndTimesHasNonStandardMinuteValues_StartTimeHas0MinsAndEndTime59Mins()
			throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-secondpush-request-with-starttime-endtime.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setStartTime(new LocalTime(8, 30));
		campaign.setEndTime(new LocalTime(17, 0));

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithExcludeSiteAndPageIds_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-exclude-site-page-ids.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setExcludedSiteIds(Lists.newArrayList("mkarlov.com", "apiSite2"));
		campaign.setExcludedPageUrls(Lists.newArrayList("www.spon.de/sport/wm-spezial/center", "www.testfz.com", "www.mkarlov.com/sports"));

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithExcludeSiteAndPageIdsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-exclude-site-page-ids-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setExcludedSiteIds(EMPTY_STRING_LIST);
		campaign.setExcludedPageUrls(EMPTY_STRING_LIST);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithPageUrls_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-page-urls.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("0212_CHLOE_ENTREE_SITE_XPR_STYLE_RG_6278");
		campaign.setPageUrls(Lists.newArrayList("www.spon.de/sport/wm-spezial/center", "www.testfz.com", "www.mkarlov.com/sports"));

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithPageUrlsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-page-urls-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setPageUrls(EMPTY_STRING_LIST);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithSectionIds_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-section-ids.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setSectionIds(Lists.newArrayList("383section", "AASec", "Adulte"));

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithSectionIdsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-section-ids-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setSectionIds(EMPTY_STRING_LIST);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithCampaignGroupIds_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-campaign-groups.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setCampaignGroupIds(Lists.newArrayList("0521_AGEN313394_Campaig_010313_12948_152", "055CASHME388747_Campaig_010313_12947_152"));

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithCampaignGroupIdsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-campaign-groups-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_1");
		campaign.setCampaignGroupIds(EMPTY_STRING_LIST);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithExternalUserIds_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-user-ids.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_2_clt");
		campaign.setExternalUserIds(Lists.newArrayList("AAExt", "alaExt"));

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}

	@Test
	public void update_WithExternalUserIdsEmpty_Success() throws Exception {
		OasApiService mockedApiService = mock(OasApiService.class);
		CampaignService service = new DefaultCampaignService(mockedApiService);

		final String expectedRequest = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("expected-second-push-request-with-user-ids-empty.xml", this.getClass()));
		final String mockedAnswer = normalizeNewLinesToCurPlatform(TestFileUtils.getTestResourceAsString("add-campaign-successful-response.xml", this.getClass()));
		when(mockedApiService.callApi(expectedRequest, false)).thenReturn(mockedAnswer);

		Campaign campaign = new Campaign();
		campaign.setId("test_campaign_gunith_2_clt");
		campaign.setExternalUserIds(EMPTY_STRING_LIST);

		service.update(campaign);
		verify(mockedApiService).callApi(expectedRequest, false);
	}
}
