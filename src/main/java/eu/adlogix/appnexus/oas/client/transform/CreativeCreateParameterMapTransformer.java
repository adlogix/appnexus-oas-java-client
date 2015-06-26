package eu.adlogix.appnexus.oas.client.transform;

import java.util.Map;

import lombok.AllArgsConstructor;

import org.testng.collections.Maps;

import eu.adlogix.appnexus.oas.client.domain.Creative;

@AllArgsConstructor
public class CreativeCreateParameterMapTransformer extends AbstractParameterMapTransformer {

	private final Creative creative;

	@Override
	public Map<String, Object> transform() {
		final Map<String, Object> parameters = Maps.newHashMap();

		checkValueAndPutParam("campaignId", creative.getCampaignId(), parameters, true);
		checkValueAndPutParam("creativeId", creative.getId(), parameters, true);
		checkValueAndPutParam("creativeName", creative.getName(), parameters, true);
		checkValueAndPutParam("clickUrl", creative.getClickUrl(), parameters, true);
		checkValueAndPutParam("discountImpressions", creative.getDiscountImpressions(), parameters, true);
		checkValueAndPutParam("display", creative.getDisplay(), parameters, true);
		checkValueAndPutParam("noCache", creative.getNoCache(), parameters, true);
		checkValueAndPutParam("expireImmediately", creative.getExpireImmediately(), parameters, true);
		checkValueAndPutParam("description", creative.getDescription(), parameters, false);
		checkValueAndPutParam("positions", creative.getPositions(), parameters, false);
		checkValueAndPutParam("creativeTypeId", creative.getCreativeTypeId(), parameters, false);
		checkValueAndPutParam("height", creative.getHeight(), parameters, false);
		checkValueAndPutParam("width", creative.getWidth(), parameters, false);
		checkValueAndPutParam("richMediaCpm", creative.getRichMediaCpm(), parameters, false);
		checkValueAndPutParam("targetwindow", creative.getTargetWindow(), parameters, false);
		checkValueAndPutParam("altText", creative.getAltText(), parameters, false);
		checkValueAndPutParam("startDate", creative.getStartDate(), parameters, false);
		checkValueAndPutParam("startTime", creative.getStartTime(), parameters, false);
		checkValueAndPutParam("endDate", creative.getEndDate(), parameters, false);
		checkValueAndPutParam("endTime", creative.getEndTime(), parameters, false);
		checkValueAndPutParam("weight", creative.getWeight(), parameters, false);
		checkValueAndPutParam("extraHtml", creative.getExtraHtml(), parameters, false);
		checkValueAndPutParam("extraText", creative.getExtraText(), parameters, false);
		checkValueAndPutParam("browserV", creative.getBrowserV(), parameters, false);
		checkValueAndPutParam("sequenceNo", creative.getSequenceNo(), parameters, false);
		checkValueAndPutParam("countOnDownload", creative.getCountOnDownload(), parameters, false);

		return parameters;
	}

}
