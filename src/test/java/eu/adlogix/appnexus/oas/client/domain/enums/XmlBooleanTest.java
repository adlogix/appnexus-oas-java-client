package eu.adlogix.appnexus.oas.client.domain.enums;

import org.testng.annotations.Test;

import eu.adlogix.appnexus.oas.client.domain.enums.XmlBoolean;
import static org.testng.Assert.assertEquals;

public class XmlBooleanTest {
	@Test
	public void fromBoolean_True_ReturnY() {
		assertEquals(XmlBoolean.fromBoolean(true), XmlBoolean.Y);
	}

	@Test
	public void fromBoolean_False_ReturnN() {
		assertEquals(XmlBoolean.fromBoolean(false), XmlBoolean.N);
	}

	@Test
	public void fromBoolean_Null_ReturnNull() {
		assertEquals(XmlBoolean.fromBoolean(null), null);
	}

	@Test
	public void toString_Y_ReturnYString() {
		assertEquals(XmlBoolean.Y.toString(), "Y");
	}

}
