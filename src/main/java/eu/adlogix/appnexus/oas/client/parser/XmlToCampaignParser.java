package eu.adlogix.appnexus.oas.client.parser;

import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createBooleanFromXmlString;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createDouble;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createInteger;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLocalDate;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLocalTime;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.createLong;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.domain.RdbTargeting;
import eu.adlogix.appnexus.oas.client.domain.SegmentTargeting;
import eu.adlogix.appnexus.oas.client.domain.Targeting;
import eu.adlogix.appnexus.oas.client.domain.Targeting.TargetingType;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;

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
		campaign.setType(parser.getTrimmedElement("//Campaign/Overview/Type"));
		campaign.setInsertionOrderId(parser.getTrimmedElement("//Campaign/Overview/InsertionOrderId"));
		campaign.setAdvertiserId(parser.getTrimmedElement("//Campaign/Overview/AdvertiserId"));
		campaign.setName(parser.getTrimmedElement("//Campaign/Overview/Name"));
		campaign.setAgencyId(parser.getTrimmedElement("//Campaign/Overview/AgencyId"));
		campaign.setDescription(parser.getTrimmedElement("//Campaign/Overview/Description"));
		campaign.setCampaignManager(parser.getTrimmedElement("//Campaign/Overview/CampaignManager"));
		campaign.setProductId(parser.getTrimmedElement("//Campaign/Overview/ProductId"));
		campaign.setStatus(parser.getTrimmedElement("//Campaign/Overview/Status"));
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
		campaign.setWeight(parser.getTrimmedElement("//Campaign/Schedule/Weight"));
		campaign.setPriorityLevel(parser.getTrimmedElement("//Campaign/Schedule/PriorityLevel"));
		campaign.setCompletion(parser.getTrimmedElement("//Campaign/Schedule/Completion"));

		String startDateString = parser.getTrimmedElement("//Campaign/Schedule/StartDate");
		campaign.setStartDate(createLocalDate(startDateString, dateFormatter));

		String startTimeString = parser.getTrimmedElement("//Campaign/Schedule/StartTime");
		campaign.setStartTime(createLocalTime(startTimeString, timeFormatter));

		String endDateString = parser.getTrimmedElement("//Campaign/Schedule/EndDate");
		campaign.setEndDate(createLocalDate(endDateString, dateFormatter));

		String endTimeString = parser.getTrimmedElement("//Campaign/Schedule/EndTime");
		campaign.setEndTime(createLocalTime(endTimeString, timeFormatter));

		campaign.setReach(parser.getTrimmedElement("//Campaign/Schedule/Reach"));
		campaign.setDailyImps(createLong(parser.getTrimmedElement("//Campaign/Schedule/DailyImp")));
		campaign.setDailyClicks(createLong(parser.getTrimmedElement("//Campaign/Schedule/DailyClicks")));
		campaign.setDailyUniques(createLong(parser.getTrimmedElement("//Campaign/Schedule/DailyUniq")));

		campaign.setSmoothOrAsap(parser.getTrimmedElement("//Campaign/Schedule/SmoothOrAsap"));
		campaign.setImpressionsOverrun(createLong(parser.getTrimmedElement("//Campaign/Schedule/ImpOverrun")));
		campaign.setCompanionPositions(parser.getTrimmedElementList("//Campaign/Schedule/CompanionPositions/CompanionPosition"));
		campaign.setStrictCompanions(parser.getTrimmedElement("//Campaign/Schedule/StrictCompanions"));
		campaign.setPrimaryImpsPerVisitor(createLong(parser.getTrimmedElement("//Campaign/Schedule/PrimaryFrequency/ImpPerVisitor")));
		campaign.setPrimaryClicksPerVisitor(createLong(parser.getTrimmedElement("//Campaign/Schedule/PrimaryFrequency/ClickPerVisitor")));
		campaign.setPrimaryFrequencyScope(createLong(parser.getTrimmedElement("//Campaign/Schedule/PrimaryFrequency/FreqScope")));
		campaign.setSecondaryImpsPerVisitor(createLong(parser.getTrimmedElement("//Campaign/Schedule/SecondaryFrequency/ImpPerVisitor")));
		campaign.setSecondaryFrequencyScope(createLong(parser.getTrimmedElement("//Campaign/Schedule/SecondaryFrequency/FreqScope")));
		campaign.setHourOfDay(parser.getTrimmedElementList("//Campaign/Schedule/HourOfDay/Hour"));
		campaign.setDayOfWeek(parser.getTrimmedElementList("//Campaign/Schedule/DayOfWeek/Day"));
		campaign.setUserTimeZone(parser.getTrimmedElement("//Campaign/Schedule/UserTimeZone"));
		campaign.setSectionIds(parser.getTrimmedElementList("//Campaign/Schedule/Sections/SectionId"));
		return campaign;

	}

	private Campaign parseAndSetTargetingAttributes(final ResponseParser parser, Campaign campaign) {
		campaign.setExcludeTargets(createBooleanFromXmlString(parser.getTrimmedElement("//Campaign/Target/ExcludeTargets")));
		campaign.setCommonTargeting(parseAndCreateCommonTargeting(parser));
		campaign.setRdbTargeting(parseAndCreateRdbTargeting(parser));
		campaign.setSegmentTargeting(parseAndCreateSegmentTargeting(parser));
		return campaign;

	}

	private Campaign parseAndSetBillingAttributes(final ResponseParser parser, Campaign campaign) {
		campaign.setCpm(createDouble(parser.getTrimmedElement("//Campaign/Billing/Cpm")));
		campaign.setCpc(createDouble(parser.getTrimmedElement("//Campaign/Billing/Cpc")));
		campaign.setCpa(createDouble(parser.getTrimmedElement("//Campaign/Billing/Cpa")));
		campaign.setFlatRate(createDouble(parser.getTrimmedElement("//Campaign/Billing/FlatRate")));
		campaign.setTax(createDouble(parser.getTrimmedElement("//Campaign/Billing/Tax")));
		campaign.setAgencyCommission(NumberUtils.createDouble(parser.getTrimmedElement("//Campaign/Billing/AgencyCommission")));
		campaign.setPaymentMethod(parser.getTrimmedElement("//Campaign/Billing/PaymentMethod"));
		campaign.setIsYieldManaged(parser.getTrimmedElement("//Campaign/Billing/IsYieldManaged"));
		campaign.setBillTo(parser.getTrimmedElement("//Campaign/Billing/BillTo"));
		campaign.setCurrency(parser.getTrimmedElement("//Campaign/Billing/Currency"));
		return campaign;

	}

	private List<Targeting> parseAndCreateCommonTargeting(final ResponseParser parser) {

		List<Targeting> targetingList = new ArrayList<Targeting>();

		for (TargetingType targetingType : TargetingType.values()) {
			Targeting targeting = new Targeting(targetingType);

			List<String> values = parser.getTrimmedElementList("//Campaign/Target/" + targetingType.toString()
					+ "/Code");
			targeting.setValues(values);

			String exculdeStr = parser.getTrimmedElement("//Campaign/Target/Exclude" + targetingType.toString());
			targeting.setExclude(createBooleanFromXmlString(exculdeStr));
			targetingList.add(targeting);
		}
		return targetingList;
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
		rdbTargeting.setGender(genderStr);

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
		targeting.setSegmentClusterMatch(parser.getTrimmedElement("//Campaign/Target/Cluster/SegmentType"));
		String exculdeStr = parser.getTrimmedElement("//Campaign/Target/ExcludeSegmentTargeting");
		List<String> targetingValues = parser.getTrimmedElementList("//Campaign/Target/Cluster/Segment");
		targeting.setValues(targetingValues);
		targeting.setExclude(createBooleanFromXmlString(exculdeStr));
		return targeting;
	}
}
