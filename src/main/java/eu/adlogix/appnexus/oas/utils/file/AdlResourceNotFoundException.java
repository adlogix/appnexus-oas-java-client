package eu.adlogix.appnexus.oas.utils.file;

public class AdlResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 7897517642374845438L;

	public AdlResourceNotFoundException(final String errorMsg) {
		super(errorMsg);
	}
}