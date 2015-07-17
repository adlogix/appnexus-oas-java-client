package eu.adlogix.appnexus.oas.client.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.collect.Maps;

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
import eu.adlogix.appnexus.oas.client.domain.enums.TargetGroup;
import eu.adlogix.appnexus.oas.client.domain.enums.TargetingCode;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createBooleanFromXmlString;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createDouble;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createInteger;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLocalDate;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLocalTime;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLong;

@AllArgsConstructor
public class XmlToCampaignParser implements XmlToObjectParser<Campaign>{

	private final ResponseParser responseParser;

	@Override
	public Campaign parse() {

		Campaign campaign = new Campaign();

		parseAndSetOverviewAttributes(campaign);
		parseAndSetScheduleAttributes(campaign);
		parseAndSetNetworkAttributes(campaign);
		parseAndSetTargetingAttributes(campaign);
		parseAndSetBillingAttributes(campaign);

		return campaign;
	}

	private void parseAndSetNetworkAttributes(Campaign campaign) {
		campaign.setPageUrls(responseParser.getTrimmedElementList("//Campaign/Pages/Url"));
		campaign.setExcludedSiteIds(responseParser.getTrimmedElementList("//Campaign/Exclude/Sites/SiteId"));
		campaign.setExcludedPageUrls(responseParser.getTrimmedElementList("//Campaign/Exclude/Pages/Url"));
	}

	private void parseAndSetOverviewAttributes(Campaign campaign) {
		campaign.setId(responseParser.getTrimmedElement("//Campaign/Overview/Id"));
		campaign.setType(CampaignType.fromString(responseParser.getTrimmedElement("//Campaign/Overview/Type")));
		campaign.setInsertionOrderId(responseParser.getTrimmedElement("//Campaign/Overview/InsertionOrderId"));
		campaign.setAdvertiserId(responseParser.getTrimmedElement("//Campaign/Overview/AdvertiserId"));
		campaign.setName(responseParser.getTrimmedElement("//Campaign/Overview/Name"));
		campaign.setAgencyId(responseParser.getTrimmedElement("//Campaign/Overview/AgencyId"));
		campaign.setDescription(responseParser.getTrimmedElement("//Campaign/Overview/Description"));
		campaign.setCampaignManager(responseParser.getTrimmedElement("//Campaign/Overview/CampaignManager"));
		campaign.setProductId(responseParser.getTrimmedElement("//Campaign/Overview/ProductId"));
		campaign.setStatus(CampaignStatus.fromString(responseParser.getTrimmedElement("//Campaign/Overview/Status")));
		campaign.setCampaignGroupIds(responseParser.getTrimmedElementList("//Campaign/Overview/CampaignGroups/CampaignGroupId"));
		campaign.setCompetitiveCategroryIds(responseParser.getTrimmedElementList("//Campaign/Overview/CompetitiveCategories/CompetitiveCategoryId"));
		campaign.setExternalUserIds(responseParser.getTrimmedElementList("//Campaign/Overview/ExternalUsers/UserId"));
		campaign.setInternalQuickReport(responseParser.getTrimmedElement("//Campaign/Overview/InternalQuickReport"));
		campaign.setExternalQuickReport(responseParser.getTrimmedElement("//Campaign/Overview/ExternalQuickReport"));
	}

