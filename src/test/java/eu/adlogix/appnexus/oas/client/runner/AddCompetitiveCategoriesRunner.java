package eu.adlogix.appnexus.oas.client.runner;

import eu.adlogix.appnexus.oas.client.domain.CompetitiveCategory;
import eu.adlogix.appnexus.oas.client.service.CompetitiveCategoryService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class AddCompetitiveCategoriesRunner {
	public static void main(String[] args) {
		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		CompetitiveCategoryService service = factory.getCompetitiveCategoryService();

		service.add(new CompetitiveCategory("testCompetitiveCategory00"));
	}
}
