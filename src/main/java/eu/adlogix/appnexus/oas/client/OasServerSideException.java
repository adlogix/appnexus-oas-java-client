package eu.adlogix.appnexus.oas.client;

import lombok.Getter;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;

@Getter
public class OasServerSideException extends RuntimeException {

	public static class OasRequestEmbeddedException extends Exception {
		private static final long serialVersionUID = -2189864208812591203L;

		public OasRequestEmbeddedException(String request) {
			super("OAS Error on Request: " + request);
		}

	}

	private static final long serialVersionUID = 8579509336306860438L;
	private final String errorMessage;
	private final Integer errorCode;
	private final String request;

	public OasServerSideException(final String errorCode, final String errorMessage, final String request) {

		super("OAS Error [" + errorCode + "]: '" + errorMessage + "'", new OasRequestEmbeddedException(request));
		this.errorMessage = errorMessage;
		this.errorCode = Integer.valueOf(errorCode);
		this.request = request;
	}

	public OasServerSideException(final ResponseParser parser, final String request) {
		this(parser.getExceptionCode(), parser.getExceptionMessage(), request);
	}
}
