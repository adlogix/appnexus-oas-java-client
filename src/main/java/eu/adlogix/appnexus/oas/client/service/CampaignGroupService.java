package eu.adlogix.appnexus.oas.client.service;

import java.util.HashMap;
import java.util.Map;

import eu.adlogix.appnexus.oas.client.domain.CampaignGroup;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotNull;

public class CampaignGroupService extends AbstractOasService {

	private final XmlRequestGenerator createCampaignGroupRequestGenerator = new XmlRequestGenerator("add-campaign-group");

	protected CampaignGroupService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Creates a Campaign Group if it doesn't exist
	 * 
	 * @param group
	 *            The campaignGroup to create on OAS
	 */
	public final void createGroup(final CampaignGroup group) {

		checkNotNull(group, "group");
		checkNotEmpty(group.getId(), "group ID");

		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("groupId", group.getId());
			}
		};

		performRequest(createCampaignGroupRequestGenerator, parameters);
	}
}
