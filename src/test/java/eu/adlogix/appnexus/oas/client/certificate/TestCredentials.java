package eu.adlogix.appnexus.oas.client.certificate;

import java.util.Properties;

public class TestCredentials {
	public static Properties getTestCredentials() {
		final Properties credentials = new Properties();
		credentials.put("xaxis.host", "https://test");
		credentials.put("xaxis.account", "x");
		credentials.put("xaxis.user", "x");
		credentials.put("xaxis.password", "x");
		return credentials;
	}
}
