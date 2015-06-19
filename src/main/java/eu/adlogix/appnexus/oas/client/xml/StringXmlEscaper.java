package eu.adlogix.appnexus.oas.client.xml;

import org.apache.commons.lang3.StringEscapeUtils;

final class StringXmlEscaper {

	final String escape(final String unescapedString) {
		return StringEscapeUtils.escapeXml11(unescapedString);
	}

}
