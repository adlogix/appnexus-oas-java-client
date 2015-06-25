package eu.adlogix.appnexus.oas.client.runner;

import java.util.List;

import eu.adlogix.appnexus.oas.client.domain.CompanionPosition;
import eu.adlogix.appnexus.oas.client.service.NetworkService;
import eu.adlogix.appnexus.oas.client.service.OasServiceFactory;
import eu.adlogix.appnexus.oas.client.util.TestCredentials;

public class GetAllCompanionPositionsRunner {
	public static void main(String[] args) {
		OasServiceFactory factory = new OasServiceFactory(TestCredentials.getCredentialsFromExternalFile());
		NetworkService service = factory.getNetworkService();

		List<CompanionPosition> positions = service.getAllCompanionsPositions();
		System.out.println(positions);
	}
}
