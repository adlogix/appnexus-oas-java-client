package eu.adlogix.appnexus.oas.client.service;

import java.util.Map;

import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.exceptions.OasServerSideException;
import eu.adlogix.appnexus.oas.client.utils.log.LogUtils;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;


public abstract class AbstractOasService {


	private static final Logger logger = LogUtils.getLogger(AbstractOasService.class);

	protected static final String OAS_DATE_FORMAT = "yyyy-MM-dd";

	private OasApiService OasApiService;

	protected AbstractOasService(OasApiService apiService) {
		this.OasApiService = apiService;
	}

	public String performRequest(final String xmlRequest) {
		return performRequest(xmlRequest, false);
	}

	public String performRequest(final String xmlRequest, final boolean retryOnConnectionErrors) {
		try {
			logger.info("Making Request:\n" + xmlRequest);
			final String xmlResponse = OasApiService.callApi(xmlRequest, retryOnConnectionErrors);
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

		throwExceptionsThrownByOas(parser, xmlRequestOne);

		parser.forEachElement(xPathLoopExpression, responseElementHandler);

		for (int pageIndex = 2; pageIndex <= maxPageIndex; pageIndex++) {
			final String xmlRequestN = requestGenerator.generateRequestWithPageIndex(pageIndex, requestParams);
			logger.info("Paged request, page #" + pageIndex + " /" + maxPageIndex + " ...");
			final String xmlResponseN = performRequest(xmlRequestN, true);
			parser = new ResponseParser(xmlResponseN);
			throwExceptionsThrownByOas(parser, xmlResponseN);
			parser.forEachElement(xPathLoopExpression, responseElementHandler);
		}
	}
	

	protected void throwExceptionsThrownByOas(final ResponseParser parser, String request) {
		if (parser.containsExceptions()) {
			throw new OasServerSideException(parser, request);
		}
	}
}
