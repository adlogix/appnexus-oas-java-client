package eu.adlogix.appnexus.oas.client.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.Maps;

import eu.adlogix.appnexus.oas.client.domain.BillTo;
import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.CampaignExcludableTargetValues;
import eu.adlogix.appnexus.oas.client.domain.CampaignStatus;
import eu.adlogix.appnexus.oas.client.domain.CampaignTargetValues;
import eu.adlogix.appnexus.oas.client.domain.CampaignType;
import eu.adlogix.appnexus.oas.client.domain.Completion;
import eu.adlogix.appnexus.oas.client.domain.DayOfWeek;
import eu.adlogix.appnexus.oas.client.domain.FrequencyScope;
import eu.adlogix.appnexus.oas.client.domain.Gender;
import eu.adlogix.appnexus.oas.client.domain.HourOfDay;
import eu.adlogix.appnexus.oas.client.domain.MobileTargetings;
import eu.adlogix.appnexus.oas.client.domain.PaymentMethod;
import eu.adlogix.appnexus.oas.client.domain.RdbTargeting;
import eu.adlogix.appnexus.oas.client.domain.Reach;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.domain.SegmentType;
import eu.adlogix.appnexus.oas.client.domain.SmoothAsap;
import eu.adlogix.appnexus.oas.client.domain.TargetGroup;
import eu.adlogix.appnexus.oas.client.domain.TargetingCode;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;

import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createBooleanFromXmlString;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createDouble;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createInteger;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLocalDate;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLocalTime;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLong;

@AllArgsConstructor
public class XmlToCampaignParser implements XmlToObjectParser<Campaign>{

	private static final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	private static final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");

	private ResponseParser responseParser;

	@Override
	public Campaign parse() {
		Campaign campaign = new Campaign();
		campaign = parseAndSetOverviewAttributes(responseParser, campaign);
		campaign = parseAndSetScheduleAttributes(responseParser, campaign);
		campaign.setPageUrls(responseParser.getTrimmedElementList("//Campaign/Pages/Url"));
		campaign.setExcludedSiteIds(responseParser.getTrimmedElementList("//Campaign/Exclude/Sites/SiteId"));
		campaign.setExcludedPageUrls(responseParser.getTrimmedElementList("//Campaign/Exclude/Pages/Url"));
		campaign = parseAndSetTargetingAttributes(responseParser, campaign);
		campaign = parseAndSetBillingAttributes(responseParser, campaign);
		return campaign;
	}

	private Campaign parseAndSetOverviewAttributes(final ResponseParser parser, Campaign campaign) {
		campaign.setId(parser.getTrimmedElement("//Campaign/Overview/Id"));
		campaign.setType(CampaignType.fromString(parser.getTrimmedElement("//Campaign/Overview/Type")));
		campaign.setInsertionOrderId(parser.getTrimmedElement("//Campaign/Overview/InsertionOrderId"));
		campaign.setAdvertiserId(parser.getTrimmedElement("//Campaign/Overview/AdvertiserId"));
		campaign.setName(parser.getTrimmedElement("//Campaign/Overview/Name"));
		campaign.setAgencyId(parser.getTrimmedElement("//Campaign/Overview/AgencyId"));
		campaign.setDescription(parser.getTrimmedElement("//Campaign/Overview/Description"));
		campaign.setCampaignManager(parser.getTrimmedElement("//Campaign/Overview/CampaignManager"));
		campaign.setProductId(parser.getTrimmedElement("//Campaign/Overview/ProductId"));
		campaign.setStatus(CampaignStatus.fromString(parser.getTrimmedElement("//Campaign/Overview/Status")));
		campaign.setCampaignGroupIds(parser.getTrimmedElementList("//Campaign/Overview/CampaignGroups/CampaignGroupId"));
		campaign.setCompetitiveCategroryIds(parser.getTrimmedElementList("//Campaign/Overview/CompetitiveCategories/CompetitiveCategoryId"));
		campaign.setExternalUserIds(parser.getTrimmedElementList("//Campaign/Overview/ExternalUsers/UserId"));
		campaign.setInternalQuickReport(parser.getTrimmedElement("//Campaign/Overview/InternalQuickReport"));
		campaign.setExternalQuickReport(parser.getTrimmedElement("//Campaign/Overview/ExternalQuickReport"));
		return campaign;
	}

