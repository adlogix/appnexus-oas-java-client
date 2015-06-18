package eu.adlogix.appnexus.oas.client.exceptions;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 7897517642374845438L;

	public ResourceNotFoundException(final String errorMsg) {
		super(errorMsg);
	}
}