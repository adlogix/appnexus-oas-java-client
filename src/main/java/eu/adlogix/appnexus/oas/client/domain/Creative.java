package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

@Data
public class Creative {

	@Data
	@AllArgsConstructor
	public static class CreativeFile {
		private String name;
		private String contentType;
		private String fileAsBase64String;
		// private FileType fileType;
	}

	public enum FileType {
		CREATIVE, COMPONENT;
	}

	private String id;
	private String campaignId;
	private String name;
	private String clickUrl;
	private List<String> positions;
	private Boolean display;
	private Boolean discountImpressions;
	private Boolean expireImmediately;
	private Boolean noCache;

	private String description;
	private String creativeTypeId;
	private Integer height;
	private Integer width;
	private Integer richMediaCpm;
	private String targetWindow;
	private String altText;
	private LocalDate startDate;
	private LocalTime startTime;
	private LocalDate endDate;
	private LocalTime endTime;
	private Integer weight;
	private String extraHtml;
	private String extraText;
	private List<String> browserV;
	private Integer sequenceNo;
	private Boolean countOnDownload;

	private CreativeFile creativeFile;

	private List<CreativeFile> componentFiles;

	private String redirectUrl;

}
