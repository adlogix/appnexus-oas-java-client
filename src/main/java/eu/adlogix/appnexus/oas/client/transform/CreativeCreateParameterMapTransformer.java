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

		checkValueAndPutParam("campaignId", creative.getCampaignId(), parameters);
		checkValueAndPutParam("creativeId", creative.getId(), parameters);
		checkValueAndPutParam("creativeName", creative.getName(), parameters);
		checkValueAndPutParam("clickUrl", creative.getClickUrl(), parameters);
		checkValueAndPutParam("discountImpressions", creative.getDiscountImpressions(), parameters);
		checkValueAndPutParam("display", creative.getDisplay(), parameters);
		checkValueAndPutParam("noCache", creative.getNoCache(), parameters);
		checkValueAndPutParam("expireImmediately", creative.getExpireImmediately(), parameters);
		checkValueAndPutParam("description", creative.getDescription(), parameters);
		checkValueAndPutParam("positions", creative.getPositions(), parameters);
		checkValueAndPutParam("creativeTypeId", creative.getCreativeTypeId(), parameters);
		checkValueAndPutParam("height", creative.getHeight(), parameters);
		checkValueAndPutParam("width", creative.getWidth(), parameters);
		checkValueAndPutParam("richMediaCpm", creative.getRichMediaCpm(), parameters);
		checkValueAndPutParam("targetwindow", creative.getTargetWindow(), parameters);
		checkValueAndPutParam("altText", creative.getAltText(), parameters);
		checkValueAndPutParam("startDate", creative.getStartDate(), parameters);
		checkValueAndPutParam("startTime", creative.getStartTime(), parameters);
		checkValueAndPutParam("endDate", creative.getEndDate(), parameters);
		checkValueAndPutParam("endTime", creative.getEndTime(), parameters);
		checkValueAndPutParam("weight", creative.getWeight(), parameters);
		checkValueAndPutParam("extraHtml", creative.getExtraHtml(), parameters);
		checkValueAndPutParam("extraText", creative.getExtraText(), parameters);
		checkValueAndPutParam("browserV", creative.getBrowserV(), parameters);
		checkValueAndPutParam("sequenceNo", creative.getSequenceNo(), parameters);
		checkValueAndPutParam("countOnDownload", creative.getCountOnDownload(), parameters);

		if (creative.getCreativeFile() != null) {

		}

		return parameters;
	}

	// public String getEncodedFileString(InputStream file) {
	// InputStream inputStream;
	// try {
	// inputStream = mediaLibrary.getMediaLibraryStorage().getFile(media);
	// return FileUtils.toBase64String(inputStream);
	// } catch (Exception e) {
	// throw new
	// RuntimeException("Error while retrieving Media or encoding to Base64 for "
	// + media, e);
	// }
	// }

}
