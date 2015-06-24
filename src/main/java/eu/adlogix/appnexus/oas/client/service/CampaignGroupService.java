package eu.adlogix.appnexus.oas.client.service;

import java.util.HashMap;
import java.util.Map;

import eu.adlogix.appnexus.oas.client.domain.CampaignGroup;
import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

public class CampaignGroupService extends AbstractOasService {

	private static final Integer ERRORCODE_ID_ALREADY_EXISTS = 512;
	private final XmlRequestGenerator createCampaignGroupRequestGenerator = new XmlRequestGenerator("add-campaign-group");

	protected CampaignGroupService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Creates a Campaign Group with a particular ID
	 * 
	 * @param group
	 *            The campaignGroup to create on OAS
	 * @return <code>true</code> if the group was created, <code>false</code> if
	 *         the group already existed
	 */
	public final boolean createGroup(final CampaignGroup group) {
		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("groupId", group.getId());
			}
		};

		try {
			performRequest(createCampaignGroupRequestGenerator, parameters);
		} catch (OasServerSideException e) {
			if (ERRORCODE_ID_ALREADY_EXISTS.equals(e.getErrorCode())) {
				return false;
			} else {
				throw e;
			}
		}
		return true;
	}
}
