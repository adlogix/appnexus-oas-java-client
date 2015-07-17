package eu.adlogix.appnexus.oas.client.transform;

import java.util.Map;

import org.testng.annotations.Test;
import org.testng.collections.Lists;

import eu.adlogix.appnexus.oas.client.domain.Creative;
import eu.adlogix.appnexus.oas.client.domain.Creative.CreativeFile;

import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.DATE_FORMATTER;
import static eu.adlogix.appnexus.oas.client.utils.ParserUtil.TIME_FORMATTER;

import static org.testng.Assert.assertEquals;

public class CreativeCreateParameterMapTransformerTest {

	@Test
	public void transform_CreativeFilesWithComponent_ParametersExist() {
		Creative creative = new Creative();
		creative.setId("MyCreativeId_102");
		creative.setCampaignId("TestCampaignId");
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

		creative.setStartDate(DATE_FORMATTER.parseLocalDate("2005-12-01"));
		creative.setStartTime(TIME_FORMATTER.parseLocalTime("04:00"));
		creative.setEndDate(DATE_FORMATTER.parseLocalDate("2006-01-31"));
		creative.setEndTime(TIME_FORMATTER.parseLocalTime("15:59"));

		creative.setWeight(10);
		creative.setExpireImmediately(false);
		creative.setNoCache(false);

		creative.setExtraHtml("extra html");
		creative.setExtraText("extra text");

		creative.setBrowserV(Lists.newArrayList("explorer6", "netscape7"));
		creative.setSequenceNo(4);
		creative.setCountOnDownload(true);

		creative.setCreativeFile(new CreativeFile("hi.html", "text/html", "2AojmQpIgeVGBIqEgNlBBL8xtNs2jld8hIdwFB4CQxIog/4IQSe0CUOxBQyPVXpTaYNZWvTZtgaNpnP6LR6zW6735gIADs=+PC9hPgo="));

		creative.setComponentFiles(Lists.newArrayList(new CreativeFile("hi2.gif", "image/gif", "2AojmQpIgeVGBIqEgNlBBL8xtNs2jld8hIdwFB4CQxIog/4IQSe0CUOxBQyPVXpTaYNZWvTZtgaNpnP6LR6zW6735gIADs=+PC9hPgo="), new CreativeFile("hi1.gif", "image/gif", "2AojmQpIgeVGBIqEgNlBBL8xtNs2jld8hIdwFB4CQxIog/4IQSe0CUOxBQyPVXpTaYNZWvTZtgaNpnP6LR6zW6735gIADs=+PC9hPgo=")));

		CreativeCreateParameterMapTransformer transformer = new CreativeCreateParameterMapTransformer(creative);
		Map<String, Object> map = transformer.transform();

		assertEquals(map.get("campaignId"), "TestCampaignId");
		assertEquals(map.get("creativeId"), "MyCreativeId_102");
		assertEquals(map.get("creativeName"), "Api Creative");
		assertEquals(map.get("description"), "This is a creative");
		assertEquals(map.get("clickUrl"), "http://google.com");
		assertEquals(map.get("positions"), Lists.newArrayList("TopLeft", "BottomLeft"));
		assertEquals(map.get("creativeTypeId"), "unknown_type");
		assertEquals(map.get("display"), "Y");
		assertEquals(map.get("height"), 30);
		assertEquals(map.get("width"), 30);
		assertEquals(map.get("richMediaCpm"), 30);
		assertEquals(map.get("targetwindow"), "_self");
		assertEquals(map.get("altText"), "alt text value");
		assertEquals(map.get("discountImpressions"), "N");

		assertEquals(map.get("startDate"), "2005-12-01");
		assertEquals(map.get("startTime"), "04:00");
		assertEquals(map.get("endDate"), "2006-01-31");
		assertEquals(map.get("endTime"), "15:59");

		assertEquals(map.get("weight"), 10);
		assertEquals(map.get("expireImmediately"), "N");
		assertEquals(map.get("noCache"), "N");

		assertEquals(map.get("extraHtml"), "extra html");
		assertEquals(map.get("extraText"), "extra text");

		assertEquals(map.get("browserV"), Lists.newArrayList("explorer6", "netscape7"));
		assertEquals(map.get("sequenceNo"), 4);
		assertEquals(map.get("countOnDownload"), "Y");

		assertEquals(map.get("creativeFileName"), "hi.html");
		assertEquals(map.get("creativeFileContentType"), "text/html");
		assertEquals(map.get("creativeFile"), "2AojmQpIgeVGBIqEgNlBBL8xtNs2jld8hIdwFB4CQxIog/4IQSe0CUOxBQyPVXpTaYNZWvTZtgaNpnP6LR6zW6735gIADs=+PC9hPgo=");

		assertEquals(map.get("componentFileNames"), Lists.newArrayList("hi2.gif", "hi1.gif"));
		assertEquals(map.get("componentFileContentTypes"), Lists.newArrayList("image/gif", "image/gif"));
		assertEquals(map.get("componentFiles"), Lists.newArrayList("2AojmQpIgeVGBIqEgNlBBL8xtNs2jld8hIdwFB4CQxIog/4IQSe0CUOxBQyPVXpTaYNZWvTZtgaNpnP6LR6zW6735gIADs=+PC9hPgo=", "2AojmQpIgeVGBIqEgNlBBL8xtNs2jld8hIdwFB4CQxIog/4IQSe0CUOxBQyPVXpTaYNZWvTZtgaNpnP6LR6zW6735gIADs=+PC9hPgo="));
	}

