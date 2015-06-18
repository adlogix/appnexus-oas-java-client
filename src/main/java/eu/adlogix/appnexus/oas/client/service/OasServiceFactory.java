package eu.adlogix.appnexus.oas.client.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.domain.Credentials;
import eu.adlogix.appnexus.oas.client.utils.log.LogUtils;

/**
 * @author piumika
 * 
 */
public class OasServiceFactory {
	
	private static String certificateInitialisedForHost = null;

	private final String host;
	private final String account;
	private final String user;
	private final String password;

	private static final Logger logger = LogUtils.getLogger(AbstractOasService.class);

	private OasApiService oasApiService;

	private CertificateManager certificateManager;

	private AdvertiserService advertiserService;

	private NetworkService networkService;

	private ReportService reportService;


	public OasServiceFactory(final Credentials credentials) {

		this.host = credentials.getHost();
		if (StringUtils.isEmpty(host)) {
			throw new IllegalStateException("The Hostname of the Oas webservice is missing from the Properties");
		}

		this.user = credentials.getUser();
		if (StringUtils.isEmpty(user)) {
			throw new IllegalStateException("The Username to access of the Oas webservice is missing from the Properties");
		}

		this.password = credentials.getPassword();
		if (StringUtils.isEmpty(password)) {
			throw new IllegalStateException("The password to access of the Oas webservice is missing from the Properties");
		}

		this.account = credentials.getAccount();
		if (StringUtils.isEmpty(account)) {
			throw new IllegalStateException("The account to access of the Oas webservice is missing from the Properties");
		}

		this.oasApiService = new OasApiService(host, account, user, password);

		this.certificateManager = new CertificateManager();

		initializeCertificate();

	}

	private synchronized void initializeCertificate() {

		if (certificateInitialisedForHost == null) {
			certificateManager.prepareKeyStoreForHost(this.host);
			certificateInitialisedForHost = host;
		} else {
			if (!certificateInitialisedForHost.equals(host)) {
				throw new RuntimeException("Can not initialize certificate for host" + host
						+ " as it was already initialized for " + certificateInitialisedForHost);
			} else {
				renewCertificate();
			}
		}
	}

	private synchronized void renewCertificate() {
		certificateManager.renewCertificateForHost(host);
	}

	public AdvertiserService getAdvertiserService() {
		if (advertiserService == null) {
			advertiserService = new AdvertiserService(oasApiService);
		}
		return advertiserService;
	}

	public NetworkService getNetworkService(){
		if (networkService == null) {
			networkService = new NetworkService(oasApiService);
		}
		return networkService;
	}

	public ReportService getReportService() {
		if (reportService == null) {
			reportService = new ReportService(oasApiService);
		}
		return reportService;
	}

}
