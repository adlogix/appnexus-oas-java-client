package eu.adlogix.appnexus.oas.client.xml;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;

public final class ResponseParser {

	public interface ResponseElementHandler {

		void processElement(ResponseElement element);

	}

	public final static class ResponseElement {

		private final DefaultElement element;

		private ResponseElement(final DefaultElement element) {
			if (element == null) {
				throw new IllegalArgumentException("Illegal element: " + element);
			}
			this.element = element;
		}

		public final String getChild(final String childElement) {
			return this.element.elementText(childElement);
		}

		public final String getTrimmedText() {
			return this.element.getTextTrim();
		}

	}

	private final Document responseDocument;

	public ResponseParser(final String response) {
		checkNotEmpty(response, "response");
		try {
			this.responseDocument = DocumentHelper.parseText(response);
		} catch (final DocumentException exception) {
			throw new IllegalArgumentException("Could not parse OAS response: \n" + response, exception);
		}
	}

	public final boolean containsExceptions() {
		return elementExists("//Exception");
	}

	public final String getExceptionMessage() {
		final DefaultElement exception = (DefaultElement) this.responseDocument.selectSingleNode("//Exception");
		final String message = exception.getText();
		return message;
	}
	
	public final String getExceptionCode() {
		return getElementAttribute("//Exception", "errorCode");
	}

	public final List<String> getTrimmedElementList(final String xpathExpression) {
		final ArrayList<String> result = new ArrayList<String>();

		@SuppressWarnings("unchecked")
		final List<DefaultElement> idNodes = this.responseDocument.selectNodes(xpathExpression);
		for (final DefaultElement idNode : idNodes) {
			final String id = idNode.getTextTrim();
			result.add(id);
		}
		return result;
	}

	public final String getElementAttribute(final String xpathExpression, final String attributeName) {
		final DefaultElement elementNode = (DefaultElement) this.responseDocument.selectSingleNode(xpathExpression);
		return elementNode.attributeValue(attributeName);
	}

	public final Element getElement(final String xpathExpression) {
		final DefaultElement elementNode = (DefaultElement) this.responseDocument.selectSingleNode(xpathExpression);
		return elementNode;
	}

	public final ResponseElement getResponseElement(final String xpathExpression) {
		final DefaultElement elementNode = (DefaultElement) this.responseDocument.selectSingleNode(xpathExpression);
		return new ResponseElement(elementNode);
	}

	public final void forEachElement(final String xpathExpression, final ResponseElementHandler elementHandler) {
		@SuppressWarnings("unchecked")
		final List<DefaultElement> elements = this.responseDocument.selectNodes(xpathExpression);
		for (final DefaultElement element : elements) {
			elementHandler.processElement(new ResponseElement(element));
		}
	}


	public final String getTrimmedElement(final String xpathExpression) {
		final DefaultElement element = (DefaultElement) this.responseDocument.selectSingleNode(xpathExpression);
		return element == null ? null : element.getTextTrim();
	}

	public final boolean elementExists(final String xpathExpression) {
		return this.responseDocument.selectSingleNode(xpathExpression) != null;
	}

	public final String asXML() {
		return this.responseDocument.asXML();
	}

	public final int parseMaxPageIndex(final String sizeHeaderTag) {

		final int pageSize = Integer.parseInt(getElementAttribute("//Response/" + sizeHeaderTag, "pageSize"));
		final int totalNumberOfEntries = Integer.parseInt(getElementAttribute("//Response/" + sizeHeaderTag,
				"totalNumberOfEntries"));
		final int maxPageIndex = totalNumberOfEntries / pageSize + 1;

		return maxPageIndex;
	}

}