	@Test
	public void transform_RedirectUrl_ParametersExist() {
		Creative creative = new Creative();
		creative.setId("MyCreativeId_102");
		creative.setCampaignId("TestCampaignId");
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

		creative.setStartDate(DATE_FORMATTER.parseLocalDate("2005-12-01"));
		creative.setStartTime(TIME_FORMATTER.parseLocalTime("04:00"));
		creative.setEndDate(DATE_FORMATTER.parseLocalDate("2006-01-31"));
		creative.setEndTime(TIME_FORMATTER.parseLocalTime("15:59"));

		creative.setWeight(10);
		creative.setExpireImmediately(false);
		creative.setNoCache(false);

		creative.setExtraHtml("extra html");
		creative.setExtraText("extra text");

		creative.setBrowserV(Lists.newArrayList("explorer6", "netscape7"));
		creative.setSequenceNo(4);
		creative.setCountOnDownload(true);

		creative.setRedirectUrl("http://google.com");

		CreativeCreateParameterMapTransformer transformer = new CreativeCreateParameterMapTransformer(creative);
		Map<String, Object> map = transformer.transform();

		assertEquals(map.get("campaignId"), "TestCampaignId");
		assertEquals(map.get("creativeId"), "MyCreativeId_102");
		assertEquals(map.get("creativeName"), "Api Creative");
		assertEquals(map.get("description"), "This is a creative");
		assertEquals(map.get("clickUrl"), "http://google.com");
		assertEquals(map.get("positions"), Lists.newArrayList("TopLeft", "BottomLeft"));
		assertEquals(map.get("creativeTypeId"), "unknown_type");
		assertEquals(map.get("display"), "Y");
		assertEquals(map.get("height"), 30);
		assertEquals(map.get("width"), 30);
		assertEquals(map.get("richMediaCpm"), 30);
		assertEquals(map.get("targetwindow"), "_self");
		assertEquals(map.get("altText"), "alt text value");
		assertEquals(map.get("discountImpressions"), "N");

		assertEquals(map.get("startDate"), "2005-12-01");
		assertEquals(map.get("startTime"), "04:00");
		assertEquals(map.get("endDate"), "2006-01-31");
		assertEquals(map.get("endTime"), "15:59");

		assertEquals(map.get("weight"), 10);
		assertEquals(map.get("expireImmediately"), "N");
		assertEquals(map.get("noCache"), "N");

		assertEquals(map.get("extraHtml"), "extra html");
		assertEquals(map.get("extraText"), "extra text");

		assertEquals(map.get("browserV"), Lists.newArrayList("explorer6", "netscape7"));
		assertEquals(map.get("sequenceNo"), 4);
		assertEquals(map.get("countOnDownload"), "Y");

		assertEquals(map.get("redirectUrl"), "http://google.com");
	}
}
