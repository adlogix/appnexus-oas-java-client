package eu.adlogix.appnexus.oas.client.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Campaign group which a Campaign would belong to
 */
@Data
@NoArgsConstructor
public class CampaignGroup {
	/**
	 * Unique ID of Campaign Group
	 */
	private String id;
	private String description;
	private String notes;
	private List<String> externalUserIds;
	private String internalQuickReport;
	private String externalQuickReport;

	public CampaignGroup(String id) {
		this.id = id;
	}

}