	private void parseAndSetScheduleAttributes(Campaign campaign) {
		campaign.setImpressions(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/Impressions")));
		campaign.setClicks(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/Clicks")));
		campaign.setUniques(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/Uniques")));
		campaign.setWeight(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/Weight")));
		campaign.setPriorityLevel(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/PriorityLevel")));
		campaign.setCompletion(Completion.fromString(responseParser.getTrimmedElement("//Campaign/Schedule/Completion")));

		String startDateString = responseParser.getTrimmedElement("//Campaign/Schedule/StartDate");
		campaign.setStartDate(createLocalDate(startDateString));

		String startTimeString = responseParser.getTrimmedElement("//Campaign/Schedule/StartTime");
		campaign.setStartTime(createLocalTime(startTimeString));

		String endDateString = responseParser.getTrimmedElement("//Campaign/Schedule/EndDate");
		campaign.setEndDate(createLocalDate(endDateString));

		String endTimeString = responseParser.getTrimmedElement("//Campaign/Schedule/EndTime");
		campaign.setEndTime(createLocalTime(endTimeString));

		campaign.setReach(Reach.fromString(responseParser.getTrimmedElement("//Campaign/Schedule/Reach")));
		campaign.setDailyImps(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/DailyImp")));
		campaign.setDailyClicks(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/DailyClicks")));
		campaign.setDailyUniques(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/DailyUniq")));

		campaign.setSmoothOrAsap(SmoothAsap.fromString(responseParser.getTrimmedElement("//Campaign/Schedule/SmoothOrAsap")));
		campaign.setImpressionsOverrun(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/ImpOverrun")));
		campaign.setCompanionPositions(responseParser.getTrimmedElementList("//Campaign/Schedule/CompanionPositions/CompanionPosition"));
		campaign.setStrictCompanions(createBooleanFromXmlString(responseParser.getTrimmedElement("//Campaign/Schedule/StrictCompanions")));
		campaign.setPrimaryImpsPerVisitor(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/PrimaryFrequency/ImpPerVisitor")));
		campaign.setPrimaryClicksPerVisitor(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/PrimaryFrequency/ClickPerVisitor")));
		campaign.setPrimaryFrequencyScope(FrequencyScope.fromString((responseParser.getTrimmedElement("//Campaign/Schedule/PrimaryFrequency/FreqScope"))));
		campaign.setSecondaryImpsPerVisitor(createLong(responseParser.getTrimmedElement("//Campaign/Schedule/SecondaryFrequency/ImpPerVisitor")));
		campaign.setSecondaryFrequencyScope(FrequencyScope.fromString(responseParser.getTrimmedElement("//Campaign/Schedule/SecondaryFrequency/FreqScope")));

		List<String> hourOfDayCodes = responseParser.getTrimmedElementList("//Campaign/Schedule/HourOfDay/Hour");
		if (hourOfDayCodes != null) {
			List<HourOfDay> hoursOfDay = new ArrayList<HourOfDay>();
			for (String hourCode : hourOfDayCodes) {
				hoursOfDay.add(HourOfDay.fromString(hourCode));
			}
			campaign.setHourOfDay(hoursOfDay);
		}

		List<String> daysOfWeekCodes = responseParser.getTrimmedElementList("//Campaign/Schedule/DayOfWeek/Day");
		if (daysOfWeekCodes != null) {
			List<DayOfWeek> daysOfWeek = new ArrayList<DayOfWeek>();
			for (String dayOfWeekCode : daysOfWeekCodes) {
				daysOfWeek.add(DayOfWeek.fromString(dayOfWeekCode));
			}
			campaign.setDayOfWeek(daysOfWeek);
		}

		campaign.setUserTimeZone(createBooleanFromXmlString(responseParser.getTrimmedElement("//Campaign/Schedule/UserTimeZone")));
		campaign.setSectionIds(responseParser.getTrimmedElementList("//Campaign/Schedule/Sections/SectionId"));

	}

	private void parseAndSetTargetingAttributes(Campaign campaign) {

		campaign.setExcludeTargets(createBooleanFromXmlString(responseParser.getTrimmedElement("//Campaign/Target/ExcludeTargets")));

		campaign.setTargetings(parseAndCreateGeneralTargeting());
		campaign.setZoneTargeting(parseAndCreateZoneTargeting());
		campaign.setMobileTargeting(parseAndCreateMobileTargeting());
		campaign.setRdbTargeting(parseAndCreateRdbTargeting());
		campaign.setSegmentTargeting(parseAndCreateSegmentTargeting());

	}

	private MobileTargetings parseAndCreateMobileTargeting() {

		MobileTargetings mobileTargetingGroup = new MobileTargetings();

		mobileTargetingGroup.setExcludeMobileDevice(createBooleanFromXmlString(responseParser.getTrimmedElement("//Campaign/Target/ExcludeMobileTargeting")));

		final Map<TargetingCode, CampaignTargetValues> targetings = Maps.newHashMap();
		for (TargetingCode targetingCode : TargetingCode.getCodesForGroup(TargetGroup.MOBILE)) {

			final List<String> values = populateTargetingValues(targetingCode);
			final CampaignTargetValues targeting = new CampaignTargetValues();
			targeting.setValues(values);
			targetings.put(targetingCode, targeting);
		}

		mobileTargetingGroup.setTargetings(targetings);
		return mobileTargetingGroup;
	}

	private void parseAndSetBillingAttributes(Campaign campaign) {
		campaign.setCpm(createDouble(responseParser.getTrimmedElement("//Campaign/Billing/Cpm")));
		campaign.setCpc(createDouble(responseParser.getTrimmedElement("//Campaign/Billing/Cpc")));
		campaign.setCpa(createDouble(responseParser.getTrimmedElement("//Campaign/Billing/Cpa")));
		campaign.setFlatRate(createDouble(responseParser.getTrimmedElement("//Campaign/Billing/FlatRate")));
		campaign.setTax(createDouble(responseParser.getTrimmedElement("//Campaign/Billing/Tax")));
		campaign.setAgencyCommission(NumberUtils.createDouble(responseParser.getTrimmedElement("//Campaign/Billing/AgencyCommission")));
		campaign.setPaymentMethod(PaymentMethod.fromString(responseParser.getTrimmedElement("//Campaign/Billing/PaymentMethod")));
		campaign.setIsYieldManaged(createBooleanFromXmlString(responseParser.getTrimmedElement("//Campaign/Billing/IsYieldManaged")));
		campaign.setBillTo(BillTo.fromString(responseParser.getTrimmedElement("//Campaign/Billing/BillTo")));
		campaign.setCurrency(responseParser.getTrimmedElement("//Campaign/Billing/Currency"));

	}

	private Map<TargetingCode, CampaignExcludableTargetValues> parseAndCreateGeneralTargeting() {

		final Map<TargetingCode, CampaignExcludableTargetValues> targetings = Maps.newHashMap();

		for (TargetingCode targetingCode : TargetingCode.getCodesForGroup(TargetGroup.GENERAL)) {

			final List<String> values = populateTargetingValues(targetingCode);
			final Boolean exclude = populateTargetingExcludeFlag(targetingCode);

			final CampaignExcludableTargetValues targeting = new CampaignExcludableTargetValues();
			targeting.setExclude(exclude);
			targeting.setValues(values);

			targetings.put(targetingCode, targeting);
		}
		return targetings;
	}

	private CampaignTargetValues parseAndCreateZoneTargeting() {

		List<String> values = populateTargetingValues(TargetingCode.ZONE);

		final CampaignTargetValues targeting = new CampaignTargetValues();
		targeting.setValues(values);

		return targeting;
	}

	private Boolean populateTargetingExcludeFlag(TargetingCode targetingCode) {
		String exculdeStr = responseParser.getTrimmedElement("//Campaign/Target/Exclude"
				+ targetingCode.getCodeForCampaigns());
		return createBooleanFromXmlString(exculdeStr);
	}

	private List<String> populateTargetingValues(TargetingCode targetingCode) {
		return responseParser.getTrimmedElementList("//Campaign/Target/" + targetingCode.getCodeForCampaigns()
				+ "/Code");
	}

	private RdbTargeting parseAndCreateRdbTargeting() {

		RdbTargeting rdbTargeting = new RdbTargeting();

		String ageExcludeStr = responseParser.getTrimmedElement("//Campaign/Target/ExcludeAgeTargeting");
		rdbTargeting.setAgeExclude(createBooleanFromXmlString(ageExcludeStr));

		String ageFromStr = responseParser.getTrimmedElement("//Campaign/Target/AgeFrom");
		rdbTargeting.setAgeFrom(createInteger(ageFromStr));

		String ageToStr = responseParser.getTrimmedElement("//Campaign/Target/AgeTo");
		rdbTargeting.setAgeTo(createInteger(ageToStr));

		String genderExculdeStr = responseParser.getTrimmedElement("//Campaign/Target/ExcludeGenderTargeting");
		rdbTargeting.setGenderExclude(createBooleanFromXmlString(genderExculdeStr));

		String genderStr = responseParser.getTrimmedElement("//Campaign/Target/Gender/Code");
		rdbTargeting.setGender(Gender.valueOf(genderStr));

		String incomeExcludeStr = responseParser.getTrimmedElement("//Campaign/Target/ExcludeIncomeTargeting");
		rdbTargeting.setIncomeExclude(createBooleanFromXmlString(incomeExcludeStr));

		String incomeFromStr = responseParser.getTrimmedElement("//Campaign/Target/IncomeFrom");
		rdbTargeting.setIncomeFrom(createLong(incomeFromStr));

		String incomeToStr = responseParser.getTrimmedElement("//Campaign/Target/IncomeTo");
		rdbTargeting.setIncomeTo(createLong(incomeToStr));

		String subsciberExculdeStr = responseParser.getTrimmedElement("//Campaign/Target/ExcludeSubscriberTargeting");
		rdbTargeting.setSubscriberCodeExclude(createBooleanFromXmlString(subsciberExculdeStr));

		String subscriberCode = responseParser.getTrimmedElement("//Campaign/Target/SubscriberCode");
		rdbTargeting.setSubscriberCode(subscriberCode);

		String preferenceFlagsExclude = responseParser.getTrimmedElement("//Campaign/Target/ExcludePreferenceTargeting");
		rdbTargeting.setPreferenceFlagsExclude(createBooleanFromXmlString(preferenceFlagsExclude));

		String preferenceFlags = responseParser.getTrimmedElement("//Campaign/Target/PreferenceFlags");
		rdbTargeting.setPreferenceFlags(preferenceFlags);

		return rdbTargeting;
	}

	private SegmentTargeting parseAndCreateSegmentTargeting() {

		SegmentTargeting targeting = new SegmentTargeting();
		targeting.setSegmentType(SegmentType.fromString(responseParser.getTrimmedElement("//Campaign/Target/Cluster/SegmentType")));
		String exculdeStr = responseParser.getTrimmedElement("//Campaign/Target/ExcludeSegmentTargeting");
		List<String> targetingValues = responseParser.getTrimmedElementList("//Campaign/Target/Cluster/Segment");
		targeting.setValues(targetingValues);
		targeting.setExclude(createBooleanFromXmlString(exculdeStr));
		return targeting;
	}
}
