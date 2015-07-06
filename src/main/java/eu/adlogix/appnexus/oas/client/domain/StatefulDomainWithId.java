package eu.adlogix.appnexus.oas.client.domain;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

@Getter
public abstract class StatefulDomainWithId extends StatefulDomain {

	private String id;

	public StatefulDomainWithId() {
		addPersistentAttribute("id");
	}

	public void setId(String id) {
		if (!StringUtils.isEmpty(this.id)) {
			throw new IllegalStateException("Id cannot be modified");
		}
		this.id = id;
	}

}