	private Campaign parseAndSetScheduleAttributes(final ResponseParser parser, Campaign campaign) {
		campaign.setImpressions(createLong(parser.getTrimmedElement("//Campaign/Schedule/Impressions")));
		campaign.setClicks(createLong(parser.getTrimmedElement("//Campaign/Schedule/Clicks")));
		campaign.setUniques(createLong(parser.getTrimmedElement("//Campaign/Schedule/Uniques")));
		campaign.setWeight(createLong(parser.getTrimmedElement("//Campaign/Schedule/Weight")));
		campaign.setPriorityLevel(createLong(parser.getTrimmedElement("//Campaign/Schedule/PriorityLevel")));
		campaign.setCompletion(Completion.fromString(parser.getTrimmedElement("//Campaign/Schedule/Completion")));

		String startDateString = parser.getTrimmedElement("//Campaign/Schedule/StartDate");
		campaign.setStartDate(createLocalDate(startDateString, dateFormatter));

		String startTimeString = parser.getTrimmedElement("//Campaign/Schedule/StartTime");
		campaign.setStartTime(createLocalTime(startTimeString, timeFormatter));

		String endDateString = parser.getTrimmedElement("//Campaign/Schedule/EndDate");
		campaign.setEndDate(createLocalDate(endDateString, dateFormatter));

		String endTimeString = parser.getTrimmedElement("//Campaign/Schedule/EndTime");
		campaign.setEndTime(createLocalTime(endTimeString, timeFormatter));

		campaign.setReach(Reach.fromString(parser.getTrimmedElement("//Campaign/Schedule/Reach")));
		campaign.setDailyImps(createLong(parser.getTrimmedElement("//Campaign/Schedule/DailyImp")));
		campaign.setDailyClicks(createLong(parser.getTrimmedElement("//Campaign/Schedule/DailyClicks")));
		campaign.setDailyUniques(createLong(parser.getTrimmedElement("//Campaign/Schedule/DailyUniq")));

		campaign.setSmoothOrAsap(SmoothAsap.fromString(parser.getTrimmedElement("//Campaign/Schedule/SmoothOrAsap")));
		campaign.setImpressionsOverrun(createLong(parser.getTrimmedElement("//Campaign/Schedule/ImpOverrun")));
		campaign.setCompanionPositions(parser.getTrimmedElementList("//Campaign/Schedule/CompanionPositions/CompanionPosition"));
		campaign.setStrictCompanions(createBooleanFromXmlString(parser.getTrimmedElement("//Campaign/Schedule/StrictCompanions")));
		campaign.setPrimaryImpsPerVisitor(createLong(parser.getTrimmedElement("//Campaign/Schedule/PrimaryFrequency/ImpPerVisitor")));
		campaign.setPrimaryClicksPerVisitor(createLong(parser.getTrimmedElement("//Campaign/Schedule/PrimaryFrequency/ClickPerVisitor")));
		campaign.setPrimaryFrequencyScope(FrequencyScope.fromString((parser.getTrimmedElement("//Campaign/Schedule/PrimaryFrequency/FreqScope"))));
		campaign.setSecondaryImpsPerVisitor(createLong(parser.getTrimmedElement("//Campaign/Schedule/SecondaryFrequency/ImpPerVisitor")));
		campaign.setSecondaryFrequencyScope(FrequencyScope.fromString(parser.getTrimmedElement("//Campaign/Schedule/SecondaryFrequency/FreqScope")));

		List<String> hourOfDayCodes = parser.getTrimmedElementList("//Campaign/Schedule/HourOfDay/Hour");
		if (hourOfDayCodes != null) {
			List<HourOfDay> hoursOfDay = new ArrayList<HourOfDay>();
			for (String hourCode : hourOfDayCodes) {
				hoursOfDay.add(HourOfDay.fromString(hourCode));
			}
			campaign.setHourOfDay(hoursOfDay);
		}

		List<String> daysOfWeekCodes = parser.getTrimmedElementList("//Campaign/Schedule/DayOfWeek/Day");
		if (daysOfWeekCodes != null) {
			List<DayOfWeek> daysOfWeek = new ArrayList<DayOfWeek>();
			for (String dayOfWeekCode : daysOfWeekCodes) {
				daysOfWeek.add(DayOfWeek.fromString(dayOfWeekCode));
			}
			campaign.setDayOfWeek(daysOfWeek);
		}

		campaign.setUserTimeZone(createBooleanFromXmlString(parser.getTrimmedElement("//Campaign/Schedule/UserTimeZone")));
		campaign.setSectionIds(parser.getTrimmedElementList("//Campaign/Schedule/Sections/SectionId"));
		return campaign;

	}

