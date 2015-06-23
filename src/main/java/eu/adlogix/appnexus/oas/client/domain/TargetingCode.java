package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;

/**
 * A type of targeting which can be assigned to a Campaign upon
 * creation/updating. The code String is accessible via {@link #toString()} or
 * via {@link #getCode()}
 */
@Getter
public enum TargetingCode {
	TOP_DOMAIN("TopDomain", "TopLevelDomain"),
	BANDWIDTH("Bandwidth"),
	CONTINENT("Continent"),
	COUNTRY("Country"),
	STATE("State"),
	MSA("Msa"),
	DMA("Dma"),
	OS("Os"),
	BROWSER("Browser"),
	BROWSER_VERSIONS("BrowserV"),
	ZONE("Zone"),
	DEVICE_GROUP("DeviceGroup", SupportedFetchDatabaseAction.SELECTEDLIST);

	public enum SupportedFetchDatabaseAction {
		LIST, SELECTEDLIST;

		public static SupportedFetchDatabaseAction getDefault() {
			return LIST;
		}

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}

	}

	private final String code;

	private final String readingCampaignCode;

	private final SupportedFetchDatabaseAction databaseAction;

	private TargetingCode(String code) {
		this.code = code;
		this.readingCampaignCode = code;
		this.databaseAction = SupportedFetchDatabaseAction.getDefault();
	}

	private TargetingCode(String code, SupportedFetchDatabaseAction databaseAction) {
		this.code = code;
		this.readingCampaignCode = code;
		this.databaseAction = databaseAction;
	}

	private TargetingCode(String code, String readingCampaignCode) {
		this.code = code;
		this.readingCampaignCode = readingCampaignCode;
		this.databaseAction = SupportedFetchDatabaseAction.getDefault();
	}

	@Override
	public String toString() {
		return code;
	}
}
