package eu.adlogix.appnexus.oas.client.xml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

public final class ResponseParser {

	public interface ResponseElementHandler {

		void processElement(ResponseElement element);

	}

	public interface ResponseObjectHandler<T> {

		void processObject(T object);

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

	public final <T> void forEachElement(final String xpathExpression, final ResponseObjectHandler<T> objectHandler,
			final Class<T> type) {
		@SuppressWarnings("unchecked")
		final List<DefaultElement> elements = this.responseDocument.selectNodes(xpathExpression);
		for (final DefaultElement element : elements) {
			objectHandler.processObject(parseAndCreateObject(element, type));
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

	public final static int parseMaxPageIndex(final String response, final String sizeHeaderTag) {
		final ResponseParser parser = new ResponseParser(response);

		final int pageSize = Integer.parseInt(parser.getElementAttribute("//Response/" + sizeHeaderTag, "pageSize"));
		final int totalNumberOfEntries = Integer.parseInt(parser.getElementAttribute("//Response/" + sizeHeaderTag,
				"totalNumberOfEntries"));
		final int maxPageIndex = totalNumberOfEntries / pageSize + 1;

		return maxPageIndex;
	}

	public <T> T parseAndCreateObject(String xpathExpression, Class<T> type) {
		return parseAndCreateObject(getElement(xpathExpression), type);
	}

	public <T> T parseAndCreateObject(Element element, Class<T> type) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(type);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Source source = new StreamSource(new StringReader(element.asXML()));
			return type.cast(jaxbUnmarshaller.unmarshal(source));

		} catch (JAXBException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
