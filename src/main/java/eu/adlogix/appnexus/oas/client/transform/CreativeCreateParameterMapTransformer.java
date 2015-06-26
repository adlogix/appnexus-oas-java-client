package eu.adlogix.appnexus.oas.client.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import eu.adlogix.appnexus.oas.client.domain.Creative;
import eu.adlogix.appnexus.oas.client.domain.Creative.CreativeFile;

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
		checkValueAndPutParam("positions", creative.getPositionNames(), parameters);
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

		if (isCreativeFileUpload()) {
			addCreativeFileToParameters(parameters);
			addComponentFilesToParameters(parameters);

		} else if (StringUtils.isNotEmpty(creative.getRedirectUrl())) {
			checkValueAndPutParam("redirectUrl", creative.getRedirectUrl(), parameters);
		}

		return parameters;
	}

	private void addCreativeFileToParameters(final Map<String, Object> parameters) {
		final CreativeFile creativeFile = creative.getCreativeFile();
		if (creativeFile != null) {
			checkValueAndPutParam("creativeFileName", creativeFile.getName(), parameters);
			checkValueAndPutParam("creativeFileContentType", creativeFile.getContentType(), parameters);
			checkValueAndPutParam("creativeFile", creativeFile.getFileAsBase64String(), parameters);
		}
	}

	private void addComponentFilesToParameters(final Map<String, Object> parameters) {
		if (CollectionUtils.isNotEmpty(creative.getComponentFiles())) {

			final int size = creative.getComponentFiles().size();
			final List<String> componentFileContentTypes = new ArrayList<String>(size);
			final List<String> componentFileNames = new ArrayList<String>(size);
			final List<String> componentFiles = new ArrayList<String>(size);

			for (final CreativeFile componentFile : creative.getComponentFiles()) {
				componentFileContentTypes.add(componentFile.getContentType());
				componentFileNames.add(componentFile.getName());
				componentFiles.add(componentFile.getFileAsBase64String());
			}

			checkValueAndPutParam("componentFileContentTypes", componentFileContentTypes, parameters);
			checkValueAndPutParam("componentFileNames", componentFileNames, parameters);
			checkValueAndPutParam("componentFiles", componentFiles, parameters);
		}
	}

	private boolean isCreativeFileUpload() {
		return creative.getCreativeFile() != null || CollectionUtils.isNotEmpty(creative.getComponentFiles());
	}

}
