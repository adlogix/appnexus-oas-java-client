package eu.adlogix.appnexus.oas.client.service;

import static eu.adlogix.appnexus.oas.client.utils.ValidatorUtils.checkNotEmpty;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.domain.BillingInformation;
import eu.adlogix.appnexus.oas.client.parser.XmlToAdvertiserParser;
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
	public final void addAdvertiser(Advertiser advertiser) {

		checkNotEmpty(advertiser.getId(), "advertiserId");
		checkNotEmpty(advertiser.getOrganization(), "advertiserOrganization");

		final Advertiser advertiserWithDefValues = setDefaultsForEmptyFields(advertiser);

		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiserId", advertiserWithDefValues.getId());
				put("advertiserName", advertiserWithDefValues.getOrganization());
				put("advertiserBillingInfoCountry", advertiserWithDefValues.getBillingInformation().getCountry());
				put("advertiserBillingInfoMethod", advertiserWithDefValues.getBillingInformation().getMethod());
				put("advertiserInternalQuickReport", advertiserWithDefValues.getInternalQuickReport());
				put("advertiserExternalQuickReport", advertiserWithDefValues.getExternalQuickReport());

			}
		};

		performRequest(addAdvertiserRequestGenerator, parameters);
		advertiser.resetModifiedFlags();
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

		final Advertiser modifiedAdvertiser = advertiser.getAdvertiserWithModifiedAttributes();


		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiserId", modifiedAdvertiser.getId());
				put("advertiserName", modifiedAdvertiser.getOrganization());
			}
		};

		performRequest(updateAdvertiserRequestGenerator, parameters);
		advertiser.resetModifiedFlags();
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
				Advertiser advertiser = new Advertiser();
				advertiser.setId(element.getChild("Id"));
				advertiser.setOrganization(element.getChild("Organization"));
				advertiser.resetModifiedFlags();
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

		XmlToAdvertiserParser advertiserParser = new XmlToAdvertiserParser(responseParser);
		Advertiser advertiser = advertiserParser.parse();
		advertiser.resetModifiedFlags();
		return advertiser;

	}



	/**
	 * Sets default values for the empty fields of the {@link Advertiser} object
	 * 
	 * @param advertiser
	 * @return {@link Advertiser} object with default values
	 */
	private Advertiser setDefaultsForEmptyFields(Advertiser advertiser) {
		if (advertiser.getBillingInformation() == null) {
			advertiser.setBillingInformation(new BillingInformation());
		}
		BillingInformation billingInformation = advertiser.getBillingInformation();
		billingInformation.setMethod(defaultIfEmpty(billingInformation.getMethod(), "M"));
		billingInformation.setCountry(defaultIfEmpty(billingInformation.getCountry(), "US"));

		advertiser.setInternalQuickReport(defaultIfEmpty(advertiser.getInternalQuickReport(), "short"));
		advertiser.setExternalQuickReport(defaultIfEmpty(advertiser.getExternalQuickReport(), "to-date"));

		return advertiser;
	}

}
