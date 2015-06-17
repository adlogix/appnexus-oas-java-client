package eu.adlogix.appnexus.oas.client.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "code" })
@XmlRootElement(name = "Method")
public class Method {

    @XmlElement(name = "Code", required = true)
    protected Code code;

	public void setCode(Code code) {
		this.code = code;
	}

}
