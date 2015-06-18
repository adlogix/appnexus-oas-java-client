package eu.adlogix.appnexus.oas.client.exceptions;

import lombok.Getter;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;

@Getter
public class OasServerSideException extends RuntimeException {

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
