package eu.adlogix.appnexus.oas.client.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.ServiceFactory;

import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.domain.Credentials;
import eu.adlogix.appnexus.oas.client.exceptions.OasConnectionException;
import eu.adlogix.appnexus.oas.client.utils.log.LogUtils;


/**
 * Handles the low level OAS API related functions such as certificate
 * initialization and making XML requests.
 * 
 **/
public class OasApiService {

	private static final Logger logger = LogUtils.getLogger(OasApiService.class);
	private static final int MAX_RETRY = 10;

	private static String certificateInitialisedForHost = null;


	private String host;
	private String account;
	private String user;
	private String password;

	private CertificateManager certificateManager;

	/**
	 * Initializes the OasApiService with the provided {@link Credentials}. The
	 * certificate will be initialized for the provided host.
	 * 
	 * @param credentials
	 *            {@link Credentials} that should be used for accessing OAS API
	 * 
	 */
	public OasApiService(Credentials credentials) {
		this.host = credentials.getHost();
		this.account = credentials.getAccount();
		this.user = credentials.getUser();
		this.password = credentials.getPassword();

		this.certificateManager = new CertificateManager();

		initializeCertificate();
	}

	/**
	 * Generic Method to call any OAS API function. If the certificate has
	 * expired, the certificate will be automatically renewed before making the
	 * API call.
	 * 
	 * @param adXML
	 *            The structured XML request containing all the parameters
	 * @param retryOnConnectionErrors
	 *            boolean which specifies whether to retry the call if there are
	 *            any connection errors
	 * @return The AdXML response from the OAS API
	 * 
	 * @throws {@link OasConnectionException} if the OAS API cannot be reached
	 * 
	 */

	public String callApi(final String adXML, boolean retryOnConnectionErrors) throws MalformedURLException,
			ServiceException,
			RemoteException {

		renewCertificate();

		if (logger.isDebugEnabled())
			logger.debug("Call Oas Api as '" + user + "' with pass: '" + password + "', account: '" + account
					+ "' on '" + host + "'...");

		final HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(final String urlHostName, final SSLSession session) {
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		final String urlString = host + "/oasapi/";
		final URL url = new URL(urlString + "OaxApi?wsdl");
		final String nameSpace = "http://api.oas.tfsm.com/";
		final QName qname = new QName(nameSpace, "OaxApiService");
		final QName port = new QName(nameSpace, "OaxApiPort");
		final QName operation = new QName(nameSpace, "OasXmlRequest");

		final ServiceFactory factory = ServiceFactory.newInstance();
		final Service service = factory.createService(url, qname);
		final Call call = service.createCall(port, operation);

		int retryCount = 0;

		String res = null;

		while ((res == null) && (retryCount < MAX_RETRY)) {
			try {
				res = (String) call.invoke(new Object[] { account, user, password, adXML });
			} catch (Exception e) {
				if (retryOnConnectionErrors) {

					retryCount++;

					if (retryCount < MAX_RETRY) {
						logger.info("OAS API call retry #" + retryCount);
					} else {
						logger.error("We just exceeded the maximum number of OAS API call retries while calling API with request ["
								+ adXML + "]");
						throw new OasConnectionException("Exception while calling OAS API: [" + e.toString()
								+ "]. Aborted after retrying " + MAX_RETRY + " times");
					}
				} else {
					logger.error("Exception while calling OAS API: [" + e.toString() + "]", e);
					throw new OasConnectionException("Exception while calling OAS API: [" + e.toString() + "]");
				}
			}
		}

		return res;
	}

	/**
	 * Initializes the OAS Certificate.
	 */
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

	/**
	 * Renews the existing OAS certificate if it has expired.
	 */
	private synchronized void renewCertificate() {
		certificateManager.renewCertificateForHost(host);
	}

}
