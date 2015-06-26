package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Represents a Creative of OAS. Some attributes are mandatory
 */
@Data
public class Creative {

	/**
	 * Represents a File uploaded as a part of a {@link Creative} of OAS. All
	 * attributes are mandatory
	 */
	@Data
	@AllArgsConstructor
	public static class CreativeFile {
		/**
		 * File name
		 */
		private String name;
		/**
		 * Mime type of the creative file
		 */
		private String contentType;
		/**
		 * File content as Base64 encoded. The Base64 encoding uses the standard
		 * 76-character chunk split and a \n line termination.
		 */
		private String fileAsBase64String;
	}

	/**
	 * Unique ID. Mandatory
	 */
	private String id;
	/**
	 * ID of Campaign it belongs to. Mandatory
	 */
	private String campaignId;
	/**
	 * Name of Creative. Mandatory
	 */
	private String name;
	/**
	 * Click URL. Mandatory
	 */
	private String clickUrl;
	/**
	 * Name of Positions which this Creative is applicable to. Mandatory
	 */
	private List<String> positionNames;
	/**
	 * Display flag. Mandatory
	 */
	private Boolean display;
	/**
	 * Discount impressions. Mandatory
	 */
	private Boolean discountImpressions;
	/**
	 * Expire immediately. Mandatory
	 */
	private Boolean expireImmediately;
	/**
	 * If the creative won't be cached. Mandatory
	 */
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

	/**
	 * you may use a Redirect URL or upload the file and its component files. If
	 * you choose to upload files, the RedirectUrl element must not be present
	 */
	private CreativeFile creativeFile;

	/**
	 * you may use a Redirect URL or upload the file and its component files. If
	 * you choose to upload files, the RedirectUrl element must not be present
	 */
	private List<CreativeFile> componentFiles;

	/**
	 * you may use a Redirect URL or upload the file and its component files. If
	 * you choose to upload files, the RedirectUrl element must not be present
	 */
	private String redirectUrl;

}
