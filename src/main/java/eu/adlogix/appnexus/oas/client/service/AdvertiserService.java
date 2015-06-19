package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.domain.BillingInformation;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Service Class which provides functions for all Advertiser related operations
 * 
 */
@SuppressWarnings("serial")
public class AdvertiserService extends AbstractOasService {

	private final XmlRequestGenerator addAdvertiserRequestGenerator = new XmlRequestGenerator("add-advertiser");
	private final XmlRequestGenerator listAdvertisersRequestGenerator = new XmlRequestGenerator("list-advertisers");
	private final XmlRequestGenerator readAdvertisersRequestGenerator = new XmlRequestGenerator("read-advertiser");
	private final XmlRequestGenerator updateAdvertiserRequestGenerator = new XmlRequestGenerator("update-advertiser");

	AdvertiserService(OasApiService apiService) {
		super(apiService);
	}

	/**
	 * Adds a new {@link Advertiser}
	 * 
	 * @param advertiser
	 *            {@link Advertiser}
	 * @return
	 */
	public final void addAdvertiser(final Advertiser advertiser) {

		checkNotEmpty(advertiser.getId(), "advertiserId");
		checkNotEmpty(advertiser.getOrganization(), "advertiserOrganization");

		setDefaultValuesForEmptyFields(advertiser);

		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiserId", advertiser.getId());
				put("advertiserName", advertiser.getOrganization());
				put("advertiserBillingInfoCountry", advertiser.getBillingInformation().getCountry());
				put("advertiserBillingInfoMethod", advertiser.getBillingInformation().getMethod());
				put("advertiserInternalQuickReport", advertiser.getInternalQuickReport());
				put("advertiserExternalQuickReport", advertiser.getExternalQuickReport());

			}
		};

		performRequest(addAdvertiserRequestGenerator, parameters);
	}

	/**
	 * Updates an existing {@link Advertiser}
	 * 
	 * @param advertiser
	 *            {@link Advertiser}
	 * @return
	 */
	public final void updateAdvertiser(final Advertiser advertiser) {

		checkNotEmpty(advertiser.getId(), "advertiserId");

		Advertiser existingAdvertiser = getAdvertiserById(advertiser.getId());

		final String existingOrganization = existingAdvertiser.getOrganization();
		final String organization = advertiser.getOrganization();

		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiserId", advertiser.getId());
				put("advertiserName", (existingOrganization.equals(organization)) ? null : organization);
			}
		};

		performRequest(updateAdvertiserRequestGenerator, parameters);
	}

	/**
	 * Retrieves a list of existing {@link Advertiser}s
	 * 
	 * @return {@link List} of {@link Advertiser}s
	 */
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

	/**
	 * Retrieves an existing {@link Advertiser} by the given advertiser id
	 * 
	 * @param id
	 *            OAS advertiser Id
	 * 
	 * @return {@link Advertiser}
	 * 
	 */
	public Advertiser getAdvertiserById(final String id) {

		checkNotEmpty(id, "advertiserId");

		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiserId", id);
			}
		};

		final ResponseParser responseParser = performRequest(readAdvertisersRequestGenerator, parameters);

		return parseAndCreateAdvertiser(responseParser);
	}

	/**
	 * Creates an Advertiser object from the {@link ResponseParser}
	 * 
	 * @param parser
	 * @return created {@link Advertiser} object
	 */
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

	/**
	 * Sets default values for the empty fields of the {@link Advertiser} object
	 * 
	 * @param advertiser
	 * @return {@link Advertiser} object with default values
	 */
	private Advertiser setDefaultValuesForEmptyFields(Advertiser advertiser) {
		if (advertiser.getBillingInformation() == null) {
			advertiser.setBillingInformation(new BillingInformation());
		}
		BillingInformation billingInformation = advertiser.getBillingInformation();
		billingInformation.setMethod(getDefaultValueIfEmpty(billingInformation.getMethod(), "M"));
		billingInformation.setCountry(getDefaultValueIfEmpty(billingInformation.getCountry(), "US"));

		advertiser.setInternalQuickReport(getDefaultValueIfEmpty(advertiser.getInternalQuickReport(), "short"));
		advertiser.setExternalQuickReport(getDefaultValueIfEmpty(advertiser.getExternalQuickReport(), "to-date"));

		return advertiser;
	}

	/**
	 * Utility method which checks if a value is empty and returns a default
	 * value if its empty
	 * 
	 * @param value
	 *            value that needs to be checked if empty
	 * @param defaultValue
	 *            default value
	 * @return if value is not empty returns value. if value is empty returns
	 *         default value
	 */
	private String getDefaultValueIfEmpty(String value, String defaultValue) {
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		return value;
	}

}
