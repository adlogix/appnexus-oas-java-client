package eu.adlogix.appnexus.oas.client.xml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;

import eu.adlogix.appnexus.oas.client.resources.ResourceUtils;

public final class XmlRequestGenerator {

	private final String requestTemplate;

	private final ParameterMapXmlEscaper parameterMapXmlSanitizer;

	public XmlRequestGenerator(final String requestTemplateName) {
		if ((requestTemplateName == null) || "".equals(requestTemplateName)) {
			throw new IllegalArgumentException("Illegal requestTemplateName: " + requestTemplateName);
		}
		final String fileName = requestTemplateName.trim().endsWith(".xml") ? requestTemplateName.trim()
				: requestTemplateName.trim() + ".xml";
		if (ResourceUtils.loadResourceInputStream(fileName) == null) {
			throw new IllegalArgumentException("Template not found: " + fileName);
		}
		this.requestTemplate = ResourceUtils.loadResourceString(fileName);

		this.parameterMapXmlSanitizer = new ParameterMapXmlEscaper();
	}

	public final String generateRequest(final Map<String, Object> requestParameters) {
		@SuppressWarnings("unchecked")
		final Map<String, Object> parameters = Collections.unmodifiableMap(requestParameters == null ? Collections.EMPTY_MAP
				: requestParameters);
		// Note that we recreate the StringTemplate on every call to this method
		// because it is a bad practice to cache them.
		// The String however is cached in the generator to avoid going back to
		// the resource every time. So there is still an advantage to
		// cache/reuse the generator over multiple request generations.
		final StringTemplate xmlRequestTemplate = new StringTemplate(this.requestTemplate);
		xmlRequestTemplate.setAttributes(this.parameterMapXmlSanitizer.escapeParameters(parameters));
		final String xmlRequest = xmlRequestTemplate.toString();
		return xmlRequest;
	}

	public final String generateRequestWithPageIndex(final int pageIndex, final Map<String, Object> requestParams) {
		@SuppressWarnings("serial")
		final HashMap<String, Object> actualRequestParams = new HashMap<String, Object>() {
			{
				put("pageIndex", pageIndex);
			}
		};

		for (Map.Entry<String, Object> requestParamEntry : requestParams.entrySet()) {
			actualRequestParams.put(requestParamEntry.getKey(), requestParamEntry.getValue());
		}

		final String xmlRequestWithPageIndex = generateRequest(actualRequestParams);

		return xmlRequestWithPageIndex;
	}
}
