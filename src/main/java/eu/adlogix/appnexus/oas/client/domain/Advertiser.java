package eu.adlogix.appnexus.oas.client.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

@XmlType(propOrder = { "id", "organization", "billingInformation", "internalQuickReport", "externalQuickReport" })
@XmlRootElement(name = "Advertiser")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Advertiser {

	@XmlElement(name = "Id")
	String id;
	@XmlElement(name = "Organization")
	String organization;
	@XmlElement(name = "BillingInformation")
	BillingInformation billingInformation;
	@XmlElement(name = "InternalQuickReport")
	String internalQuickReport;
	@XmlElement(name = "ExternalQuickReport")
	String externalQuickReport;

	public Advertiser(String id, String organization) {
		this();
		this.id = id;
		this.organization = organization;

	}

	public Advertiser() {
		this.billingInformation = new BillingInformation();
		billingInformation.setMethod(new Method(new Code("M")));

		List<Code> countryCodes = new ArrayList<Code>();
		countryCodes.add(new Code("US"));
		billingInformation.setCountry(new Country(countryCodes));

		this.internalQuickReport = "short";
		this.externalQuickReport = "to-date";
	}

}