	private Campaign parseAndSetTargetingAttributes(final ResponseParser parser, Campaign campaign) {
		campaign.setExcludeTargets(createBooleanFromXmlString(parser.getTrimmedElement("//Campaign/Target/ExcludeTargets")));
		campaign.setTargetings(parseAndCreateGeneralTargeting(parser));
		campaign.setZoneTargeting(parseAndCreateZoneTargeting(parser));
		campaign.setMobileTargeting(parseAndCreateMobileTargeting(parser));
		campaign.setRdbTargeting(parseAndCreateRdbTargeting(parser));
		campaign.setSegmentTargeting(parseAndCreateSegmentTargeting(parser));
		return campaign;

	}

	private MobileTargetings parseAndCreateMobileTargeting(ResponseParser parser) {

		MobileTargetings mobileTargetingGroup = new MobileTargetings();

		mobileTargetingGroup.setExcludeMobileDevice(createBooleanFromXmlString(parser.getTrimmedElement("//Campaign/Target/ExcludeMobileTargeting")));

		final Map<TargetingCode, CampaignTargetValues> targetings = Maps.newHashMap();
		for (TargetingCode targetingCode : TargetingCode.getCodesForGroup(TargetGroup.MOBILE)) {

			final List<String> values = populateTargetingValues(targetingCode, parser);
			final CampaignTargetValues targeting = new CampaignTargetValues();
			targeting.setValues(values);
			targetings.put(targetingCode, targeting);
		}

		mobileTargetingGroup.setTargetings(targetings);
		return mobileTargetingGroup;
	}

	private Campaign parseAndSetBillingAttributes(final ResponseParser parser, Campaign campaign) {
		campaign.setCpm(createDouble(parser.getTrimmedElement("//Campaign/Billing/Cpm")));
		campaign.setCpc(createDouble(parser.getTrimmedElement("//Campaign/Billing/Cpc")));
		campaign.setCpa(createDouble(parser.getTrimmedElement("//Campaign/Billing/Cpa")));
		campaign.setFlatRate(createDouble(parser.getTrimmedElement("//Campaign/Billing/FlatRate")));
		campaign.setTax(createDouble(parser.getTrimmedElement("//Campaign/Billing/Tax")));
		campaign.setAgencyCommission(NumberUtils.createDouble(parser.getTrimmedElement("//Campaign/Billing/AgencyCommission")));
		campaign.setPaymentMethod(PaymentMethod.fromString(parser.getTrimmedElement("//Campaign/Billing/PaymentMethod")));
		campaign.setIsYieldManaged(createBooleanFromXmlString(parser.getTrimmedElement("//Campaign/Billing/IsYieldManaged")));
		campaign.setBillTo(BillTo.fromString(parser.getTrimmedElement("//Campaign/Billing/BillTo")));
		campaign.setCurrency(parser.getTrimmedElement("//Campaign/Billing/Currency"));
		return campaign;

	}

	private Map<TargetingCode, CampaignExcludableTargetValues> parseAndCreateGeneralTargeting(
			final ResponseParser parser) {

		final Map<TargetingCode, CampaignExcludableTargetValues> targetings = Maps.newHashMap();

		for (TargetingCode targetingCode : TargetingCode.getCodesForGroup(TargetGroup.GENERAL)) {

			final List<String> values = populateTargetingValues(targetingCode, parser);
			final Boolean exclude = populateTargetingExcludeFlag(parser, targetingCode);

			final CampaignExcludableTargetValues targeting = new CampaignExcludableTargetValues();
			targeting.setExclude(exclude);
			targeting.setValues(values);

			targetings.put(targetingCode, targeting);
		}
		return targetings;
	}

