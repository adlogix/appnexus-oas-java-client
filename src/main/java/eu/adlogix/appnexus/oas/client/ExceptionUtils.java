package eu.adlogix.appnexus.oas.client;

public final class ExceptionUtils {

	public final static <T> T throwException(final String message, Class<T> classToReturn) {
		throw new RuntimeException(message);
	}
	
	private ExceptionUtils() {
		// Utility class: should never be instantiated
	}
}
