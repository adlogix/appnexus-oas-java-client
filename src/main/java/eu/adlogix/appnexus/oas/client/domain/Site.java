package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a Site (Website)
 */
@AllArgsConstructor
@Data
public final class Site {

	private final String id;
	private final String name;

}
