package eu.adlogix.appnexus.oas.client.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseObjectHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlGenerator;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

public class AdvertiserService extends AbstractOasService {

	private final XmlRequestGenerator addAdvertiserRequestGenerator = new XmlRequestGenerator("add-advertiser");
	private final XmlRequestGenerator listAdvertisersRequestGenerator = new XmlRequestGenerator("list-advertisers");
	private final XmlRequestGenerator readAdvertisersRequestGenerator = new XmlRequestGenerator("read-advertiser");
	private final XmlRequestGenerator updateAdvertiserRequestGenerator = new XmlRequestGenerator("update-advertiser");

	private XmlGenerator xmlGenerator = new XmlGenerator();

	public AdvertiserService(Properties credentials) {
		super(credentials);
	}

	public AdvertiserService(Properties credentials, OasApiService apiService, CertificateManager certificateManager) {
		super(credentials, apiService, certificateManager);
	}

	public final void addAdvertiser(final Advertiser advertiser) {
		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiser", xmlGenerator.generate(advertiser, Advertiser.class));
			}
		};


		final String request = this.addAdvertiserRequestGenerator.generateRequest(parameters, false);

		final String response = performRequest(request);

		ResponseParser responseParser = new ResponseParser(response);

		if (responseParser.containsExceptions()) {
			throw new RuntimeException(responseParser.getExceptionMessage());
		}

	}

	public final void updateAdvertiser(final Advertiser advertiser) {

		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiser", xmlGenerator.generate(advertiser, Advertiser.class));
			}
		};


		final String request = this.updateAdvertiserRequestGenerator.generateRequest(parameters, false);
		final String response = performRequest(request);

		ResponseParser responseParser = new ResponseParser(response);

		if (responseParser.containsExceptions()) {
			throw new RuntimeException(responseParser.getExceptionMessage());
		}


	}

	public final List<Advertiser> getAllAdvertisers() {
		final List<Advertiser> result = new ArrayList<Advertiser>();

		ResponseObjectHandler<Advertiser> advertiserResponseHandler = new ResponseObjectHandler<Advertiser>() {
			@Override
			public void processObject(Advertiser object) {
				result.add(object);
			}
		};

		performPagedRequest(listAdvertisersRequestGenerator, new HashMap<String, Object>(), "List", "//List/Advertiser", advertiserResponseHandler, Advertiser.class);

		return Collections.unmodifiableList(result);
	}

	public Advertiser getAdvertiserById(final String id) {

		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiserId", id);
			}
		};

		final String request = this.readAdvertisersRequestGenerator.generateRequest(parameters);
		final String response = performRequest(request);

		ResponseParser responseParser = new ResponseParser(response);

		if (responseParser.containsExceptions()) {
			throw new RuntimeException(responseParser.getExceptionMessage());
		}

		return parseAndCreateAdvertiser(responseParser);
	}

	private Advertiser parseAndCreateAdvertiser(ResponseParser parser) {
		return parser.parseAndCreateObject("//Advertiser", Advertiser.class);

	}

}
