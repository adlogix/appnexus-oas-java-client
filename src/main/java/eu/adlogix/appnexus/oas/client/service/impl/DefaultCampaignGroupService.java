package eu.adlogix.appnexus.oas.client.service.impl;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

import java.util.HashMap;
import java.util.Map;

import eu.adlogix.appnexus.oas.client.domain.CampaignGroup;
import eu.adlogix.appnexus.oas.client.service.AbstractOasService;
import eu.adlogix.appnexus.oas.client.service.CampaignGroupService;
import eu.adlogix.appnexus.oas.client.service.OasApiService;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Default implementation of {@link CampaignGroupService} which provides
 * functions for OAS {@link CampaignGroup} related activities
 */
public class DefaultCampaignGroupService extends AbstractOasService implements CampaignGroupService {

	private final XmlRequestGenerator createCampaignGroupRequestGenerator = new XmlRequestGenerator("add-campaign-group");

	public DefaultCampaignGroupService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Creates a Campaign Group if it doesn't exist
	 * 
	 * @param group
	 *            The campaignGroup to create on OAS
	 */
	public final void add(final CampaignGroup group) {

		checkNotNull(group, "group");
		checkNotEmpty(group.getId(), "group ID");

		final CampaignGroup campaignGroupWithDefaults = setDefaultsForEmptyFields(group);
		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("groupId", campaignGroupWithDefaults.getId());
				put("groupDescription", campaignGroupWithDefaults.getDescription());
				put("notes", campaignGroupWithDefaults.getNotes());
				put("externalUserIds", campaignGroupWithDefaults.getExternalUserIds());
				put("internalQuickReport", campaignGroupWithDefaults.getInternalQuickReport());
				put("externalQuickReport", campaignGroupWithDefaults.getExternalQuickReport());
			}
		};

		performRequest(createCampaignGroupRequestGenerator, parameters);
	}

	/**
	 * Sets default values for the empty fields of the {@link CampaignGroup}
	 * object
	 * 
	 * @param campaignGroup
	 * @return {@link CampaignGroup} object with default values
	 */
	private CampaignGroup setDefaultsForEmptyFields(CampaignGroup campaignGroup) {
		campaignGroup.setInternalQuickReport(defaultIfEmpty(campaignGroup.getInternalQuickReport(), "to-date"));
		campaignGroup.setExternalQuickReport(defaultIfEmpty(campaignGroup.getExternalQuickReport(), "short"));
		return campaignGroup;
	}
}
