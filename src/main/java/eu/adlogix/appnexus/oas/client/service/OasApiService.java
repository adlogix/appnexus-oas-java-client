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

import lombok.AllArgsConstructor;

import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.utils.log.LogUtils;

@AllArgsConstructor
public class OasApiService {

	private static final Logger logger = LogUtils.getLogger(OasApiService.class);
	private static final int MAX_RETRY = 10;

	String host;
	String account;
	String user;
	String password;

	/**
	 * This generic method may be reused to call any Oas API function
	 * 
	 * @param oasHost
	 *            The host on which OAS resides
	 * @param oasAccount
	 *            The OAS Account to which you have access
	 * @param oasUser
	 *            The OAS User name given to you
	 * @param oasPassword
	 *            The OAS Password for your user
	 * @param adXML
	 *            The structured XML request containing all the parameters
	 * @return The AdXML response from the OAS API
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */

	public String callApi(final String adXML, boolean retryOnConnectionErrors) throws MalformedURLException,
			ServiceException,
			RemoteException {

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
					logger.error("Exception while calling OAS API: [" + e.toString() + "]", e);

					retryCount++;

					if (retryCount < MAX_RETRY) {
						logger.info("OAS API call retry #" + retryCount);
					} else {
						logger.error("We just exceeded the maximal number of OAS API calls retries while querying ["
								+ adXML + "]");
						throw new RuntimeException("Too many OAS API calls retries", e);
					}
				} else {
					// we dont retry on errors
					throw new RuntimeException("Not retrying OAS API call", e);
				}
			}
		}

		return res;
	}

}
