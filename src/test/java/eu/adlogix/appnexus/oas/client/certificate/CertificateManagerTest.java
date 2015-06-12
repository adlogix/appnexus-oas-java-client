package eu.adlogix.appnexus.oas.client.certificate;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;

public class CertificateManagerTest {

	@DataProvider
	public Object[][] dataProvider() {
		return new Object[][] { { "training7.247realmedia.com", "training7.247realmedia.com", true },
				{ "test-training7.247realmedia.com", "training7.247realmedia.com", false },
				{ "*.247realmedia.com", "training7.247realmedia.com", true },
				{ "*realmedia.com", "training7.247realmedia.com", true },
				{ "*.247realmedia.com", "test-training7.247realmedia.com", true },
				{ "*.247test.com", "training7.247realmedia.com", false },
				{ "training7.*.com", "training7.247realmedia.com", false },
				{ "*.training7.*.com", "training7.247realmedia.com", false } };
	}

	@Test(dataProvider = "dataProvider")
	public void testHostMatchesCommonName(String commonName, String hostName, boolean expected) {
		assertEquals(CertificateManager.hostMatchesCommonName(commonName, hostName), expected);
	}

}
