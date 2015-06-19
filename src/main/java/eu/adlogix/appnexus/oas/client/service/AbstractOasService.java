package eu.adlogix.appnexus.oas.client.service;

import java.util.Map;

import org.slf4j.Logger;

import eu.adlogix.appnexus.oas.client.exceptions.OasRequestEmbeddedException;
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

	/**
	 * Perform a request on OAS API which won't be retried and where
	 * {@link OasServerSideException}s will be thrown at logic failures in OAS
	 * side
	 * 
	 * @param xmlRequestGenerator
	 *            Generates an XML request based on the source XML document and
	 *            parameters
	 * @param parameters
	 *            Parameters as a {@link Map} where the parameter names as keys
	 *            and parameter values as values
	 * @return {@link ResponseParser} of the Response String
	 */
	public ResponseParser performRequest(final XmlRequestGenerator xmlRequestGenerator,
			final Map<String, Object> parameters) {
		return performRequest(xmlRequestGenerator, parameters, false);
	}

	/**
	 * Perform a request on OAS API where {@link OasServerSideException}s will
	 * be thrown at logic failures in OAS side
	 * 
	 * @param xmlRequestGenerator
	 *            Generates an XML request based on the source XML document and
	 *            parameters
	 * @param parameters
	 *            Parameters as a {@link Map} where the parameter names as keys
	 *            and parameter values as values
	 * @param retryOnConnectionErrors
	 *            If <code>true</code>, it will retry if any exceptions are
	 *            found on invocation. Best for state less methods
	 * @return {@link ResponseParser} of the Response String
	 */
	public ResponseParser performRequest(final XmlRequestGenerator xmlRequestGenerator,
			final Map<String, Object> parameters, final boolean retryOnConnectionErrors) {
		return performRequest(xmlRequestGenerator, parameters, retryOnConnectionErrors, false);
	}

	/**
	 * Perform a request on OAS API
	 * 
	 * @param xmlRequestGenerator
	 *            Generates an XML request based on the source XML document and
	 *            parameters
	 * @param parameters
	 *            Parameters as a {@link Map} where the parameter names as keys
	 *            and parameter values as values
	 * @param retryOnConnectionErrors
	 *            If <code>true</code>, it will retry if any exceptions are
	 *            found on invocation. Best for state less methods
	 * @param suppressOasServerSideExceptions
	 *            If <code>true</code> this method will not throw any
	 *            {@link OasServerSideException}s where OAS logic fails. But
	 *            these exceptions can be found by looking into the
	 *            {@link ResponseParser#containsExceptions()},
	 *            {@link ResponseParser#getExceptionCode()} and
	 *            {@link ResponseParser#getExceptionMessage()}
	 * @return {@link ResponseParser} of the Response String
	 */
	public ResponseParser performRequest(final XmlRequestGenerator xmlRequestGenerator,
			final Map<String, Object> parameters, final boolean retryOnConnectionErrors,
			final boolean suppressOasServerSideExceptions) {

		final String xmlRequest = xmlRequestGenerator.generateRequest(parameters);

		final String xmlResponse = performRequest(xmlRequest, retryOnConnectionErrors);

		final ResponseParser parser = new ResponseParser(xmlResponse);
		if (parser.containsExceptions() && !suppressOasServerSideExceptions) {
			throw new OasServerSideException(parser, xmlRequest);
		}

		return parser;
	}

	private String performRequest(final String xmlRequest, final boolean retryOnConnectionErrors) {
		try {
			logger.info("Making Request:\n" + xmlRequest);
			final String xmlResponse = OasApiService.callApi(xmlRequest, retryOnConnectionErrors);
			logger.info("Recieved Response:\n" + xmlResponse);
			return xmlResponse;
		} catch (final Exception exception) {
			throw new OasRequestEmbeddedException(xmlRequest, exception);
		}
	}

	/**
	 * Perform a request where the response is paginated. In such a case the
	 * each atomic request should also mention the page number to access.
	 * 
	 * @param requestGenerator
	 *            Generates an XML request based on the source XML document and
	 *            parameters
	 * @param requestParams
	 *            Parameters as a {@link Map} where the parameter names as keys
	 *            and parameter values as values
	 * @param sizeHeaderTag
	 *            Is the name of the tag of the response which the value of
	 *            number of pages are found
	 * @param xPathLoopExpression
	 *            XPath expression of the response of the tag which is used to
	 *            identify an entity returned by a response
	 * @param responseElementHandler
	 *            An implementation of {@link ResponseElementHandler} which does
	 *            a custom action on finding instances of xPathLoopExpression
	 */
	public void performPagedRequest(final XmlRequestGenerator requestGenerator,
			final Map<String, Object> requestParams, final String sizeHeaderTag, final String xPathLoopExpression,
			final ResponseElementHandler responseElementHandler) {

		final String xmlRequestOne = requestGenerator.generateRequestWithPageIndex(1, requestParams);

		logger.info("Paged request, page #1 /? ...");

		final String xmlResponseOne = performRequest(xmlRequestOne, true);
		ResponseParser parser = new ResponseParser(xmlResponseOne);
		throwExceptionsThrownByOas(parser, xmlResponseOne);

		final int maxPageIndex = parser.parseMaxPageIndex(sizeHeaderTag);

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
	
	private void throwExceptionsThrownByOas(final ResponseParser parser, String request) {
		if (parser.containsExceptions()) {
			throw new OasServerSideException(parser, request);
		}
	}
}
