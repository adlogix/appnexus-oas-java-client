package eu.adlogix.appnexus.oas.client.domain;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.google.common.collect.Lists;

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
	ZONE("Zone", TargetGroup.ZONE, false),
	DEVICE_GROUP("DeviceGroup", TargetGroup.MOBILE, false, SupportedFetchDatabaseAction.SELECTEDLIST);

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

	private final TargetGroup group;

	private final boolean isSupportingExcludeFlagForCampaigns;

	private final SupportedFetchDatabaseAction databaseAction;

	private TargetingCode(String code) {
		this(code, code, TargetGroup.getDefault(), true, SupportedFetchDatabaseAction.getDefault());
	}

	private TargetingCode(final String code, final TargetGroup group,
			final boolean isSupportingExcludeFlagForCampaigns,
			final SupportedFetchDatabaseAction databaseAction) {
		this(code, code, group, isSupportingExcludeFlagForCampaigns, databaseAction);
	}

	private TargetingCode(String code, String readingCampaignCode) {
		this(code, readingCampaignCode, TargetGroup.getDefault(), true, SupportedFetchDatabaseAction.getDefault());
	}

	private TargetingCode(final String code, final TargetGroup group, final boolean isSupportingExcludeFlagForCampaigns) {
		this(code, code, group, isSupportingExcludeFlagForCampaigns, SupportedFetchDatabaseAction.getDefault());
	}

	@Override
	public String toString() {
		return code;
	}

	public static List<TargetingCode> getCodesForGroup(final TargetGroup group) {
		List<TargetingCode> codes = Lists.newArrayList();
		for (TargetingCode targetingCode : values()) {
			if (targetingCode.group == group) {
				codes.add(targetingCode);
			}
		}
		return codes;
	}
}
