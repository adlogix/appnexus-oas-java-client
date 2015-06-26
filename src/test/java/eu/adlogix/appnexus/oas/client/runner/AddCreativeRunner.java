package eu.adlogix.appnexus.oas.client.runner;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.collections.Lists;

import eu.adlogix.appnexus.oas.client.domain.Creative;
import eu.adlogix.appnexus.oas.client.service.CreativeService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class AddCreativeRunner {

	protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
	protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm");

	public static void main(String[] args) {
		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		CreativeService service = factory.getCreativeService();

		Creative creative = new Creative();
		creative.setId("GuniCreative_001");
		creative.setCampaignId("test_campaign_055");
		creative.setName("Api Creative");
		creative.setDescription("This is a creative");
		creative.setClickUrl("http://google.com");
		creative.setPositionNames(Lists.newArrayList("TopLeft", "BottomLeft"));
		creative.setCreativeTypeId("unknown_type");
		creative.setDisplay(true);
		creative.setHeight(30);
		creative.setWidth(30);
		creative.setRichMediaCpm(30);
		creative.setTargetWindow("_self");
		creative.setAltText("alt text value");
		creative.setDiscountImpressions(false);

		creative.setStartDate(DATE_FORMATTER.parseLocalDate("2015-12-01"));
		creative.setStartTime(TIME_FORMATTER.parseLocalTime("04:00"));
		creative.setEndDate(DATE_FORMATTER.parseLocalDate("2016-01-31"));
		creative.setEndTime(TIME_FORMATTER.parseLocalTime("15:59"));

		creative.setWeight(10);
		creative.setExpireImmediately(false);
		creative.setNoCache(false);

		creative.setExtraHtml("extra html");
		creative.setExtraText("extra text");

		creative.setBrowserV(Lists.newArrayList("explorer6", "netscape7"));
		creative.setSequenceNo(4);
		creative.setCountOnDownload(true);

		creative.setRedirectUrl("http://x.y.z/image.gif");

		service.createCreative(creative);
	}
}
