package eu.adlogix.appnexus.oas.client.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "method", "country" })
@XmlRootElement(name = "BillingInformation")
public class BillingInformation {

    @XmlElement(name = "Method")
    protected Method method;
    @XmlElement(name = "Country")
    protected Country country;


}
