package eu.adlogix.appnexus.oas.client.runner;

import java.util.List;

import eu.adlogix.appnexus.oas.client.domain.CompetitiveCategory;
import eu.adlogix.appnexus.oas.client.service.CompetitiveCategoryService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class GetCompetitiveCategoriesRunner {
	public static void main(String[] args) {
		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		CompetitiveCategoryService service = factory.getCompetitiveCategoryService();

		List<CompetitiveCategory> list = service.getAllCompetitiveCategories();
		System.out.println(list);
	}
}
