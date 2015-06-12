package eu.adlogix.appnexus.oas.client.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlType(propOrder = { "id", "organization" })
@XmlRootElement(name = "Advertiser")
public class Advertiser {

	String id;
	String organization;

	@XmlElement(name = "Id")
	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "Organization")
	public void setOrganization(String organization) {
		this.organization = organization;
	}
}
