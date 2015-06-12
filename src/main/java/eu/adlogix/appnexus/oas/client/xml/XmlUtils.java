package eu.adlogix.appnexus.oas.client.xml;

public final class XmlUtils {
	
	public static final String normalizeXml(final String xml) {
		return xml == null ? null : xml.replace("\t", "  ").replace("\r", "");
	}
	
	private XmlUtils() {
		// Utility class: should never be instatiated
	}

}
