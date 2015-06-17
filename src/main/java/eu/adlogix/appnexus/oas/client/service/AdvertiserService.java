package eu.adlogix.appnexus.oas.client.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eu.adlogix.appnexus.oas.client.certificate.CertificateManager;
import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.domain.BillingInformation;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

public class AdvertiserService extends AbstractOasService {

	private final XmlRequestGenerator addAdvertiserRequestGenerator = new XmlRequestGenerator("add-advertiser");
	private final XmlRequestGenerator listAdvertisersRequestGenerator = new XmlRequestGenerator("list-advertisers");
	private final XmlRequestGenerator readAdvertisersRequestGenerator = new XmlRequestGenerator("read-advertiser");
	private final XmlRequestGenerator updateAdvertiserRequestGenerator = new XmlRequestGenerator("update-advertiser");

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
				put("advertiserId", advertiser.getId());
				put("advertiserName", advertiser.getOrganization());
				put("advertiserBillingInfoCountry", advertiser.getBillingInformation().getCountry());
				put("advertiserBillingInfoMethod", advertiser.getBillingInformation().getMethod());
			}
		};

		final String request = this.addAdvertiserRequestGenerator.generateRequest(parameters);
		final String response = performRequest(request);

		ResponseParser responseParser = new ResponseParser(response);

		if (responseParser.containsExceptions()) {
			throw new RuntimeException(responseParser.getExceptionMessage());
		}

	}

	public final void updateAdvertiser(final Advertiser advertiser) {

		Advertiser existingAdvertiser = getAdvertiserById(advertiser.getId());

		final String existingOrganization = existingAdvertiser.getOrganization();
		final String organization = advertiser.getOrganization();

		@SuppressWarnings("serial")
		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiserId", advertiser.getId());
				put("advertiserName", (existingOrganization.equals(organization)) ? null : organization);
			}
		};

		final String request = this.updateAdvertiserRequestGenerator.generateRequest(parameters);
		final String response = performRequest(request);

		ResponseParser responseParser = new ResponseParser(response);

		if (responseParser.containsExceptions()) {
			throw new RuntimeException(responseParser.getExceptionMessage());
		}

	}

	public final List<Advertiser> getAllAdvertisers() {
		final List<Advertiser> result = new ArrayList<Advertiser>();

		ResponseElementHandler advertiserResponseHandler = new ResponseElementHandler() {

			@Override
			public void processElement(ResponseElement element) {
					Advertiser advertiser=new Advertiser();
				advertiser.setId(element.getChild("Id"));
					advertiser.setOrganization(element.getChild("Organization"));
					result.add(advertiser);
				}
			
		};

		performPagedRequest(listAdvertisersRequestGenerator, new HashMap<String, Object>(), "List", "//List/Advertiser", advertiserResponseHandler);

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
		Advertiser advertiser = new Advertiser();
		advertiser.setId(parser.getTrimmedElement("//Advertiser/Id"));
		advertiser.setOrganization(parser.getTrimmedElement("//Advertiser/Organization"));
		BillingInformation billingInformation = new BillingInformation();
		billingInformation.setMethod(parser.getTrimmedElement("//Advertiser/BillingInformation/Method/Code"));
		billingInformation.setCountry(parser.getTrimmedElement("//Advertiser/BillingInformation/Country/Code"));
		advertiser.setBillingInformation(billingInformation);
		return advertiser;
	}

}
