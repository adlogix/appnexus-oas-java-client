package eu.adlogix.appnexus.oas.client.parser;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Advertiser;
import eu.adlogix.appnexus.oas.client.domain.BillingInformation;
import eu.adlogix.appnexus.oas.client.xml.ResponseParser;

@AllArgsConstructor
public class XmlToAdvertiserParser implements XmlToObjectParser<Advertiser> {

	private ResponseParser parser;

	@Override
	public Advertiser parse() {
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