	private CampaignTargetValues parseAndCreateZoneTargeting(final ResponseParser parser) {

		List<String> values = populateTargetingValues(TargetingCode.ZONE, parser);

		final CampaignTargetValues targeting = new CampaignTargetValues();
		targeting.setValues(values);

		return targeting;
	}

	private Boolean populateTargetingExcludeFlag(final ResponseParser parser,
			TargetingCode targetingCode) {
		String exculdeStr = parser.getTrimmedElement("//Campaign/Target/Exclude"
				+ targetingCode.getCodeForCampaigns());
		return createBooleanFromXmlString(exculdeStr);
	}

	private List<String> populateTargetingValues(TargetingCode targetingCode, final ResponseParser parser) {
		return parser.getTrimmedElementList("//Campaign/Target/" + targetingCode.getCodeForCampaigns()
				+ "/Code");
	}

	private RdbTargeting parseAndCreateRdbTargeting(final ResponseParser parser) {
		RdbTargeting rdbTargeting = new RdbTargeting();

		String ageExcludeStr = parser.getTrimmedElement("//Campaign/Target/ExcludeAgeTargeting");
		rdbTargeting.setAgeExclude(createBooleanFromXmlString(ageExcludeStr));

		String ageFromStr = parser.getTrimmedElement("//Campaign/Target/AgeFrom");
		rdbTargeting.setAgeFrom(createInteger(ageFromStr));

		String ageToStr = parser.getTrimmedElement("//Campaign/Target/AgeTo");
		rdbTargeting.setAgeTo(createInteger(ageToStr));

		String genderExculdeStr = parser.getTrimmedElement("//Campaign/Target/ExcludeGenderTargeting");
		rdbTargeting.setGenderExclude(createBooleanFromXmlString(genderExculdeStr));

		String genderStr = parser.getTrimmedElement("//Campaign/Target/Gender/Code");
		rdbTargeting.setGender(Gender.valueOf(genderStr));

		String incomeExcludeStr = parser.getTrimmedElement("//Campaign/Target/ExcludeIncomeTargeting");
		rdbTargeting.setIncomeExclude(createBooleanFromXmlString(incomeExcludeStr));

		String incomeFromStr = parser.getTrimmedElement("//Campaign/Target/IncomeFrom");
		rdbTargeting.setIncomeFrom(createLong(incomeFromStr));

		String incomeToStr = parser.getTrimmedElement("//Campaign/Target/IncomeTo");
		rdbTargeting.setIncomeTo(createLong(incomeToStr));

		String subsciberExculdeStr = parser.getTrimmedElement("//Campaign/Target/ExcludeSubscriberTargeting");
		rdbTargeting.setSubscriberCodeExclude(createBooleanFromXmlString(subsciberExculdeStr));

		String subscriberCode = parser.getTrimmedElement("//Campaign/Target/SubscriberCode");
		rdbTargeting.setSubscriberCode(subscriberCode);

		String preferenceFlagsExclude = parser.getTrimmedElement("//Campaign/Target/ExcludePreferenceTargeting");
		rdbTargeting.setPreferenceFlagsExclude(createBooleanFromXmlString(preferenceFlagsExclude));

		String preferenceFlags = parser.getTrimmedElement("//Campaign/Target/PreferenceFlags");
		rdbTargeting.setPreferenceFlags(preferenceFlags);

		return rdbTargeting;
	}

	private SegmentTargeting parseAndCreateSegmentTargeting(final ResponseParser parser) {

		SegmentTargeting targeting = new SegmentTargeting();
		targeting.setSegmentType(SegmentType.fromString(parser.getTrimmedElement("//Campaign/Target/Cluster/SegmentType")));
		String exculdeStr = parser.getTrimmedElement("//Campaign/Target/ExcludeSegmentTargeting");
		List<String> targetingValues = parser.getTrimmedElementList("//Campaign/Target/Cluster/Segment");
		targeting.setValues(targetingValues);
		targeting.setExclude(createBooleanFromXmlString(exculdeStr));
		return targeting;
	}
}
