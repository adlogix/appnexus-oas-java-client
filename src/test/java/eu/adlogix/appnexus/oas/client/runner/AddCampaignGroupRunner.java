package eu.adlogix.appnexus.oas.client.runner;

import java.util.Arrays;

import eu.adlogix.appnexus.oas.client.domain.CampaignGroup;
import eu.adlogix.appnexus.oas.client.service.CampaignGroupService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class AddCampaignGroupRunner {

	public static void main(String[] args) {

		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());

		CampaignGroupService campaignGroupService = factory.getCampaignGroupService();
		CampaignGroup group = new CampaignGroup();
		group.setId("testCampaignGrpGuni10");
		group.setDescription("test");
		group.setNotes("test note");
		group.setExternalUserIds(Arrays.asList(new String[] { "AAExt", "alaExt" }));
		campaignGroupService.createGroup(group);
	}
}
