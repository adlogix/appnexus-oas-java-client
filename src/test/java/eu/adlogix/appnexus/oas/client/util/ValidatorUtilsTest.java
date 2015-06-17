package eu.adlogix.appnexus.oas.client.util;

import org.testng.annotations.Test;

public class ValidatorUtilsTest {

	@Test
	public void checkNotNull_IsNotNull_NoException() {
		ValidatorUtils.checkNotNull("foo", "foo");
	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "foo shouldn't have a null value")
	public void checkNotNull_IsNull_Exception() {
		ValidatorUtils.checkNotNull(null, "foo");
	}

	@Test
	public void checkEmpty_IsNotNullNorEmpty_NoException() {
		ValidatorUtils.checkEmpty("foo", "foo");
	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "foo shouldn't have a empty value")
	public void checkEmpty_IsNull_Exception() {
		ValidatorUtils.checkEmpty(null, "foo");
	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "foo shouldn't have a empty value")
	public void checkEmpty_IsEmpty_Exception() {
		ValidatorUtils.checkEmpty("", "foo");
	}
}
