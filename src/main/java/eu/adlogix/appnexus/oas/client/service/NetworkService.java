package eu.adlogix.appnexus.oas.client.service;

import java.util.Properties;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;

public class NetworkService extends AbstractXaxisService {

	protected NetworkService(Properties credentials) {
		super(credentials);
	}

	public NetworkService(Properties credentials, XaxisApiService apiService, CertificateManager certificateManager) {
		super(credentials, apiService, certificateManager);
		// TODO Auto-generated constructor stub
	}

}
