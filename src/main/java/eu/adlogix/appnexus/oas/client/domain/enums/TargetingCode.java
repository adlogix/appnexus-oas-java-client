package eu.adlogix.appnexus.oas.client.domain.enums;


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
	TOP_DOMAIN("TopDomain", "TopLevelDomain", TargetGroup.GENERAL),
	BANDWIDTH("Bandwidth", TargetGroup.GENERAL),
	CONTINENT("Continent", TargetGroup.GENERAL),
	COUNTRY("Country", TargetGroup.GENERAL),
	STATE("State", TargetGroup.GENERAL),
	MSA("Msa", TargetGroup.GENERAL),
	DMA("Dma", TargetGroup.GENERAL),
	OS("Os", TargetGroup.GENERAL),
	BROWSER("Browser", TargetGroup.GENERAL),
	BROWSER_VERSIONS("BrowserV", TargetGroup.GENERAL),
	ZONE("Zone", "Zones", TargetGroup.ZONE, false, SupportedFetchDatabaseAction.getDefault()),
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

	private TargetingCode(String code, final TargetGroup group) {
		this(code, code, group, true, SupportedFetchDatabaseAction.getDefault());
	}

	private TargetingCode(final String code, final TargetGroup group,
			final boolean isSupportingExcludeFlagForCampaigns,
			final SupportedFetchDatabaseAction databaseAction) {
		this(code, code, group, isSupportingExcludeFlagForCampaigns, databaseAction);
	}

	private TargetingCode(String code, String readingCampaignCode, final TargetGroup group) {
		this(code, readingCampaignCode, group, true, SupportedFetchDatabaseAction.getDefault());
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
