package eu.adlogix.appnexus.oas.client.service;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.domain.PushLevel;
import eu.adlogix.appnexus.oas.client.util.Credentials;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseObjectHandler;
import eu.adlogix.utils.log.LogUtils;


public class AbstractXaxisService {

	private static String certificateInitialisedForHost = null;

	private final String host;
	private final String account;
	private final String user;
	private final String password;
	private final PushLevel pushLevel;

	private static final Logger logger = LogUtils.getLogger(AbstractXaxisService.class);
	private XaxisApiService xaxisApiService;
	private CertificateManager certificateManager;


	protected AbstractXaxisService(final Properties credentials, XaxisApiService apiService,
			CertificateManager certificateManager) {

		this.host = Credentials.getHost(credentials);
		if (StringUtils.isEmpty(host)) {
			throw new IllegalStateException("The Hostname of the Xaxis webservice is missing from the Properties");
		}
		
		this.user = Credentials.getUser(credentials);
		if (StringUtils.isEmpty(user)) {
			throw new IllegalStateException("The Username to access of the Xaxis webservice is missing from the Properties");
		}
		
		this.password = Credentials.getPassword(credentials);
		if (StringUtils.isEmpty(password)) {
			throw new IllegalStateException("The password to access of the Xaxis webservice is missing from the Properties");
		}

		this.account = Credentials.getAccount(credentials);
		if (StringUtils.isEmpty(account)) {
			throw new IllegalStateException("The account to access of the Xaxis webservice is missing from the Properties");
		}

		this.pushLevel = Credentials.getPushLevel(credentials);

		if (apiService == null) {
			apiService = new XaxisApiService(host, account, user, password);
		}
		this.xaxisApiService = apiService;

		if (certificateManager == null) {
			certificateManager = new CertificateManager();
		}

		this.certificateManager = certificateManager;

		initializeCertificate();

	}

	protected AbstractXaxisService(final Properties credentials) {
		this(credentials, null, null);
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
				certificateManager.renewCertificateForHost(host);
			}
		}
	}

	public String performRequest(final String xmlRequest) {
		return performRequest(xmlRequest, false);
	}

	public String performRequest(final String xmlRequest, final boolean retryOnConnectionErrors) {
		try {
			logger.info("Making Request:\n" + xmlRequest);
			final String xmlResponse = xaxisApiService.callApi(xmlRequest, retryOnConnectionErrors);
			logger.info("Recieved Response:\n" + xmlResponse);
			return xmlResponse;
		} catch (final Exception exception) {
			throw new RuntimeException("Failed request: \n----- START -----\n" + xmlRequest + "\n----- END -----\n", exception);
		}
	}

	public final void performPagedRequest(final XmlRequestGenerator requestGenerator,
			final Map<String, Object> requestParams, final String sizeHeaderTag, final String xPathLoopExpression,
			final ResponseElementHandler responseElementHandler) {
		final String xmlRequestOne = requestGenerator.generateRequestWithPageIndex(1, requestParams);

		logger.info("Paged request, page #1 /? ...");
		final String xmlResponseOne = performRequest(xmlRequestOne, true);

		final int maxPageIndex = ResponseParser.parseMaxPageIndex(xmlResponseOne, sizeHeaderTag);

		ResponseParser parser = new ResponseParser(xmlResponseOne);

		parser.forEachElement(xPathLoopExpression, responseElementHandler);

		for (int pageIndex = 2; pageIndex <= maxPageIndex; pageIndex++) {
			final String xmlRequestN = requestGenerator.generateRequestWithPageIndex(pageIndex, requestParams);
			logger.info("Paged request, page #" + pageIndex + " /" + maxPageIndex + " ...");
			final String xmlResponseN = performRequest(xmlRequestN, true);
			parser = new ResponseParser(xmlResponseN);
			parser.forEachElement(xPathLoopExpression, responseElementHandler);
		}
	}
	
	public final <T> void performPagedRequest(final XmlRequestGenerator requestGenerator,
			final Map<String, Object> requestParams, final String sizeHeaderTag, final String xPathLoopExpression,
			final ResponseObjectHandler<T> responseObjectHandler, Class<T> type) {
		final String xmlRequestOne = requestGenerator.generateRequestWithPageIndex(1, requestParams);

		logger.info("Paged request, page #1 /? ...");
		final String xmlResponseOne = performRequest(xmlRequestOne, true);

		final int maxPageIndex = ResponseParser.parseMaxPageIndex(xmlResponseOne, sizeHeaderTag);

		ResponseParser parser = new ResponseParser(xmlResponseOne);

		parser.forEachElement(xPathLoopExpression, responseObjectHandler, type);

		for (int pageIndex = 2; pageIndex <= maxPageIndex; pageIndex++) {
			final String xmlRequestN = requestGenerator.generateRequestWithPageIndex(pageIndex, requestParams);
			logger.info("Paged request, page #" + pageIndex + " /" + maxPageIndex + " ...");
			final String xmlResponseN = performRequest(xmlRequestN, true);
			parser = new ResponseParser(xmlResponseN);
			parser.forEachElement(xPathLoopExpression, responseObjectHandler, type);
		}
	}

}
