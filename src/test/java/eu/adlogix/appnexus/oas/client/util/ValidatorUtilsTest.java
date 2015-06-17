package eu.adlogix.appnexus.oas.client.util;

import java.util.List;

import org.testng.annotations.Test;
import org.testng.collections.Lists;

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
	public void checkNotEmpty_IsNotNullNorEmpty_NoException() {
		ValidatorUtils.checkNotEmpty("foo", "foo");
	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "foo shouldn't have a empty value")
	public void checkNotEmpty_IsNull_Exception() {
		String foo = null;
		ValidatorUtils.checkNotEmpty(foo, "foo");
	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "foo shouldn't have a empty value")
	public void checkNotEmpty_IsEmpty_Exception() {
		ValidatorUtils.checkNotEmpty("", "foo");
	}
	
	@Test
	public void checkNotEmpty_IsNotNullNorEmptyList_NoException() {
		ValidatorUtils.checkNotEmpty(Lists.newArrayList("foo"), "foo");
	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "foo shouldn't be empty")
	public void checkNotEmpty_IsNullList_Exception() {
		List<String> foo = null;
		ValidatorUtils.checkNotEmpty(foo, "foo");
	}

	@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "foo shouldn't be empty")
	public void checkNotEmpty_IsEmptyList_Exception() {
		ValidatorUtils.checkNotEmpty(Lists.newArrayList(), "foo");
	}
}
