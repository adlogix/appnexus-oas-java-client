package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A type of targeting which can be assigned to a Campaign upon
 * creation/updating. The code String is accessible via {@link #toString()} or
 * via {@link #getCode()}
 */
@AllArgsConstructor
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
	ZONE("Zone", false),
	DEVICE_GROUP("DeviceGroup", false, SupportedFetchDatabaseAction.SELECTEDLIST);

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

	private final String codeForCampaigns;

	private final boolean isSupportingExcludeFlagForCampaigns;

	private final SupportedFetchDatabaseAction databaseAction;

	private TargetingCode(String code) {
		this(code, code, true, SupportedFetchDatabaseAction.getDefault());
	}

	private TargetingCode(final String code, final boolean isSupportingExcludeFlagForCampaigns,
			final SupportedFetchDatabaseAction databaseAction) {
		this(code, code, isSupportingExcludeFlagForCampaigns, databaseAction);
	}

	private TargetingCode(String code, String readingCampaignCode) {
		this(code, readingCampaignCode, true, SupportedFetchDatabaseAction.getDefault());
	}

	private TargetingCode(final String code, final boolean isSupportingExcludeFlagForCampaigns) {
		this(code, code, isSupportingExcludeFlagForCampaigns, SupportedFetchDatabaseAction.getDefault());
	}

	@Override
	public String toString() {
		return code;
	}
}
