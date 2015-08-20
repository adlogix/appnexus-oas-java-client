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
import eu.adlogix.appnexus.oas.client.domain.StatefulDomainManager;
import eu.adlogix.appnexus.oas.client.parser.XmlToAdvertiserParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElement;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser.ResponseElementHandler;
import eu.adlogix.appnexus.oas.client.xml.XmlRequestGenerator;

/**
 * Default implementation of {@link AdvertiserService} which provides functions
 * for Advertiser related operations
 * 
 */
@SuppressWarnings("serial")
public class DefaultAdvertiserService extends AbstractOasService implements AdvertiserService {

	private final XmlRequestGenerator addAdvertiserRequestGenerator = new XmlRequestGenerator("add-advertiser");
	private final XmlRequestGenerator listAdvertisersRequestGenerator = new XmlRequestGenerator("list-advertisers");
	private final XmlRequestGenerator readAdvertisersRequestGenerator = new XmlRequestGenerator("read-advertiser");
	private final XmlRequestGenerator updateAdvertiserRequestGenerator = new XmlRequestGenerator("update-advertiser");

	public DefaultAdvertiserService(OasApiService apiService) {
		super(apiService);
	}

	@Override
	public final void add(Advertiser advertiser) {

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
		advertiser.resetModifiedAttributes();
	}

	@Override
	public final void update(final Advertiser advertiser) {

		checkNotEmpty(advertiser.getId(), "advertiserId");

		final Advertiser modifiedAdvertiser = new StatefulDomainManager().getModifiedObject(advertiser);


		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiserId", modifiedAdvertiser.getId());
				put("advertiserName", modifiedAdvertiser.getOrganization());
			}
		};

		performRequest(updateAdvertiserRequestGenerator, parameters);
		advertiser.resetModifiedAttributes();
	}

	@Override
	public final List<Advertiser> getAll() {
		final List<Advertiser> result = new ArrayList<Advertiser>();

		ResponseElementHandler advertiserResponseHandler = new ResponseElementHandler() {

			@Override
			public void processElement(ResponseElement element) {
				Advertiser advertiser = new Advertiser();
				advertiser.setId(element.getChild("Id"));
				advertiser.setOrganization(element.getChild("Organization"));
				advertiser.resetModifiedAttributes();
				result.add(advertiser);
			}
			
		};

		performPagedRequest(listAdvertisersRequestGenerator, new HashMap<String, Object>(), "List", "//List/Advertiser", advertiserResponseHandler);

		return Collections.unmodifiableList(result);
	}

	@Override
	public Advertiser getById(final String id) {

		checkNotEmpty(id, "advertiserId");

		final Map<String, Object> parameters = new HashMap<String, Object>() {
			{
				put("advertiserId", id);
			}
		};

		final ResponseParser responseParser = performRequest(readAdvertisersRequestGenerator, parameters);

		XmlToAdvertiserParser advertiserParser = new XmlToAdvertiserParser(responseParser);
		Advertiser advertiser = advertiserParser.parse();
		advertiser.resetModifiedAttributes();
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
