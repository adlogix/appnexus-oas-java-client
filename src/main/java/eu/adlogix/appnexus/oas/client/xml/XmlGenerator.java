package eu.adlogix.appnexus.oas.client.xml;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.sun.xml.bind.marshaller.DataWriter;

public class XmlGenerator {

	public <T> String generate(T object, Class<T> type) {
		JAXBContext jaxbContext;
		try {

			jaxbContext = JAXBContext.newInstance(type);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new JaxbCharacterEscapeHandler());
			dataWriter.setIndentStep("  ");
			jaxbMarshaller.marshal(object, dataWriter);

			return stringWriter.toString();

		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
